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
    private String[][] final_matrix;
    

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
    private Label time2;
    private Label time3;
    private Label time4;
    private Label time5;
    private Label time6;
    private Label time7;
    private Label time8;
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
    
    private String timeString1;
    private String playerNameString1;
    private String carNameString1;
    
    private String playerNameString2;
    private String timeString2;
    private String carNameString2;
    
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
        
        if(!twoPlayers){
        final_matrix = GetNewMatrix1P(playerNames, carNames, times, playerNameString1,carNameString1, timeString1);
        //List of names from last list, list of cars from last list, lits of times from last list, new name, new time, new String
        //This method reads and classes the the names, car names and times according to the fastest times
        //The output is a matrix containing [] (the position) []the characteristic [0] = name, [1] = carName, [2] = time
        }
        else
            final_matrix = GetNewMatrix2P(playerNames, carNames, times, playerNameString1, timeString1, timeString1, playerNameString2, timeString2, timeString2);
        
        
        setValues();
        
System.out.println(Arrays.toString(playerNames));
        
    }
    
    private void setValues() {
        //note, STORE IN ARRAY? 
        
        /**Player name*/
        //car.getPlayerNum(); if playerNum == 1, then playerName = PlayerScreen.PlayerOneName, etc.
        playerName1 = new Label(final_matrix[0][0], lbl_style); 
        playerName2 = new Label(final_matrix[1][0], lbl_style);
        playerName3 = new Label(final_matrix[2][0], lbl_style);
        playerName4 = new Label(final_matrix[3][0], lbl_style);
        playerName5 = new Label(final_matrix[4][0], lbl_style);
        playerName6 = new Label(final_matrix[5][0], lbl_style);
        playerName7 = new Label(final_matrix[6][0], lbl_style);
        playerName8 = new Label(final_matrix[7][0], lbl_style);
        
        
        /** Car name*/
        carNameString1 = ""; 
        //car.getCarNum(); if carNum == 1, then carNameString = " VW Golf", etc. 
        carName1 = new Label(final_matrix[0][1], lbl_style);
        carName2 = new Label(final_matrix[1][1], lbl_style);
        carName3 = new Label(final_matrix[2][1], lbl_style);
        carName4 = new Label(final_matrix[3][1], lbl_style);
        carName5 = new Label(final_matrix[4][1], lbl_style);
        carName6 = new Label(final_matrix[5][1], lbl_style);
        carName7 = new Label(final_matrix[6][1], lbl_style);
        carName8 = new Label(final_matrix[7][1], lbl_style);
        
        time1 = new Label(final_matrix[0][2], lbl_style);
        time2 = new Label(final_matrix[1][2], lbl_style);
        time3 = new Label(final_matrix[2][2], lbl_style);
        time4 = new Label(final_matrix[3][2], lbl_style);
        time5 = new Label(final_matrix[4][2], lbl_style);
        time6 = new Label(final_matrix[5][2], lbl_style);
        time7 = new Label(final_matrix[6][2], lbl_style);
        time8 = new Label(final_matrix[7][2], lbl_style);
        
        /** Time */
        //time format 
        timeString1 = String.format("%02d : %02d : %03d",
            2, 45, 345
            //hud.getSeconds(), hud.getMinutes(), etc.
        );
        time1.setText(timeString1);
        
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
        
        String newTextFile = (final_matrix[0][0] + "," + final_matrix[1][0] + "," + final_matrix[2][0] + "," + final_matrix[3][0] + "," + final_matrix[4][0]
                 + "," + final_matrix[5][0] + "," + final_matrix[6][0] + "," + final_matrix[7][0] + "\n" + final_matrix[0][1] + "," + final_matrix[1][1]
                 + "," + final_matrix[2][1] + "," + final_matrix[3][1] + "," + final_matrix[4][1] + "," + final_matrix[5][1] + "," + final_matrix[6][1]
                 + "," + final_matrix[7][1] + "\n" + final_matrix[0][2] + "," + final_matrix[1][2] + "," + final_matrix[2][2] + "," + final_matrix[3][2]
                 + "," + final_matrix[4][2]+ "," + final_matrix[5][2]+ "," + final_matrix[6][2]+ "," + final_matrix[7][2]);
        
        file.writeString(newTextFile, false);
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
    
    public String[][] GetNewMatrix1P (String[] playerNames, String[] carNames, String[] times, String newPlayerName, String newCarName, String newTime ){
                
        String[][] a = new String[9][3];
        int[] unorderedTime = new int[9];
        int[] orderedTime = new int[9];
        String[][] b = new String[9][3];
        String[][] final_one = new String[8][3];
        
        for(int i = 0; i < 8; i++){  //Only obtains the characteristics of the ones collected
            a[i][0] = playerNames[i];
            a[i][1] = carNames[i];
            a[i][2] = times[i];
        }
        //INSERT NEW INFORMATION INTO STRING[][] a
        a[8][0] = newPlayerName;
        a[8][1] = newCarName;
        a[8][2] = newTime;
        
        for(int i = 0; i < 9; i++){
            times = a[i][3].split(":");
            int t = Integer.parseInt(times[0])*60000 + Integer.parseInt(times[1])*100 + Integer.parseInt(times[2]);
            unorderedTime[i] = t;
            orderedTime[i] = t;
        }
            BubbleSort(orderedTime);
        
           for(int i = 0; i < 9; i++){
               for(int j = 0; j < 9; j++){
                   if(orderedTime[i] == unorderedTime[j]){
                       b[i][0] = a[j][0];
                       b[i][1] = a[j][1];
                       b[i][2] = a[j][2];
                       break;
                   }
               }
           }
               for(int z = 0; z < 8; z++){
                   final_one[z][0] = b[z][0];
                   final_one[z][1] = b[z][1];
                   final_one[z][1] = b[z][2];
               }
               return final_one;
    }
  
        public String[][] GetNewMatrix2P (String[] playerNames, String[] carNames, String[] times, String newPlayerName1, String newCarName1, String newTime1
        , String newPlayerName2, String newCarName2, String newTime2){
                
        String[][] a = new String[10][3];
        int[] unorderedTime = new int[10];
        int[] orderedTime = new int[10];
        String[][] b = new String[10][3];
        String[][] final_one = new String[8][3];
        
        for(int i = 0; i < 8; i++){  //Only obtains the characteristics of the ones collected
            a[i][0] = playerNames[i];
            a[i][1] = carNames[i];
            a[i][2] = times[i];
        }
        //INSERT NEW INFORMATION INTO STRING[][] a
        a[8][0] = newPlayerName1;
        a[8][1] = newCarName1;
        a[8][2] = newTime1;
        
        a[9][0] = newPlayerName2;
        a[9][1] = newCarName2;
        a[9][2] = newTime2;
        
        for(int i = 0; i < 10; i++){
            times = a[i][3].split(":");
            int t = Integer.parseInt(times[0])*60000 + Integer.parseInt(times[1])*100 + Integer.parseInt(times[2]);
            unorderedTime[i] = t;
            orderedTime[i] = t;
        }
            BubbleSort(orderedTime);
        
           for(int i = 0; i < 10; i++){
               for(int j = 0; j < 10; j++){
                   if(orderedTime[i] == unorderedTime[j]){
                       b[i][0] = a[j][0];
                       b[i][1] = a[j][1];
                       b[i][2] = a[j][2];
                       break;
                   }
               }
           }
               for(int z = 0; z < 8; z++){
                   final_one[z][0] = b[z][0];
                   final_one[z][1] = b[z][1];
                   final_one[z][1] = b[z][2];
               }
               return final_one;
    }
    
    
    public static void BubbleSort( int [] num ){
     int j;
     boolean flag = true;   // set flag to true to begin first pass
     int temp;   //holding variable

     while ( flag )
     {
            flag= false;    //set flag to false awaiting a possible swap
            for( j=0;  j < num.length -1;  j++ )
            {
                   if ( num[ j ] < num[j+1] )   // change to > for ascending sort
                   {
                           temp = num[ j ];                //swap elements
                           num[ j ] = num[ j+1 ];
                           num[ j+1 ] = temp;
                          flag = true;              //shows a swap occurred  
                  } 
            } 
      } 
} 
    
    public void obtainCarData(boolean twoPlayers){
        if(twoPlayers){
   //timeString1 = hud.getTIME1;
  // playerNameString1 = PlayerScreen.playerOne;
   // carNameString1 = GameScreen.getCar().getCarName();
    
  //  playerNameString2 = ;
   // timeString2 = ;
  //  carNameString2 =  = GameScreen.getCar2().getCarName();
        }
        else if(!twoPlayers){
    //timeString1 = hud.getTIME1;
   //playerNameString1 = PlayerScreen.playerOne;
   //carNameString1 = GameScreen.getCar().getCarName();
    }
    }
    
}
