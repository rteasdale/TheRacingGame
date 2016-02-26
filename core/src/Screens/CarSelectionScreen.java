/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.mygdx.game.RacingGame;
import javafx.scene.control.ComboBox;

/**
 *
 * @author ROSY
 */
public class CarSelectionScreen implements Screen {
    private final RacingGame game;
    private Stage stage;
    private TextureRegion bgTexture;
    
    private Image title;
    private Button selectNextCarButton;
    private Button selectPreviousCarButton;
    private TextArea carDescriptionBox;
    private ComboBox selectCarColor;
    
    public CarSelectionScreen(RacingGame game) {
        this.game = game;
        bgTexture = new TextureRegion();
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float f) {

    }

    @Override
    public void resize(int i, int i1) {

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
