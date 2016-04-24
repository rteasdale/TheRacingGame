/*
 * http://stackoverflow.com/questions/33651323/libgdx-best-way-to-load-all-assets-in-the-game 
 */
package handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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
    
    //car selection screen assets
    public static final AssetDescriptor<Texture> stat_box = 
        new AssetDescriptor<Texture>(Gdx.files.internal("menu/car_stat.png"), Texture.class);       
    
    
    
    /** Atlas */ 
    public static final AssetDescriptor<TextureAtlas> buttons_atlas = 
            new AssetDescriptor<TextureAtlas>(Gdx.files.internal("menu/menubtns_atlas.txt"), TextureAtlas.class);
    
    public static final AssetDescriptor<TextureAtlas> atlas = 
            new AssetDescriptor<TextureAtlas>(Gdx.files.internal("menu/uiskin.txt"), TextureAtlas.class);    

    public static final AssetDescriptor<TextureAtlas> box_atlas = 
            new AssetDescriptor<TextureAtlas>(Gdx.files.internal("menu/box_atlas.txt"), TextureAtlas.class); 
    
    
    /** Skin */
    public static final AssetDescriptor<Skin> buttons_skin =
            new AssetDescriptor<Skin>(Gdx.files.internal("menu/menubtns_atlas.png"), Skin.class, 
                    new SkinLoader.SkinParameter("menu/menubtns_atlas.txt"));

    public static final AssetDescriptor<Skin> skin =
            new AssetDescriptor<Skin>(Gdx.files.internal("menu/uiskin.png"), Skin.class, 
                    new SkinLoader.SkinParameter("menu/uiskin.txt"));
    
    
    public void loadSplashScreen() {
        manager.load(splash_image);
    }
    
    public void loadMainMenuScreen() {
        manager.load(mainTitle);
        manager.load(background);
        manager.load(buttons_atlas);
        //manager.load(buttons_skin);
    }
    
    public void loadPlayerScreen() {
        manager.load(playerTitle);
        manager.load(buttons_atlas);
        manager.load(atlas);
        //manager.load(buttons_skin);
        //manager.load(skin);
    }
    
    public void loadCarSelectionScreen() {
        manager.load(carTitle);
        manager.load(stat_box);
        manager.load(buttons_atlas);
        manager.load(box_atlas);
    }
    
    public void loadMapSelectionScreen() {
        
    }
    
    public void loadGameScreen() {
        
    }
    
    public void dispose() {
        manager.dispose();
    }
}
