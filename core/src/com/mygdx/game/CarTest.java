/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import car.Car;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Array;
import java.util.Arrays;

/**
 *
 * @author Administrateur
 */

public class CarTest implements ApplicationListener{
    
    Array nodes; 
    
    public static void main(String[]args) {
    }

    @Override
    public void create() {
        Car2 car = new Car2();
        String[] porsche = car.getFordData();
        
        Car2 porscheCar = new Car2(porsche);
        
        System.out.println(porscheCar.getWEIGHT_STAT());
        System.out.println(porscheCar.getACCELERATION_STAT());
        System.out.println(porscheCar.getTOPSPEED_STAT());
        System.out.println(porscheCar.getHANDLING_STAT());
        System.out.println(porscheCar.getTANK_CAPACITY_STAT());
        System.out.println(porscheCar.getFUEL_CONSUMPTION_STAT());
        
        System.out.println(porscheCar.toString());
        
        Node n1 = new Node(null);
        
        nodes.add(car);
        
        Graph g = new Graph() {

            @Override
            public Array getConnections(Object n) {
                return nodes; 
            }
        };
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