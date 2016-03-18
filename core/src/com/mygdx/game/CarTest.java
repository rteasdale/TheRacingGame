/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.util.Arrays;

/**
 *
 * @author Administrateur
 */

public class CarTest implements ApplicationListener{
    public static void main(String[]args) {
    }

    @Override
    public void create() {
        Car car = new Car();
        String[] porsche = car.getFordData();
        
        Car porscheCar = new Car(porsche);
        
        System.out.println(porscheCar.getWEIGHT_STAT());
        System.out.println(porscheCar.getACCELERATION_STAT());
        System.out.println(porscheCar.getTOPSPEED_STAT());
        System.out.println(porscheCar.getHANDLING_STAT());
        System.out.println(porscheCar.getTANK_CAPACITY_STAT());
        System.out.println(porscheCar.getFUEL_CONSUMPTION_STAT());
        
        System.out.println(porscheCar.toString());
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