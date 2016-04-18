package Screens;


import Scenes.Hud;

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
import car.CarMath;
import car.Constants;
import car.FuelAreaType;
import car.GroundAreaType;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.RacingGame;
import handlers.CarContactListener;
import handlers.InputManager;
import java.util.concurrent.TimeUnit;

public final class GameScreen implements Screen {

    private RacingGame game;
    private Hud hud;
    
    public static int mapNum = 0;
    private boolean twoPlayers;
    
    private static int carNumP1;
    private static int carNumP2;
    private static int carColorP1;
    private static int carColorP2;
    
    private float totalTime = 0;
    private long startTime = TimeUtils.millis();
    
    private TiledMap tileMap;
    private OrthogonalTiledMapRenderer tmr;
    public static boolean debug = true; //Boolean if I want B2D Debug on or off

    private Array<Body> tmpBodies = new Array<Body>();
    private Texture bg;
    private SpriteBatch batch;
    private Sprite carSprite;
    
    public World world;
    public static OrthographicCamera camera;
    Box2DDebugRenderer renderer;
    InputManager inputManager;
    CarContactListener cl;
    
    public static Car car;
    public static Car car2;

    float red;
    float green;
    float blue;
    float alpha;
    String mapAdress;
    
    public GameScreen(RacingGame game, boolean twoPlayers, int mapNum) {
        this.game = game;
        this.twoPlayers = twoPlayers;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, RacingGame.V_WIDTH, RacingGame.V_HEIGHT);
        camera.zoom = 0.2f;
        camera.position.x = 0;
        camera.position.y = 0;
        
        System.out.println(twoPlayers);
        hud = new Hud(batch);

        world = new World(new Vector2(0, 0f), true);

        cl = new CarContactListener();
        world.setContactListener(cl);

        renderer = new Box2DDebugRenderer();
        renderer.setDrawJoints(false);

        inputManager = new InputManager(this);
        Gdx.input.setInputProcessor(inputManager);
        
        /**Create cars*/
	car = new Car(world, carNumP1, carColorP1, 1);
        
        // If two players, construct another car
        if (twoPlayers == true) {
            car2 = new Car(world, carNumP2, carColorP2, 2);
        }
        
        ////////////////////////////////////////////////////
        //Load Tiled Map
        choseMap(mapNum);
        tileMap = new TmxMapLoader().load(mapAdress);
        tmr = new OrthogonalTiledMapRenderer(tileMap, 1/4f);

