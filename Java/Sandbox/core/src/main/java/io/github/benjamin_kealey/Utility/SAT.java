package io.github.benjamin_kealey.Utility;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class SAT {

    public static class Projection {
        public float min;
        public float max;

        public Projection(float min, float max) {
            this.min = min;
            this.max = max;
        }
    }

    public static CollisionManifold hasCollided(Vector2[] poly1, Vector2[] poly2, Double maxDist) {
        // Do an optimization check using the maxDist
        if (maxDist != null) {
            if (Math.pow(poly1[1].x - poly2[0].x, 2) + Math.pow(poly1[1].y - poly2[0].y, 2) <= Math.pow(maxDist, 2)) {
                // Collision is possible so run SAT on the polys
                return runSAT(poly1, poly2);
            } else {
                return new CollisionManifold(false, null, 0, null);
            }
        } else {
            // No maxDist so run SAT on the polys
            return runSAT(poly1, poly2);
        }
    }

    private static CollisionManifold runSAT(Vector2[] poly1, Vector2[] poly2) {
        // Implements the actual SAT algorithm
        float minOverlap = Float.POSITIVE_INFINITY;
        Vector2 smallestAxis = null;
        ArrayList<Vector2> edges = polyToEdges(poly1);
        edges.addAll(polyToEdges(poly2));
        Vector2[] axes = new Vector2[edges.size()];
        for (int i = 0; i < edges.size(); i++) {
            axes[i] = new Vector2(-edges.get(i).y, edges.get(i).x);
        }

        for (Vector2 axis : axes) {
            axis = axis.cpy().nor(); //normalize the axis

            Projection p1 = project(poly1, axis);
            Projection p2 = project(poly2, axis);

            float overlap = Math.min(p1.max, p2.max) - Math.max(p1.min, p2.min);
            
            if (overlap <= 0) {
                return new CollisionManifold(false, null, 0, null);
            }
            if (overlap < minOverlap) {
                minOverlap = overlap;
                smallestAxis = axis;
            }
            //if (!overlap(project(poly1, axis), project(poly2, axis))) {
                // The polys don't overlap on this axis so they can't be touching
              //  return new CollisionManifold(false, null, 0, null);
            //}
        }

        // The polys overlap on all axes so they must be touching
        return new CollisionManifold(true, );
    }

    /**
     * Returns a vector going from point1 to point2
     */
    private static Vector2 edgeVector(Vector2 point1, Vector2 point2) {
        return new Vector2(point2.x - point1.x, point2.y - point1.y);
    }

    /**
     * Returns an array of the edges of the poly as vectors
     */
    private static ArrayList<Vector2> polyToEdges(Vector2[] poly) {
        ArrayList<Vector2> vectors = new ArrayList<>(poly.length);
        for (int i = 0; i < poly.length; i++) {
            vectors.add(edgeVector(poly[i], poly[(i + 1) % poly.length]));
        }
        return vectors;
    }

    /**
     * Returns a vector showing how much of the poly lies along the axis
     */
    private static Projection project(Vector2[] poly, Vector2 axis) {
        float min = Float.POSITIVE_INFINITY;
        float max = Float.NEGATIVE_INFINITY;

        for (Vector2 v : poly) {
            float p = v.dot(axis);

            if (p < min) min = p;
            if (p > max) max = p;
        }

        return new Projection(min, max);
    }

    /**
     * Returns a boolean indicating if the two projections overlap
     */
    private static boolean overlap(Projection projection1, Projection projection2) {
        return projection1.min <= projection2.max &&
                projection2.min <= projection1.max;
    }
}