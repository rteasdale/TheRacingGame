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

public class CarTest implements ApplicationListener {
    public static void main(String[]args)
    {
        
    }

    @Override
    public void create() {
        Car car = new Car();
        System.out.print(car.toString());
        String[] prius = car.getGolfData();
        System.out.print(prius[0]);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
     
    }

    @Override
    public void pause() {
      
    }
    @Override
    public void resume() {
      
    }

    @Override
    public void dispose() {
        
    }

}
