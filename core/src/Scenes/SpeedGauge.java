/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 *
 * @author ROSY
 */
public class SpeedGauge extends Actor {
    private Image needle;
    private Image gauge;
    private float speed;
    
    public SpeedGauge(float speed) {
        this.speed = speed;
        needle = new Image(new Texture("HUD/speed_needle.png"));
        gauge = new Image(new Texture("HUD/speedgauge.png"));
    }
    
    public void updateSpeed() {
        needle.rotateBy(speed*(float) Math.sin(50));
    }

}
