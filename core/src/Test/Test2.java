/*
 * ApplicationListener 
 */
package Test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 *
 * @author ROSY
 */
public class Test2 implements ApplicationListener {
       FileHandle file;
        String text;
    public static void main(String[] args) {
            
    }

    @Override
    public void create() {
        file = Gdx.files.internal("data/carData.txt");
        text = file.readString();
        System.out.println(text);
    }

    @Override
    public void resize(int i, int i1) {
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
