/*
 * http://stackoverflow.com/questions/33651323/libgdx-best-way-to-load-all-assets-in-the-game 
 */
package handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 *
 * @author ROSY
 */
public class ScreenAssets {
    
    public ScreenAssets() {
    }
    
    public AssetManager manager = new AssetManager();
    
    /** Textures */
    //splash screen
    public static final AssetDescriptor<Texture> splash_image = 
        new AssetDescriptor<Texture>(Gdx.files.internal("menu/splashscreen_image.jpeg"), Texture.class);    
    
    //main menu screen background
    public static final AssetDescriptor<Texture> background = 
        new AssetDescriptor<Texture>(Gdx.files.internal("menu/menu_image.jpg"), Texture.class);     
    
    //main menu screen title
    public static final AssetDescriptor<Texture> mainTitle = 
        new AssetDescriptor<Texture>(Gdx.files.internal("menu/menu_title.png"), Texture.class);      
    
    //player screen title
    public static final AssetDescriptor<Texture> playerTitle = 
        new AssetDescriptor<Texture>(Gdx.files.internal("menu/playername_title.png"), Texture.class);     
    
    //car selection screen title
    public static final AssetDescriptor<Texture> carTitle = 
        new AssetDescriptor<Texture>(Gdx.files.internal("menu/carselection_title.png"), Texture.class);     
    
    //map selection screen title
    public static final AssetDescriptor<Texture> mapTitle = 
        new AssetDescriptor<Texture>(Gdx.files.internal("menu/mapselection_title.png"), Texture.class);
    
    //settings screen title       
    public static final AssetDescriptor<Texture> settingsTitle = 
        new AssetDescriptor<Texture>(Gdx.files.internal("menu/settings_title.png"), Texture.class);    
    
    //leader board screen title
    public static final AssetDescriptor<Texture> leaderboardTitle = 
        new AssetDescriptor<Texture>(Gdx.files.internal("menu/leaderboard_title.png"), Texture.class);    
    
    //car selection screen assets
    public static final AssetDescriptor<Texture> stat_box = 
        new AssetDescriptor<Texture>(Gdx.files.internal("menu/car_stat.png"), Texture.class); 
    
    //controls ASDW
    public static final AssetDescriptor<Texture> ASDW_controls = 
        new AssetDescriptor<Texture>(Gdx.files.internal("menu/ASDW_controls.png"), Texture.class);     

    //controls arrows
    public static final AssetDescriptor<Texture> arrows_controls = 
        new AssetDescriptor<Texture>(Gdx.files.internal("menu/arrowkeys_controls.png"), Texture.class);     


    
    //HUD assets
    public static final AssetDescriptor<Texture> speed_gauge = 
        new AssetDescriptor<Texture>(Gdx.files.internal("HUD/speedgauge.png"), Texture.class); 
    
    public static final AssetDescriptor<Texture> fuel_gauge = 
        new AssetDescriptor<Texture>(Gdx.files.internal("HUD/fuelgauge.png"), Texture.class);
    
    public static final AssetDescriptor<Texture> speed_needle = 
        new AssetDescriptor<Texture>(Gdx.files.internal("HUD/speedneedle.png"), Texture.class);  
    
    public static final AssetDescriptor<Texture> fuel_needle = 
        new AssetDescriptor<Texture>(Gdx.files.internal("HUD/fuelneedle.png"), Texture.class);       
    
    
    /**Bitmap font*/ 
    public static final AssetDescriptor<BitmapFont> font = 
            new AssetDescriptor<BitmapFont>(Gdx.files.internal("menu/button_font.fnt"), BitmapFont.class, 
                    new BitmapFontLoader.BitmapFontParameter());    
    public static final AssetDescriptor<BitmapFont> hud_font = 
            new AssetDescriptor<BitmapFont>(Gdx.files.internal("HUD/countdown.fnt"), BitmapFont.class, 
                    new BitmapFontLoader.BitmapFontParameter());        
    
    /** Atlas */ 
    public static final AssetDescriptor<TextureAtlas> buttons_atlas = 
            new AssetDescriptor<TextureAtlas>(Gdx.files.internal("menu/menubtns_atlas.txt"), TextureAtlas.class);
    
    public static final AssetDescriptor<TextureAtlas> atlas = 
            new AssetDescriptor<TextureAtlas>(Gdx.files.internal("menu/uiskin.txt"), TextureAtlas.class);    

    public static final AssetDescriptor<TextureAtlas> box_atlas = 
            new AssetDescriptor<TextureAtlas>(Gdx.files.internal("menu/box_atlas.txt"), TextureAtlas.class); 
    
