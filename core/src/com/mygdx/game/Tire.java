/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author ROSY
 */
public class Tire {
    
    public Body body;
    private BodyDef bdef;
    private PolygonShape shape;
    
    private final int LEFT = -3;
    private final int RIGHT = -2;
    private final int DOWN = -1;
    private final int HSTOP = 0;
    private final int UP = 1;
    private float PosTorque = 30;
    private float NegTorque = -30;

    float maxForwardSpeed;
    float maxBackwardSpeed;
    float maxDriveForce;
    float maxLateralImpulse;
    float currentTraction;
    
    public Tire(World world)  {
        bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        
        shape = new PolygonShape();
        shape.setAsBox(.2f, .4f);
        body.createFixture(shape, .5f); //create fixture with shape and density
    }
    
    public Tire() {
        body.getWorld().destroyBody(body);
    }
    
    public void setCharacteristics(float maxForwardSpeed, float maxBackwardSpeed, float maxDriveForce, float maxLateralImpulse) {
        this.maxForwardSpeed = maxForwardSpeed;
        this.maxBackwardSpeed = maxBackwardSpeed;
        this.maxDriveForce = maxDriveForce;
        this.maxLateralImpulse = maxLateralImpulse;        
    }
    
    /*void addGroundArea(GroundAreaFUD* ga) { m_groundAreas.insert(ga); updateTraction(); }
    void removeGroundArea(GroundAreaFUD* ga) { m_groundAreas.erase(ga); updateTraction(); }*/
    
    public Vector2 getLateralVelocity() {
        Vector2 currentRightNormal = body.getWorldVector(new Vector2(1,0));
        return currentRightNormal.scl(currentRightNormal.dot(body.getLinearVelocity()));
    }
    
    public Vector2 getForwardVelocity(){
        Vector2 currentForwardNormal = body.getWorldVector(new Vector2(0,1));
        return currentForwardNormal.scl(currentForwardNormal.dot(body.getLinearVelocity()));
    }
    
    public void updateFriction() {
        Vector2 impulse = getLateralVelocity().scl(-body.getMass());
        
        if(impulse.len() > maxLateralImpulse){
            impulse.x *= maxLateralImpulse / impulse.len();
            impulse.y *= maxLateralImpulse / impulse.len();
        }
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
        body.applyAngularImpulse(0.1f * body.getInertia() * -body.getAngularVelocity(), true);
        
        Vector2 currentForwardNormal = getForwardVelocity(); 
        float currentForwardSpeed = currentForwardNormal.nor().len(); // Basically 1
        float dragForceMagnitude = -2 * currentForwardSpeed;
        body.applyForce((currentForwardNormal.scl(dragForceMagnitude)), body.getWorldCenter(), true);
    }
    
    public void updateDrive(int controlState){
        float desiredSpeed = 0;
        if(controlState == UP){
            desiredSpeed = maxForwardSpeed;
        }
        else if(controlState == DOWN){
            desiredSpeed = maxBackwardSpeed;
        }
        else{
            return;
        }
        
        Vector2 currentForwardNormal = body.getWorldVector(new Vector2(0,1));
        float currentSpeed = getForwardVelocity().dot(currentForwardNormal);

        float force = 0;
        
        if(desiredSpeed > currentSpeed){
            force = maxDriveForce;
        }
        else if (desiredSpeed < currentSpeed){
            force = -maxDriveForce;
        }
        else{
            return;
        }
       body.applyForce(currentForwardNormal.scl(force), body.getWorldCenter(), true);
        //body.applyForce(new Vector2(x * force, y * force), body.getWorldCenter(), true);
    }
    
    public void updateTurn(int controlState){
        float desiredTorque = 0;
        switch(controlState & (LEFT | RIGHT)){
            case LEFT: desiredTorque = 15; break;
            case RIGHT: desiredTorque = -15;break;
            default: ; //nothing
        }
        body.applyTorque(desiredTorque, true);
    }    
}
