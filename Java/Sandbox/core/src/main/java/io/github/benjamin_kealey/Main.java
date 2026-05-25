package io.github.benjamin_kealey;

import io.github.benjamin_kealey.PhysicalObjects.*;
import io.github.benjamin_kealey.Utility.*;
import io.github.benjamin_kealey.Interfaces.*;

import java.nio.channels.CancelledKeyException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;

import org.javatuples.Pair;
import org.javatuples.Tuple;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.InputMultiplexer;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTextButton;

//TODO: make side panel
//TODO: different shaped particles
//TODO: spinning/rotating/rolling

public class Main extends ApplicationAdapter {
    private HashMap<Cell, Set<worldObject>> p_data = new HashMap<>(); // this is the grid. The keys are the coordinates and the values are the particles
    private worldObject[] things = new worldObject[0];
    OrthographicCamera camera;
    Cell cell;
    Vector3 worldPos = new Vector3();
    ShapeRenderer p1s;
    Stage stage;
    InputAdapter clicky;
    float mouseX;
    World world;
    boolean place = false;

    InputMultiplexer multiplexer = new InputMultiplexer();

    // === SIDE PANEL STUFF === //
    private Table sidePanel;
    private float hiddenX_sidePanel;
    private float shownX_sidePanel;
    private boolean expanded = false;
    private final float slideSpeed = 10f;
    private final float panelWidth = 300f;
    private final float triggerWidth = 20f;
    private TextField radiusField, massField, bouncinessField, termVelField;
    private VisTextButton newParticleButton;
    private TextField.TextFieldFilter numberFilter = new TextField.TextFieldFilter() {
        @Override
        public boolean acceptChar(TextField textField, char c) {
            if (textField.getText().contains(".") && c == '.') {
                return false;
            }
            if (textField.getText().isEmpty() && c == '.') {
                return false;
            }
            return (c >= '0' && c <= '9') || c == '.';
        }
    };

    int MAX_PARTICLE_RADIUS = 50;
    int CELL_SIZE = MAX_PARTICLE_RADIUS * 2;

