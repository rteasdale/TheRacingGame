/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author Administrateur
 */
public class CarType extends FixtureUserData{
    
    public Sprite sprite;
    public Car car;
    
    public CarType(Sprite sprite, Car car){
        super(FixtureUserDataType.FUD_CAR);
                this.sprite = sprite;
                this.car = car;
    }
    
}
