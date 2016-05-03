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
import com.badlogic.gdx.files.FileHandle;
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
import java.io.File;
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
    
    private FileHandle leaderboard_data;
    private Hud hud;
    private Table table;
    private Stage stage;
    private Skin skin; 
    
    private ImageButton return_mainmenu;
    
    private String[] times;
    private String[] playerNames;
    private String[] carNames;
    private String[] data;

    private int totalTime; 
    
    private final BitmapFont font;
    
    private Label positionLbl;
    private Label carNameLbl; //get description?
    private Label playerNameLbl; //get player names
    private Label timeLbl;
    private Label mapNameLbl;
    private Label totalFuelConsumptionLbl; // TO DO???
    private Label numberOfStopsForFuelLbl;
    
    
    private Label playerName1;
    private Label playerName2;
    private Label playerName3;
    private Label playerName4;
    private Label playerName5;
    private Label playerName6;
    private Label playerName7;
    private Label playerName8;
    private Label carName1;
    private Label carName2;
    private Label carName3;
    private Label carName4;
    private Label carName5;
    private Label carName6;
    private Label carName7;
    private Label carName8;
    private Label time1;
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
                        //variables in constructor: car, car2, assets, hud1, hud2, mapNum
        this.game = game;
        //this.assets = assets;
        //this.car = car;
        //this.car2 = car2;
        //this.hud = hud;
        //this.mapNum = mapNum;

        table = new Table();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        leaderboard_data = Gdx.files.local("data/map1_table.txt");
        /** FileHandle */ 
//        if (mapNum == 1) {
//            leaderboard_data = Gdx.files.local("map1_table.txt");
//        }
//        else if (mapNum == 2) {
//            leaderboard_data = Gdx.files.local("map2_table.txt");
//        }
//        else if (mapNum == 3) {
//            leaderboard_data = Gdx.files.local("map3_table.txt");
//        }

        /**TextureAtlas and skin */ 
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("menu/uiskin.txt"));
        skin = new Skin(atlas);
        
        /** BitmapFont */
        font = new BitmapFont(Gdx.files.internal("menu/button_font.fnt"), Gdx.files.internal("menu/button_font.png"), false);
        
        /**Style*/
        lbl_style = new Label.LabelStyle();
        lbl_style.font = font;
        lbl_style.fontColor = new Color(Color.WHITE);
        
        String[] dataLines = leaderboard_data.readString().split("\n");
        
        
        /**File data*/
        playerNames = dataLines[0].split(",");
        carNames = dataLines[1].split(",");
        times = dataLines[2].split(",");
System.out.println(Arrays.toString(playerNames));
        
    }
    
    private void setValues() {
        //note, STORE IN ARRAY? 
        
        /**Player name*/
        //car.getPlayerNum(); if playerNum == 1, then playerName = PlayerScreen.PlayerOneName, etc.
        playerName1 = new Label(playerNames[0], lbl_style); 
        playerName2 = new Label(playerNames[1], lbl_style);
        playerName3 = new Label(playerNames[2], lbl_style);
        playerName4 = new Label(playerNames[3], lbl_style);
        playerName5 = new Label(playerNames[4], lbl_style);
        playerName6 = new Label(playerNames[5], lbl_style);
        playerName7 = new Label(playerNames[6], lbl_style);
        playerName8 = new Label(playerNames[7], lbl_style);
        
        
        /** Car name*/
        carNameString = ""; 
        //car.getCarNum(); if carNum == 1, then carNameString = " VW Golf", etc. 
        carName1 = new Label(carNames[0], lbl_style);
        carName2 = new Label(carNames[1], lbl_style);
        carName3 = new Label(carNames[2], lbl_style);
        carName4 = new Label(carNames[3], lbl_style);
        carName5 = new Label(carNames[4], lbl_style);
        carName6 = new Label(carNames[5], lbl_style);
        carName7 = new Label(carNames[6], lbl_style);
        carName8 = new Label(carNames[7], lbl_style);
        
        
        /** Time */
        //time format 
        timeString = String.format("%02d : %02d : %03d",
            2, 45, 345
            //hud.getSeconds(), hud.getMinutes(), etc.
        );
        time1.setText(timeString);
        
        /** Map */
        map = new Label("", lbl_style);
        
        /** Total fuel consumption */
        totalFuelConsumption = new Label("", lbl_style);
        
        /** Total number of stops for fuel */ 
        numberOfStopsForFuel = new Label("", lbl_style);
        
        
    }
    
    private void readData(FileHandle file, int mapNum) {
       
    }
    
    private void writeData(FileHandle file, int mapNum) {
        file.writeString("name"+ ","+"time", true);
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
        //mapNameLbl = new Label(" MAP ", lbl_style);
        //totalFuelConsumptionLbl = new Label(" TOTAL FUEL\nCONSUMPTION ", lbl_style);
        //numberOfStopsForFuelLbl = new Label(" TOTAL NUMBER OF\nSTOPS FOR FUEL ", lbl_style);
        
        /**Labels*/ 
        playerName1 = new Label("", lbl_style);
        carName1 = new Label("", lbl_style);
        time1 = new Label("", lbl_style);
        //map = new Label("", lbl_style);
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
        //table.add(mapNameLbl).pad(10);
        //table.add(totalFuelConsumptionLbl).pad(10);
        //table.add(numberOfStopsForFuelLbl).pad(10);
        table.row();
        
        /**Row 2*/
        table.add(positionNum1).pad(10);
        table.add(playerName1).pad(10);
        table.add(carName1).pad(10);
        table.add(time1).pad(10);
        table.row();
        /**Row 3*/
        table.add(positionNum2).pad(10);
        //table.add(playerName)
        
        
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
