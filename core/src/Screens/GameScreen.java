package Screens;


import Scenes.Hud;
import Scenes.MusicPlayer;
import Scenes.SoundPlayer;

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
import car.CarType;
import car.Constants;
import car.FinishLineType;
import car.FuelAreaType;
import car.GroundAreaType;

import car.TireObsType;
import car.WallType;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.RacingGame;
import handlers.CarContactListener;
import handlers.InputManager;
import handlers.ScreenAssets;

public final class GameScreen implements Screen {

    private RacingGame game;
    private Hud hud;
    private Hud hud2;
    private ScreenAssets assets;
    
    private FitViewport viewport1;
    private FitViewport viewport2;
    private static MusicPlayer musicPlayer;
    private SoundPlayer soundPlayer; 
    private Sound click;
    
    private long startTime;
    private boolean countdownState = false;
    private boolean gamingState = false;
    private boolean finishState = false;
    private boolean RanLeaderBoard = false;
    
    private int mapNum = 0;
    private int maxLap = 0;
    private static boolean twoPlayers;
    
    private boolean P1Finished = false;
    private boolean P2Finished = false; 
    
    Sprite tireSprite;
    
    private static int carNumP1;
    private static int carNumP2;
    private static int carColorP1;
    private static int carColorP2;
    
    private TiledMap tileMap;
    private OrthogonalTiledMapRenderer tmr;
    private static boolean debug = false; //Boolean if I want B2D Debug on or off

    private Array<Body> tmpBodies = new Array<Body>();
    private Texture bg;
    private SpriteBatch batch;
    private Sprite carSprite;
    
    public World world;
    public static OrthographicCamera camera;
    public static OrthographicCamera camera2;
    
    private float aspectRatio;

    Box2DDebugRenderer renderer;
    InputManager inputManager;
    CarContactListener cl;
    private InputMultiplexer multiplexer; 
    
    private static Car car;
    private static Car car2;

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
    
    boolean s1IsPlaying = false;
    boolean s2IsPlaying = false;
    boolean s3IsPlaying = false;
    
    private Stage stage;
    private Skin skin;
    private ImageButtonStyle style;
    private ImageButton exit_button; 
    
