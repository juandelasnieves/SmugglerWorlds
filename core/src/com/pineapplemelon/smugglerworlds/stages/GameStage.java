package com.pineapplemelon.smugglerworlds.stages;

/**
 * Created by juandelasnieves on 3/1/16.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.pineapplemelon.smugglerworlds.actor.Background;
import com.pineapplemelon.smugglerworlds.actor.Disparo;
import com.pineapplemelon.smugglerworlds.actor.Enemy;
import com.pineapplemelon.smugglerworlds.actor.Ground;
import com.pineapplemelon.smugglerworlds.actor.Runner;
import com.pineapplemelon.smugglerworlds.box2d.UserData;
import com.pineapplemelon.smugglerworlds.enums.EnemyType;
import com.pineapplemelon.smugglerworlds.enums.UserDataType;
import com.pineapplemelon.smugglerworlds.utils.BodyUtils;
import com.pineapplemelon.smugglerworlds.utils.Constants;
import com.pineapplemelon.smugglerworlds.utils.WorldUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Iterator;


public class GameStage extends Stage implements ContactListener {

    // This will be our viewport measurements while working with the debug renderer
    private static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    private static final int VIEWPORT_HEIGHT = Constants.APP_HEIGHT;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;
    private World world;
    private Ground ground;
    private Runner runner;
    private Disparo disparo;
    private Rectangle screenRightSide;
    private Rectangle screenLeftSide;
    private Vector3 touchPoint;
    private Boolean create = false;
    Array<Body> deleteList;

    public GameStage() {
        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT,
                new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));

        deleteList = new Array<Body>();
        setUpWorld();
        setupCamera();
        setupTouchControlAreas();
    }

    private void setupCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        sweepDeadBodies();

        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);

        for (Body body : bodies) {
            update(body);
        }

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        //TODO: Implement interpolation

    }

    /*@Override
    public void draw() {
        super.draw();
        renderer.render(world, camera.combined);
    }*/

    public void sweepDeadBodies() {
        for (int i=0; i<deleteList.size;i++ ) {
            if (deleteList.get(i) != null) {
                    world.destroyBody(deleteList.get(i));
                    deleteList.get(i).setUserData(null);
                deleteList.removeIndex(i);
                }
            }
        }

    private void setUpWorld() {
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        setUpBackground();
        setUpGround();
        setUpRunner();
        createEnemy();
    }

    private void setUpBackground() {
        addActor(new Background());
    }

    private void setUpGround() {
        ground = new Ground(WorldUtils.createGround(world));
        addActor(ground);
    }

    private void setUpRunner() {
        runner = new Runner(WorldUtils.createRunner(world));
        addActor(runner);
    }

    //basurita
    private void disparar(){
        disparo = new Disparo(WorldUtils.createDisparo(world));
        addActor(disparo);
    }

    private void setupTouchControlAreas() {
        touchPoint = new Vector3();
        screenLeftSide = new Rectangle(0, 0, getCamera().viewportWidth / 2, getCamera().viewportHeight);
        screenRightSide = new Rectangle(getCamera().viewportWidth / 2, 0, getCamera().viewportWidth / 2,
                getCamera().viewportHeight);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        // Need to get the actual coordinates
        translateScreenToWorldCoordinates(x, y);

        if (rightSideTouched(touchPoint.x, touchPoint.y)) {
            runner.jump();
        } else if (leftSideTouched(touchPoint.x, touchPoint.y)) {
            if(!runner.isHit()){
                disparar();
                runner.dodge();
            }
        }

        return super.touchDown(x, y, pointer, button);
    }

    private boolean rightSideTouched(float x, float y) {
        return screenRightSide.contains(x, y);
    }

    /**
     * Helper function to get the actual coordinates in my world
     * @param x
     * @param y
     */
    private void translateScreenToWorldCoordinates(int x, int y) {
        getCamera().unproject(touchPoint.set(x, y, 0));
    }

    @Override
    public void beginContact(Contact contact) {

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if ((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsEnemy(b)) ||
                (BodyUtils.bodyIsEnemy(a) && BodyUtils.bodyIsRunner(b))) {
            runner.hit();
        } else if ((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsGround(b)) ||
                (BodyUtils.bodyIsGround(a) && BodyUtils.bodyIsRunner(b))) {
            runner.landed();
        }else if((BodyUtils.bodyIsDisparo(a) && BodyUtils.bodyIsEnemy(b)) ||
                BodyUtils.bodyIsDisparo(b) && BodyUtils.bodyIsEnemy(a)){
            deleteList.add(a);
            deleteList.add(b);
            create = true;
        }

    }

    public void removeBodySafely(Body body) {
        //to prevent some obscure c assertion that happened randomly once in a blue moon
        final Array<JointEdge> list = body.getJointList();
        while (list.size > 0) {
            world.destroyJoint(list.get(0).joint);
        }
        // actual remove
        world.destroyBody(body);
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private boolean leftSideTouched(float x, float y) {
        return screenLeftSide.contains(x, y);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (runner.isDodging()) {
            runner.stopDodge();
        }

        return super.touchUp(screenX, screenY, pointer, button);
    }

    private void update(Body body) {
        if (!BodyUtils.bodyInBounds(body) || create) {
            if(!create) world.destroyBody(body);
            if ((BodyUtils.bodyIsEnemy(body) && !runner.isHit())|| create) {
                createEnemy();
                create = false;
            }
        }
    }

    private void createEnemy() {
        Enemy enemy = new Enemy(WorldUtils.createEnemy(world));
        addActor(enemy);
    }


}