/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.mygdx.game.Tire;

/**
 *
 * @author ROSY
 */
public class Car {
    private World world;
    private Texture bg;
    private OrthographicCamera camera;
    private final float PIXELS_TO_METERS = 32;

    private Body body;
    private BodyDef bdef;
    private Fixture fixture;
    private PolygonShape boxShape;
    private FixtureDef fixDef;
    
    RevoluteJoint jointDef1, jointDef2;
    Vector<Tire> tires;

    public Car(World world) {
        bdef = new BodyDef();
        body = world.createBody(bdef);
        body.setAngularDamping(.99f);
        boxShape.setAsBox(.5f , 1.25f);
        
        fixture = body.createFixture(boxShape, 1);
        
        
        body = jointDef1.getBodyA();
        jointDef1.enableLimit(true);
        jointDef1.setLimits(0, 0);
        jointDef1.getLocalAnchorB();

        float maxForwardSpeed = 250;
        float maxBackwardSpeed = -40;
        float backTireMaxDriveForce = 300;
        float frontTireMaxDriveForce = 500;
        float backTireMaxLateralImpulse = (float) 8.5;
        float frontTireMaxLateralImpulse = (float) 7.5;
        
        //back left tire
        Tire tire1 = new Tire(world);
        tire1.getCharacteristics(maxForwardSpeed, maxBackwardSpeed, backTireMaxDriveForce, backTireMaxLateralImpulse);
        jointDef1.getBodyB() = tire.body;
    }
    
}
