/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Screens;

import Scenes.Hud;
import car.Car;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.RacingGame;
import handlers.ScreenAssets;
import java.util.Arrays;

/**
 *
 * @author ROSY
 */
public class LeaderboardScreen implements Screen {
    private RacingGame game;
    private Car car;
    private Car car2;
    private ScreenAssets assets;
    
    private Hud hud;
    private Table table;
    private Stage stage;
    private Skin skin; 
    
    private ImageButton return_mainmenu;
    
    private Label[] playerNames;
    
    private final BitmapFont font;
    
    private Label positionLbl;
    private Label carNameLbl; //get description?
    private Label playerNameLbl; //get player names
    private Label timeLbl;
    private Label mapNameLbl;
    private Label totalFuelConsumptionLbl; // TO DO???
    private Label numberOfStopsForFuelLbl;
    
    private Label carName;
    private Label playerName;
    private Label time;
    private Label map;
    private Label totalFuelConsumption;
    private Label numberOfStopsForFuel;
    
    private Label positionNum1;
    private Label positionNum2;
    private Label positionNum3;
    private Label positionNum4;
    private Label positionNum5;
    private Label positionNum6;
    private Label positionNum7;
    private Label positionNum8;
    
    private String timeString;
    private String playerNameString;
    private String carNameString;
    private String mapNameString;
    
    private final Label.LabelStyle lbl_style;
    
    public LeaderboardScreen(RacingGame game, boolean twoPlayers) {
                        //variables in constructor: car, car2, assets, hud1, hud2
        this.game = game;
        //this.assets = assets;
        //this.car = car;
        //this.car2 = car2;
        //this.hud = hud;

        table = new Table();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        /**TextureAtlas and skin */ 
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("menu/uiskin.txt"));
        skin = new Skin(atlas);
        
        /** BitmapFont */
        font = new BitmapFont(Gdx.files.internal("menu/button_font.fnt"), Gdx.files.internal("menu/button_font.png"), false);
        
        /**Style*/
        lbl_style = new Label.LabelStyle();
        lbl_style.font = font;
        lbl_style.fontColor = new Color(Color.WHITE);
        
    }
    
    private void setValues() {
        //note, STORE IN ARRAY? 
        
        /**Player name*/
        playerNameString = "";
        //car.getPlayerNum(); if playerNum == 1, then playerName = PlayerScreen.PlayerOneName, etc.
        playerName = new Label("", lbl_style); 
        
        
        /** Car name*/
        carNameString = ""; 
        //car.getCarNum(); if carNum == 1, then carNameString = " VW Golf", etc. 
        carName = new Label("", lbl_style);
        
        /** Time */
        //time format 
        timeString = String.format("%02d : %02d : %03d",
            2, 45, 345
            //hud.getSeconds(), hud.getMinutes(), etc.
        );
        time.setText(timeString);
        
        /** Map */
        map = new Label("", lbl_style);
        
        /** Total fuel consumption */
        totalFuelConsumption = new Label("", lbl_style);
        
        /** Total number of stops for fuel */ 
        numberOfStopsForFuel = new Label("", lbl_style);
    }
    
    private void sortTime(int[] list) {
        //sort from lowest to highest
        Arrays.sort(list);
        
    }
    
    @Override
    public void show() {
        table.setSkin(skin);
        
        /**Column Labels*/
        positionLbl = new Label(" POSITION ", lbl_style);
        carNameLbl = new Label(" CAR\nUSED ", lbl_style);
        playerNameLbl = new Label(" PLAYER\nNAME ", lbl_style);
        timeLbl = new Label(" TIME ", lbl_style);
        mapNameLbl = new Label(" MAP ", lbl_style);
        //totalFuelConsumptionLbl = new Label(" TOTAL FUEL\nCONSUMPTION ", lbl_style);
        //numberOfStopsForFuelLbl = new Label(" TOTAL NUMBER OF\nSTOPS FOR FUEL ", lbl_style);
        
        /**Labels*/ 
        playerName = new Label("", lbl_style);
        carName = new Label("", lbl_style);
        time = new Label("", lbl_style);
        map = new Label("", lbl_style);
        //totalFuelConsumption = new Label("", lbl_style);
        //numberOfStopsForFuel = new Label("", lbl_style);
        
        /** Position numbers*/
        positionNum1 = new Label(Integer.toString(1), lbl_style);
        positionNum2 = new Label(Integer.toString(2), lbl_style);
        positionNum3 = new Label(Integer.toString(3), lbl_style);
        positionNum4 = new Label(Integer.toString(4), lbl_style);
        positionNum5 = new Label(Integer.toString(5), lbl_style);
        positionNum6 = new Label(Integer.toString(6), lbl_style);
        positionNum7 = new Label(Integer.toString(7), lbl_style);
        positionNum8 = new Label(Integer.toString(8), lbl_style);

        setValues();        
        
        /** TABLE */
        /**Row 1*/
        table.add(positionLbl).pad(10);
        table.add(playerNameLbl).pad(10);
        table.add(carNameLbl).pad(10);
        table.add(timeLbl).pad(10);
        table.add(mapNameLbl).pad(10);
        //table.add(totalFuelConsumptionLbl).pad(10);
        //table.add(numberOfStopsForFuelLbl).pad(10);
        table.row();
        
        for (int row = 0; row < 8; row++) {
            
        }
        
        /**Row 2*/
        table.add(positionNum1).pad(10);
        table.add(playerName).pad(10);
        table.add(carName).pad(10);
        table.add(time).pad(10);
        table.row();
        /**Row 3*/
        table.add(positionNum2).pad(10);
        table.row();
        /**Row 4*/
        table.add(positionNum3).pad(10);
        table.row();
        /**Row 5*/
        table.add(positionNum4).pad(10);
        table.row();
        /**Row 6*/
        table.add(positionNum5).pad(10);
        table.row();
        /**Row 7*/
        table.add(positionNum6).pad(10);
        table.row();
        /**Row 8*/
        table.add(positionNum7).pad(10);
        table.row();
        /**Row 9*/
        table.add(positionNum8).pad(10);         

        table.setPosition(500, 350);
        stage.addActor(table);
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(3/255f,13/255f,128/255f,1); //set background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);   
        
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
        stage.dispose();
        font.dispose();
        skin.dispose();
        
    }
    
}
