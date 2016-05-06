/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.RacingGame;

/**
 *
 * @author ROSY
 */
public class PauseScreen implements Screen{
    private RacingGame game;
    private Image image;
    private Texture pauseTexture;
    
    public PauseScreen(RacingGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        pauseTexture = new Texture("");
        image = new Image();
    }

    @Override
    public void render(float f) {
    }

    @Override
    public void resize(int width, int height) {
        
        

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
    
}
