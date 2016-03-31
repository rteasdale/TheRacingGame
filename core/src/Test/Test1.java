/**
 * ApplicationAdapter
 */

package Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Car;
import com.mygdx.game.InputController;
import com.mygdx.game.RacingGame;

public class Test1 implements Screen {
    private RacingGame game;
    private Box2DDebugRenderer debugRenderer;
    private SpriteBatch batch;
    private Sprite sprite;
    private World world;
    private Texture bg; 
    private OrthographicCamera camera;
    private Car car;
    private Body body;
    private Fixture fixture;
    private PolygonShape shape;
    
    private int controlState;
    
    private final int LEFT = 1;
    private final int VSTOP = 2;
    private final int RIGHT = 4;
    private final int UP = 8;

    private final int DOWN = -1;
    private final int HSTOP = 0;

    private int currentVState = VSTOP;
    private int currentHState = HSTOP;
    
    private int PIXELS_TO_METERS = 36;
    
    public Test1(RacingGame game) {
        this.game = game;
        batch = new SpriteBatch();
        bg = new Texture(Gdx.files.internal("maps/map1.png"));
        world = new World(new Vector2(0,0), true);
        world.setGravity(new Vector2(0,0));
        camera = new OrthographicCamera(Gdx.graphics.getWidth() / PIXELS_TO_METERS, Gdx.graphics.getHeight() / PIXELS_TO_METERS);
    }
    
    @Override
    public void show() {
        debugRenderer = new Box2DDebugRenderer();
        car = new Car(world);
        
        car.bdef.position.set(0, 1);
        
        controlState = 0;
        
        //CONTROLS
        Gdx.input.setInputProcessor(new InputController() {
            @Override
            public boolean keyDown(int keycode) {
               switch(keycode) {
                   case Input.Keys.UP: controlState |= UP; break;
                   case Input.Keys.DOWN: controlState |= DOWN; break;
                   case Input.Keys.LEFT: controlState |= LEFT; break;  
                   case Input.Keys.RIGHT: controlState |= RIGHT;
                   default: ;
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
                   case Input.Keys.UP: controlState &= UP; break;
                   case Input.Keys.DOWN: controlState &= DOWN; break;
                   case Input.Keys.LEFT: controlState &= LEFT; break;
                   case Input.Keys.RIGHT: controlState &= RIGHT;
                   default: ;
                }
                return true;
            }
            
        });
    }

    @Override
    public void render(float f) {
        
        car.tire3.updateFriction();
        car.tire3.updateDrive(controlState);
        car.tire3.updateTurn(controlState);
        
        car.tire4.updateFriction();
        car.tire4.updateDrive(controlState);
        
        car.tire4.updateTurn(controlState);
        
        car.update(controlState);
        
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        
        

        Gdx.gl.glClearColor(3/255f,13/255f,128/255f,1); //set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);   
        
        batch.begin();
        batch.draw(bg, 0, 0);
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
    }

    @Override
    public void dispose() {
        world.dispose();
        bg.dispose();
        game.dispose();
    }
}


