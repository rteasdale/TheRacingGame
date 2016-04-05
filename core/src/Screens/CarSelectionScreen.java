/*
*NOTE: DO NOT INITIALIZE EVERYTHING IN CREATE, CHECK IF REALLY NECESSARY
 */
package Screens;

import Scenes.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.RacingGame;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ROSY
 */
public class CarSelectionScreen implements Screen{
    private RacingGame game;

    private OrthographicCamera camera;
    private Stage stage;
    private BitmapFont font;
    private Hud hud;
    
    private boolean twoPlayers;
    private FileHandle file;
    String[] group;
    String[] car;
    String[] previews;
    private int currentCar = 1;
    private int i = 0;
    
    private Image title;
    private ImageButton selectNextCarButton;
    private ImageButton selectPreviousCarButton;
    
    private Rectangle weight_lvl;
    private Rectangle accel_lvl;
    private Rectangle top_speed_lvl;
    private Rectangle handling_lvl;
    private Rectangle tank_lvl;
    private Rectangle consumption_lvl;

    private ImageButton next_btn;
    private ImageButton back_btn;
    private ImageButtonStyle next_style;
    private ImageButtonStyle back_style;
    
    private Image preview;
    private Image logo_preview;
    
    private TextArea carDescription;
    private TextField.TextFieldStyle txt_style;
    private SelectBox<String> color_select; 
    private SelectBox.SelectBoxStyle box_style;
    
    private TextureAtlas buttons_atlas;
    private TextureAtlas box_atlas;
    private Skin buttons_skin;
    private Skin box_skin;
     
   

    private ImageButtonStyle nextcar_style;
    private ImageButtonStyle prevcar_style;
    private Label.LabelStyle lbl_style;
    
    private Image weight;
    private Image acceleration;
    private Image top_speed;
    private Image handling;
    private Image tank_capacity;
    private Image consumption;
    
    private Label weight_lbl;
    private Label accel_lbl;
    private Label top_speed_lbl;
    private Label handling_lbl;
    private Label capacity_lbl;
    private Label consumption_lbl;
    
    public CarSelectionScreen(RacingGame game, boolean twoPlayers) {
        Gdx.app.log("CarSelection", "constructor called");
        this.game = game;
        this.twoPlayers = twoPlayers;
        
        //hud = new Hud(game.batch);
        
        file = new FileHandle("data/car.txt");
        group = file.readString().split("\n");
        car = group[0].split(",");
        previews = group[1].split(",");
                
//        System.out.println(Arrays.toString(car));
//        System.out.println(group[1]);
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//
        
        /** BitmapFont */
        font = new BitmapFont(Gdx.files.internal("menu/button_font.fnt"), Gdx.files.internal("menu/button_font.png"),false);

        /** Atlas and skin */
        buttons_atlas = new TextureAtlas(Gdx.files.internal("menu/menubtns_atlas.txt"));
        buttons_skin= new Skin(buttons_atlas);

        box_atlas = new TextureAtlas(Gdx.files.internal("menu/box_atlas.txt"));
        box_skin = new Skin(box_atlas);

        /** Styles */
        next_style = new ImageButtonStyle(buttons_skin.getDrawable("next_button"), null, null, null, null, null);
        back_style = new ImageButtonStyle(buttons_skin.getDrawable("back_button"), null, null, null, null, null);
        nextcar_style = new ImageButtonStyle(buttons_skin.getDrawable("nextarrow_small"), null, null, null, null, null);
        prevcar_style = new ImageButtonStyle(buttons_skin.getDrawable("backarrow_small"), null, null, null, null, null);
        
        box_style = new SelectBox.SelectBoxStyle();
        box_style.background = box_skin.getDrawable("select_box1");
        box_style.font = font;
        box_style.fontColor = new Color(Color.YELLOW);
        box_style.listStyle = new List.ListStyle(font, new Color(Color.GOLD), new Color(Color.WHITE), box_skin.getDrawable("select_box1"));
        box_style.scrollStyle = new ScrollPane.ScrollPaneStyle();
        box_style.scrollStyle.background = box_skin.getDrawable("select_box2");
        box_style.scrollStyle.corner = box_skin.getDrawable("select_box1");
        box_style.scrollStyle.vScroll = box_skin.getDrawable("select_box2");
        box_style.scrollStyle.hScroll = box_skin.getDrawable("select_box2");
        
        txt_style = new TextField.TextFieldStyle();
        txt_style.font = font;
        txt_style.fontColor = new Color(Color.WHITE);
        txt_style.background = box_skin.getDrawable("text_box");
        
        lbl_style = new Label.LabelStyle();
        lbl_style.font = font;
        lbl_style.fontColor = new Color(Color.WHITE);
        
    }

