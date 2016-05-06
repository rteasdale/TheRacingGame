/*
*NOTE: DO NOT INITIALIZE EVERYTHING IN CREATE, CHECK IF REALLY NECESSARY
 */
package Screens;

import Scenes.MusicPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.kotcrab.vis.ui.VisUI;
import com.mygdx.game.RacingGame;
import handlers.ScreenAssets;
import java.util.Arrays;

/**
 *
 * @author ROSY
 */
public class CarSelectionScreen implements Screen { //extends PlayerScreen
    private RacingGame game;
    private MusicPlayer musicPlayer;
    private Sound click;
    private Sound click2;
    
    private Screen scr = this;
    private OrthographicCamera camera;
    private Stage stage;
    private BitmapFont font;
    
    private final ScreenAssets assets;
    private final Texture title_texture;
    private final Texture stat_box;
    
    private ShapeRenderer renderer;
    
    public int playerNum;
    private boolean twoPlayers;
    private String playerName;
    
    private String currentPlayer;
    
    public int currentColor;
    public int currentCar = 0;
    
    private FileHandle file;
    private String[] group;
    private String[] car;
    private String[] carPreview;
    private String[] stats;
    
    public static String[] golf_colors;
    public static String[] lambo_colors;
    public static String[] prius_colors;
    public static String[] porsche_colors;
    public static String[] truck_colors;
    public static String[] zondaf_colors;
    
    
    private Image title;
    private ImageButton selectNextCarButton;
    private ImageButton selectPreviousCarButton;

    private ImageButton next_btn;
    private ImageButton back_btn;
    private ImageButtonStyle next_style;
    private ImageButtonStyle back_style;
    
    private Image preview;
    
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
    
    private Label player;
    
