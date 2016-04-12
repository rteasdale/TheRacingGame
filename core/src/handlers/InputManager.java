package handlers;

import com.badlogic.gdx.Gdx;
import java.util.HashSet;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import Screens.GameScreen;

public class InputManager  implements InputProcessor  {

    GameScreen mainClass;

    public InputManager(GameScreen main){
            this.mainClass = main;
    }

    public enum Key {
            Up, Down, Right, Left,Escape,
            W, S, D, A
    }

    public HashSet<Key> pressedKeys = new HashSet<Key>();

    public void update(){
        mainClass.car.update(pressedKeys);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP) {
                if (!pressedKeys.contains(Key.Up)) {
                        pressedKeys.add(Key.Up);
                }
        } else if (keycode == Input.Keys.DOWN) {
                if (!pressedKeys.contains(Key.Down)) {
                        pressedKeys.add(Key.Down);
                }
        } else if (keycode == Input.Keys.LEFT) {
                if (!pressedKeys.contains(Key.Left)) {
                        pressedKeys.add(Key.Left);
                }
        } else if (keycode == Input.Keys.RIGHT) {
                if (!pressedKeys.contains(Key.Right)) {
                        pressedKeys.add(Key.Right);
                }
        }
        else if(keycode == Input.Keys.ESCAPE){
            Gdx.app.exit();
        }
        
        if(GameScreen.debug){
         if(keycode == Input.Keys.P){
            System.out.println("Position");
            System.out.println("x : " + GameScreen.car.body.getPosition().x);
            System.out.println("y : " + GameScreen.car.body.getPosition().y);
        }
        else if(keycode == Input.Keys.C){
            System.out.println("Camera Position");
            System.out.println("x : " + GameScreen.camera.position.x);
            System.out.println("y : " + GameScreen.camera.position.y);
        }
         
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.UP) {
                if (pressedKeys.contains(Key.Up)) {
                        pressedKeys.remove(Key.Up);
                }
        } else if (keycode == Input.Keys.DOWN) {
                if (pressedKeys.contains(Key.Down)) {
                        pressedKeys.remove(Key.Down);
                }
        } else if (keycode == Input.Keys.LEFT) {
                if (pressedKeys.contains(Key.Left)) {
                        pressedKeys.remove(Key.Left);
                }
        } else if (keycode == Input.Keys.RIGHT) {
                if (pressedKeys.contains(Key.Right)) {
                        pressedKeys.remove(Key.Right);
                }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
            // TODO Auto-generated method stub
            return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            // TODO Auto-generated method stub
            return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            // TODO Auto-generated method stub
            return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
            // TODO Auto-generated method stub
            return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
            // TODO Auto-generated method stub
            return false;
    }
    
    @Override
    public boolean scrolled(int amount) {
        if(GameScreen.debug){
        mainClass.camera.zoom += amount / 25f;
        return true;
        }
        else {
            return true;
        }
    }
}