    @Override
    public void show() {
        Gdx.app.log("CarSelection", "show called");
        Gdx.app.log("Current car", car[i]);
        Gdx.app.log("Next car", car[i+=2]);
        i = 0;
        /** Title */
        title = new Image(new Texture(Gdx.files.internal("menu/carselection_title.png")));
        title.setPosition(280, 648);
        
        /** SelectBox and TextArea*/
        color_select = new SelectBox(box_style);
        color_select.setItems("-- Select Car Color --", "Blue", "Dark Blue", "Green", "Orange", "Purple", "Red", "White", "Yellow");
        color_select.setPosition(200, 300);
        color_select.setSize(432, 40);

        carDescription = new TextArea("Description", txt_style);
        carDescription.setPosition(200,170);
        carDescription.setSize(432, 120);
        carDescription.setText(car[i]);
        
        /** Preview */
        preview = new Image(new Texture(Gdx.files.internal("golf/golf_white.png")));
        preview.setPosition(530, 450);
        preview.sizeBy(52, 100);
        preview.rotateBy(90);
        
        //logo_preview = new Image(new Texture(Gdx.files.internal("")));

        /** Buttons */
        next_btn = new ImageButton(next_style);
        next_btn.setPosition(1016, 24);
        back_btn = new ImageButton(back_style);
        back_btn.setPosition(24, 24);
        
        selectPreviousCarButton = new ImageButton(prevcar_style);
        selectPreviousCarButton.setX(24);
        selectPreviousCarButton.setY(480);
        selectNextCarButton = new ImageButton(nextcar_style);
        selectNextCarButton.setX(750);
        selectNextCarButton.setY(480);

        /** Car stats */
        weight = new Image(new Texture(Gdx.files.internal("menu/car_stat.png")));
        weight.setPosition(850, 550);
        acceleration = new Image(new Texture(Gdx.files.internal("menu/car_stat.png")));
        acceleration.setPosition(850, 480);
        top_speed = new Image(new Texture(Gdx.files.internal("menu/car_stat.png")));
        top_speed.setPosition(850, 410);
        handling = new Image(new Texture(Gdx.files.internal("menu/car_stat.png")));
        handling.setPosition(850, 340);
        tank_capacity = new Image(new Texture(Gdx.files.internal("menu/car_stat.png")));
        tank_capacity.setPosition(850, 270);
        consumption = new Image(new Texture(Gdx.files.internal("menu/car_stat.png")));
        consumption.setPosition(850, 200);
            
        /**Labels */
        weight_lbl = new Label("Weight", lbl_style);
        weight_lbl.setPosition(855, 570);
        accel_lbl = new Label("Acceleration", lbl_style);
        accel_lbl.setPosition(855, 500);
        top_speed_lbl = new Label("Top Speed", lbl_style);
        top_speed_lbl.setPosition(855, 430);
        handling_lbl = new Label("Handling", lbl_style);
        handling_lbl.setPosition(855, 360);
        capacity_lbl = new Label("Tank Capacity", lbl_style);
        capacity_lbl.setPosition(855, 290);
        consumption_lbl = new Label("Fuel Consumption", lbl_style);
        consumption_lbl.setPosition(855, 220);
        

        stage.addActor(weight);
        stage.addActor(acceleration);
        stage.addActor(handling);
        stage.addActor(top_speed);
        stage.addActor(tank_capacity);
        stage.addActor(consumption);
        
        stage.addActor(weight_lbl);
        stage.addActor(accel_lbl);
        stage.addActor(top_speed_lbl);
        stage.addActor(handling_lbl);
        stage.addActor(capacity_lbl);
        stage.addActor(consumption_lbl);
        
        stage.addActor(preview);
        stage.addActor(carDescription);
        stage.addActor(color_select);
        
        stage.addActor(selectNextCarButton);
        stage.addActor(selectPreviousCarButton);
        stage.addActor(next_btn);
        stage.addActor(back_btn);
        stage.addActor(title);
        
        /** Listeners */
        back_btn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                if (twoPlayers == true) {
                    
                }
            }
        });
        
        next_btn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                if (twoPlayers == true) {
                    
                }
            }
        });   
        
        /**Color*/
        color_select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.log("color selected:", color_select.getSelected());
                if (color_select.getSelected().equals("Blue")) {
                    
                }
            }
        });
        
        /** Car type*/
        selectNextCarButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (i < 6) {
                    Gdx.app.log("Current car", car[i]);
                    i++;
                    currentCar++;
                    carDescription.setText(car[i]);
                    preview.remove();
                    preview = new Image(new Texture(Gdx.files.internal(previews[i])));
                    preview.setPosition(530, 450);
                    preview.sizeBy(52, 100);
                    preview.rotateBy(90);
                    stage.addActor(preview);
                }
                else 
                    i = 0;
            }
        });
        
        selectPreviousCarButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (i > 0) {
                    currentCar--;
                    i--;
                    Gdx.app.log("Previous car", car[i+1]);
                    Gdx.app.log("Current car", Integer.toString(currentCar));                    
                }
            }
        });        
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(3/255f,13/255f,128/255f,1); //set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      
//        game.batch.begin();
//        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
//        game.batch.end();
//        hud.stage.draw();
        
        stage.act();
        stage.draw();
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
    }

    @Override
    public void dispose() {
        Gdx.app.log("Car Selection", "dispose called");
        game.dispose();
        font.dispose();
        stage.dispose();
        stage.getBatch().dispose();
        buttons_skin.dispose();
        box_skin.dispose();
    }
    
}