    public CarSelectionScreen(RacingGame game, boolean twoPlayers, int playerNum, String playerName, ScreenAssets assets, MusicPlayer musicPlayer) {
        this.game = game;
        this.assets = assets;
        this.playerNum = playerNum;
        this.playerName = playerName;
        this.twoPlayers = twoPlayers;
        this.musicPlayer = musicPlayer;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);        
        renderer = new ShapeRenderer();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); //** stage is responsive **// 

        
        click = assets.manager.get(ScreenAssets.click_sound);
        click2 = assets.manager.get(ScreenAssets.click_sound2);
        
        /** File data */
        file = new FileHandle("data/car.txt");
        group = file.readString().split("\n");
        car = group[0].split(",");
        carPreview = group[1].split(",");
        stats = group[8].split(" ");
        
        golf_colors = group[2].split(",");
        lambo_colors = group[3].split(",");
        prius_colors = group[4].split(",");
        porsche_colors = group[5].split(",");
        truck_colors = group[6].split(",");
        zondaf_colors = group[7].split(",");
        
        /** Textures*/ 
        title_texture = assets.manager.get(ScreenAssets.carTitle);
        stat_box = assets.manager.get(ScreenAssets.stat_box);
        
        /** BitmapFont */
        font = assets.manager.get(ScreenAssets.font);

        /** Atlas and skin */
        buttons_atlas = assets.manager.get(ScreenAssets.buttons_atlas);
        buttons_skin= new Skin(buttons_atlas);
        box_atlas = assets.manager.get(ScreenAssets.box_atlas);
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
        //Gdx.app.log("CarSelection", "show called");         
        
        /** Title */
        title = new Image(title_texture);
        title.setPosition(280, 648);
        
        /** SelectBox and TextArea */
        color_select = new SelectBox(box_style);
        color_select.setItems("-- Select Car Color --", "Light Blue", "Dark Blue", "Green", "Orange", "Purple", "Red", "White", "Yellow");
        color_select.setPosition(200, 300);
        color_select.setSize(432, 40);
        color_select.setSelected("-- Selected Car Color --"); //initialize selected

        carDescription = new TextArea("Description", txt_style);
        carDescription.setPosition(200,170);
        carDescription.setSize(432, 120);

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
        weight = new Image(stat_box);
        weight.setPosition(850, 550);
        acceleration = new Image(stat_box);
        acceleration.setPosition(850, 480);
        top_speed = new Image(stat_box);
        top_speed.setPosition(850, 410);
        handling = new Image(stat_box);
        handling.setPosition(850, 340);
        tank_capacity = new Image(stat_box);
        tank_capacity.setPosition(850, 270);
        consumption = new Image(stat_box);
        consumption.setPosition(850, 200);
            
        /**Stats labels */
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
        
        /** Player label*/ 
        player = new Label("Player Selecting: " + playerName.toUpperCase(), lbl_style);
        player.setPosition(200, 580);
        player.setColor(Color.ORANGE);

        stage.addActor(player);
        
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
        
        stage.addActor(carDescription);
        stage.addActor(color_select);
        
        stage.addActor(selectNextCarButton);
        stage.addActor(selectPreviousCarButton);
        stage.addActor(next_btn);
        stage.addActor(back_btn);
        stage.addActor(title);
        
        listeners();
  
    }
    
    private void listeners() {
        /** Next / Back Buttons*/
        next_btn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                try{
                click.play(SettingsScreen.getSFXPourcentage());
                }catch(NullPointerException e1){
                    click.play(0.75f);
                }
                if (twoPlayers == true && playerNum == 1) {
                    GameScreen.setCarNumP1(getCarNum());
                    GameScreen.setCarColorP1(getCarColor());                          
                    game.setScreen(new CarSelectionScreen(game, twoPlayers, 2, PlayerScreen.playerNameP2, assets, musicPlayer));
                }
                if (twoPlayers == true && playerNum == 2) {
                    GameScreen.setCarNumP2(getCarNum());
                    GameScreen.setCarColorP2(getCarColor());
                    game.setScreen(new MapSelectionScreen(game, twoPlayers, assets, musicPlayer));
                }
                if (twoPlayers == false) {
                    GameScreen.setCarNumP1(getCarNum());
                    GameScreen.setCarColorP1(getCarColor());                    
                    game.setScreen(new MapSelectionScreen(game, twoPlayers, assets, musicPlayer));
                }
            }
        });  
        
        back_btn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                try{
                click.play(SettingsScreen.getSFXPourcentage());
                }catch(NullPointerException e1){
                    click.play(0.75f);
                }
                if (twoPlayers == true && playerNum == 2) {
                    //return to car selection P1
                    game.setScreen(new CarSelectionScreen(game, twoPlayers, 1, PlayerScreen.playerNameP1, assets, musicPlayer));
                }
                if (twoPlayers == true && playerNum == 1) {
                    //return to player screen
                    game.setScreen(new PlayerScreen(game, twoPlayers, assets, musicPlayer));
                }                
                if (twoPlayers == false) {
                    //return to player screen
                    game.setScreen(new PlayerScreen(game, twoPlayers, assets, musicPlayer));
                }
            }
        });
        
        /**Color select */
        color_select.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                try{
                click2.play(SettingsScreen.getSFXPourcentage());
                }catch(NullPointerException e1){
                    click2.play(0.75f);
                }
            /** Default*/
                if (color_select.getSelected().equals("-- Select Car Color --")) {
                    Gdx.app.log("color selected:", color_select.getSelected());
                    //golf
                    if (currentCar == 0) {
                        //setCarColor(4);
                        setCarColor(0);
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(carPreview[0])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                            
                    }
                    //lambo
                    if (currentCar == 1) {
                        //setCarColor(0);
                        setCarColor(0);
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(carPreview[1])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    //prius
                    if (currentCar == 2) {
                        //setCarColor(1);
                        setCarColor(0);
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(carPreview[2])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    //porsche
                    if (currentCar == 3) {
                        //setCarColor(2);
                        setCarColor(0);
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(carPreview[3])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    //truck
                    if (currentCar == 4) {
                        //setCarColor(5);
                        setCarColor(0);
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(carPreview[4])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }  
                    //zondaf
                    if (currentCar == 5) {
                        //setCarColor(3);
                        setCarColor(0);
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(carPreview[5])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }                    
                 }                
            /** Light Blue*/
                if (color_select.getSelected().equals("Light Blue")) {
                    Gdx.app.log("Color selected", color_select.getSelected());
                    setCarColor(0);
                    if (currentCar == 0) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(golf_colors[0])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                            
                    }
                    if (currentCar == 1) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(lambo_colors[0])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 2) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(prius_colors[0])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 3) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(porsche_colors[0])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 4) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(truck_colors[0])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }  
                    if (currentCar == 5) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(zondaf_colors[0])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }                    
                 }
                
            /** Dark Blue*/
                if (color_select.getSelected().equals("Dark Blue")) {
                    Gdx.app.log("Color selected", color_select.getSelected());
                    color_select.setSelected("Dark Blue");
                    setCarColor(1);
                    if (currentCar == 0) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(golf_colors[1])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                            
                    }
                    if (currentCar == 1) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(lambo_colors[1])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 2) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(prius_colors[1])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 3) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(porsche_colors[1])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 4) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(truck_colors[1])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }  
                    if (currentCar == 5) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(zondaf_colors[1])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }                    
                } 
            /** Yellow*/
                
                if (color_select.getSelected().equals("Yellow")) {
                    Gdx.app.log("Color selected", color_select.getSelected());
                    setCarColor(2);
                    if (currentCar == 0) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(golf_colors[2])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                            
                    }
                    if (currentCar == 1) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(lambo_colors[2])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 2) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(prius_colors[2])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 3) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(porsche_colors[2])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 4) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(truck_colors[2])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }  
                    if (currentCar == 5) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(zondaf_colors[2])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }                    
                }                
            /** Green*/
                
                if (color_select.getSelected().equals("Green")) {
                    Gdx.app.log("Color selected", color_select.getSelected());
                    setCarColor(3);
                    if (currentCar == 0) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(golf_colors[3])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                            
                    }
                    if (currentCar == 1) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(lambo_colors[3])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 2) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(prius_colors[3])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 3) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(porsche_colors[3])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 4) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(truck_colors[3])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }  
                    if (currentCar == 5) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(zondaf_colors[3])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }                    
                }                
            /** White*/
                
                if (color_select.getSelected().equals("White")) {
                    Gdx.app.log("Color selected", color_select.getSelected());
                    setCarColor(4);
                    if (currentCar == 0) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(golf_colors[4])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                            
                    }
                    if (currentCar == 1) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(lambo_colors[4])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 2) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(prius_colors[4])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 3) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(porsche_colors[4])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 4) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(truck_colors[4])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }  
                    if (currentCar == 5) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(zondaf_colors[4])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }                    
                }          
            /** Red*/
                
                if (color_select.getSelected().equals("Red")) {
                    Gdx.app.log("Color selected", color_select.getSelected());
                    setCarColor(5);
                    if (currentCar == 0) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(golf_colors[5])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                            
                    }
                    if (currentCar == 1) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(lambo_colors[5])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 2) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(prius_colors[5])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 3) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(porsche_colors[5])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 4) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(truck_colors[5])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }  
                    if (currentCar == 5) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(zondaf_colors[5])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }                    
                }                
            /** Purple*/
                
                if (color_select.getSelected().equals("Purple")) {
                    Gdx.app.log("Color selected", color_select.getSelected());
                    setCarColor(6);
                    if (currentCar == 0) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(golf_colors[6])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                            
                    }
                    if (currentCar == 1) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(lambo_colors[6])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 2) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(prius_colors[6])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 3) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(porsche_colors[6])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 4) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(truck_colors[6])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }  
                    if (currentCar == 5) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(zondaf_colors[6])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }                    
                }    
               /** Orange */ 
                if (color_select.getSelected().equals("Orange")) {
                    Gdx.app.log("Color selected", color_select.getSelected());
                    setCarColor(7);
                    if (currentCar == 0) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(golf_colors[7])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                            
                    }
                    if (currentCar == 1) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(lambo_colors[7])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 2) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(prius_colors[7])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 3) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(porsche_colors[7])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }
                    if (currentCar == 4) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(truck_colors[7])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }  
                    if (currentCar == 5) {
                        preview.remove();
                        preview = new Image(new Texture(Gdx.files.internal(zondaf_colors[7])));
                        preview.setPosition(530, 450);
                        preview.sizeBy(52, 100);
                        preview.rotateBy(90);
                        stage.addActor(preview);                       
                    }                    
                }                
                
            }
        });

        //System.out.println(Arrays.toString(carPreview));
        //Gdx.app.log("Current car", car[0]);
        
        carDescription.setText("\n" + car[0]);
        
        /** Preview */
        preview = new Image(new Texture(Gdx.files.internal(carPreview[0])));
        preview.setPosition(530, 450);
        preview.sizeBy(52, 100);
        preview.rotateBy(90);
        stage.addActor(preview);   
        
        /** Car type*/
        selectNextCarButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                try{
                    click2.play(SettingsScreen.getSFXPourcentage());                
                }catch(NullPointerException e1){                    
                    click2.play(0.75f);                
                };
                //System.out.print(currentCar);
                color_select.setSelected("-- Select Car Color --");
                /*Car description*/
                if (currentCar < 5) {
                currentCar++;
                Gdx.app.log("Current car", car[currentCar]);
                carDescription.setText("\n" + car[currentCar]);
                preview.remove();
                preview = new Image(new Texture(Gdx.files.internal(carPreview[currentCar])));
                preview.setPosition(530, 450);
                preview.sizeBy(52, 100);
                preview.rotateBy(90);
                stage.addActor(preview);    
                }
                else {
                currentCar = 0;
                Gdx.app.log("Current car", car[0]);
                carDescription.setText("\n" + car[0]); 
                /** Preview */
                preview.remove();
                preview = new Image(new Texture(Gdx.files.internal(carPreview[0])));
                preview.setPosition(530, 450);
                preview.sizeBy(52, 100);
                preview.rotateBy(90);
                stage.addActor(preview);                    
                }
                
                
            }
        });
        
        selectPreviousCarButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                try{                
                    click2.play(SettingsScreen.getSFXPourcentage());                
                }catch(NullPointerException e1){
                    click2.play(0.75f);                
                };
                
                color_select.setSelected("-- Select Car Color --");
                if (currentCar > 0) {
                currentCar--;
                System.out.println(currentCar);
                Gdx.app.log("Current car", car[currentCar]);
                carDescription.setText("\n" + car[currentCar]);
                preview.remove();
                preview = new Image(new Texture(Gdx.files.internal(carPreview[currentCar])));
                preview.setPosition(530, 450);
                preview.sizeBy(52, 100);
                preview.rotateBy(90);
                stage.addActor(preview);                    
                }
                else {
                currentCar = 5;
                Gdx.app.log("Current car", car[5]);
                carDescription.setText("\n" + car[5]); 
                /** Preview */
                preview.remove();
                preview = new Image(new Texture(Gdx.files.internal(carPreview[5])));
                preview.setPosition(530, 450);
                preview.sizeBy(52, 100);
                preview.rotateBy(90);
                stage.addActor(preview);    
                }                
            }
        });        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(3/255f,13/255f,128/255f,1); //set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        /** Render stats levels*/ 
        renderer.setProjectionMatrix(camera.combined);

        String[] temp = stats[currentCar].split(",");
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLUE);
        renderer.rect(850, 550, Integer.parseInt(temp[0])*28.5f, 20);
        renderer.rect(850, 480, Integer.parseInt(temp[1])*28.5f, 20);
        renderer.rect(850, 410, Integer.parseInt(temp[2])*28.5f, 20);
        renderer.rect(850, 340, Integer.parseInt(temp[3])*28.5f, 20);
        renderer.rect(850, 270, Integer.parseInt(temp[4])*28.5f, 20);
        renderer.rect(850, 200, Integer.parseInt(temp[5])*28.5f, 20);
        renderer.end();  

        stage.act();
        stage.draw();
    }
    
    public int getCarColor() {
        return currentColor;
    }
    
    public void setCarColor(int colorNum) {
        this.currentColor = colorNum;
    }
    
    public int getCarNum() {
        return currentCar;
    }

    public void setCarNum(int carNum) {
        this.currentCar = carNum;
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
//        Gdx.app.log("Player num", Integer.toString(playerNum));
//        Gdx.app.log("Two Players?", Boolean.toString(twoPlayers));
//        
    }

    @Override
    public void dispose() {
        Gdx.app.log("Car Selection", "dispose called");
//        game.dispose();
//        click.dispose();
//        click2.dispose();
//        stage.dispose();
//        renderer.dispose();
//        font.dispose();
//        buttons_atlas.dispose();
//        box_atlas.dispose();
//        buttons_skin.dispose();
//        box_skin.dispose();
    }
    
}