    public GameScreen(RacingGame game, boolean twoPlayers, final int mapNum, ScreenAssets assets) {
        this.game = game;
        this.assets = assets;
        this.twoPlayers = twoPlayers;
        this.mapNum = mapNum;
        //Gdx.app.log("twoPlayers", Boolean.toString(twoPlayers));
        
        click = assets.manager.get(ScreenAssets.click_sound2);
        
        soundPlayer = new SoundPlayer();
        
        choseMap(mapNum);
        
        
        stage = new Stage();
        multiplexer = new InputMultiplexer();
        
        multiplexer.addProcessor(stage);
        
        TextureAtlas atlas = new TextureAtlas("menu/homelogo_atlas.txt");
        skin = new Skin(atlas);
        style = new ImageButtonStyle(skin.getDrawable("home_logo"), null,null, null, null, null);
        style.over = skin.getDrawable("home_logo2");
        
        exit_button = new ImageButton(style);
        
        if (!twoPlayers) {
            exit_button.setPosition(15, 665);
        }
        if (twoPlayers) {
            exit_button.setPosition(15, 560);
        }
        
        stage.addActor(exit_button);
        
        batch = new SpriteBatch();

        world = new World(new Vector2(0, 0f), true);
            
        
        /**Create cars*/
	car = new Car(world, carNumP1, carColorP1, 1, assets);
     
        /**Cameras*/
        if (!twoPlayers) {
            camera = new OrthographicCamera();
            hud = new Hud(batch, twoPlayers, gamingState, finishState, 1, assets, maxLap);
        }

        if (twoPlayers) {
            // If two players, construct another car
            car2 = new Car(world, carNumP2, carColorP2, 2, assets);
            
            aspectRatio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
            
            camera = new OrthographicCamera();
            viewport1 = new FitViewport(RacingGame.V_HEIGHT*aspectRatio, RacingGame.V_HEIGHT);
            hud = new Hud(batch, twoPlayers,gamingState, finishState, 1, assets, maxLap);
            viewport1.apply();

            camera2 = new OrthographicCamera();
            viewport2 = new FitViewport(RacingGame.V_HEIGHT*aspectRatio, RacingGame.V_HEIGHT);
            hud2 = new Hud(batch, twoPlayers, gamingState, finishState, 2, assets, maxLap); 
            viewport2.apply();

            camera2.zoom = 0.2f;
            camera2.position.x = 0;
            camera2.position.y = 0;
        }
        
        cl = new CarContactListener();
        world.setContactListener(cl);  
        
        camera.zoom = 0.2f;
        camera.position.x = 0;
        camera.position.y = 0;

        renderer = new Box2DDebugRenderer();
        renderer.setDrawJoints(false);
        
        inputManager = new InputManager(this);


        song2 = assets.manager.get(ScreenAssets.song2);
        song5 = assets.manager.get(ScreenAssets.song5);
        song6 = assets.manager.get(ScreenAssets.song6);
        
        
        ////////////////////////////////////////////////////
        //Load Tiled Map
        
        
        bg = new Texture(mapAddressI);
        
        Timer.schedule(new Task(){
            @Override
            public void run() {
                // playMusic(mapNum);  ////////////////////////////////// MUSIC IS TURNED OFF FOR TESTING!
            }
        }, 5);   
        
       
       
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
    
    private void listeners() {
        /**Exit button*/
        exit_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                soundPlayer.setSound(click);
                soundPlayer.playSound(SettingsScreen.getSFXPourcentage());
                game.setScreen(new MainMenuScreen(game, assets));
                musicPlayer.stopMusic();
            }
        });
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
       
        Timer.schedule(new Task(){
            @Override
            public void run() {
                startTime = TimeUtils.millis();
            }
        }, 7);   
        
        Timer.schedule(new Task(){
            @Override
            public void run() {
                playMusic(mapNum);
            }
        }, 3);           
        
        listeners();
    }
    
    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(red,green,blue,alpha);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        world.step(1 / 60f, 6, 2);

        //draw tile map
        //tmr.setView(camera);
        //tmr.render();

        if(!twoPlayers){
        camera.viewportHeight = Gdx.graphics.getHeight();
        camera.viewportWidth = Gdx.graphics.getWidth();
        camera.update();
        camera.position.set(new Vector3(car.body.getPosition().x, car.body.getPosition().y, camera.position.z));

        renderSprites(batch, camera);  
        
        //load HUD 
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();   
        hud.stage.act();
        stage.draw();
        stage.act();        
        }
        
        
        if (twoPlayers)  {
            /**NOTE: ORDER IN WHICH YOU RENDER IT MATTERS!*/
            /*Right Half*/
            Gdx.gl.glViewport(Gdx.graphics.getWidth()/2,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight());
            camera.viewportHeight = Gdx.graphics.getHeight();
            camera.viewportWidth = Gdx.graphics.getWidth()/2;
            camera.update();
            camera.position.set(new Vector3(car.body.getPosition().x, car.body.getPosition().y, camera.position.z));
            renderSprites(batch, camera);
            batch.setProjectionMatrix(hud.stage.getCamera().combined);
            hud.stage.draw();
            hud.stage.act();

            /*Left Half*/
            Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight());
            camera2.viewportHeight = Gdx.graphics.getHeight();
            camera2.viewportWidth = Gdx.graphics.getWidth()/2;
            camera2.update();
            camera2.position.set(new Vector3(car2.body.getPosition().x, car2.body.getPosition().y, camera2.position.z));
            renderSprites(batch, camera2);
            batch.setProjectionMatrix(hud2.stage.getCamera().combined);
            hud2.stage.draw();
            hud2.stage.act();
            exit_button.setSize(100, 50);
            stage.draw();
            stage.act();           
        }  

        //Box2D Debug Renderer
        if(debug){
            if (twoPlayers) {
                renderer.render(world, camera2.combined);
            }
            else if (!twoPlayers) {
                renderer.render(world, camera.combined);
            }
        }
        
        /**CountdownState*/ 
        if (countdownState == true) {
            hud.updateCountDown(f);
            if (twoPlayers) {
                hud2.updateCountDown(f);
            }
            gamingState = hud.getGamingState();
        }
        
        /**Gaming state*/
        if (gamingState == true) {
            multiplexer.addProcessor(inputManager);
            Gdx.input.setInputProcessor(multiplexer);
            inputManager.updateControls(twoPlayers); //update controls for two players

            hud.updateTime(startTime, P1Finished);
            
            if (twoPlayers) {
                hud2.updateTime(startTime, P2Finished);
            }
            
            float carSpeed = car.body.getLinearVelocity().len();
            
            //update speed gauge
            hud.updateSpeed(carSpeed, car);
            
            if (twoPlayers) {
                float carSpeed2 = car2.body.getLinearVelocity().len();
                hud2.updateSpeed(carSpeed2, car2);
            }

            float fuel = car.getFuelTank();
            
            //update fuel tank
            hud.updateFuel(fuel, car);
            
            if (twoPlayers) {
                float fuel2 = car2.getFuelTank();
                hud2.updateFuel(fuel2, car2);
            }
            
            hud.updateLap(car);
            
            if (twoPlayers) {
                hud2.updateLap(car2);
            }
        }
        
        
        
        /** Finish state*/
        //for single player, game ends if current lap number = max lap number
        if (twoPlayers == false) {
            if (car.getLapNumber()== maxLap) {
                finishState = true;
                musicPlayer.stopMusic();
                P1Finished = true;
                hud.updateTime(startTime, P1Finished);
                gamingState = false;
                inputManager.disposeAll(car);
                hud.updateFinish(twoPlayers);
            }
        }

        if (twoPlayers == true) {
            if (car.getLapNumber()== maxLap) {
                musicPlayer.stopMusic();
                inputManager.disposeP1(car);
                P1Finished = true;
                car.setCarDone(P1Finished);
                hud.updateTime(startTime, P1Finished);
                hud.updateFinish(twoPlayers);
                //if car1 and car2 have the same num of laps, game is over
                if (car2.getLapNumber()== car.getLapNumber()) {
                    musicPlayer.stopMusic();
                    finishState = true;
                    
                }
            }
            
            if (car2.getLapNumber()== maxLap) {
                inputManager.disposeP2(car2);
                P2Finished = true;
                car2.setCarDone(P2Finished);
                hud2.updateTime(startTime, P2Finished);
                hud2.updateFinish(twoPlayers);  
                if (car2.getLapNumber()== car.getLapNumber()) {
                    musicPlayer.stopMusic();
                    finishState = true;
                    
                }
            }
        }
            
        /** Game over state*/
        //if in two players mode
            //if tank is zero, then -1 lap penalty
        if (twoPlayers == true) {
        if (car.getFuelTank() < 0.5) {
            car.setFuelTank(60);
            car.setLapNumber(car.getLapNumber()-1);
            hud.updateLap(car);
        }

        
        if (car2.getFuelTank() < 0.5) {
            car2.setFuelTank(60);
            car2.setLapNumber(car2.getLapNumber()-1);
            hud2.updateLap(car2);
        }
        }

        //single player mode
            //if tank has zero, game over 

        if (!twoPlayers) {
        if (car.getFuelTank() < 0.5) {
            finishState = true;
            hud.updateTime(startTime, finishState);
            gamingState = false;
            inputManager.disposeAll(car);
            hud.updateGameOver();
        }
        }
        
        if (finishState == true) {
            if(!RanLeaderBoard){
            Timer.schedule(new Task(){
                @Override
                public void run() {
                    
                    if (twoPlayers == true) {
                        game.setScreen(new LeaderboardScreen(game, twoPlayers, car, car2, assets, hud, hud2, mapNum));
                    }
                    
                    else if (twoPlayers == false) {
                        game.setScreen(new LeaderboardScreen(game, twoPlayers, car, null, assets, hud, null, mapNum));
                    }

                }
            }, 3);          
            RanLeaderBoard = true;
        }
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
            groundAreaFixture.setUserData(new GroundAreaType(0.9f, false,0));
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
            groundAreaFixture.setUserData(new GroundAreaType(0.02f, false,0));
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
            Fixture fixture = body.createFixture(fdef);
            fixture.setUserData(new TireObsType());
            
            
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
            groundAreaFixture.setUserData(new GroundAreaType(0.9f, false,0));
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
            groundAreaFixture.setUserData(new GroundAreaType(0.02f, false,0));
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
            Fixture fixture = body.createFixture(fdef);
            fixture.setUserData(new TireObsType());
            
            
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
            groundAreaFixture.setUserData(new GroundAreaType(0.001f, false,0));
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
            groundAreaFixture.setUserData(new GroundAreaType(0.90f, false,1));
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
            Fixture fixture = body.createFixture(fdef);
            fixture.setUserData(new WallType());
           
            
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
            groundAreaFixture.setUserData(new GroundAreaType(0.9f, false,0));
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
            groundAreaFixture.setUserData(new GroundAreaType(0.02f, false,0));
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
            Fixture fixture = body.createFixture(fdef);
            fixture.setUserData(new TireObsType());
            
            
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
            groundAreaFixture.setUserData(new GroundAreaType(0.90f, false,2));
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
        skin.dispose();
        stage.dispose();
        batch.dispose();
        renderer.dispose();
        tmr.dispose();
        bg.dispose();
        world.dispose();
        song1.dispose();
        song2.dispose();
        song3.dispose();
        song4.dispose();
        song5.dispose();
        song6.dispose();
        if(twoPlayers){
            hud.dispose();
            hud2.dispose();
        }
        else{
            hud.dispose();
        }
    }
    
    public void renderSprites(SpriteBatch batch, OrthographicCamera camera){
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
   

public void isOutside(){ //Could work with car[]
        car.body.setLinearVelocity(GameScreen.car.body.getLinearVelocity().scl(0.3f));
    }

///////////////////////////////////////////////////////////////////////////////////////

    private void playMusic(int map) { //Method for playing music inside the game
        
        musicPlayer = new MusicPlayer();
        

        //Always change the volume setting before going into Game mode
        if(map == 0){
            musicPlayer.setSong(song2);
            //System.out.println("Song playing : Song2");
            
            if(!s1IsPlaying){
                musicPlayer.playMusic();
            try{
           musicPlayer.setVolumeValue(SettingsScreen.getMusicPourcentage());
            }catch(NullPointerException e1){
                musicPlayer.setVolumeValue(0.75f);
            } 
                song2.setLooping(true);
                s1IsPlaying = true;
            }
        }

        else if(map == 1){
            //System.out.println("Song playing : Song5");
            musicPlayer.setSong(song5);
            if(!s2IsPlaying){
                musicPlayer.playMusic();
            try{
           musicPlayer.setVolumeValue(SettingsScreen.getMusicPourcentage());
            }catch(NullPointerException e1){
                musicPlayer.setVolumeValue(0.75f);
            } 
                song5.setLooping(true);
                s2IsPlaying = true;
            }
        }
        else if(map == 2){
            //System.out.println("Song playing : Song6");
            musicPlayer.setSong(song6);
            if (!s3IsPlaying) {
                musicPlayer.playMusic();
            try{
           musicPlayer.setVolumeValue(SettingsScreen.getMusicPourcentage());
            }catch(NullPointerException e1){
                musicPlayer.setVolumeValue(0.75f);
            } 
                song6.setLooping(true);
                s3IsPlaying = true;
            }
        }
    }

    private int xPositionDraw(int mapNum){
        if(mapNum == 0){
            return -60;
        }
        else if(mapNum == 1){
            return -300;
        }
        else {
            return -408;
        }
    }
    
    private int yPositionDraw(int mapNum){
        if(mapNum == 0){
            return -175;
        }
        else if(mapNum == 1){
            return -220;
        }
        else {
            return -200;
        }
    }
    
    public static boolean getTwoPlayers(){
        return twoPlayers;
    }
    
    public static boolean getDebug(){
        return debug;
    }

    public static Car getCar(){
        return car;
    }
    
    public static Car getCar2(){
        return car2;
    }
    
    
}

