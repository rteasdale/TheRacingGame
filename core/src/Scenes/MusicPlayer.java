/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import com.badlogic.gdx.audio.Music;

/**
 *
 * @author ROSY
 */
public class MusicPlayer {
    private Music song;
    private Music[] list;
    
    public MusicPlayer() {

    }
    
    public void addList(Music[] list) {
        this.list = list;
    }
    
    public void setSong(Music song) {
        this.song = song; 
    }

    public Music getSong() {
        return song;
    }
    
    public void stopMusic() {
        song.stop();
    }
    
    public void pauseMusic() {
        song.pause();
    }
    
    public void playMusic() {
        song.play();
    }
    
    public void setVolumeValue(float volume) {
        song.setVolume(volume);
        
    }
    
    public float getVolumeValue() {
        return song.getVolume();
    }


    
}
