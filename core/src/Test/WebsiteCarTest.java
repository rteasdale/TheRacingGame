/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 *
 * @author Administrateur
 */


public class WebsiteCarTest extends ApplicationAdapter {
    private double speed = 0;
  
    private double maxSpeed = 8;
    private double maxRevSpeed = -3;
    private double speedDeceleration = .90;
    private double speedAcceleration = .15;
    private double groundFriction = .95;
    
    private double steering = 0;
    private double maxSteering = 2;
    private double steeringAcceleration = .10;
    private double steeringFriction = .98;
    
    private double Xvelocity = 0;
    private double Yvelocity = 0;
    
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    
    private Stage stage;
    private Skin skin;
    private Image image;
    @Override
    public void create () {
        stage = new Stage(new ScreenViewport());
        FileHandle evilFileHandle = Gdx.files.internal("evil.png");

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));


        image = new Image(new Texture("shark.png"));
        image.setX(100);
        image.setY(100);

        final TextButton button = new TextButton("Click Me", skin, "default");
        button.setWidth(200);
        button.setHeight(50);

        final Dialog dialog = new Dialog("Click Message", skin);

        stage.addActor(button);
        stage.addActor(image);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render () {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
    }
}
