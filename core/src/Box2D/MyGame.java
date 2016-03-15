/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Box2D;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Car;


/**
 *
 * @author ROSY
 */
public class MyGame extends Game {
    
    public SpriteBatch batch; 
    public BitmapFont font;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new CarTestScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
