/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;

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
        String priusInfo = car.getPriusData();
        System.out.print(priusInfo);
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
