/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car;

import Screens.CarSelectionScreen;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import handlers.InputManager;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author ROSY
 */
public class AICar implements Telegraph{
    
    MessageManager msgManager; 
    Vector2 travelPos;
    boolean fueling; 
    
    
    public Body body;
    Array<Tire> tires;
    RevoluteJoint leftJoint, rightJoint;

    int car = 0;
    int lapCount = 0;

    float maxFSpeed;
    float maxBSpeed;
    float backTireMDriveForce;
    float frontTireMDriveForce;
    float backTireMLateralImpulse;
    float frontTireMLateralImpulse;
    float breakingFPourcentage;
    Vector2 InitialPosition;
                        
    Sprite carSprite;
    public String carLink;
    
    public AICar(World world, int CarNum, int ColorNum) {
        whichCar(CarNum, ColorNum);        
        
        tires = new Array<Tire>();
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
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
                                            
        if(car == 1){
            fixtureDef.density = 0.025f;
        }
        else if(car == 2){
            fixtureDef.density = 0.05f;
        }
        else {
            fixtureDef.density = .1f;
        }
                                            
        fixtureDef.filter.categoryBits = Constants.CAR;
        fixtureDef.filter.maskBits = Constants.GROUND | Constants.TIREOBS | Constants.CAR;
                                            
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
        Tire tire = new Tire(world, 0);
        tire.setCharacteristics(maxForwardSpeed, maxBackwardSpeed,
        backTireMaxDriveForce, backTireMaxLateralImpulse, breakingForcePourcentage);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(-1.125f, -1.5f);
        world.createJoint(jointDef);
        tires.add(tire);

        //Lower Right
        tire = new Tire(world, 0);
        tire.setCharacteristics(maxForwardSpeed, maxBackwardSpeed,
        backTireMaxDriveForce, backTireMaxLateralImpulse, breakingForcePourcentage);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(1.125f, -1.5f);
        world.createJoint(jointDef);
        tires.add(tire);

        //Upper Left
        tire = new Tire(world, 0);
        tire.setCharacteristics(maxForwardSpeed, maxBackwardSpeed,
                        frontTireMaxDriveForce, frontTireMaxLateralImpulse,breakingForcePourcentage);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(-1.125f, 1.5f);
        leftJoint = (RevoluteJoint)world.createJoint(jointDef);
        tires.add(tire);
                                            
        //Upper Right
        tire = new Tire(world, 0);
        tire.setCharacteristics(maxForwardSpeed, maxBackwardSpeed,
                        frontTireMaxDriveForce, frontTireMaxLateralImpulse,breakingForcePourcentage);
        jointDef.bodyB = tire.body;
        jointDef.localAnchorA.set(1.125f, 1.5f);
        rightJoint = (RevoluteJoint)world.createJoint(jointDef);
        tires.add(tire);
    }

    
    public void update(HashSet<InputManager.Key> keys) {
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
        
        if(keys.contains(InputManager.Key.Left)){
            desiredAngle = lockAngle;
        } else if(keys.contains(InputManager.Key.Right)){
            desiredAngle = -lockAngle;
        }
        
        float angleNow = leftJoint.getJointAngle();
        float angleToTurn = desiredAngle - angleNow;
        angleToTurn = CarMath.clamp(angleToTurn, -turnPerTimeStep, turnPerTimeStep);
        float newAngle = angleNow + angleToTurn;

        leftJoint.setLimits(newAngle, newAngle);
        rightJoint.setLimits(newAngle, newAngle);
    }
    
    public void whichCar(int car, int Color){
        if(car ==0){
            //Golf
            maxFSpeed = 75;
            maxBSpeed = -20;
            backTireMDriveForce = 150;
            frontTireMDriveForce = 250;
            backTireMLateralImpulse = 4.25f;
            frontTireMLateralImpulse = 3.75f;
            breakingFPourcentage = 0.3f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.golf_colors[Color];
        }
        else if(car == 1){
            //Lamborghini
            maxFSpeed = 75;
            maxBSpeed = -20;
            backTireMDriveForce = 75;
            frontTireMDriveForce = 125;
            backTireMLateralImpulse = 4.25f;
            frontTireMLateralImpulse = 3.75f;
            breakingFPourcentage = 0.3f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.lambo_colors[Color];
        }
        else if(car ==2){
            //prius
            maxFSpeed = 75;
            maxBSpeed = -20;
            backTireMDriveForce = 150;
            frontTireMDriveForce = 250;
            backTireMLateralImpulse = 4.25f;
            frontTireMLateralImpulse = 3.75f;
            breakingFPourcentage = 0.3f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.prius_colors[Color];
        }
        else if(car ==3){
            //Porsche
            maxFSpeed = 125;
            maxBSpeed = -20;
            backTireMDriveForce = 75; //Affects Acceleration
            frontTireMDriveForce = 125; 
            backTireMLateralImpulse = 2.125f;//Affects steering
            frontTireMLateralImpulse = 1.875f;
            breakingFPourcentage = 0.3f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.porsche_colors[Color];
        }
        else if(car == 4){
            //Truck
            maxFSpeed = 75;
            maxBSpeed = -20;
            backTireMDriveForce = 150;
            frontTireMDriveForce = 250;
            backTireMLateralImpulse = 4.25f;
            frontTireMLateralImpulse = 3.75f;
            breakingFPourcentage = 0.3f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.truck_colors[Color];
        }
        else if(car == 5){
            //Zonda
            maxFSpeed = 75;
            maxBSpeed = -20;
            backTireMDriveForce = 150;
            frontTireMDriveForce = 250;
            backTireMLateralImpulse = 4.25f;
            frontTireMLateralImpulse = 3.75f;
            breakingFPourcentage = 0.3f;
            InitialPosition = Position();
            carLink = CarSelectionScreen.zondaf_colors[Color];
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
        }
                              
    } //end of which car
                                
    public Vector2 Position(){
        Vector2 position = new Vector2();

        position = new Vector2(3,3);

        return position;
    
    }
    
    private void initMessages() {
        this.msgManager = MessageManager.getInstance();
//        MessageManager.getInstance().dispatchMessage(0,0); //equal to line below
//        this.msgManager.dispatchMessage(0,0);
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        return false;
    }
}
