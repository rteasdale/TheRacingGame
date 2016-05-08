package car;

import Screens.CarSelectionScreen;
import Screens.SettingsScreen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.HashSet;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import handlers.InputManager.Key;
import handlers.ScreenAssets;

public class Car {
    
    boolean MetalPlayedOnce = false;
    boolean BridgePlayedOnce = false;
    boolean loopedCar_fueling = false;
    
    boolean carDone = false;
    
    private int playerNum;
    int carNum;
    
    public Body body;
    Array<Tire> tires;
    Array<Integer> currentCheckpoints;
    Array<FuelAreaType> fuelAreas;
    RevoluteJoint leftJoint, rightJoint;
    
    
    private String carName = null;
    private int car = 0;
    private int lapCounter = 2;
    private int i = 0;
    private float pitch = 0.5f;
    boolean fuel = true;
    private float maxFSpeed;
    private float maxBSpeed;
    private float backTireMDriveForce;
    private float frontTireMDriveForce;
    private float backTireMLateralImpulse;
    private float frontTireMLateralImpulse;
    private float breakingFPourcentage;
    private float density;
    
    private int numOfFuelStops;
    private float totalFuelConsum;
    
    private float FuelTank;
    private float MaxFuelCapacity;
    private float fuelConsumption;
    
    private boolean isAccelerating;
    private boolean onFuelPad;
    
    private Vector2 InitialPosition;
                        
    private Sprite carSprite;
    private String carLink;
    
    private Sound car_car;
    private Sound car_wall1;
    private Sound car_wall2;
    private Sound car_wall3;
    private Sound car_Tire;
    private Sound car_Bridge;
    private Sound car_Metal;
    private Sound car_lap_complete;
    private Sound car_fueling;
    private Sound car_going_on_fuel;
    
    ScreenAssets assets;
    
    public Car(World world, int CarNum, int ColorNum, int playerNum, ScreenAssets assets) {
        this.playerNum = playerNum;
        this.carNum = CarNum;
        this.assets = assets;
        whichCar(CarNum, ColorNum);
        
        car_car =  assets.manager.get(ScreenAssets.car_car_sound);
        car_wall1 = assets.manager.get(ScreenAssets.car_wall_sound1);
        car_wall2 = assets.manager.get(ScreenAssets.car_wall_sound2);
        car_wall3 = assets.manager.get(ScreenAssets.car_wall_sound3);
        car_Tire = assets.manager.get(ScreenAssets.car_tire_sound);
        car_Bridge = assets.manager.get(ScreenAssets.car_bridge_loop);
        car_Metal = assets.manager.get(ScreenAssets.car_metal_loop_sound);
        car_lap_complete = assets.manager.get(ScreenAssets.lap_complete_sound);
        car_fueling = assets.manager.get(ScreenAssets.refueling_loop);
        car_going_on_fuel = assets.manager.get(ScreenAssets.getting_on_fuel_sound);
        
        fuelAreas = new Array<FuelAreaType>();
       currentCheckpoints = new Array<Integer>();
        
        tires = new Array<Tire>();
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(InitialPosition);
		
        body = world.createBody(bodyDef);
        body.setAngularDamping(3);
		
	Vector2[] vertices = new Vector2[8];
    
        vertices[0] = new Vector2(0.75f, -2.5f);
        vertices[1] = new Vector2(1.5f, -2f);
        vertices[2] = new Vector2(1.5f,  2f);
        vertices[3] = new Vector2(0.75f, 2.5f);
        vertices[4] = new Vector2(-0.75f, 2.5f);
        vertices[5] = new Vector2(-1.5f, 2f);
        vertices[6] = new Vector2(-1.5f, -2f);
        vertices[7] = new Vector2(-0.75f, -2.5f);
                

	PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);

