/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import static Test.fixtureUserDataType.FUD_CAR_TIRE;

/**
 *
 * @author ROSY
 */
public enum fixtureUserDataType {
    FUD_CAR_TIRE,
    FUD_GROUND_AREA 
}

//a class to allow subclassing of different fixture user data
class FixtureUserData {
    fixtureUserDataType type;
    
    public FixtureUserData() {}
    
    protected FixtureUserData(fixtureUserDataType type) {
        this.type = type;
    }
    public fixtureUserDataType getType() {
        return type;
    }
}

class CarTireFUD extends FixtureUserData {
    public CarTireFUD() {
    
    }
}

class GroundAreaFUD {
    public float frictionModifier;
    public boolean outOfCourse;

    public GroundAreaFUD(float fm, boolean ooc) {
        frictionModifier = fm;
        outOfCourse = ooc;
    }
}