package Astar;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class distanceleft {
    public static double distance() {
        double x = 0;
        double y = 0;
        double[] position = {x, y};
        double distanceleft = sqrt(pow((10 - position[0]), 2) + pow((10 - position[1]), 2));
        return distanceleft;
    }
}
