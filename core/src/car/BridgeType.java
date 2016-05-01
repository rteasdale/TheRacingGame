/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car;

/**
 *
 * @author Administrateur
 */
public class BridgeType extends FixtureUserData {

public float frictionModifier;
boolean outOfCourse;
    
    public BridgeType(float frictionModifier, boolean outOfCourse) {
        super(FixtureUserDataType.FUD_BRIDGE);
        this.frictionModifier = frictionModifier;
        this.outOfCourse = outOfCourse;
    }
    
}