    public static final AssetDescriptor<TextureAtlas> settings_atlas = 
            new AssetDescriptor<TextureAtlas>(Gdx.files.internal("menu/settings_atlas.txt"), TextureAtlas.class); 

    
    /**Music*/ 
    public static final AssetDescriptor<Music> menu_music = 
            new AssetDescriptor<Music>(Gdx.files.internal("music/menu_track.mp3"), Music.class); 
    public static final AssetDescriptor<Music> song1 = 
            new AssetDescriptor<Music>(Gdx.files.internal("music/map1_track1.mp3"), Music.class);     
    public static final AssetDescriptor<Music> song2 = 
            new AssetDescriptor<Music>(Gdx.files.internal("music/map1_track2.mp3"), Music.class);     
    public static final AssetDescriptor<Music> song3 = 
            new AssetDescriptor<Music>(Gdx.files.internal("music/map1_track3.mp3"), Music.class);     
    public static final AssetDescriptor<Music> song4 = 
            new AssetDescriptor<Music>(Gdx.files.internal("music/map1_track4.mp3"), Music.class); 
    public static final AssetDescriptor<Music> song5 = 
            new AssetDescriptor<Music>(Gdx.files.internal("music/map2_track.mp3"), Music.class);     
    public static final AssetDescriptor<Music> song6 = 
            new AssetDescriptor<Music>(Gdx.files.internal("music/map3_track.mp3"), Music.class); 
    
    /**Sounds*/ 
    public static final AssetDescriptor<Sound> click_sound = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/click_sound.mp3"), Sound.class);     
    public static final AssetDescriptor<Sound> click_sound2 = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/click_sound2.mp3"), Sound.class);      
    public static final AssetDescriptor<Sound> countdown_sound1 = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/countdown_p1.mp3"), Sound.class);      
    public static final AssetDescriptor<Sound> countdown_sound2 = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/countdown_p2.mp3"), Sound.class);
    public static final AssetDescriptor<Sound> car_car_sound = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/car_car_contact.mp3"), Sound.class);   
    public static final AssetDescriptor<Sound> car_tire_sound = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/car_tireobs_contact.mp3"), Sound.class);   
    public static final AssetDescriptor<Sound> car_wall_sound1 = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/car_wall_sound1.mp3"), Sound.class);  
    public static final AssetDescriptor<Sound> car_wall_sound2 = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/car_wall_sound2.mp3"), Sound.class);  
     public static final AssetDescriptor<Sound> car_wall_sound3 = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/car_wall_sound3.mp3"), Sound.class);  
    public static final AssetDescriptor<Sound> ready_botton_sound = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/ready_button.mp3"), Sound.class);
    public static final AssetDescriptor<Sound> out_of_fuel_alarm = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/out_of_fuel_alarm.mp3"), Sound.class);   
    public static final AssetDescriptor<Sound> car_bridge_initial = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/car_bridge_initial.mp3"), Sound.class);   
    public static final AssetDescriptor<Sound> car_bridge_loop = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/car_bridge_loop.mp3"), Sound.class);
    public static final AssetDescriptor<Sound> lap_complete_sound = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/lap_complete_sound.mp3"), Sound.class);
    public static final AssetDescriptor<Sound> getting_on_fuel_sound = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/getting_on_fuel_sound.mp3"), Sound.class);
    public static final AssetDescriptor<Sound> refueling_loop = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/refueling_loop.mp3"), Sound.class);
    public static final AssetDescriptor<Sound> end_race_gingle = 
            new AssetDescriptor<Sound>(Gdx.files.internal("FXSounds/end_race_gingle.mp3"), Sound.class);

    
   
    
    public void loadSplashScreen() {
        manager.load(splash_image);
        manager.load(font);
        manager.load(menu_music);
    }
    
    public void loadMainMenuScreen() {
        manager.load(mainTitle);
        manager.load(background);
        manager.load(buttons_atlas);
    }
    
    public void loadPlayerScreen() {
        manager.load(playerTitle);
        //manager.load(font);
        //manager.load(buttons_atlas);
        manager.load(atlas);
        //manager.load(buttons_skin);
        //manager.load(skin);
    }
    
    public void loadCarSelectionScreen() {
        manager.load(carTitle);
        //manager.load(font);
        manager.load(stat_box);
        //manager.load(buttons_atlas);
        manager.load(box_atlas);
    }
    
    public void loadMapSelectionScreen() {
        manager.load(mapTitle);
    }
    
    public void loadLoadingScreen() {
        //manager.load(font);
        manager.load(ASDW_controls);
        manager.load(arrows_controls);        
    }
    
    public void loadHUD() {
        manager.load(speed_gauge);
        manager.load(fuel_gauge);
        manager.load(speed_needle);
        manager.load(fuel_needle);
        manager.load(hud_font);
        manager.load(countdown_sound1);
        manager.load(countdown_sound2);
        manager.load(out_of_fuel_alarm);
        manager.load(end_race_gingle);
    }
    
    public void loadGameScreen() {

        manager.load(song1);
        manager.load(song2);
        manager.load(song3);
        manager.load(song4);
        manager.load(song5);
        manager.load(song6);
        manager.load(car_bridge_loop);
        manager.load(car_car_sound);
        manager.load(car_tire_sound);
        manager.load(car_wall_sound1);
        manager.load(car_wall_sound2);
        manager.load(car_wall_sound3);
        manager.load(getting_on_fuel_sound);
        manager.load(lap_complete_sound);
        manager.load(out_of_fuel_alarm);
        manager.load(refueling_loop);
    }
    
    public void loadSettingsScreen() {
        manager.load(settingsTitle);
        manager.load(settings_atlas);
    }
    
    public void loadSounds() {
        manager.load(click_sound);
        manager.load(click_sound2);
    }
    
    public void loadLeaderboard() {
        manager.load(leaderboardTitle);
    }

}
