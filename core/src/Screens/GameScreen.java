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
import car.CarType;
import car.Constants;
import car.FinishLineType;
import car.FuelAreaType;
import car.GroundAreaType;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.RacingGame;
import com.sun.prism.image.ViewPort;
import handlers.CarContactListener;
import handlers.InputManager;
import handlers.ScreenAssets;
import java.util.Random;

public final class GameScreen implements Screen {

    private RacingGame game;
    private Hud hud;
    private ScreenAssets assets;
    
    private ViewPort viewPort;
            
    private long startTime;
    private boolean countdownState;
    private boolean gamingState = false;
    private boolean finishState = false;
    
    public static int mapNum = 0;
    public static int maxLap = 0;
    private boolean twoPlayers;
    
    Sprite tireSprite;
    
    private static int carNumP1;
    private static int carNumP2;
    private static int carColorP1;
    private static int carColorP2;
    
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
    String mapAddressT; //Map Adress for the TiledMap
    String mapAddressI; // Map Adress for the Image
    
    Music song1;
    Music song2;
    Music song3;
    Music song4;
    Music song5;
    Music song6;
    
    
    public GameScreen(RacingGame game, boolean twoPlayers, int mapNum, ScreenAssets assets) {
        this.game = game;
        this.assets = assets;
        this.twoPlayers = twoPlayers;
        this.mapNum = mapNum;
        //Gdx.app.log("twoPlayers", Boolean.toString(twoPlayers));

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, RacingGame.V_WIDTH, RacingGame.V_HEIGHT);
        camera.zoom = 0.2f;
        camera.position.x = 0;
        camera.position.y = 0;
        
        hud = new Hud(batch, twoPlayers, gamingState, assets, maxLap);
        world = new World(new Vector2(0, 0f), true);
        cl = new CarContactListener();
        world.setContactListener(cl);

        renderer = new Box2DDebugRenderer();
        renderer.setDrawJoints(false);
        
        inputManager = new InputManager(this);

        /** Songs*/
        song1 = assets.manager.get(ScreenAssets.song1);
        song2 = assets.manager.get(ScreenAssets.song2);
        song3 = assets.manager.get(ScreenAssets.song3);
        song4 = assets.manager.get(ScreenAssets.song4);
        song5 = assets.manager.get(ScreenAssets.song5);
        song6 = assets.manager.get(ScreenAssets.song6);        
        
        /**Create cars*/
	car = new Car(world, carNumP1, carColorP1, 1);

        // If two players, construct another car
        if (twoPlayers == true) {
            car2 = new Car(world, carNumP2, carColorP2, 2);
        }
        
        ////////////////////////////////////////////////////
        //Load Tiled Map
        
        choseMap(mapNum);
        bg = new Texture(mapAddressI);
        //playMusic(mapNum);
       
        tileMap = new TmxMapLoader().load(mapAddressT);
        tmr = new OrthogonalTiledMapRenderer(tileMap, 1/4f);

