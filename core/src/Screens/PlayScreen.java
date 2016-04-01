/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Scenes.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.InputController;
import com.mygdx.game.RacingGame;

/**
 *
 * @author JONATHAN
 */
public class PlayScreen implements Screen {

  private RacingGame game;
  private Texture bg;
  
private World world;
private Box2DDebugRenderer debugRenderer;
private final SpriteBatch batch;
private OrthographicCamera camera;
private float speed = 200;
private final float PIXELS_TO_METERS = 32;
private Sprite boxSprite;
private Viewport port;

private Hud hud;

private final int LEFT = -1;
private final int VSTOP = 0;
private final int RIGHT = 1;

private final int DOWN = -1;
private final int HSTOP = 0;
private final int UP = 1;

private int currentVState = VSTOP;
private int currentHState = HSTOP;

private float maxForwardSpeed = 300;
private float maxBackwardSpeed = -20;
private float maxDriveForce = 150;
private int PosTorque = 30;
private int NegTorque = -30;

private Vector2 movement;

   public PlayScreen(RacingGame game) {
        this.game = game;
        batch = new SpriteBatch();
        hud = new Hud(batch);
        camera = new OrthographicCamera();
        
    }
    
   
    @Override
    public void show() {
        bg = new Texture(Gdx.files.internal("maps/map2.png"));
        
        world = new World(new Vector2(0,0) , true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth() / PIXELS_TO_METERS, Gdx.graphics.getHeight() / PIXELS_TO_METERS);
        
        
        //CONTROLS
        
        
        Gdx.input.setInputProcessor(new InputController() {
            @Override
            public boolean keyDown(int keycode) {
               switch(keycode) {
                   case Keys.ESCAPE: 
                   Gdx.app.exit();
                   break;
                    
                   case Keys.W : //Something 
                   case Keys.UP : //Something
                       currentVState = UP;                       
                   break;
                       
                   case Keys.A : //Something
                   case Keys.LEFT : //Something
                      currentHState = LEFT;
                   break;
                   
                   case Keys.DOWN : //Something
                   case Keys.S : //Something
                       currentVState = DOWN;
                   break;
                                    
                    case Keys.D :   
                    case Keys.RIGHT : //Something
                        currentHState = RIGHT;
                        
                    
                }
                return true;
            }
            
            // CONTROLLING ZOOM OF CAMERA THROUGH SCROLL
            
            @Override
            public boolean scrolled(int amount) {
                camera.zoom += amount / 25f;
                return true;
            }
            
            //CONTROLS WHEN YOU LIFT YOUR FINGER FROM THE KEY
            
            @Override
            public boolean keyUp(int keycode) {
                switch(keycode) {
                    
                   case Keys.W : 
                   case Keys.UP:
                   case Keys.S : 
                   case Keys.DOWN:
                       currentVState = VSTOP;
                   break;

                       
                   case Keys.A : 
                   case Keys.LEFT:
                   case Keys.RIGHT:
                   case Keys.D : 
                       currentHState = HSTOP;
                       
                }
                return true;
            }
            
        });
        
        //MAKING BOX2D BOX
        
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        
        //Body Def
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 1);
       
        //BALL shape
        CircleShape ballShape = new CircleShape();
        ballShape.setPosition(new Vector2(0, 1));
        ballShape.setRadius(0.25f);
        
        //Fixture Def
        fixtureDef.shape = ballShape;
        fixtureDef.density = 5f;
        fixtureDef.friction = 0.25f; //Between 0 and 1;    1 = Max friction (100% friction);  0 = No friction (0% friction)
        fixtureDef.restitution = 1f;
        
        world.createBody(bodyDef).createFixture(fixtureDef);
        
        ballShape.dispose();
        
        //BOX
        //Body definition
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(2.25f, 10);
        
        
        //box shape
        PolygonShape polyShape = new PolygonShape();
        
