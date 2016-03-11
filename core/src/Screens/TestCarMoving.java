/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.RacingGame;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.InputController;
import com.mygdx.game.RacingGame;

/**
 *
 * @author ROSY
 */
public class TestCarMoving implements Screen {

  private RacingGame game;
private World world;
private Box2DDebugRenderer debugRenderer;
private OrthographicCamera camera;
private float speed = 500;
private float PixelperMeter = 32;

   public TestCarMoving(RacingGame game) {
        this.game = game;
    }
    
   
    @Override
    public void show() {
        world = new World(new Vector2(0,0) , true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth() / PixelperMeter, Gdx.graphics.getHeight() / PixelperMeter);
        
        Gdx.input.setInputProcessor(new InputController() {
            @Override
            public boolean keyDown(int keycode) {
               switch(keycode) {
                   case Keys.ESCAPE: 
                   Gdx.app.exit();
                   break;
                    
                   case Keys.W : //Something
                       movement.y  = speed;
                   break;
                       
                   case Keys.A : //Something
                       movement.x = -speed;
                   break;
                   
                   case Keys.S : //Something
                       movement.y = -speed;
                   break;
                                    
                   case Keys.D : //Something
                       movement.x = speed;
                    
                }
                return true;
            }
            
            @Override
            public boolean keyUp(int keycode) {
                switch(keycode) {
                    
                   case Keys.W : 
                   case Keys.S : 
                       movement.y  = 0;
                   break;
                       
                   case Keys.A : 
                   case Keys.D : 
                       movement.x = 0;
                       
                }
                return true;
            }
            
        });
        
        
        
        
        //Body Def
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 1);
        
        //ball shape
        CircleShape ballShape = new CircleShape();
        ballShape.setPosition(new Vector2(0, 1));
        ballShape.setRadius(0.25f);
        
        //Fixture Def
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShape;
        fixtureDef.density = 100f;
        fixtureDef.friction = 0.25f;         //Between 0 and 1;    1 = Max friction (100% friction);  0 = No friction (0% friction)
        fixtureDef.restitution = 1f;
        
        world.createBody(bodyDef).createFixture(fixtureDef);
        
        ballShape.dispose();
        
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
        
        //BOX
        //Body definition
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(2.25f, 10);
        
        
        //box shape
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(.5f , 1);
        
        //Fisture definition
        fixtureDef.shape = boxShape;
        fixtureDef.friction = .75f;
        fixtureDef.restitution = .1f;
        fixtureDef.density = 5;
        
        box = world.createBody(bodyDef);
        box.createFixture(fixtureDef);
        
        boxShape.dispose();
        
        box.applyAngularImpulse(250, true);
        
    }

    private final float TIMESTEP = 1/60f;
    private final int VELOCITYETIRATIONS = 8, POSITIONITERATIONS = 3;
    private Body box;
    private Vector2 movement = new Vector2();
    
    
    
@Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        world.step(TIMESTEP, VELOCITYETIRATIONS, POSITIONITERATIONS);
        box.applyForceToCenter(movement, true);
        
        camera.position.set(box.getPosition().x,box.getPosition().y , 0);
        camera.update();
        
        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / PixelperMeter;
        camera.viewportHeight = height / PixelperMeter;
        camera.update();
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
    }

}
