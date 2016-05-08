/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.List;

/**
 *
 * @author ROSY
 */
public class SoundPlayer {
    private Sound sound;
    
    public SoundPlayer() {
        
    }
    
    public void setSound(Sound sound) {
        this.sound = sound; 
    }

    public Sound getSound() {
        return sound;
    }
    
    public void stopSound() {
        sound.stop();
    }
    
    public void pauseSound() {
        sound.pause();
    }
    
    public void playSound() {
        sound.play();
    }
    
    public void playSound(float f) {
        sound.play(f);
    }
  
    public void loopSound(float f) {
        sound.loop(f);
    }
    
    public void setVolumeValue(long soundID, float pitch) {
        sound.setVolume(soundID, pitch);
        
    }

    public void setPitch(long soundID, float pitch){
        sound.setPitch(soundID, pitch); 
        //soundID ("HOW TO GET IT ----->  long soundID = nameOfSong.play())
        //pitch ("HOW IT WORKS" ------> Value between 0.5 and 2.0 // pitch < 1 = low pitch ; pitch > 1 high pitch ; pitch == 1 : normal value)
    }

}