        Vector2[] vertices = new Vector2[16];
        vertices[0].set(0.46f, 2);
        vertices[1].set(0.81f, 1.92f);
        vertices[2].set(0.90f, 1.83f);
        vertices[3].set(0.90f, 1.5f);
        vertices[4].set(1.00f, 1.46f);
        vertices[5].set(0.90f, 1.33f);
        vertices[6].set(0.90f, 0.30f);
        vertices[7].set(0.80f, 0.06f);
        vertices[8].set(0.50f,0.02f );
        vertices[9].set(0.13f, 0.10f);
        vertices[10].set(0.08f, 1.30f);
        vertices[11].set(0,1.40f);
        vertices[12].set(0.08f, 1.5f);
        vertices[13].set(0.08f, 1.83f);
        vertices[14].set(0.17f, 1.92f);
        vertices[15].set(0.46f, 2);
        
        polyShape.set(vertices);
        
        //Fixture definition
        fixtureDef.shape = polyShape;
        fixtureDef.friction = .75f;
        fixtureDef.restitution = .1f;
        fixtureDef.density = 5;
        
        box = world.createBody(bodyDef);
        box.createFixture(fixtureDef);
        
        boxSprite = new Sprite(new Texture("lamborghini/lamborghini_white.png"));
        boxSprite.setSize(0.5f * 2, 1  * 2);
        boxSprite.setOrigin(boxSprite.getWidth() / 2, boxSprite.getHeight() / 2);
        box.setUserData(boxSprite);
        
        polyShape.dispose();
        
        
        //MAKING BOX2D BORDER LINES
         
        //Ground
        //Body definition
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);
        
        //ground shape
        ChainShape groundShape  = new ChainShape();  //POLYLINE
        groundShape.createChain(new Vector2[] {new Vector2(-500, 0), new Vector2(500,0)});
        
        ChainShape LWallShape = new ChainShape();
        LWallShape.createChain(new Vector2[]{ new Vector2(-500,0), new Vector2(-500, 500)});
        
        ChainShape RWallShape = new ChainShape();
        RWallShape.createChain(new Vector2[]{ new Vector2(500,0), new Vector2(500, 500)});
        
        ChainShape CeilingShape = new ChainShape();
        CeilingShape.createChain(new Vector2[]{ new Vector2(-500,500), new Vector2(500, 500)});
        
        
        //fixture definition
        fixtureDef.shape = groundShape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;
        
        world.createBody(bodyDef).createFixture(fixtureDef);
        
        fixtureDef.shape = LWallShape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;
        
        world.createBody(bodyDef).createFixture(fixtureDef);
        
        fixtureDef.shape = RWallShape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;
        
        world.createBody(bodyDef).createFixture(fixtureDef);
        
        fixtureDef.shape = CeilingShape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;
        
        world.createBody(bodyDef).createFixture(fixtureDef);
        
        LWallShape.dispose();
        RWallShape.dispose();
        CeilingShape.dispose();
        groundShape.dispose();
        
    }

    //TUTORIAL PART
    
    private final float TIMESTEP = 1/60f;
    private final int VELOCITYETIRATIONS = 8, POSITIONITERATIONS = 3;
    private Body box;
   //private Vector2 movement = new Vector2();
    
    private Array<Body> tmpBodies = new Array<Body>();
    
