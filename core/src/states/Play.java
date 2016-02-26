/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import Test.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import handlers.GameStateManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import static handlers.B2DVars.PPM; 
//Static variable for the ratio (#pixel/meter) since Box2D works in mks (meter/kilogram/second) (PPM = Pixel per Meter)
//To apply the ration, divide everything by the PPM value

/**
 *
 * @author Administrateur
 */
public class Play extends GameState{
    
    private World world;
    private Box2DDebugRenderer b2dr;
    
    private OrthographicCamera b2dCam;
    
    public Play(GameStateManager gsm) {
        
        super(gsm);
        
        world = new World(new Vector2(0,-9.81f), true);
        b2dr = new Box2DDebugRenderer();
        
        //create platform
        BodyDef bdef = new BodyDef();
        bdef.position.set(160/ PPM,120 / PPM);
        bdef.type = BodyType.StaticBody;
        Body body = world.createBody(bdef);
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50 / PPM, 5 / PPM);
        
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        body.createFixture(fdef);
        
        //Static body - don't move, unnaffected by forces (ground)
        
        //Kinematic body - dont get affected forces (platform)
        
        // dynamic body - always get affected by forces (player)
        
        
        
        //create falling box
        bdef.position.set(160/PPM,200/PPM);
        bdef.type = BodyType.DynamicBody;
        body = world.createBody(bdef);
        
        shape.setAsBox(5/PPM,5/PPM);
        fdef.shape = shape;
        fdef.restitution = 1;
        body.createFixture(fdef);
        
        // set up box2d cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT/PPM);
        
    }
    
    public void handleInput() {}
    
    public void update(float dt) {
    
        world.step(dt, 6, 2);
    
    }
    
    public  void render() {
        
        //clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
                // draw box2d world
        b2dr.render(world, b2dCam.combined);
        
    }
    
    public void dispose() {}
    
    
}
