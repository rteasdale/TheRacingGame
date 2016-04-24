/*
 * http://stackoverflow.com/questions/33651323/libgdx-best-way-to-load-all-assets-in-the-game 
 */
package handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 *
 * @author ROSY
 */
public class ScreenAssets {
    
    public ScreenAssets() {
    }
    
    public AssetManager manager = new AssetManager();
    
    /** Texture */
    public static final AssetDescriptor<Texture> splash_image = 
        new AssetDescriptor<Texture>(Gdx.files.internal("menu/splashscreen_image.jpeg"), Texture.class);    
    
    /** Atlas */ 
    public static final AssetDescriptor<TextureAtlas> buttons_atlas = 
            new AssetDescriptor<TextureAtlas>(Gdx.files.internal("menu/menubtns_atlas.txt"), TextureAtlas.class);
    
    public static final AssetDescriptor<TextureAtlas> atlas = 
            new AssetDescriptor<TextureAtlas>(Gdx.files.internal("menu/uiskin.txt"), TextureAtlas.class);    

    public static final AssetDescriptor<TextureAtlas> box_atlas = 
            new AssetDescriptor<TextureAtlas>(Gdx.files.internal("menu/box_atlas.txt"), TextureAtlas.class); 
    
    /** Skin */
    public static final AssetDescriptor<Skin> buttons_skin =
            new AssetDescriptor<Skin>(Gdx.files.internal("menu/menubtns_atlas.txt"), Skin.class);

    public static final AssetDescriptor<Skin> skin =
            new AssetDescriptor<Skin>(Gdx.files.internal("menu/uiskin.txt"), Skin.class);
    
    public void loadSplashScreen() {
        manager.load(splash_image);
    }
    
    public void loadMainMenuScreen() {
        manager.load(buttons_atlas);
        manager.load(atlas);
    }
    
    public void loadPlayerScreen() {
        
    }
    
    public void loadCarSelectionScreen() {
        
    }
    
    public void loadMapSelectionScreen() {
        
    }
    
    public void loadGameScreen() {
        
    }
    
    public void dispose() {
        manager.dispose();
    }
}
