/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.RacingGame;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.RacingGame;

/**
 *
 * @author ROSY
 */
public class TestCarMoving implements Screen {

  private RacingGame game;
private World world;
private Box2DDebugRenderer debugRenderer;
private OrthographicCamera camera;

   public TestCarMoving(RacingGame game) {
        this.game = game;
    }
    
    @Override
    public void show() {
        world = new World(new Vector2(0,0) , true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        //Body Def
        BodyDef ballDef = new BodyDef();
        ballDef.type = BodyDef.BodyType.DynamicBody;
        ballDef.position.set(0, 1);
        
        //ball shape
        CircleShape shape = new CircleShape();
        shape.setRadius(0.25f);
        
        //Fixture Def
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = 0.25f;         //Between 0 and 1;    1 = Max friction (100% friction);  0 = No friction (0% friction)
        fixtureDef.restitution = .75f;
        
        world.createBody(ballDef).createFixture(fixtureDef);
        
        shape.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        
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