//    private float currentVelocity = getForwardVelocity().dot(box.getWorldVector(new Vector2(0,1)));
    
    public Vector2 getLateralVelocity() {
        Vector2 currentRightNormal = box.getWorldVector(new Vector2(1,0));
        return currentRightNormal.scl(currentRightNormal.dot(box.getLinearVelocity()));
    }
    
    public Vector2 getForwardVelocity(){
        Vector2 currentForwardNormal = box.getWorldVector(new Vector2(0,1));
        return currentForwardNormal.scl(currentForwardNormal.dot(box.getLinearVelocity()));
    }
    
    public void updateFriction() {
        float maxLateralImpulse = 2.5f;
        Vector2 impulse = getLateralVelocity().scl(-box.getMass());
        if(impulse.len() > maxLateralImpulse){
            impulse.x *= maxLateralImpulse / impulse.len();
            impulse.y *= maxLateralImpulse / impulse.len();
        }
        box.applyLinearImpulse(impulse, box.getWorldCenter(), true);
        box.applyAngularImpulse(0.1f * box.getInertia() * -box.getAngularVelocity(), true);
        
        Vector2 currentForwardNormal = getForwardVelocity(); 
        float currentForwardSpeed = currentForwardNormal.nor().len(); // Basically 1
        float dragForceMagnitude = -2 * currentForwardSpeed;
        box.applyForce((currentForwardNormal.scl(dragForceMagnitude)), box.getWorldCenter(), true);
    }
    
    public void updateDrive(int currentVState){
        
        float desiredSpeed = 0;
        switch(currentVState){
            case UP: desiredSpeed = maxForwardSpeed; break;
            case DOWN : desiredSpeed = maxBackwardSpeed; break;
            case VSTOP : return;
        }
        
        Vector2 currentForwardNormal = box.getWorldVector(new Vector2(0,1));
        
        System.out.print(currentForwardNormal.x  + " " + currentForwardNormal.y);
        
        float x = currentForwardNormal.x;
        float y = currentForwardNormal.y;
        
        float currentSpeed = getForwardVelocity().dot(currentForwardNormal);
        
        
        
        float force = 0;
        
        if(desiredSpeed > currentSpeed){
            force = maxDriveForce;
        }
        else if (desiredSpeed < currentSpeed){
            force = -maxDriveForce;
        }
        else{
            return;
        }
        

        
       // box.applyForce(currentForwardNormal.scl(force), box.getWorldCenter(), true);
        box.applyForce(new Vector2(x * force, y * force), box.getWorldCenter(), true);
    }
    
    public void updateTurn(int currentHState){
           float desiredTorque = 0;
        switch(currentHState){
            case LEFT: desiredTorque = PosTorque; break;
            case RIGHT: desiredTorque = NegTorque;break;
            default : ;
        }
        box.applyTorque(desiredTorque, true);
        
    }
    
    
    public void movements(int CurrentVState, int CurrentHState){

        if(CurrentVState == UP){
            movement.y  = speed;
        }
        
        if(CurrentVState == DOWN){
            movement.y = -speed;
        }
        
//        if(CurrentHState == LEFT){
//             movement.x = -speed;
//        }
//        
//        if(CurrentHState == RIGHT){
//            movement.x = speed;
//        }
        
        if(CurrentVState == VSTOP){
            movement.y = 0;
            
        }
        
//        if(CurrentHState == HSTOP){
//            movement.x = 0;
//        
//        }
        
        
        System.out.println("Vertical : " + CurrentVState + "\n Horizontal : " + CurrentHState);
        
        
        
        
        box.applyForceToCenter(movement, true);
        
    }

    
    
@Override
    public void render(float delta) {
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
   //    System.out.println("V State : " + currentVState);
     //   System.out.println("H State : " + currentHState);
        //movements(currentVState , currentHState); 
        updateFriction();
        updateDrive(currentVState);
        updateTurn(currentHState);
        System.out.println(box.getAngle());
        world.step(TIMESTEP, VELOCITYETIRATIONS, POSITIONITERATIONS);
       
        //Vector2 impulse = box.getMass().(-body.getLateralVelocity());
        box.setLinearDamping(0.50f);
        
        //box.setAngularDamping(0.99f);
        
        
        
        camera.position.set(box.getPosition().x,box.getPosition().y , 0);
        camera.update();
       
        
        //To create Sprites for cars with SpriteBatch
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bg, -500, 0);
        world.getBodies(tmpBodies);
        for(Body body : tmpBodies)
            if(body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth()/ 2, body.getPosition().y - sprite.getHeight()/2);
                sprite.setRotation(body.getAngle() * ( MathUtils.radiansToDegrees));
                
                sprite.draw(batch);
            }
       
        
        batch.end();
        
        debugRenderer.render(world, camera.combined);
        
        
        
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / PIXELS_TO_METERS;
        camera.viewportHeight = height / PIXELS_TO_METERS;
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        boxSprite.getTexture().dispose();
    }

}
