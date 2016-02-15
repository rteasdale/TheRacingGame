/*
 * ApplicationListener 
 */
package Test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.sin;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author ROSY
 */
public class Test2 implements ApplicationListener {
private SpriteBatch batch;
private Texture evilTexture;
private Sprite evil;
    
    @Override
    public void create() {
        float h = Gdx.graphics.getHeight();
        float w = Gdx.graphics.getWidth();
        
        batch = new SpriteBatch();
        FileHandle evilFileHandle = Gdx.files.internal("car.png");
        evilTexture = new Texture(evilFileHandle);
        
        evil = new Sprite(evilTexture);
        
        evil.setPosition(w/2 - evil.getWidth()/2, h/2 - evil.getHeight()/2); //set position
    }

    @Override
    public void resize(int width, int height) {
    }

    void moveSprite() {
            /** move sprite */
    
    if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            evil.translateX(-1f);
        } else {
            evil.translateX(-10.0f);
        }
    }
    
    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)) {
                evil.translateX(1f);
        } else {
                evil.translateX(10.0f);
        }
    }
    
    if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            evil.translateY(10.0f);
        } else {
            evil.translateY(10.0f);
        }
    }      
    
    if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            evil.translateY(-10.0f);
        } else {
            evil.translateY(-10.0f);
        }
    }
    
    }
    
    @Override
    public void render() {
    Gdx.gl.glClearColor(1, 1, 1, 1); //change background color to white
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    moveSprite(); 
     
    Vector2 carLocationX;
    float carHeading = 40;
    float carSpeed = 200;
    float steerAngle = 70;
    float wheelBase = 20; // the distance between the two axles
  
    Vector2 frontWheelX = carLocationX + wheelBase/2 * cos(carHeading);
    
    Vector2 backWheel = carLocation - wheelBase/2 * new Vector2(cos(carHeading), sin(carHeading));
    
    batch.begin();
    evil.draw(batch);
    batch.end();
    
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        evilTexture.dispose();
    }
    
}
