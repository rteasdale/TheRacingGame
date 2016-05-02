package handlers;

import com.badlogic.gdx.Gdx;
import java.util.HashSet;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import Screens.GameScreen;

public class InputManager  implements InputProcessor  {

    private GameScreen mainClass;
    private boolean shiftMode = false;

    public InputManager(GameScreen main){
        this.mainClass = main;
    }

    public enum Key {
        Up, Down, Right, Left,Escape,
        w, s, d, a
    }

    public HashSet<Key> pressedKeys = new HashSet<Key>();
    public HashSet<Key> pressedKeys2 = new HashSet<Key>();


    public void updateControls(boolean twoPlayers){
        GameScreen.car.update(pressedKeys);
        
        if (twoPlayers == true) {
            GameScreen.car2.update(pressedKeys2);
        }
    }


    @Override
    public boolean keyDown(int keycode) {
        /**Arrows*/
        if (keycode == Input.Keys.UP) {
            if (!pressedKeys.contains(Key.Up)) {
                    pressedKeys.add(Key.Up);
                    GameScreen.car.setIsAccelerating(true);
            }
        }     
        else if (keycode == Input.Keys.DOWN) {
            if (!pressedKeys.contains(Key.Down)) {
                    pressedKeys.add(Key.Down);
                    GameScreen.car.setIsAccelerating(true);
                
            }
        } else if (keycode == Input.Keys.LEFT) {
            if (!pressedKeys.contains(Key.Left)) {
                pressedKeys.add(Key.Left);
            }
        } else if (keycode == Input.Keys.RIGHT) {
            if (!pressedKeys.contains(Key.Right)) {
                pressedKeys.add(Key.Right);
            }
        }else if(keycode == Input.Keys.ESCAPE){
            Gdx.app.exit();
        }
        
        /**ASDW*/
        if(GameScreen.twoPlayers){
           if (keycode == Input.Keys.W) {
              if (!pressedKeys2.contains(Key.w)) {
                pressedKeys2.add(Key.w);
                GameScreen.car2.setIsAccelerating(true);
            }
        } else if (keycode == Input.Keys.S) {
              if (!pressedKeys2.contains(Key.s)) {
                pressedKeys2.add(Key.s);
                GameScreen.car2.setIsAccelerating(true);
            }
        } else if (keycode == Input.Keys.A) {
              if (!pressedKeys2.contains(Key.a)) {
                pressedKeys2.add(Key.a);
            }
        } else if (keycode == Input.Keys.D) {
              if (!pressedKeys2.contains(Key.d)) {
                pressedKeys2.add(Key.d);
            }
           } 
        }
                
        
        /**Debug buttons*/ 
        if(GameScreen.debug){
         if(keycode == Input.Keys.V){
            System.out.println("Position");
            System.out.println("x : " + GameScreen.car.body.getPosition().x);
            System.out.println("y : " + GameScreen.car.body.getPosition().y);
        }
        else if(keycode == Input.Keys.B){
            System.out.println("Camera Position");
            System.out.println("x : " + GameScreen.camera.position.x);
            System.out.println("y : " + GameScreen.camera.position.y);
        }
         else if(keycode == Input.Keys.N){
            System.out.println("Camera zoom");
            System.out.println("x : " + GameScreen.camera.zoom);
        }
         
         else if(keycode == Input.Keys.M){
             if(GameScreen.car.getOnFuelPad()){
             System.out.println("onFuelPas : false");
             GameScreen.car.setOnFuelPad(false);
             }
             else if(!GameScreen.car.getOnFuelPad()){
             System.out.println("onFuelPas : true");
             GameScreen.car.setOnFuelPad(true);
             }
            }
         
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        /**Arrows*/
        if (keycode == Input.Keys.UP) {
            if (pressedKeys.contains(Key.Up)) {
                pressedKeys.remove(Key.Up);
                GameScreen.car.setIsAccelerating(false);
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
        
        /**ASDW*/
        else if (keycode == Input.Keys.W) {
            if (pressedKeys2.contains(Key.w)) {
                pressedKeys2.remove(Key.w);
            }
        }
        else if (keycode == Input.Keys.S) {
            if (pressedKeys2.contains(Key.s)) {
                pressedKeys2.remove(Key.s);
            }
        } else if (keycode == Input.Keys.A) {
            if (pressedKeys2.contains(Key.a)) {
                pressedKeys2.remove(Key.a);
            }
        } else if (keycode == Input.Keys.D) {
            if (pressedKeys2.contains(Key.d)) {
                pressedKeys2.remove(Key.d);
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
            GameScreen.camera.zoom += amount / 100f;
            
            return true;
        }
        else {
            return true;
        }
    }
    
    public void setShiftMode(boolean state){
        shiftMode = state;
    }
    
    public boolean getShiftMode(){
        return shiftMode;
    }
    
    public void disposeAll() {
        pressedKeys.clear();
        pressedKeys2.clear();
        GameScreen.car.setIsAccelerating(false);
        GameScreen.car2.setIsAccelerating(false);
    }
    
    public void disposeP1() {
        pressedKeys.clear();
        GameScreen.car.setIsAccelerating(false);        
    }
    
    public void disposeP2() {
        pressedKeys2.clear();
        GameScreen.car2.setIsAccelerating(false);        
    }

}


