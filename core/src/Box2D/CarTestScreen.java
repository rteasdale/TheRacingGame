/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Box2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author ROSY
 */
public class CarTestScreen implements Screen {

    final MyGame game; 

    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    
    private World world;
    private BodyType bodyType;
    private Body body;
    
    public CarTestScreen(final MyGame game) {
        this.game = game;
    }
    
    static final float WORLD_TO_BOX = 0.01f;
    static final float BOX_WORLD_TO = 100f;

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, camera.combined);
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
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}
