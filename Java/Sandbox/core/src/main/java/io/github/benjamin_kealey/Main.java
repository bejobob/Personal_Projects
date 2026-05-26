package io.github.benjamin_kealey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTextButton;

import io.github.benjamin_kealey.EffectsObjects.*;
import io.github.benjamin_kealey.Interfaces.*;
import io.github.benjamin_kealey.PhysicalObjects.*;
import io.github.benjamin_kealey.Utility.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

//TODO: make side panel
//TODO: different shaped particles
//TODO: spinning/rotating/rolling

public class Main extends ApplicationAdapter {
    private HashMap<Cell, Set<worldObject>> p_data = new HashMap<>(); // this is the grid. The keys are the coordinates and the values are the particles
    private ArrayList<worldObject> things = new ArrayList<>(); // this contains all the objects in the world so that I don't lose reference to them when I clear the grid
    private ArrayList<worldObject> thingsToAdd = new ArrayList<>();
    OrthographicCamera camera; // camera
    Cell cell; // this is a cell. I use it a few times. 
    Vector3 worldPos = new Vector3(); // used to convert coordinates to the correct places
    ShapeRenderer p1s; // thing.
    Stage stage; // other thing.
    InputAdapter clicky; // for when I make mouse presses
    float mouseX; // the x coordinate of the cursor. Can't remember why I don't need a mouseY, but I don't!
    World world; // this is the world that the simulation runs in. It just contains all the data about the world. I use it in some places.
    boolean place = false; // for if I want to place a particle.

    InputMultiplexer multiplexer = new InputMultiplexer(); // to juggle with clicky and stage.

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
        public boolean acceptChar(TextField textField, char c) { // makes sure only numbers are typed into number fields.
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
        cell = new Cell(-1, -1); // default value for the cell. this position doesn't actually exits.
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight(); // height and width of the screen.

        camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.position.set(screenWidth/2f, screenHeight/2f, 0);
        camera.update();
        p1s = new ShapeRenderer();
        world = new World(screenWidth, screenHeight, 9.81f, things, thingsToAdd);

        VisUI.load();
        stage = new Stage(new ScreenViewport());
        clicky = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (place) { // place determines if we want to place a particle when we click
                    worldPos = camera.unproject(new Vector3(screenX, screenY, 0)); // readjust the coordinate system 
                    ndParticle p = createParticle(worldPos.x, worldPos.y); // we create a particle
                    addToCells(p);
                    things.add(p); // we extend the list of everything and add the new thing to it
                    place = false; // uncheck place
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
        p1s.begin(ShapeRenderer.ShapeType.Filled);
        p1s.setColor(1,0,0,1);
        
            for (worldObject ob : things){
                cell.set((int)Math.floor(ob.x/CELL_SIZE), (int)Math.floor(ob.y/CELL_SIZE));
                if (ob instanceof Updateable && (!expanded && !place)){
                    ((Updateable) ob).update(Gdx.graphics.getDeltaTime(), world);
                }
                if (ob instanceof Collidable && (!expanded && !place)){
                    for (Cell adjCell: adjacentCells(cell)){

                        p_data.getOrDefault(adjCell, Collections.emptySet()).forEach(other -> {

                            if (other instanceof Collidable && other != ob && checkCollision((physicalObject) ob, (physicalObject) other)){
                                collision((physicalObject)ob, (physicalObject) other);
                            }
                        });
                    }
                }
                if (ob instanceof Renderable){
                    ((Renderable) ob).render(p1s);
                }
        }
        thingsToAdd.forEach(a -> {
            things.add(a);
        });
        thingsToAdd.clear();
        p_data.clear();
        for (worldObject ob : things){
            if (ob instanceof physicalObject)
                addToCells((physicalObject)ob);
            }
        p1s.end();            
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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

    public void addToCells(physicalObject p){
        int left   = (int)Math.floor((p.x - p.radius) / CELL_SIZE);
        int right  = (int)Math.floor((p.x + p.radius) / CELL_SIZE);
        int bottom = (int)Math.floor((p.y - p.radius) / CELL_SIZE);
        int top    = (int)Math.floor((p.y + p.radius) / CELL_SIZE);
        for (int cx = left; cx <= right; cx++) {
            for (int cy = bottom; cy <= top; cy++) {  // we iterate over every adjacent cell to the center cell
                p_data.computeIfAbsent(new Cell(cx, cy), k -> new HashSet<>()).add(p);
            }   
        }
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