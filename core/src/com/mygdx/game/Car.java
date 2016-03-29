/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.mygdx.game.Tire;
import java.util.ArrayList;

/**
 *
 * @author ROSY
 */
public class Car {
    private Sprite sprite;

    public Body body;
    public BodyDef bdef;
    public Fixture fixture;
    public PolygonShape shape;
    public FixtureDef fdef;
    
    public Tire tire1;
    public Tire tire2;
    public Tire tire3;
    public Tire tire4;

    private float PosTorque = 30;
    private float NegTorque = -30;    
    
    ArrayList<Tire> tires = new ArrayList<Tire>();
    RevoluteJoint flJoint, frJoint;
    

    public Car(World world) {
        sprite = new Sprite(new Texture("lamborghini/lamborghini_white.png"));
        //create car body
        
        bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setAngularDamping(3);
    
        shape = new PolygonShape();
        shape.setAsBox(.5f, .9f);

        fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1;
        
        //fixture = body.createFixture(shape, 1);
        
        //common joint parameters
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = body;
        jointDef.enableLimit = true;
        jointDef.lowerAngle = 0;
        jointDef.upperAngle = 0;
        jointDef.localAnchorB.setZero(); //center of tire

        float maxForwardSpeed = 250;
        float maxBackwardSpeed = -40;
        float backTireMaxDriveForce = 300;
        float frontTireMaxDriveForce = 500;
        float backTireMaxLateralImpulse = (float) 8.5;
        float frontTireMaxLateralImpulse = (float) 7.5;
        
        //back left tire
        tire1 = new Tire(world);
        tire1.setCharacteristics(maxForwardSpeed, maxBackwardSpeed, backTireMaxDriveForce, backTireMaxLateralImpulse);
        jointDef.bodyB = tire1.body;
        jointDef.localAnchorA.set(-1, -.75f);
        world.createJoint(jointDef);
        tires.add(tire1);
        
        //back right tire 
        tire2 = new Tire(world);
        tire2.setCharacteristics(maxForwardSpeed, maxBackwardSpeed, backTireMaxDriveForce, backTireMaxLateralImpulse);
        jointDef.bodyB = tire2.body;
        jointDef.localAnchorA.set(1, -.75f);
        world.createJoint(jointDef);
        tires.add(tire2);
        
        //front left tire
        tire3 = new Tire(world);
        tire3.setCharacteristics(maxForwardSpeed, maxBackwardSpeed, frontTireMaxDriveForce, frontTireMaxLateralImpulse);
        jointDef.bodyB = tire3.body;
        jointDef.localAnchorA.set( -1, .75f );
        flJoint = (RevoluteJoint)world.createJoint(jointDef);
        tires.add(tire3);
        
        //front right tire
        tire4 = new Tire(world);
        tire4.setCharacteristics(maxForwardSpeed, maxBackwardSpeed, backTireMaxDriveForce, backTireMaxLateralImpulse);
        jointDef.bodyB = tire4.body;
        jointDef.localAnchorA.set(1, .75f);
        frJoint = (RevoluteJoint)world.createJoint(jointDef);
        tires.add(tire4);
        
    }
    
    public Car() {
        for (int i = 0; i < tires.size(); i++) {
            tires.remove(i);
        }
   
    }
    
    //Clamp: http://stackoverflow.com/questions/16656651/does-java-have-a-clamp-function
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
    
    public void update(int controlState) {
        for(int i = 0; i < tires.size(); i++) {
            tires.get(i).updateFriction();
        }
        for (int i = 0; i < tires.size(); i++) {
            tires.get(i).updateDrive(controlState);
        }
        
        //control steering
        float lockAngle = (float)Math.toRadians(35);
        float turnSpeedPerSec = (float)Math.toRadians(160);
        float turnPerTimeStep = turnSpeedPerSec / 60.0f;
        float desiredAngle = 0;
        
        switch(controlState & (LEFT | RIGHT)){
            case LEFT: desiredAngle = lockAngle; break;
            case RIGHT: desiredAngle = -lockAngle;break;
            default: ; //nothing
        }
        float angleNow = flJoint.getJointAngle();
        float angleToTurn = desiredAngle - angleNow;
        angleToTurn = clamp(angleToTurn, -turnPerTimeStep, turnPerTimeStep);
        
        float newAngle = angleNow + angleToTurn;
        flJoint.setLimits(newAngle, newAngle);
        frJoint.setLimits(newAngle, newAngle);
    }
}