        createGrounds();
        CreateTires();
	
    }

    @Override
    public void show() {

    }
    
    @Override
    public void render (float f) {
        Gdx.gl.glClearColor(red,green,blue,alpha);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
   
        inputManager.update(twoPlayers); //update for two players
        
        world.step(1 / 60f, 6, 2);
        camera.update();

        //draw tile map
        tmr.setView(camera);
        tmr.render();

        //System.out.println(car.getFuelTank());
        
        renderSprites();
                                           
        //Box2D Debug Renderer
        if(debug){
            renderer.render(world, camera.combined);
        }

        if(twoPlayers == false){
        camera.position.set(new Vector3(car.body.getPosition().x, car.body.getPosition().y, camera.position.z));
        }
        
        if(twoPlayers == true){
            Vector2 CameraPosition = CarMath.getCenterPoint(car.body.getPosition(), car2.body.getPosition());
            camera.position.set(new Vector3(CameraPosition.x, CameraPosition.y, camera.position.z));
        }
        
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        
        //if state == GO 
        hud.updateTime(startTime);
        //
    }
    
    private void createGrounds() {
        //Road Layer
        MapLayer Roadlayer = tileMap.getLayers().get("Road ObjectLayer");
           
        //body
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
            
        for(MapObject ro : Roadlayer.getObjects()) {
            bdef.type = BodyType.StaticBody;

            float x = (float) ro.getProperties().get("x", Float.class);
            float y = (float) ro.getProperties().get("y", Float.class);

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
            groundAreaFixture.setUserData(new GroundAreaType(0.9f, false));
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
            ////////////////////////////////////////////////////////
            //FUEL LAYER
            MapLayer FuelLayer = tileMap.getLayers().get("Fuel ObjectLayer");
        
        for(MapObject fu : FuelLayer.getObjects()){
            bdef.type = BodyType.StaticBody;

           float  x = (float) fu.getProperties().get("x", Float.class) ;
            float y = (float) fu.getProperties().get("y", Float.class) ;

            float width = (float) fu.getProperties().get("width", Float.class);
            float height = (float) fu.getProperties().get("height", Float.class);

            Vector2 size = new Vector2((x+width*0.5f)*1/4f, (y+height*0.5f)*1/4f);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width*0.5f*1/4f, height*0.5f*1/4f, size, 0.0f);

            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.FUEL;
            fdef.filter.maskBits = Constants.CAR;

            Body body = world.createBody(bdef);

            Fixture groundAreaFixture = body.createFixture(fdef);
            groundAreaFixture.setUserData(new FuelAreaType());
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
    
    }//end of class createGrounds()
        
    /** Chose map*/

    public void choseMap(int mapNum){
        if(mapNum == 0){
            System.out.println("Map 1 Selected");
            red = 71/255f;
            green = 122/255f;
            blue = 25/255f;
            alpha = 1;
            mapAdress = "maps/map1.tmx";
        }
        else if(mapNum == 1){
            System.out.println("Map 2 Selected");
            red = 254/255f;
            green = 254/255f;
            blue = 255/255f;
            alpha = 1;
            mapAdress = "maps/map2.tmx";
        }
        else if(mapNum == 2){
            System.out.println("Map 3 Selected");
            red = 13/255f;
            green = 8/255f;
            blue = 36/255f;
            alpha = 1;
            mapAdress = "maps/map3.tmx";
        }
        else{
            System.out.println("Map DEFAULT Selected");
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
        batch.dispose();
        renderer.dispose();
        tmr.dispose();
        bg.dispose();
        world.dispose();
    }
    
    public void renderSprites(){
    //draw Object sprites
    batch.begin();
        batch.setProjectionMatrix(camera.combined);
        //batch.draw(bg, -750, 0);
        world.getBodies(tmpBodies);
        for(Body body : tmpBodies) {
            if(body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition((body.getPosition().x - sprite.getWidth()/ 2), (body.getPosition().y - sprite.getHeight()/2));
                sprite.setRotation(body.getAngle() * ( MathUtils.radiansToDegrees));
                sprite.draw(batch);
            }
        }
        
    batch.end();
    }
    
    public void CreateTires(){
        MapLayer TireLayer = tileMap.getLayers().get("Tire ObjectLayer");
                
        for(MapObject ti : TireLayer.getObjects()) {
            BodyDef bdef = new BodyDef();
            bdef.type = BodyType.DynamicBody;

            float x = (float) ti.getProperties().get("x", Float.class) ;
            float y = (float) ti.getProperties().get("y", Float.class) ;

            float width = (float) ti.getProperties().get("width", Float.class);

            CircleShape shape = new CircleShape();
            shape.setRadius(width/8f);
            shape.setPosition(new  Vector2(x*1/4f + width*1/8f, y*1/4f + width*1/8f));

            FixtureDef fdef = new FixtureDef();

            fdef.shape = shape;
            fdef.isSensor = false;
            fdef.density = 1000;
            fdef.restitution = 0.5f;
            fdef.filter.categoryBits = Constants.TIREOBS;
            fdef.filter.maskBits = Constants.CAR | Constants.GROUND;

            Body body = world.createBody(bdef);
            body.createFixture(fdef);
            Sprite tireSprite = new Sprite(new Texture("Tire.png"));
            tireSprite.setSize(width/4,width/4);
            tireSprite.setOrigin(tireSprite.getWidth() / 2, tireSprite.getHeight()/2);
            body.setUserData(tireSprite);  
        }   
    }

    static void setCarColorP1(int currentColor) {
        carColorP1 = currentColor;
    }
    
    public int getCarColorP1() {
        return carColorP1;
    }
    
    static void setCarNumP1(int currentCar) {
        carNumP1 = currentCar;
    }    
    
    public int getCarNumP1() {
        return carNumP1;
    }
    
    static void setCarColorP2(int currentColor) {
        carColorP2 = currentColor;
    }
    
    public int getCarColorP2() {
        return carColorP2;
    }

    static void setCarNumP2(int currentCar) {
        carNumP2 = currentCar;
    }
    
    public int getCarNumP2() {
        return carNumP2;
    }    
    
}

