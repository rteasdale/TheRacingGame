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
		Up, Down, Right, Left,Shift,
                                            W, S, D, A
	}

	public HashSet<Key> pressedKeysP1 = new HashSet<Key>();
        public HashSet<Key> pressedKeysP2 = new HashSet<Key>();

	public void update(){
		mainClass.car.update(pressedKeysP1);
                mainClass.car2.update(pressedKeysP2);
	}
	
	@Override
	public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) { 
                Gdx.app.exit();
                }
            else if (keycode == (Input.Keys.UP | Input.Keys.W)) {
                    if (!pressedKeysP1.contains(Key.Up) | !pressedKeysP2.contains(Key.W)) {
                            pressedKeysP1.add(Key.Up);
                            pressedKeysP2.add(Key.W);
                    }
            } else if (keycode == (Input.Keys.DOWN | Input.Keys.S)) {
                    if (!pressedKeysP1.contains(Key.Down) | !pressedKeysP2.contains(Key.S)) {
                            pressedKeysP1.add(Key.Down);
                            pressedKeysP2.add(Key.S);
                    }
            } else if (keycode == (Input.Keys.LEFT | Input.Keys.A)) {
                    if (!pressedKeysP1.contains(Key.Left) | !pressedKeysP2.contains(Key.A)) {
                            pressedKeysP1.add(Key.Left);
                            pressedKeysP2.add(Key.A);
                    }
            } else if (keycode == (Input.Keys.RIGHT | Input.Keys.D)) {
                    if (!pressedKeysP1.contains(Key.Right) | !pressedKeysP2.contains(Key.D)) {
                            pressedKeysP1.add(Key.Right);
                            pressedKeysP2.add(Key.D);
                    }
            }else if (keycode== Input.Keys.SHIFT_RIGHT){
                if(!pressedKeysP1.contains(Key.Shift) | !pressedKeysP2.contains(Key.Shift)){
                pressedKeysP1.add(Key.Shift);
                pressedKeysP2.add(Key.Shift);
            }
              }
            return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.UP) {
			if (pressedKeysP1.contains(Key.Up) | pressedKeysP2.contains(Key.Up)) {
				pressedKeysP1.remove(Key.Up);
                                pressedKeysP2.remove(Key.W);
			}
		} else if (keycode == Input.Keys.DOWN) {
			if (pressedKeysP1.contains(Key.Down) | pressedKeysP2.contains(Key.S)) {
				pressedKeysP1.remove(Key.Down);
                                pressedKeysP2.remove(Key.S);
			}
		} else if (keycode == Input.Keys.LEFT) {
			if (pressedKeysP1.contains(Key.Left) | pressedKeysP2.contains(Key.Up)) {
				pressedKeysP1.remove(Key.Up);
                                pressedKeysP2.remove(Key.W);
			}
		} else if (keycode == Input.Keys.RIGHT) {
			if (pressedKeysP1.contains(Key.Right)) {
				pressedKeysP1.remove(Key.Right);
			}
		}else if (keycode== Input.Keys.SHIFT_RIGHT){
                if(pressedKeysP1.contains(Key.Shift)){
                    pressedKeysP1.remove(Key.Shift);
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
                mainClass.camera.zoom += amount / 25f;
                return true;
            }

}
