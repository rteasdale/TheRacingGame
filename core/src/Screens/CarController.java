/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import car.Car;
import java.util.concurrent.Callable;

/**
 *
 * @author ROSY
 */
public class CarController {
    public Callable<Void> pushAccelerator;
    public Callable<Void> releaseAccelerator;
    
    public CarController(final Car car) {

    }
}
