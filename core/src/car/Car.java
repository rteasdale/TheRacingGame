package car;

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
import handlers.Assets;
import handlers.InputManager.Key;



public class Car {
	public Body body;
	Array<Tire> tires;
	RevoluteJoint leftJoint, rightJoint;
        
                    public static Assets assets;
                    
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
                        public String carLink = "prius/prius_darkblue.png";

	public Car(World world, int i) {
		
                                            assets = new Assets();
                                            
                                            assets.loadTexture(carLink, "");
            
                                            whichCar(car);
                                            
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
//		vertices[0] = new Vector2(1, -5);
//		vertices[1] = new Vector2(3, -3);
//		vertices[2] = new Vector2(3,  3);
//		vertices[3] = new Vector2(1, 5);
//		vertices[4] = new Vector2(-1, 5);
//		vertices[5] = new Vector2(-3, 3);
//		vertices[6] = new Vector2(-3, -3);
//		vertices[7] = new Vector2(-1, -5);
                
                //OG CAR VERTICES
//                                      vertices[0] = new Vector2(1.5f, 0);
//		vertices[1] = new Vector2(3, 2.5f);
//		vertices[2] = new Vector2(2.8f, 5.5f);
//		vertices[3] = new Vector2(1, 10);
//		vertices[4] = new Vector2(-1, 10);
//		vertices[5] = new Vector2(-2.8f, 5.5f);
//		vertices[6] = new Vector2(-3, 2.5f);
//		vertices[7] = new Vector2(-1.5f, 0);

		PolygonShape polygonShape = new PolygonShape();
                                           polygonShape.set(vertices);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
                                            
                                            if(car == 1){
		fixtureDef.density = 0.025f;
                                            }
                                            else if(car == 2){
		fixtureDef.density = 0.05f;
                                            }
                                            else{
                                                fixtureDef.density = .1f;
                                            }
                                            
		fixtureDef.filter.categoryBits = Constants.CAR;
		fixtureDef.filter.maskBits = Constants.GROUND;
                                            
		body.createFixture(fixtureDef);
                                   //       body.applyTorque(1000, true);

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
		Tire tire = new Tire(world);
		tire.setCharacteristics(maxForwardSpeed, maxBackwardSpeed,
				backTireMaxDriveForce, backTireMaxLateralImpulse, breakingForcePourcentage);
		jointDef.bodyB = tire.body;
		jointDef.localAnchorA.set(-1.125f, -1.5f);
		world.createJoint(jointDef);
		tires.add(tire);

                                            //Lower Right
		tire = new Tire(world);
		tire.setCharacteristics(maxForwardSpeed, maxBackwardSpeed,
				backTireMaxDriveForce, backTireMaxLateralImpulse, breakingForcePourcentage);
		jointDef.bodyB = tire.body;
		jointDef.localAnchorA.set(1.125f, -1.5f);
		world.createJoint(jointDef);
		tires.add(tire);

                                            //Upper Left
		tire = new Tire(world);
		tire.setCharacteristics(maxForwardSpeed, maxBackwardSpeed,
				frontTireMaxDriveForce, frontTireMaxLateralImpulse,breakingForcePourcentage);
		jointDef.bodyB = tire.body;
		jointDef.localAnchorA.set(-1.125f, 1.5f);
		leftJoint = (RevoluteJoint)world.createJoint(jointDef);
		tires.add(tire);
                                            
                                            //Upper Right
		tire = new Tire(world);
		tire.setCharacteristics(maxForwardSpeed, maxBackwardSpeed,
				frontTireMaxDriveForce, frontTireMaxLateralImpulse,breakingForcePourcentage);
		jointDef.bodyB = tire.body;
		jointDef.localAnchorA.set(1.125f, 1.5f);
		rightJoint = (RevoluteJoint)world.createJoint(jointDef);
		tires.add(tire);
	}

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

		if(keys.contains(Key.Left)){
			desiredAngle = lockAngle;
		} else if(keys.contains(Key.Right)){
			desiredAngle = -lockAngle;
		}
		
		float angleNow = leftJoint.getJointAngle();
		float angleToTurn = desiredAngle - angleNow;
		angleToTurn = CarMath.clamp(angleToTurn, -turnPerTimeStep, turnPerTimeStep);
		float newAngle = angleNow + angleToTurn;
		
		leftJoint.setLimits(newAngle, newAngle);
		rightJoint.setLimits(newAngle, newAngle);
                
	}
        
                                public void whichCar(int car){
                                    if(car == 1){
                                           maxFSpeed = 75;
		maxBSpeed = -20;
		 backTireMDriveForce = 150;
		 frontTireMDriveForce = 250;
		 backTireMLateralImpulse = 4.25f;
		 frontTireMLateralImpulse = 3.75f;
                                            breakingFPourcentage = 0.3f;
                                            InitialPosition = Position();
                                    }
                                    
                                    else if(car ==2){
                                          maxFSpeed = 75;
		maxBSpeed = -20;
		 backTireMDriveForce = 150;
		 frontTireMDriveForce = 250;
		 backTireMLateralImpulse = 4.25f;
		 frontTireMLateralImpulse = 3.75f;
                                            breakingFPourcentage = 0.3f;
                                            InitialPosition = Position();
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
                                    }
                              
                                }
                                
                                public Vector2 Position(){
                                    Vector2 position = new Vector2();
                                    
                                    if(GameScreen.mapNum == 1){
                                        position = new Vector2(3,3);
                                    }
                                    else if(GameScreen.mapNum == 2){
                                        position = new Vector2(3,3);
                                    }
                                    else if(GameScreen.mapNum == 3){
                                        position = new Vector2(3,3);
                                    }
                                    
                                    return position;
                                }
}
