/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;

/**
 *
 * @author Administrateur
 */
public class Assets {
    
    private HashMap<String, Texture> textures;
    
    public Assets(){
        textures = new HashMap<String, Texture>();
    }
    
    public void loadTexture(String path, String key){
        Texture tex = new Texture(Gdx.files.internal(path));
        textures.put(key, tex);
    }
    
    public Texture getTexture(String key){
           return textures.get(key);
    }
    
    public void disposeTexture(String key) {
        Texture tex = textures.get(key);
        if (tex != null) tex.dispose();
    }
}
