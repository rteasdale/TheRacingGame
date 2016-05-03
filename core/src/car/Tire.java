package car;

import Screens.GameScreen;
import java.util.HashSet;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import handlers.InputManager.Key;


public class Tire {
    private int playerNum;
    Body body;
    float maxForwardSpeed;
    float maxBackwardSpeed;
    float maxDriveForce;
    float maxLateralImpulse;
    float breakingForcePourcentage;
    
    Array<GroundAreaType> groundAreas;
    float currentTraction;

    public Tire(World world, int playerNum) {
        this.playerNum = playerNum;
        groundAreas = new Array<GroundAreaType>();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.25f, 0.625f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Constants.TIRE;
        fixtureDef.filter.maskBits  = Constants.GROUND | Constants.FINISH | Constants.FUEL;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new CarTireType());

        body.setUserData(this);
        currentTraction = 0.2f;
        
    }
    
    public void addGroundArea(GroundAreaType item) {
        groundAreas.add(item);
        updateTraction();
    }

    public void removeGroundArea(GroundAreaType item) {
        groundAreas.removeValue(item, false);
        updateTraction();
    }

    void setCharacteristics(float maxForwardSpeed, float maxBackwardSpeed,
        float maxDriveForce, float maxLateralImpulse, float breakingForcePourcentage) {
        this.maxForwardSpeed = maxForwardSpeed;
        this.maxBackwardSpeed = maxBackwardSpeed;
        this.maxDriveForce = maxDriveForce;
        this.maxLateralImpulse = maxLateralImpulse;
        this.breakingForcePourcentage = breakingForcePourcentage;
    }
    
    void updateTraction() {
        if (groundAreas.size == 0) {
            GameScreen.car.body.setLinearVelocity(GameScreen.car.body.getLinearVelocity().scl(0.3f));
            if(GameScreen.getTwoPlayers()){
            GameScreen.car2.body.setLinearVelocity(GameScreen.car2.body.getLinearVelocity().scl(0.3f));
            }
            currentTraction = 0.2f;
            return;
	}
        
        currentTraction = 0;
        
        for (GroundAreaType groundType : groundAreas) {
            if (groundType.frictionModifier > currentTraction) {
		currentTraction = groundType.frictionModifier;
            }
	}
    }

    public Vector2 getLateralVelocity() {
            Vector2 currentRightNormal = body.getWorldVector(new Vector2(1, 0));
            return CarMath.multiply(
                            currentRightNormal.dot(body.getLinearVelocity()),
                            currentRightNormal);
    }

    public Vector2 getForwardVelocity() {
        Vector2 currentForwardNormal = body.getWorldVector(new Vector2(0, 1));
        return CarMath.multiply(
            currentForwardNormal.dot(body.getLinearVelocity()),
            currentForwardNormal);
    }

    public void updateFriction() {
        Vector2 lat = CarMath.minus(getLateralVelocity());
        Vector2 impulse = CarMath.multiply(body.getMass(), 
                CarMath.minus(getLateralVelocity()));
        
        if (impulse.len() > maxLateralImpulse) {
            impulse = CarMath.multiply(impulse, maxLateralImpulse / impulse.len());
        }
        
        body.applyLinearImpulse(CarMath.multiply(currentTraction, impulse), 
                body.getWorldCenter(), true);
        body.applyAngularImpulse(currentTraction * 0.1f * body.getInertia()
                * -body.getAngularVelocity(), true);

        Vector2 currentForwardNormal = getForwardVelocity();
        float currentForwardSpeed = CarMath.normalize(currentForwardNormal);
        float dragForceMagnitude = -2 * currentForwardSpeed;
        body.applyForce(CarMath.multiply(currentTraction * dragForceMagnitude,
                currentForwardNormal), body.getWorldCenter(), true);
    }

    void updateDrive(HashSet<Key> keys) {
        float desiredSpeed = 0;
        
        /** if player 1 */
        if (playerNum == 1) {
            if(keys.contains(Key.Up)){
                desiredSpeed = maxForwardSpeed;
            } else if(keys.contains(Key.Down)){
                desiredSpeed = maxBackwardSpeed;
            }
            else {
                return;
            }
        }
        
         /** if player 2 */
        if (playerNum == 2) {
            if(keys.contains(Key.w)){
                desiredSpeed = maxForwardSpeed;
            } else if(keys.contains(Key.s)){
                desiredSpeed = maxBackwardSpeed;
            }
            else {
                return;
            }
        }        

        Vector2 currentForwardNormal = body.getWorldVector(new Vector2(0, 1));
        float currentSpeed = getForwardVelocity().dot(currentForwardNormal);

        float force = 0;
        if(GameScreen.car.getFuelTank() > 0){
        if (desiredSpeed > currentSpeed) {
            force = maxDriveForce;
        } else if (desiredSpeed < currentSpeed) {
            force = (-maxDriveForce)*breakingForcePourcentage;
        } else {
            return;
        }
        }
        else if(GameScreen.car.getFuelTank() < 0){
            force = 0;
        }
        body.applyForce(
            CarMath.multiply(currentTraction * force, currentForwardNormal),
                body.getWorldCenter(), true);
    }

    void updateTurn(CarMoves moves){
        float desiredTorque = 0;
         /** if player 1 */
        if (playerNum == 1) {
            switch(moves){
                case Left : desiredTorque = 15; break;
                case Right: desiredTorque = -15; break;
                default: return;
            }
        }
        /** if player 2 */
        if (playerNum == 2) {
            switch(moves){
                case A: desiredTorque = 15; break;
                case D: desiredTorque = -15; break;
                default: return;
            }
        }        

        body.applyTorque(desiredTorque, true);
    }
    
    public void setAngle(float angle){
        body.applyTorque(90, true);
    }
    
}
