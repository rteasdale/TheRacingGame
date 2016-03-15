/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import java.util.Arrays;

/**
 *
 * @author Administrateur
 */

public class CarTest{
    public static void main(String[]args)
    {
        Car car = new Car();
    //   car.createCarInstance();
       
        String[] carArray = car.zondaProperties;
        System.out.println(carArray[22]);
    }
}