	FixtureDef fixtureDef = new FixtureDef();
	fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = false;
        fixtureDef.density = density;
        
                                            
        fixtureDef.filter.categoryBits = Constants.CAR;
        fixtureDef.filter.maskBits = Constants.GROUND | Constants.TIREOBS | Constants.CAR | Constants.WALL | Constants.FUEL | Constants.FINISH
                | Constants.BRIDGE | Constants.METAL | Constants.ICE;
                                            
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new CarType(carSprite, this));
        //body.applyTorque(1000, true);

        carSprite = new Sprite(new Texture(carLink));
        carSprite.setSize(3,6);
        carSprite.setOrigin(carSprite.getWidth() / 2, carSprite.getHeight()/2);
        body.setUserData(new CarType(carSprite, this));

        
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = body;
        jointDef.enableLimit = true;
        jointDef.lowerAngle = 0;
        jointDef.upperAngle = 0;
        jointDef.localAnchorB.setZero();

        float maxForwardSpeed = maxFSpeed;
        float maxBackwardSpeed = maxBSpeed;
        float backTireMaxDriveForce = backTireMDriveForce;
        float frontTireMaxDriveForce = frontTireMDriveForce;
        float backTireMaxLateralImpulse = backTireMLateralImpulse;
        float frontTireMaxLateralImpulse = frontTireMLateralImpulse;
        float breakingForcePourcentage = breakingFPourcentage;

        //Lower Left
        Tire tire = new Tire(world, playerNum);
        tire.setCharacteristics(maxForwardSpeed, maxBackwardSpeed,
        backTireMaxDriveForce, backTireMaxLateralImpulse, breakingForcePourcentage);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(-1.125f, -1.5f);
        world.createJoint(jointDef);
        tires.add(tire);

        //Lower Right
        tire = new Tire(world, playerNum);
        tire.setCharacteristics(maxForwardSpeed, maxBackwardSpeed,
        backTireMaxDriveForce, backTireMaxLateralImpulse, breakingForcePourcentage);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(1.125f, -1.5f);
        world.createJoint(jointDef);
        tires.add(tire);

        //Upper Left
        tire = new Tire(world, playerNum);
        tire.setCharacteristics(maxForwardSpeed, maxBackwardSpeed,
                        frontTireMaxDriveForce, frontTireMaxLateralImpulse,breakingForcePourcentage);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(-1.125f, 1.5f);
        leftJoint = (RevoluteJoint)world.createJoint(jointDef);
        tires.add(tire);
                                            
        //Upper Right
        tire = new Tire(world, playerNum);
        tire.setCharacteristics(maxForwardSpeed, maxBackwardSpeed,
                        frontTireMaxDriveForce, frontTireMaxLateralImpulse,breakingForcePourcentage);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(1.125f, 1.5f);
        rightJoint = (RevoluteJoint)world.createJoint(jointDef);
        tires.add(tire);
    } //end of car

    public void update(HashSet<Key> keys) {
        for (Tire tire : tires) {
            tire.updateFriction();
        }
        for (Tire tire : tires) {
            tire.updateDrive(keys);
        }

        float lockAngle = 35 * Constants.DEGTORAD;
        float turnSpeedPerSec = 160 * Constants.DEGTORAD;
        float turnPerTimeStep = turnSpeedPerSec / 60.0f;
        float desiredAngle = 0;

         /** if player 1 */
        if (playerNum == 1) {
            if(keys.contains(Key.Left)){
                desiredAngle = lockAngle;
            } else if(keys.contains(Key.Right)){
                desiredAngle = -lockAngle;
            }
        }
         /** if player 2 */
        if (playerNum == 2) {
            if(keys.contains(Key.a)){
                desiredAngle = lockAngle;
            } else if(keys.contains(Key.d)){
                desiredAngle = -lockAngle;
            }
        }
        
        float angleNow = leftJoint.getJointAngle();
        float angleToTurn = desiredAngle - angleNow;
        angleToTurn = CarMath.clamp(angleToTurn, -turnPerTimeStep, turnPerTimeStep);
        float newAngle = angleNow + angleToTurn;

        leftJoint.setLimits(newAngle, newAngle);
        rightJoint.setLimits(newAngle, newAngle);
        
        
        if(fuel){
            
        if(!carDone){    
        UseFuel(isAccelerating, fuelConsumption);
        
        if(onFuelPad){
            
        addFuel();
        
        }
        }
        }
        
       //System.out.println("LapCounter : " +  lapCounter);
        
    }
    
    public void whichCar(int car, int Color){
        
        //120 = MaxSpeed! of Car
        
        if(car ==0){
            //Golf
            carName = "Volkswagen Golf";
            maxFSpeed = 80;
            maxBSpeed = -30;
            backTireMDriveForce = 172; //was 130
            frontTireMDriveForce = 172;
            backTireMLateralImpulse = 3.1875f;
            frontTireMLateralImpulse = 2.8125f;
            breakingFPourcentage = 0.095f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.golf_colors[Color];
            MaxFuelCapacity = 100;
            FuelTank = MaxFuelCapacity;
            fuelConsumption = 8/255f;
            density = 0.1f;
            
        }
        else if(car == 1){
            //Lamborghini
            carName = "Lamborghini Gallardo";
            maxFSpeed = 110;
            maxBSpeed = -40;
            backTireMDriveForce = 278; //was 130
            frontTireMDriveForce = 278;
            backTireMLateralImpulse = 4f;
            frontTireMLateralImpulse = 3.5f;
            breakingFPourcentage = 0.1f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.lambo_colors[Color];
            MaxFuelCapacity = 100;
            FuelTank = MaxFuelCapacity;
            fuelConsumption = 13/204f;
            density = 0.1f;
        }
        else if(car ==2){
            //prius
            carName = "Toyota Prius";
            maxFSpeed = 70;
            maxBSpeed = -20;
            backTireMDriveForce = 150; //was 130
            frontTireMDriveForce = 150;
            backTireMLateralImpulse = 4.25f;
            frontTireMLateralImpulse = 3.75f;
            breakingFPourcentage = 0.1f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.prius_colors[Color];
            MaxFuelCapacity = 100;
            FuelTank = MaxFuelCapacity;
            fuelConsumption = 5/204f;
            density = 0.1f;
        }
        else if(car ==3){
            //Porsche
            carName = "Porsche 911";
            maxFSpeed = 100;
            maxBSpeed = -30;
            backTireMDriveForce = 350; //was 130
            frontTireMDriveForce = 350;
            backTireMLateralImpulse = 4f;
            frontTireMLateralImpulse = 3.75f;
            breakingFPourcentage = 0.15f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.porsche_colors[Color];
            MaxFuelCapacity = 100;
            FuelTank = MaxFuelCapacity;
            fuelConsumption = 5/68f;
            density = 0.1f;
        }
        else if(car == 4){
            //Truck
            carName = "F-150 Raptor";
            maxFSpeed = 80;
            maxBSpeed = -30;
            backTireMDriveForce = 170; 
            frontTireMDriveForce = 170;
            backTireMLateralImpulse = 2.125f;
            frontTireMLateralImpulse =1.875f;
            breakingFPourcentage = 0.125f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.truck_colors[Color];
            MaxFuelCapacity = 100;
            FuelTank = MaxFuelCapacity;
            fuelConsumption = 7/102f;
            density = 0.1f;
        }
        else if(car == 5){
            //Zonda
            carName = "ZondaF";
            maxFSpeed = 121;
            maxBSpeed = -50;
            backTireMDriveForce = 301; //was 130
            frontTireMDriveForce = 301;
            backTireMLateralImpulse = 4f;
            frontTireMLateralImpulse = 3.5f;
            breakingFPourcentage = 0.09f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.zondaf_colors[Color];
            MaxFuelCapacity = 100;
            FuelTank = MaxFuelCapacity;
            fuelConsumption = 1/12f;
            density = 0.1f;
        }
        else{
            maxFSpeed = 75;
            maxBSpeed = -20;
            backTireMDriveForce = 150;
            frontTireMDriveForce = 250;
            backTireMLateralImpulse = 4.25f;
            frontTireMLateralImpulse = 3.75f;
            breakingFPourcentage = 0.3f;
            InitialPosition  = Position();
            carLink = CarSelectionScreen.golf_colors[Color];
            MaxFuelCapacity = 100;
            FuelTank = MaxFuelCapacity;
            fuelConsumption = 1/12f;
            density = 0.1f;
        }
                              
    } //end of which car
                                
    public Vector2 Position(){
        Vector2 position = new Vector2();

    position = new Vector2(0,0);

        return position;
    }                           

    private void UseFuel(boolean isAccelerating, float fuelConsumption) {
        if(this.getFuelTank() > 0){
        if(this.getOnFuelPad() == false){
            if(isAccelerating) {
                setFuelTank(this.getFuelTank()-fuelConsumption);
            } else {
                setFuelTank(this.getFuelTank()-(fuelConsumption*.1f));
            }
        }
        }
        

            //When your car has no more fuel

        
        
    }
    
        private void addFuel(){
            if(this.getFuelTank() <= this.getMaxFuelCapacity()){ 
                if(!loopedCar_fueling){
                    try{
                    car_fueling.loop(SettingsScreen.getSFXPourcentage());
                    }catch(NullPointerException e1){
                        car_fueling.loop(0.75f);
                        }
                    loopedCar_fueling = true;
                }

                this.setFuelTank(FuelTank + 1/6f);

            }
            
        }

    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }
    
    public int getCarNum() {
        return carNum;
    }
    
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
    
    public int getPlayerNum() {
        return playerNum;
    }
        
    public void setFuelTank(float fuelTank){
        this.FuelTank = fuelTank;
    }
    
    public float getFuelTank(){
        return FuelTank;
    }
    
    public boolean getIsAccelerating(){
        return isAccelerating;
    }
    
    public void setIsAccelerating(boolean isAccelerating){
        this.isAccelerating = isAccelerating;
    }
    
    public boolean getOnFuelPad(){
        return onFuelPad;
    }
    
    public void setOnFuelPad(boolean onFuelPad){
        this.onFuelPad = onFuelPad;
    }
    
    public float getMaxFuelCapacity(){
        return MaxFuelCapacity;
    }
    
    
    public float getMaxFSpeed(){
        return maxFSpeed;
    }
    
    public float getMaxBSpeed(){
        return maxBSpeed;
    }
    
    public float getBackTireMDriveForce(){
        return backTireMDriveForce;
    }
    
    public float getFrontTireMDriveForce(){
        return frontTireMDriveForce;
    }
    
    public float getBackTireMLateralImpulse(){
        return backTireMLateralImpulse;
    }
    
    public float getFrontTireMLateralImpulse(){
        return frontTireMLateralImpulse;
    }
    
      public float getBreakingFPourcentage(){
        return breakingFPourcentage;
    }

      public float getFuelConsumption(){
        return fuelConsumption;
    }
      
      public int getLapNumber() {
          return lapCounter;
      }

      public void setLapNumber(int lap) {
          this.lapCounter = lap;
      }
      
     public void checkpointCheck(int num){

        if(num == 5){ //Checks if the car is on the last checkpoint
            
         if(currentCheckpoints.contains(0, true) && currentCheckpoints.contains(1, true) && 
              currentCheckpoints.contains(2, true) && currentCheckpoints.contains(3, true) &&
              currentCheckpoints.contains(4, true)){  //Checks if car has gone on every checkpoint
             
              lapCounter++;  //If true, Add 1 to counter
              
              try{
              car_lap_complete.play(SettingsScreen.getSFXPourcentage()); // Plays sound to indicate that the lap is complete
              }catch (NullPointerException e1){
                  car_lap_complete.play(0.75f);
              }
              currentCheckpoints.clear(); //Clear the list of checkpoint
             
               }
         
         else {
             System.out.println("Missed one or more checkpoints");
         }
         
        }
        
        
      else{
        if(!currentCheckpoints.contains(num, true)){ //Checks if the list already has the checkpoint
            currentCheckpoints.add(num); //If not, it adds it
              }  
        }
         
     }
    
     public void addFuelArea(FuelAreaType item) {
        fuelAreas.add(item);
        
        try{
        car_going_on_fuel.play(SettingsScreen.getSFXPourcentage());
        }catch(NullPointerException e1){
            car_going_on_fuel.play(0.75f);
        }
        loopedCar_fueling = false;
        updateFuel();
    }

    public void removeFuelArea(FuelAreaType item) {
        fuelAreas.removeValue(item, false);
        car_fueling.stop();
        updateFuel();
    }
    
    public void updateFuel(){
        if(fuelAreas.size != 0){
            onFuelPad = true;
            
        }
        else {
            onFuelPad = false;
        }
            
    }


    public void playCarOnCarSound() {
        try{
            car_car.play(SettingsScreen.getSFXPourcentage());
        }catch(NullPointerException e1){
            car_car.play(0.75f);
        }
        
    }

    public void playCarOnWallSound() {
        int r = (int)(Math.random() * 100)%2; 
        if(r == 0){
            playCarOnWall1();
        }
        else if(r == 1){
            playCarOnWall2();
        }
        else if(r == 2){
            playCarOnWall3();
        }
    }

    public void playCarOnTireSound() {

        try{
        car_Tire.play(SettingsScreen.getSFXPourcentage());
        }catch(NullPointerException e1){
            car_Tire.play(0.75f);
        }
    }
    
        public void playCarOnWall1(){
                            
            try{
        car_wall1.play(SettingsScreen.getSFXPourcentage());
            }catch(NullPointerException e1){
                car_wall1.play(0.75f);
            }
    }
    public void playCarOnWall2(){
        try{
        car_wall2.play(SettingsScreen.getSFXPourcentage());
        }catch (NullPointerException e1){
            car_wall2.play(0.75f);
        }
    }
    public void playCarOnWall3(){

        try{
            car_wall3.play(SettingsScreen.getSFXPourcentage());
        } catch(NullPointerException e1){
            car_wall3.play(0.75f);
        }
       
}
    public void loopMetalSounds(){
        if(!MetalPlayedOnce){
            
            try{
            car_Metal.loop(SettingsScreen.getSFXPourcentage());
            }catch(NullPointerException e1){
                car_Metal.loop(0.75f);
            }
        }
    }
    public void loopBridgeSounds(){
        if(!BridgePlayedOnce){
            try{
        car_Bridge.loop(SettingsScreen.getSFXPourcentage());
            }catch(NullPointerException e1){
                car_Bridge.loop(0.75f);
            }
        BridgePlayedOnce = true;
        }
    }
    
    public void stopMetalSounds(){
        
        car_Metal.stop();
        MetalPlayedOnce = false;
    }
    
    public void stopBridgeSounds(){
        car_Bridge.stop();
        MetalPlayedOnce = false;
    }
    
    public void setCarDone(boolean carDone){
        this.carDone = carDone;
    }
    
    public String getCarName(){
        return carName;
    }
    
    public void setCarFuelStops(int numOffuelStops) {
        this.numOfFuelStops = numOffuelStops;
    }
    
    public void setCarTotalFuelConsumption(float totalFuelConsum) {
        this.totalFuelConsum = totalFuelConsum;
    }
    
}
