package handlers;

import com.badlogic.gdx.Gdx;
import java.util.HashSet;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import Screens.GameScreen;
import car.Car;

public class InputManager  implements InputProcessor  {

    private final GameScreen mainClass;
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
        GameScreen.getCar().update(pressedKeys);
        
        if (twoPlayers == true) {
            GameScreen.getCar2().update(pressedKeys2);
        }
    }


    @Override
    public boolean keyDown(int keycode) {
        /**Arrows*/
        if (keycode == Input.Keys.UP) {
            if (!pressedKeys.contains(Key.Up)) {
                    pressedKeys.add(Key.Up);
                    GameScreen.getCar().setIsAccelerating(true);
            }
        }     
        else if (keycode == Input.Keys.DOWN) {
            if (!pressedKeys.contains(Key.Down)) {
                    pressedKeys.add(Key.Down);
                    GameScreen.getCar().setIsAccelerating(true);
                
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
        if(GameScreen.getTwoPlayers()){
           if (keycode == Input.Keys.W) {
              if (!pressedKeys2.contains(Key.w)) {
                pressedKeys2.add(Key.w);
                GameScreen.getCar2().setIsAccelerating(true);
            }
        } else if (keycode == Input.Keys.S) {
              if (!pressedKeys2.contains(Key.s)) {
                pressedKeys2.add(Key.s);
                GameScreen.getCar2().setIsAccelerating(true);
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
        if(GameScreen.getDebug()){
         if(keycode == Input.Keys.V){
            System.out.println("Position");
            System.out.println("x : " + GameScreen.getCar().body.getPosition().x);
            System.out.println("y : " + GameScreen.getCar().body.getPosition().y);
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
         
         else if(keycode == Input.Keys.M){ //This activates the fueling bubble sound since you turn OnFuelPad true
             if(GameScreen.getCar().getOnFuelPad()){
             System.out.println("onFuelPas : false");
             GameScreen.getCar().setOnFuelPad(false);
             }
             else if(!GameScreen.getCar().getOnFuelPad()){
             System.out.println("onFuelPas : true");
             GameScreen.getCar().setOnFuelPad(true);
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
                GameScreen.getCar().setIsAccelerating(false);
            }
        } else if (keycode == Input.Keys.DOWN) {
            if (pressedKeys.contains(Key.Down)) {
                pressedKeys.remove(Key.Down);
                GameScreen.getCar().setIsAccelerating(false);
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
                GameScreen.getCar2().setIsAccelerating(false);
            }
        }
        else if (keycode == Input.Keys.S) {
            if (pressedKeys2.contains(Key.s)) {
                pressedKeys2.remove(Key.s);
                GameScreen.getCar2().setIsAccelerating(false);
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
        if(GameScreen.getDebug()){
            GameScreen.camera.zoom += amount / 100f;
            
            return true;
        }
        else {
            return true;
        }
    }

    
    public void disposeAll(Car car) {
        pressedKeys.clear();
        pressedKeys2.clear();
        car.setIsAccelerating(false);
        //GameScreen.car2.setIsAccelerating(false);
    }
    
    public void disposeP1(Car car) {
        pressedKeys.clear();
        car.setIsAccelerating(false);        
    }
    
    public void disposeP2(Car car) {
        pressedKeys2.clear();
        car.setIsAccelerating(false);        
    }

}