        if(mapNum == 0){
        createCollisionsM1();
        }
        if(mapNum == 1){
            car.body.setTransform(new Vector2(0,0), 90*Constants.DEGTORAD);
            if(twoPlayers){
                car2.body.setTransform(new Vector2(0,0), 90*Constants.DEGTORAD);
            }
                    
            createCollisionsM2();
        }
        if(mapNum == 2){
            createCollisionsM3();
        }
        
        
	
    }

    @Override
    public void show() {
        
        float delay = 2; // seconds

        Timer.schedule(new Task(){
            @Override
            public void run() {
                countdownState = true;
            }
        }, delay);      
        

    }
    
    @Override
    public void render (float f) {
        Gdx.gl.glClearColor(red,green,blue,alpha);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        inputManager.updateControls(twoPlayers); //update controls for two players
        
        world.step(1 / 60f, 6, 2);
        camera.update();    
    
        //draw tile map
        //tmr.setView(camera);
        //tmr.render();

        //System.out.println(car.body.getLinearVelocity().len());
        //System.out.println(car.getFuelTank());
        
        renderSprites();
                                           
        //Box2D Debug Renderer
        if(debug){
            renderer.render(world, camera.combined);
        }

        if(!twoPlayers){
        camera.position.set(new Vector3(car.body.getPosition().x, car.body.getPosition().y, camera.position.z));
        }
        
        if(twoPlayers){
            Vector2 CameraPosition = CarMath.getCenterPoint(car.body.getPosition(), car2.body.getPosition());
            camera.position.set(new Vector3(CameraPosition.x, CameraPosition.y, camera.position.z));
        }
        
        //load HUD 
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        hud.stage.act();
        
        /**CountdownState*/ 
        if (countdownState) {
            hud.updateCountDown(f);   
            gamingState = hud.getGamingState();
            
        }
        
        /**Gaming state*/
        if (gamingState) {
            Gdx.input.setInputProcessor(inputManager); 
            
            hud.updateTime();
            
            float carSpeed = car.body.getLinearVelocity().len();
            //update speed gauge
            hud.updateSpeed(carSpeed, car);  

            float fuel = car.getFuelTank();
            //update fuel tank
            hud.updateFuel(fuel, car);
            
            hud.updateLap(twoPlayers, car, car2);

        }
       
    }
    
    private void createCollisionsM1(){
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
                
            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum)+2, ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum)+1);
                
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

            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum)+2, ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum)+1);

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

            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum)+2, ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum)+1);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width*0.5f*1/4f, height*0.5f*1/4f, size, 0.0f);

            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.FUEL;
            fdef.filter.maskBits = Constants.CAR | Constants.TIRE;

            Body body = world.createBody(bdef);

            Fixture fuelAreaFixture = body.createFixture(fdef);
            fuelAreaFixture.setUserData(new FuelAreaType());
        }
        
        ////////////////////////////////////////////////////////
            //FINISH LINE LAYER
            MapLayer FinishLayer = tileMap.getLayers().get("FinishLine ObjectLayer");
        
        for(MapObject fi : FinishLayer.getObjects()){
            bdef.type = BodyType.StaticBody;

           float  x = (float) fi.getProperties().get("x", Float.class) ;
            float y = (float) fi.getProperties().get("y", Float.class) ;

            float width = (float) fi.getProperties().get("width", Float.class);
            float height = (float) fi.getProperties().get("height", Float.class);
             String ID_Finish_String = (String) fi.getProperties().get("ID_Finish", String.class);
             int ID_Finish = Integer.parseInt(ID_Finish_String);

            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum)+2, ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum)+1);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width*0.5f*1/4f, height*0.5f*1/4f, size, 0.0f);

            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.FINISH;
            fdef.filter.maskBits = Constants.CAR | Constants.TIRE;

            Body body = world.createBody(bdef);

            Fixture finishAreaFixture = body.createFixture(fdef);
           finishAreaFixture.setUserData(new FinishLineType(ID_Finish));
        }
        
        
        MapLayer TireLayer = tileMap.getLayers().get("Tire ObjectLayer");
                
        for(MapObject ti : TireLayer.getObjects()) {
            bdef = new BodyDef();
            bdef.type = BodyType.DynamicBody;

            float x = (float) ti.getProperties().get("x", Float.class) ;
            float y = (float) ti.getProperties().get("y", Float.class) ;

            float width = (float) ti.getProperties().get("width", Float.class);
            
            bdef.position.set( new Vector2((x*1/4f + width*1/8f)+xPositionDraw(mapNum)+2, (y*1/4f + width*1/8f)+yPositionDraw(mapNum)+1));
            
            CircleShape shape = new CircleShape();
            shape.setRadius(width/8f);

            fdef = new FixtureDef();

            fdef.shape = shape;
            fdef.isSensor = false;
            fdef.density = 1000;
            fdef.restitution = 0.5f;
            fdef.filter.categoryBits = Constants.TIREOBS;
            fdef.filter.maskBits = Constants.CAR | Constants.GROUND;

            Body body = world.createBody(bdef);
            body.createFixture(fdef);
            
            
            tireSprite = new Sprite(new Texture("Tire.png"));
            tireSprite.setSize(width/4,width/4);
            tireSprite.setOrigin(tireSprite.getWidth() / 2, tireSprite.getHeight()/2);
            body.setUserData(tireSprite);  
        }   
        
    }
    
    
    //Create collisions for map 2
    public void createCollisionsM2(){
         
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
                
            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum), ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum));
                
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

            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum), ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum));

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

            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum), ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum));

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width*0.5f*1/4f, height*0.5f*1/4f, size, 0.0f);

            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.FUEL;
            fdef.filter.maskBits = Constants.CAR | Constants.TIRE;

            Body body = world.createBody(bdef);

            Fixture fuelAreaFixture = body.createFixture(fdef);
            fuelAreaFixture.setUserData(new FuelAreaType());
        }
        
                ////////////////////////////////////////////////////////
            //FINISH LINE LAYER
            MapLayer FinishLayer = tileMap.getLayers().get("FinishLine ObjectLayer");
        
        for(MapObject fi : FinishLayer.getObjects()){
            bdef.type = BodyType.StaticBody;

           float  x = (float) fi.getProperties().get("x", Float.class) ;
            float y = (float) fi.getProperties().get("y", Float.class) ;

            float width = (float) fi.getProperties().get("width", Float.class);
            float height = (float) fi.getProperties().get("height", Float.class);
            
             String ID_Finish_String = (String) fi.getProperties().get("ID_Finish", String.class);
             int ID_Finish = Integer.parseInt(ID_Finish_String);

            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum), ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum));

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width*0.5f*1/4f, height*0.5f*1/4f, size, 0.0f);

            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.FINISH;
            fdef.filter.maskBits = Constants.CAR | Constants.TIRE;

            Body body = world.createBody(bdef);

            Fixture finishAreaFixture = body.createFixture(fdef);
            finishAreaFixture.setUserData(new FinishLineType(ID_Finish));
        }
        
        MapLayer TireLayer = tileMap.getLayers().get("Tire ObjectLayer");
                
        for(MapObject ti : TireLayer.getObjects()) {
            bdef = new BodyDef();
            bdef.type = BodyType.DynamicBody;

            float x = (float) ti.getProperties().get("x", Float.class) ;
            float y = (float) ti.getProperties().get("y", Float.class) ;

            float width = (float) ti.getProperties().get("width", Float.class);
            
            bdef.position.set( new Vector2((x*1/4f + width*1/8f)+xPositionDraw(mapNum), (y*1/4f + width*1/8f)+yPositionDraw(mapNum)));
            
            CircleShape shape = new CircleShape();
            shape.setRadius(width/8f);

            fdef = new FixtureDef();

            fdef.shape = shape;
            fdef.isSensor = false;
            fdef.density = 1000;
            fdef.restitution = 0.5f;
            fdef.filter.categoryBits = Constants.TIREOBS;
            fdef.filter.maskBits = Constants.CAR | Constants.GROUND;

            Body body = world.createBody(bdef);
            body.createFixture(fdef);
            
            
            tireSprite = new Sprite(new Texture("Tire.png"));
            tireSprite.setSize(width/4,width/4);
            tireSprite.setOrigin(tireSprite.getWidth() / 2, tireSprite.getHeight()/2);
            body.setUserData(tireSprite);  
        }   
        
        //ICE LAYER
        MapLayer IceLayer = tileMap.getLayers().get("Ice ObjectLayer");
        
        for(MapObject ic : IceLayer.getObjects()){
            bdef.type = BodyType.StaticBody;

            float x = (float) ic.getProperties().get("x", Float.class) ;
            float y = (float) ic.getProperties().get("y", Float.class) ;

            float width = (float) ic.getProperties().get("width", Float.class);
            float height = (float) ic.getProperties().get("height", Float.class);

            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum)-47, ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum)-42);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width*0.5f*1/4f, height*0.5f*1/4f, size, 0.0f);

            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.ICE;
            fdef.filter.maskBits = Constants.TIRE;

            Body body = world.createBody(bdef);

            Fixture groundAreaFixture = body.createFixture(fdef);
            groundAreaFixture.setUserData(new GroundAreaType(0.001f, false));
        }
            
            
        //Bridge Layer
        MapLayer BridgeLayer = tileMap.getLayers().get("Bridge ObjectLayer");
        
        for(MapObject br : BridgeLayer.getObjects()){
            bdef.type = BodyType.StaticBody;

            float x = (float) br.getProperties().get("x", Float.class) ;
            float y = (float) br.getProperties().get("y", Float.class) ;

            float width = (float) br.getProperties().get("width", Float.class);
            float height = (float) br.getProperties().get("height", Float.class);

            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum)-47, ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum)-42);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width*0.5f*1/4f, height*0.5f*1/4f, size, 0.0f);

            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.BRIDGE;
            fdef.filter.maskBits = Constants.TIRE;

            Body body = world.createBody(bdef);

            Fixture groundAreaFixture = body.createFixture(fdef);
            groundAreaFixture.setUserData(new GroundAreaType(0.90f, false));
        }    
            
        MapLayer WallLayer = tileMap.getLayers().get("Wall ObjectLayer");
                
        for(MapObject wa : WallLayer.getObjects()) {
            bdef = new BodyDef();
            bdef.type = BodyType.DynamicBody;

            float x = (float) wa.getProperties().get("x", Float.class) ;
            float y = (float) wa.getProperties().get("y", Float.class) ;

            float width = (float) wa.getProperties().get("width", Float.class);
            float height = (float) wa.getProperties().get("height", Float.class);

            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum), ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum));

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width*0.5f*1/4f, height*0.5f*1/4f, size, 0.0f);
            
            fdef = new FixtureDef();

            fdef.shape = shape;
            fdef.isSensor = false;
            fdef.density = 1000;
            fdef.restitution = 0.5f;
            fdef.filter.categoryBits = Constants.WALL;
            fdef.filter.maskBits = Constants.CAR | Constants.GROUND;

            Body body = world.createBody(bdef);
            body.createFixture(fdef);
           
            
        }
    }
    
    public void createCollisionsM3() {
        
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
                
            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum)+2, ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum)+1);
                
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

            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum)+2, ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum)+1);

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

            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum)+2, ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum)+1); 

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width*0.5f*1/4f, height*0.5f*1/4f, size, 0.0f);

            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.FUEL;
            fdef.filter.maskBits = Constants.CAR | Constants.TIRE;

            Body body = world.createBody(bdef);

            Fixture groundAreaFixture = body.createFixture(fdef);
            groundAreaFixture.setUserData(new FuelAreaType());
        }
        
                ////////////////////////////////////////////////////////
            //FINISH LINE LAYER
            MapLayer FinishLayer = tileMap.getLayers().get("FinishLine ObjectLayer");
        
        for(MapObject fi : FinishLayer.getObjects()){
            bdef.type = BodyType.StaticBody;

           float  x = (float) fi.getProperties().get("x", Float.class) ;
            float y = (float) fi.getProperties().get("y", Float.class) ;

            float width = (float) fi.getProperties().get("width", Float.class);
            float height = (float) fi.getProperties().get("height", Float.class);
            
            String ID_Finish_String = (String) fi.getProperties().get("ID_Finish", String.class);
             int ID_Finish = Integer.parseInt(ID_Finish_String);

            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum)+2, ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum)+1);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width*0.5f*1/4f, height*0.5f*1/4f, size, 0.0f);

            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.FINISH;
            fdef.filter.maskBits = Constants.CAR | Constants.TIRE;

            Body body = world.createBody(bdef);

            Fixture groundAreaFixture = body.createFixture(fdef);
            groundAreaFixture.setUserData(new FinishLineType(ID_Finish));
        }
        
        MapLayer TireLayer = tileMap.getLayers().get("Tire ObjectLayer");
                
        for(MapObject ti : TireLayer.getObjects()) {
            bdef = new BodyDef();
            bdef.type = BodyType.DynamicBody;

            float x = (float) ti.getProperties().get("x", Float.class) ;
            float y = (float) ti.getProperties().get("y", Float.class) ;

            float width = (float) ti.getProperties().get("width", Float.class);
            
            bdef.position.set( new Vector2((x*1/4f + width*1/8f)+xPositionDraw(mapNum)+2, (y*1/4f + width*1/8f)+yPositionDraw(mapNum)+1));
            
            CircleShape shape = new CircleShape();
            shape.setRadius(width/8f);

            fdef = new FixtureDef();

            fdef.shape = shape;
            fdef.isSensor = false;
            fdef.density = 1000;
            fdef.restitution = 0.5f;
            fdef.filter.categoryBits = Constants.TIREOBS;
            fdef.filter.maskBits = Constants.CAR | Constants.GROUND;

            Body body = world.createBody(bdef);
            body.createFixture(fdef);
            
            
            tireSprite = new Sprite(new Texture("Tire.png"));
            tireSprite.setSize(width/4,width/4);
            tireSprite.setOrigin(tireSprite.getWidth() / 2, tireSprite.getHeight()/2);
            body.setUserData(tireSprite);  
        }   
        
        MapLayer MetalLayer = tileMap.getLayers().get("Metal ObjectLayer");
        
        for(MapObject me : MetalLayer.getObjects()){
            bdef.type = BodyType.StaticBody;

            float x = (float) me.getProperties().get("x", Float.class) ;
            float y = (float) me.getProperties().get("y", Float.class) ;

            float width = (float) me.getProperties().get("width", Float.class);
            float height = (float) me.getProperties().get("height", Float.class);

            Vector2 size = new Vector2(((x+width*0.5f)*1/4f)+xPositionDraw(mapNum)-13, ((y+height*0.5f)*1/4f)+yPositionDraw(mapNum)-110);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width*0.5f*1/4f, height*0.5f*1/4f, size, 0.0f);

            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.METAL;
            fdef.filter.maskBits = Constants.CAR | Constants.TIRE;

            Body body = world.createBody(bdef);

            Fixture groundAreaFixture = body.createFixture(fdef);
            groundAreaFixture.setUserData(new GroundAreaType(0.90f, false));
        }
                           
    }
    
    /** Chose map*/

    public void choseMap(int mapNum){
        if(mapNum == 0){
            //System.out.println("Map 1 Selected");
            red = 71/255f;
            green = 122/255f;
            blue = 25/255f;
            alpha = 1;
            mapAddressT = "maps/map1.tmx";
            mapAddressI = "maps/map1.png";
            maxLap = 5;
        }
        else if(mapNum == 1){
            //System.out.println("Map 2 Selected");
            red = 254/255f;
            green = 254/255f;
            blue = 255/255f;
            alpha = 1;
            mapAddressT = "maps/map2.tmx";
            mapAddressI = "maps/map2.png";
            maxLap = 3;
            
        }
        else if(mapNum == 2){
            //System.out.println("Map 3 Selected");
            red = 13/255f;
            green = 8/255f;
            blue = 36/255f;
            alpha = 1;
            mapAddressT = "maps/map3.tmx";
            mapAddressI = "maps/map3.png";
            maxLap = 3;
        }
        else{
            //System.out.println("Map DEFAULT Selected");
            mapAddressT = "maps/map1.tmx";
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
        //System.out.println(mapNum);
        batch.draw(bg, xPositionDraw(mapNum), yPositionDraw(mapNum));
        
        world.getBodies(tmpBodies);

        for(Body body : tmpBodies) {
            
            if(body.getUserData() != null && (body.getUserData() instanceof Sprite)) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition((body.getPosition().x - sprite.getWidth()/ 2), (body.getPosition().y - sprite.getHeight()/2));
                sprite.setRotation(body.getAngle() * ( MathUtils.radiansToDegrees));
                sprite.draw(batch);
            }
            
            else if(body.getUserData() != null && (body.getUserData() instanceof CarType)) {
                CarType type = (CarType) body.getUserData();
                Sprite sprite = (Sprite) type.sprite;
                sprite.setPosition((body.getPosition().x - sprite.getWidth()/ 2), (body.getPosition().y - sprite.getHeight()/2));
                sprite.setRotation(body.getAngle() * ( MathUtils.radiansToDegrees));
                sprite.draw(batch);
            }
            
        }
        
        
    batch.end();
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

    public static void setPosition(){
     car.body.setTransform(new Vector2(200,500), 90);
    //car.body.applyForceToCenter(new Vector2(100000, 0), debug);
    }    

public void isOutside(){ //Could work with car[]
        car.body.setLinearVelocity(GameScreen.car.body.getLinearVelocity().scl(0.3f));
    }

///////////////////////////////////////////////////////////////////////////////////////

    private void playMusic(int map) { //Method for playing music inside the game
        if(map == 0){
            Random rand = new Random();
            
            int r = rand.nextInt(3);
            switch(r){
                case 0: song1.play();
                song1.setVolume(0.75f);
                song1.setLooping(true);
                System.out.println("Song playing : Song1"); break;
                case 1: song2.play();
                song2.setVolume(0.75f);
                song2.setLooping(true);
                System.out.println("Song playing : Song2"); break;
                case 2: song3.play();
                song3.setVolume(0.75f);
                song3.setLooping(true);
                System.out.println("Song playing : Song3"); break;
                default:  song4.play();
                song4.setVolume(0.75f);
                song4.setLooping(true);
                System.out.println("Song playing : Song4");
            }
        }
        else if(map == 1){
            song5.play();
            song5.setVolume(0.75f);
            song5.setLooping(true);
            System.out.println("Song playing : Song4");
        }
        else if(map == 2){
            song6.play();
            song6.setVolume(0.75f);
            song6.setLooping(true);
            System.out.println("Song playing : Song4");
        }
    }

    private int xPositionDraw(int mapNum){
        if(mapNum == 0){
            return -60;
        }
        else if(mapNum == 1){
            return -300;
        }
        else
            return -408;
    }
    
    private int yPositionDraw(int mapNum){
        if(mapNum == 0){
            return -175;
        }
        else if(mapNum == 1){
            return -220;
        }
        else
            return -200;
    }

    
}