    @Override
    public void create() {
        cell = new Cell(-1, -1);
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        //batch = new SpriteBatch();
        //image = new Texture("libgdx.png");
        camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.position.set(screenWidth/2f, screenHeight/2f, 0);
        camera.update();
        p1s = new ShapeRenderer();
        world = new World(screenWidth, screenHeight, 9.81f);

        VisUI.load();
        stage = new Stage(new ScreenViewport());
        clicky = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (place) {
                    worldPos = camera.unproject(new Vector3(screenX, screenY, 0));
                    ndParticle p = createParticle(worldPos.x, worldPos.y);
                    cell.set((int)Math.floor(p.x/CELL_SIZE), (int)Math.floor(p.y/CELL_SIZE));
                    for (Cell t: adjacentCells(cell)){
                        p_data.computeIfAbsent(new Cell(cell.getX()+t.getX(), cell.getY()+t.getY()), k -> new HashSet<>()).add(p);
                    }
                    //System.out.println(p_data.keySet());
                    things = java.util.Arrays.copyOf(things, things.length + 1);
                    things[things.length - 1] = p;
                    place = false;
                }
                return false;
            }
        };

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(clicky);

        Gdx.input.setInputProcessor(multiplexer);



        shownX_sidePanel = screenWidth - panelWidth;
        hiddenX_sidePanel = screenWidth;

        sidePanel = new Table(VisUI.getSkin());
        radiusField = new TextField(null, VisUI.getSkin());
        massField = new TextField(null, VisUI.getSkin());
        bouncinessField = new TextField(null, VisUI.getSkin());
        termVelField = new TextField(null, VisUI.getSkin());
        newParticleButton = new VisTextButton("New Particle");
        radiusField.setMessageText("Radius");
        radiusField.setTextFieldFilter(numberFilter);
        massField.setMessageText("Mass");
        massField.setTextFieldFilter(numberFilter);
        bouncinessField.setMessageText("Bounciness");
        bouncinessField.setTextFieldFilter(numberFilter);
        termVelField.setMessageText("Terminal Velocity");
        termVelField.setTextFieldFilter(numberFilter);

        sidePanel.setSize(panelWidth, screenHeight);
        sidePanel.setPosition(hiddenX_sidePanel, 0);
        sidePanel.setBackground("window");
        sidePanel.add(radiusField).pad(20);
        sidePanel.row();
        sidePanel.add(massField).pad(20);
        sidePanel.row();
        sidePanel.add(bouncinessField).pad(20);
        sidePanel.row();
        sidePanel.add(termVelField).pad(20);
        sidePanel.row();
        sidePanel.add(newParticleButton).pad(20);
        stage.addActor(sidePanel);

        newParticleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                place = true;
                expanded = false;
            }
        });
    }
    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        updatePanel();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        p1s.begin(ShapeRenderer.ShapeType.Filled);
        p1s.setColor(1,1,0,1);
        
            for (worldObject ob : things){
                cell.set((int)Math.floor(ob.x/CELL_SIZE), (int)Math.floor(ob.y/CELL_SIZE));
                //((ndParticle)ob).print();
                if (ob instanceof Updateable){
                    //System.out.println("test");
                    ((Updateable) ob).update(Gdx.graphics.getDeltaTime(), world);
                }
                if (ob instanceof Collidable){
                    //System.out.println("test");
                    for (Cell adjCell: adjacentCells(cell)){
                        //System.out.println(adjCell);
                        //System.out.println(p_data.containsKey(adjCell));
                        p_data.getOrDefault(adjCell, Collections.emptySet()).forEach(other -> {
                                //System.out.println(ob);
                                //System.out.println(other);
                                //System.out.println(other == ob);
                                //System.out.println(other instanceof Collidable);
                                if (other instanceof Collidable && other != ob && checkCollision((physicalObject) ob, (physicalObject) other)){
                                    //System.out.println("test");
                                    collision((physicalObject)ob, (physicalObject) other);
                                }
                        });
                    }
                }
                if (ob instanceof Renderable){
                    ((Renderable) ob).render(p1s);
                }
                /*
                if (!p.fixed) {
                    p.x += (p.vel_vec.x) * Gdx.graphics.getDeltaTime();
                    if (p.x + p.radius > Gdx.graphics.getWidth()) {
                        p.x = Gdx.graphics.getWidth() - p.radius;
                        p.vel_vec.x *= -p.bounciness;
                    } else if (p.x - p.radius < 0) {
                        p.x = p.radius;
                        p.vel_vec.x *= -p.bounciness;
                    }
                    p.y += (p.vel_vec.y) * Gdx.graphics.getDeltaTime();
                    if (p.y + p.radius > Gdx.graphics.getHeight()) {
                        p.y = Gdx.graphics.getHeight() - p.radius;
                        p.vel_vec.y *= -p.bounciness;
                    } else if (p.y - p.radius < 0) {
                        p.y = p.radius;
                        p.vel_vec.y *= -p.bounciness;
                    }
                    p.vel_vec.x += (p.acc_vec.x) * Gdx.graphics.getDeltaTime();
                    p.vel_vec.y += (p.acc_vec.y) * Gdx.graphics.getDeltaTime();
                    if (p.vel_vec.y < -p.terminalVelocity) {
                        p.vel_vec.y = (float)(-p.terminalVelocity);
                    }
                }*/
            
        }
        p_data.clear();
        for (worldObject ob : things){
            cell.set((int)Math.floor(ob.x/CELL_SIZE), (int)Math.floor(ob.y/CELL_SIZE));
            p_data.computeIfAbsent(new Cell(cell.getX(), cell.getY()), k -> new HashSet<>()).add(ob);
        }

        p1s.end();            
    }
    
    public void collision(physicalObject o1, physicalObject o2) {
        Vector2 relativeVelocity = new Vector2(o2.velVec.x - o1.velVec.x, o2.velVec.y - o1.velVec.y);
        float e = (float) Math.min(o1.bounciness, o2.bounciness);
        Vector2 collision_normal = new Vector2((float)(o2.x - o1.x), (float)(o2.y - o1.y)).nor();
        Vector2 collision_tangent = new Vector2(-collision_normal.y, collision_normal.x);
        if (relativeVelocity.dot(collision_normal) >= 0) {
            return;
        }
        separate(o1, o2, collision_normal);

        float p1v_ni = o1.velVec.dot(collision_normal);
        float p2v_ni = o2.velVec.dot(collision_normal);

        float p1v_ti = o1.velVec.dot(collision_tangent);
        float p2v_ti = o2.velVec.dot(collision_tangent);

        float p1v_nf = (float) ((p1v_ni * (o2.mass - e * o2.mass) + (1 + e) * o2.mass * p2v_ni) / (o1.mass + o2.mass));
        float p2v_nf = (float) ((p2v_ni * (o1.mass - e * o1.mass) + (1 + e) * o1.mass * p1v_ni) / (o1.mass + o2.mass));

        float p1v_tf = p1v_ti;
        float p2v_tf = p2v_ti;

        Vector2 p1_final = collision_normal.cpy().scl(p1v_nf).add(collision_tangent.cpy().scl(p1v_tf));
        Vector2 p2_final = collision_normal.cpy().scl(p2v_nf).add(collision_tangent.cpy().scl(p2v_tf));

        o1.velVec.set(p1_final);
        o2.velVec.set(p2_final);
    }

    public boolean checkCollision(physicalObject o1, physicalObject o2) {
        float distance = (float)Math.sqrt((o1.x - o2.x) * (o1.x - o2.x) + (o1.y - o2.y) * (o1.y - o2.y));
        //System.out.println(distance < (o1.radius + o2.radius));
        return distance < (o1.radius + o2.radius);
    }

    public void separate(physicalObject o1, physicalObject o2, Vector2 collisionNormal) {
        double overlap = (o1.radius + o2.radius) - (Math.sqrt((o2.x - o1.x)*(o2.x - o1.x) + (o2.y - o1.y)*(o2.y - o1.y)));
        if (overlap < 0) {
            return;
        }
        double correction = overlap / 2;
        o1.x -= correction * collisionNormal.x;
        o1.y -= correction * collisionNormal.y;
        o2.x += correction * collisionNormal.x;
        o2.y += correction * collisionNormal.y;
    }

    public ArrayList<Cell> adjacentCells(Cell cell) {
        ArrayList<Cell> adjacent = new ArrayList<>();
        for (int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++){
                adjacent.add(new Cell((int)cell.getX() + i, (int)cell.getY() + j));
            }
        }
        return adjacent;
    }

    public void updatePanel() {
        float mouseX = Gdx.input.getX();
        float screenWidth = Gdx.graphics.getWidth();
        if (mouseX > screenWidth - triggerWidth) {
            expanded = true;
        } else if (mouseX < screenWidth - panelWidth - 50f) {
            expanded = false;
        }

        float targetX = expanded ? shownX_sidePanel : hiddenX_sidePanel;
        float currentX = sidePanel.getX();
        float newX = currentX + (targetX - currentX) * Gdx.graphics.getDeltaTime() * slideSpeed;
        sidePanel.setX(newX);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        shownX_sidePanel = width - panelWidth;
        hiddenX_sidePanel = width;

        sidePanel.setHeight(height);

        sidePanel.setX(expanded ? shownX_sidePanel : hiddenX_sidePanel);
    }

    public ndParticle createParticle(float x, float y) {
        return new ndParticle(
            x,
            y,
            radiusField.getText().isEmpty() ? 50f : Float.parseFloat(radiusField.getText()),
            false,
            massField.getText().isEmpty() ? 100f : Float.parseFloat(massField.getText()),
            bouncinessField.getText().isEmpty() ? 0.9f : Float.parseFloat(bouncinessField.getText()),
            termVelField.getText().isEmpty() ? 999f : Float.parseFloat(termVelField.getText())
        );
    }

    @Override
    public void dispose() {
        p1s.dispose();
        stage.dispose();
        VisUI.dispose();
    }
}