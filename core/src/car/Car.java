package car;

import Screens.CarSelectionScreen;
import Screens.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.HashSet;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import handlers.InputManager.Key;

public class Car {
    private int playerNum;
    
    public Body body;
    Array<Tire> tires;
    RevoluteJoint leftJoint, rightJoint;

    int car = 0;
    int lapCount = 0;
    boolean fuel = false;

    float maxFSpeed;
    float maxBSpeed;
    float backTireMDriveForce;
    float frontTireMDriveForce;
    float backTireMLateralImpulse;
    float frontTireMLateralImpulse;
    float breakingFPourcentage;
    
    float FuelTank;
    float MaxFuelCapacity;
    float fuelConsumption;
    
    boolean isAccelerating;
    boolean onFuelPad;
    
    Vector2 InitialPosition;
                        
    Sprite carSprite;
    public String carLink;
    
    public Car(World world, int CarNum, int ColorNum, int playerNum) {
        this.playerNum = playerNum;
        whichCar(CarNum, ColorNum);
        
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
                
    //6X10 CAR VERTICES
//	vertices[0] = new Vector2(1, -5);
//	vertices[1] = new Vector2(3, -3);
//	vertices[2] = new Vector2(3,  3);
//	vertices[3] = new Vector2(1, 5);
//	vertices[4] = new Vector2(-1, 5);
//	vertices[5] = new Vector2(-3, 3);
//	vertices[6] = new Vector2(-3, -3);
//	vertices[7] = new Vector2(-1, -5);

    //OG CAR VERTICES
//      vertices[0] = new Vector2(1.5f, 0);
//	vertices[1] = new Vector2(3, 2.5f);
//	vertices[2] = new Vector2(2.8f, 5.5f);
//	vertices[3] = new Vector2(1, 10);
//	vertices[4] = new Vector2(-1, 10);
//	vertices[5] = new Vector2(-2.8f, 5.5f);
//	vertices[6] = new Vector2(-3, 2.5f);
//	vertices[7] = new Vector2(-1.5f, 0);

	PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);

	FixtureDef fixtureDef = new FixtureDef();
	fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = false;
            fixtureDef.density = .1f;
        
                                            
        fixtureDef.filter.categoryBits = Constants.CAR;
        fixtureDef.filter.maskBits = Constants.GROUND | Constants.TIREOBS | Constants.CAR | Constants.WALL;
                                            
	body.createFixture(fixtureDef);
        //body.applyTorque(1000, true);

        carSprite = new Sprite(new Texture(carLink));
        carSprite.setSize(3,6);
        carSprite.setOrigin(carSprite.getWidth() / 2, carSprite.getHeight()/2);
        body.setUserData(carSprite);

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
        UseFuel(isAccelerating, fuelConsumption);
        addFuel(onFuelPad);
        }
    }
    
    public void whichCar(int car, int Color){
        if(car ==0){
            //Golf
            maxFSpeed = 220;
            maxBSpeed = -30;
            backTireMDriveForce = 75;
            frontTireMDriveForce = 125;
            backTireMLateralImpulse = 4.25f;
            frontTireMLateralImpulse = 3.75f;
            breakingFPourcentage = 0.3f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.golf_colors[Color];
            MaxFuelCapacity = 100;
            FuelTank = MaxFuelCapacity;
            fuelConsumption = 1/12f;
            
        }
        else if(car == 1){
            //Lamborghini
            maxFSpeed = 320;
            maxBSpeed = -40;
            backTireMDriveForce = 75;
            frontTireMDriveForce = 125;
            backTireMLateralImpulse = 4.25f;
            frontTireMLateralImpulse = 3.75f;
            breakingFPourcentage = 0.3f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.lambo_colors[Color];
            MaxFuelCapacity = 100;
            FuelTank = MaxFuelCapacity;
            fuelConsumption = 1/12f;
        }
        else if(car ==2){
            //prius
            maxFSpeed = 150;
            maxBSpeed = -20;
            backTireMDriveForce = 150;
            frontTireMDriveForce = 250;
            backTireMLateralImpulse = 4.25f;
            frontTireMLateralImpulse = 3.75f;
            breakingFPourcentage = 0.3f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.prius_colors[Color];
            MaxFuelCapacity = 100;
            FuelTank = MaxFuelCapacity;
            fuelConsumption = 1/12f;
        }
        else if(car ==3){
            //Porsche
            maxFSpeed = 260;
            maxBSpeed = -30;
            backTireMDriveForce = 75; //Affects Acceleration
            frontTireMDriveForce = 125; 
            backTireMLateralImpulse = 2.125f;//Affects steering
            frontTireMLateralImpulse = 1.875f;
            breakingFPourcentage = 0.3f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.porsche_colors[Color];
            MaxFuelCapacity = 100;
            FuelTank = MaxFuelCapacity;
            fuelConsumption = 1/12f;
        }
        else if(car == 4){
            //Truck
            maxFSpeed = 220;
            maxBSpeed = -30;
            backTireMDriveForce = 150;
            frontTireMDriveForce = 250;
            backTireMLateralImpulse = 4.25f;
            frontTireMLateralImpulse = 3.75f;
            breakingFPourcentage = 0.3f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.truck_colors[Color];
            MaxFuelCapacity = 100;
            FuelTank = MaxFuelCapacity;
            fuelConsumption = 1/12f;
        }
        else if(car == 5){
            //Zonda
            maxFSpeed = 500;
            maxBSpeed = -50;
            backTireMDriveForce = 360;
            frontTireMDriveForce = 600;
            backTireMLateralImpulse = 4.25f;
            frontTireMLateralImpulse = 3.75f;
            breakingFPourcentage = 0.3f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.zondaf_colors[Color];
            MaxFuelCapacity = 100;
            FuelTank = MaxFuelCapacity;
            fuelConsumption = 1/12f;
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
        }
                              
    } //end of which car
                                
    public Vector2 Position(){
        Vector2 position = new Vector2();

    position = new Vector2(3,3);

        return position;
    }                           

    private void UseFuel(boolean isAccelerating, float fuelConsumption) {
        if(this.getFuelTank() > 0){
        if(this.getOnFuelPad() == false){
            if(isAccelerating)
            setFuelTank(this.getFuelTank()-fuelConsumption);
        
        else
            setFuelTank(this.getFuelTank()-(fuelConsumption*.1f));
        }
        }
        

            //When your car has no more fuel

        
        
    }
    
        private void addFuel(boolean onFuelPad){
            if(this.getFuelTank() <= this.getMaxFuelCapacity()){
            if(onFuelPad)
                this.setFuelTank(FuelTank + fuelConsumption*2);
            }
            
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
    
    public void setTiresTo90(){
       Tire[] tire = tires.items;

        for(int i = 0; i > tires.size; i++){
            tire[i].body.setTransform(body.getWorldCenter(), 90*Constants.DEGTORAD);
        }
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
    
}
