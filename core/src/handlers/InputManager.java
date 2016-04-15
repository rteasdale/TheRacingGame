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
            w, s, d, a
    }

    public HashSet<Key> pressedKeys = new HashSet<Key>();
    public HashSet<Key> pressedKeys2 = new HashSet<Key>();

    public void update(boolean twoPlayers){
        mainClass.car.update(pressedKeys);
        if (twoPlayers == true) {
            mainClass.car2.update(pressedKeys2);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        /**Arrows*/
        if (keycode == Input.Keys.UP) {
            if (!pressedKeys.contains(Key.Up)) {
                
                if(GameScreen.car.getFuelTank() >= 0){
                    pressedKeys.add(Key.Up);
                    GameScreen.car.setIsAccelerating(true);
                }
                
                else if(GameScreen.car.getFuelTank() <= 0){
                    //Do Something When The Tank is empty For P1
                }
                
            }
        }     
        else if (keycode == Input.Keys.DOWN) {
            if (!pressedKeys.contains(Key.Down)) {
                
                if(GameScreen.car.getFuelTank() >= 0){
                    pressedKeys.add(Key.Down);
                    GameScreen.car.setIsAccelerating(true);
                }
                
                else if(GameScreen.car.getFuelTank() <= 0){
                    //Do Something When The Tank is empty For P1
                }
                
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
        /**ASDW*/
        else if (keycode == Input.Keys.W) {
            if (!pressedKeys2.contains(Key.w)) {
                
                if(GameScreen.car2.getFuelTank() >= 0){
                    pressedKeys.add(Key.w);
                    GameScreen.car.setIsAccelerating(true);
                }
                
                else if(GameScreen.car2.getFuelTank() <= 0){
                    //Do Something When The Tank is empty For P2
                }
                
            }
        } else if (keycode == Input.Keys.S) {
            if (!pressedKeys2.contains(Key.s)) {
                
                if(GameScreen.car2.getFuelTank() >= 0){
                    pressedKeys.add(Key.s);
                    GameScreen.car.setIsAccelerating(true);
                }
                
                else if(GameScreen.car2.getFuelTank() <= 0){
                    //Do Something When The Tank is empty For P2
                }
                
            }
        } else if (keycode == Input.Keys.A) {
            if (!pressedKeys2.contains(Key.a)) {
                pressedKeys2.add(Key.a);
            }
        } else if (keycode == Input.Keys.D) {
            if (!pressedKeys2.contains(Key.d)) {
                pressedKeys2.add(Key.d);
            }
        } else if(keycode == Input.Keys.ESCAPE){
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
         else if(keycode == Input.Keys.Z){
            System.out.println("Camera zoom");
            System.out.println("x : " + GameScreen.camera.zoom);
        }
         else if(keycode == Input.Keys.F){
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
            mainClass.camera.zoom += amount / 100f;
            return true;
        }
        else {
            return true;
        }
    }
}


