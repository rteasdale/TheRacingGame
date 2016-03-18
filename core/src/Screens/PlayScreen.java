/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

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
import com.mygdx.game.InputController;
import com.mygdx.game.RacingGame;

/**
 *
 * @author JONATHAN
 */
public class PlayScreen implements Screen {

  private RacingGame game;
private World world;
private Box2DDebugRenderer debugRenderer;
private SpriteBatch batch;
private OrthographicCamera camera;
private float speed = 200;
private final float PIXELS_TO_METERS = 32;
private Sprite boxSprite;


   public PlayScreen(RacingGame game) {
        this.game = game;
    }
    
   
    @Override
    public void show() {
        world = new World(new Vector2(0,0) , true);
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        
        
        camera = new OrthographicCamera(Gdx.graphics.getWidth() / PIXELS_TO_METERS, Gdx.graphics.getHeight() / PIXELS_TO_METERS);
        
        Gdx.input.setInputProcessor(new InputController() {
            @Override
            public boolean keyDown(int keycode) {
               switch(keycode) {
                   case Keys.ESCAPE: 
                   Gdx.app.exit();
                   break;
                    
                   case Keys.W : //Something 
                   case Keys.UP : //Something
                       movement.y  = speed;
                   break;
                       
                   case Keys.A : //Something
                   case Keys.LEFT : //Something
                       movement.x = -speed;
                   break;
                   
                   case Keys.DOWN : //Something
                   case Keys.S : //Something
                       movement.y = -speed;
                   break;
                                    
                    case Keys.D :   
                    case Keys.RIGHT : //Something
                       movement.x = speed;
                    
                }
                return true;
            }
            
            @Override
            public boolean scrolled(int amount) {
                camera.zoom += amount / 25f;
                return true;
            }
            
            @Override
            public boolean keyUp(int keycode) {
                switch(keycode) {
                    
                   case Keys.W : 
                   case Keys.UP:
                   case Keys.S : 
                   case Keys.DOWN:
                       movement.y  = 0;
                   break;

                       
                   case Keys.A : 
                   case Keys.LEFT:
                   case Keys.RIGHT:
                   case Keys.D : 
                       movement.x = 0;
                       
                }
                return true;
            }
            
        });
        
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
        fixtureDef.friction = 0.25f;         //Between 0 and 1;    1 = Max friction (100% friction);  0 = No friction (0% friction)
        fixtureDef.restitution = 1f;
        
        world.createBody(bodyDef).createFixture(fixtureDef);
        
        ballShape.dispose();
        
       
        
        //BOX
        //Body definition
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(2.25f, 10);
        
        
        //box shape
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(.5f , 1);
        
        //Fixture definition
        fixtureDef.shape = boxShape;
        fixtureDef.friction = .75f;
        fixtureDef.restitution = .1f;
        fixtureDef.density = 5;
        
        box = world.createBody(bodyDef);
        box.createFixture(fixtureDef);
        
        boxSprite = new Sprite(new Texture("lamborghini/lamborghini_white.png"));
        boxSprite.setSize(0.5f * 2, 1  * 2);
        boxSprite.setOrigin(boxSprite.getWidth() / 2, boxSprite.getHeight() / 2);
        box.setUserData(boxSprite);
        
        boxShape.dispose();
        
         
        //Ground
        //Body definition
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);
        
        //ground shape
        ChainShape groundShape  = new ChainShape();  //POLYLINE
        groundShape.createChain(new Vector2[] {new Vector2(-500, 0), new Vector2(500,0)});
        
        
        
        //fixture definition
        fixtureDef.shape = groundShape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;
        
        world.createBody(bodyDef).createFixture(fixtureDef);
        
        
        groundShape.dispose();
        
        
        
    }

    private final float TIMESTEP = 1/60f;
    private final int VELOCITYETIRATIONS = 8, POSITIONITERATIONS = 3;
    private Body box;
    private Vector2 movement = new Vector2();
    
    private Array<Body> tmpBodies = new Array<Body>();
    
    
@Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        world.step(TIMESTEP, VELOCITYETIRATIONS, POSITIONITERATIONS);
        box.applyForceToCenter(movement, true);
        
        //Vector2 impulse = box.getMass().(-body.getLateralVelocity());
        //box.setLinearDamping(0.75f);
        //box.setAngularDamping(0.99f);
        
        camera.position.set(box.getPosition().x,box.getPosition().y , 0);
        camera.update();
        
        //To create Sprites for cars with SpriteBatch
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
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
