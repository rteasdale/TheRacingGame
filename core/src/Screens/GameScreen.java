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
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.RacingGame;
import handlers.CarContactListener;
import handlers.InputManager;

public class GameScreen implements Screen {
    
    private TiledMap tileMap;
    private OrthogonalTiledMapRenderer tmr;    
    
private RacingGame game;



    public static boolean debug = true; //Boolean if I want B2D Debug on or off

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
                    public Car car2;
        
                     public static int mapNum = 2;
        
                    float red;
                    float green;
                    float blue;
                    float alpha;
                    String mapAdress;

	public  GameScreen (RacingGame game, int mapNum) {
            this.game = game;

            choseMap(mapNum);

                                            batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, RacingGame.V_WIDTH, RacingGame.V_HEIGHT);
		camera.zoom = 0.2f;
		camera.position.x = 0;
		camera.position.y = 0;
		
		world = new World(new Vector2(0, 0f), true);
                                           
		cl = new CarContactListener();
		world.setContactListener(cl);
		
		renderer = new Box2DDebugRenderer();
		renderer.setDrawJoints(false);
		
		inputManager = new InputManager(this);
		Gdx.input.setInputProcessor(inputManager);
		
	    this.car = new Car(world, Constants.PRIUS,Constants.GREEN );
                        this.car2 = new Car(world, Constants.LAMBORGHINI,Constants.DARKBLUE );

		
                
                ////////////////////////////////////////////////////
                
                //Load Tiled Map
                choseMap(mapNum);
                
                tileMap = new TmxMapLoader().load(mapAdress);
                tmr = new OrthogonalTiledMapRenderer(tileMap, 1/4f);
                
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
            
            //draw tile map
            tmr.setView(camera);
            tmr.render();
            
          renderSprites();
                                           
            //Box2D Debug Renderer
            if(debug){
	renderer.render(world, camera.combined);
                                  }
            
               camera.position.set(new Vector3(car.body.getPosition().x, car.body.getPosition().y, camera.position.z));

	
        }
	private void createGrounds(){
		 
            //Road Layer
            
            
            MapLayer Roadlayer = tileMap.getLayers().get("Road ObjectLayer");
            
            BodyDef bdef = new BodyDef();
            FixtureDef fdef = new FixtureDef();
            
            for(MapObject ro : Roadlayer.getObjects()){
                
                bdef.type = BodyType.StaticBody;
                
                float x = (float) ro.getProperties().get("x", Float.class) ;
                float y = (float) ro.getProperties().get("y", Float.class) ;
                
                float width = (float) ro.getProperties().get("width", Float.class);
                float height = (float) ro.getProperties().get("height", Float.class);
                
                Vector2 size = new Vector2((x+width*0.5f)*1/4f, (y+height*0.5f)*1/4f);
                
                PolygonShape shape = new PolygonShape();
                shape.setAsBox(width*0.5f*1/4f, height*0.5f*1/4f, size, 0.0f);
                
                fdef.shape = shape;
                fdef.isSensor = true;
                fdef.filter.categoryBits = Constants.GROUND;
                fdef.filter.maskBits = Constants.TIRE;
                
                Body body = world.createBody(bdef);
                
                Fixture groundAreaFixture = body.createFixture(fdef);
                groundAreaFixture.setUserData(new GroundAreaType(1, false));
            }
                ////////////////////////////////////////////////////////
                //Oil Layer
                
                MapLayer OilLayer = tileMap.getLayers().get("Oil ObjectLayer");
                
                for(MapObject oi : OilLayer.getObjects()){
                
                bdef.type = BodyType.StaticBody;
                
                float x = (float) oi.getProperties().get("x", Float.class) ;
                float y = (float) oi.getProperties().get("y", Float.class) ;
                
                float width = (float) oi.getProperties().get("width", Float.class);
                float height = (float) oi.getProperties().get("height", Float.class);
                
                Vector2 size = new Vector2((x+width*0.5f)*1/4f, (y+height*0.5f)*1/4f);
                
                PolygonShape shape = new PolygonShape();
                shape.setAsBox(width*0.5f*1/4f, height*0.5f*1/4f, size, 0.0f);
                
                fdef.shape = shape;
                fdef.isSensor = true;
                fdef.filter.categoryBits = Constants.OILOBS;
                fdef.filter.maskBits = Constants.TIRE;
                
                Body body = world.createBody(bdef);
                
                Fixture groundAreaFixture = body.createFixture(fdef);
                groundAreaFixture.setUserData(new GroundAreaType(0.02f, false));
                
            }
            
            ///////////////////////////////////////////////////////
            
//Tutorial Ground
            
//		BodyDef bodyDef = new BodyDef();
//		Body ground = world.createBody(bodyDef);
//		
//		PolygonShape shape = new PolygonShape();
//		FixtureDef fixtureDef = new FixtureDef();
//		fixtureDef.shape = shape;
//		fixtureDef.isSensor = true;
//		fixtureDef.filter.categoryBits = Constants.GROUND;
//		fixtureDef.filter.maskBits = Constants.TIRE;
//		
//		shape.setAsBox(9, 7, new Vector2(-10,15), 20*Constants.DEGTORAD);
//		Fixture groundAreaFixture = ground.createFixture(fixtureDef);
//		groundAreaFixture.setUserData(new GroundAreaType(0.02f, false));
//		
//		shape.setAsBox(100,  100, new Vector2(5, 20), -40 * Constants.DEGTORAD);
//		groundAreaFixture = ground.createFixture(fixtureDef);
//		groundAreaFixture.setUserData(new GroundAreaType(1f, false));
		
	}
        
        public void choseMap(int mapNum){
            if(mapNum == 1){
                red = 71/255f;
                green = 122/255f;
                blue = 25/255f;
                alpha = 1;
                mapAdress = "maps/map1.tmx";
            }
            
            if(mapNum == 2){
                red = 254/255f;
                green = 254/255f;
                blue = 255/255f;
                alpha = 1;
                mapAdress = "maps/map2.tmx";
                
            }
            if(mapNum == 3){
                red = 13/255f;
                green = 8/255f;
                blue = 36/255f;
                alpha = 1;
                mapAdress = "maps/map3.tmx";
            }
                else{
                        mapAdress = "maps/map1.tmx";
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
        bg.dispose();
        world.dispose();
    }
    
    public void renderSprites(){
          //draw Object sprites
            batch.begin();
            batch.setProjectionMatrix(camera.combined);
           //batch.draw(bg, -750, 0);
            world.getBodies(tmpBodies);
             for(Body body : tmpBodies)
                 
             if(body.getUserData() != null && body.getUserData() instanceof Sprite) {
            Sprite sprite = (Sprite) body.getUserData();
           sprite.setPosition((body.getPosition().x - sprite.getWidth()/ 2), (body.getPosition().y - sprite.getHeight()/2));
           sprite.setRotation(body.getAngle() * ( MathUtils.radiansToDegrees));

           
             sprite.draw(batch);
             }
            batch.end();
    }
    
}

