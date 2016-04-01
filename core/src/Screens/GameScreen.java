package Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import car.Car;
import car.Constants;
import car.GroundAreaType;
import com.badlogic.gdx.Screen;
import com.mygdx.game.RacingGame;
import handlers.CarContactListener;
import handlers.InputManager;

public class GameScreen implements Screen {
private RacingGame game;
	public static final int V_WIDTH = 1280;
	public static final int V_HEIGHT = 720;
        
                    public boolean debug = true; //Boolean if I want B2D Debug on or off
                    
                     private Array<Body> tmpBodies = new Array<Body>();
                    private Texture bg;
                    private SpriteBatch batch;
                    private Sprite carSprite;
                    
	public OrthographicCamera camera;
	Box2DDebugRenderer renderer;
	public World world;
	InputManager inputManager;
	CarContactListener cl;
	public Car car;
        
                     int mapNum = 1;
        
                    float red;
                    float green;
                    float blue;
                    float alpha;
                    String mapAdress;

	public  GameScreen (RacingGame game) {
                                            this.game = game;
                                            
                                            choseMap(mapNum);
                                            
                                           batch = new SpriteBatch();
                                           bg = new Texture(Gdx.files.internal(mapAdress));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		camera.zoom = .5f;
		camera.position.x = 0;
		camera.position.y = 0;
		
		world = new World(new Vector2(0, 0f), true);

		cl = new CarContactListener();
		world.setContactListener(cl);
		
		renderer = new Box2DDebugRenderer();
		renderer.setDrawJoints(false);
		
		inputManager = new InputManager(this);
		Gdx.input.setInputProcessor(inputManager);
		
	    this.car = new Car(world);

		createGrounds();
	}

                @Override
                public void show() {

                }
	@Override
	public void render (float f) {
                        Gdx.gl.glClearColor(red,green,blue,alpha);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		inputManager.update();

		world.step(1 / 60f, 6, 2);
                
                                            
                                           
                                           camera.update();
                                           batch.begin();
                                           batch.setProjectionMatrix(camera.combined);
                                           batch.draw(bg, 0, 0);
                                           world.getBodies(tmpBodies);
                                            for(Body body : tmpBodies)
                                            if(body.getUserData() != null && body.getUserData() instanceof Sprite) {
                                           Sprite sprite = (Sprite) body.getUserData();
                                          sprite.setPosition(body.getPosition().x - sprite.getWidth()/ 2, body.getPosition().y - sprite.getHeight()/2);
                                          sprite.setRotation(body.getAngle() * ( MathUtils.radiansToDegrees));
                                          
                                            sprite.draw(batch);
                                            }
                                           batch.end();
                                           
                                           //Box2D Debug Renderer
                                           if(debug){
		renderer.render(world, camera.combined);
                                           }
                                           
		camera.position.set(new Vector3(car.body.getPosition().x, car.body
				.getPosition().y, camera.position.z));

	
        }
	private void createGrounds(){
		 
		BodyDef bodyDef = new BodyDef();
		Body ground = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.isSensor = true;
		fixtureDef.filter.categoryBits = Constants.GROUND;
		fixtureDef.filter.maskBits = Constants.TIRE;
		
		shape.setAsBox(9, 7, new Vector2(-10,15), 20*Constants.DEGTORAD);
		Fixture groundAreaFixture = ground.createFixture(fixtureDef);
		groundAreaFixture.setUserData(new GroundAreaType(2, false));
		
		shape.setAsBox(9,  5, new Vector2(5, 20), -40 * Constants.DEGTORAD);
		groundAreaFixture = ground.createFixture(fixtureDef);
		groundAreaFixture.setUserData(new GroundAreaType(0.02f, false));
		
	}
        
        public void choseMap(int mapNum){
            if(mapNum == 1){
                red = 71/255f;
                green = 122/255f;
                blue = 25/255f;
                alpha = 1;
                mapAdress = "maps/map1.png";
            }
            
            if(mapNum == 2){
                red = 254/255f;
                green = 254/255f;
                blue = 255/255f;
                alpha = 1;
                mapAdress = "maps/map2.png";
                
            }
            if(mapNum == 3){
                red = 13/255f;
                green = 8/255f;
                blue = 36/255f;
                alpha = 1;
                mapAdress = "maps/map3.png";
                
            }

        }
    @Override
    public void resize(int width, int height) {
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
        
    }
}

