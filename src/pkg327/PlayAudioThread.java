
package pkg327;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Taylor Meyer
 */
public class PlayAudioThread extends Thread {
    
    Main playAudio;
    String mp3FilePath;
    
    // Setup
    public PlayAudioThread()
    {
        playAudio = new Main();
        mp3FilePath = "imperial.mp3";
    }
    
    // Stop audio, thread should then exit normally.
    public void stopRunning()
    {
        System.out.println("Audio thread stopped running.");
        playAudio.stopAudo();
    }
    
    // Play audio file passed to this class in driver
    @Override
    public void run()
    {
        System.out.println("Audio thread started running.");
        try {
            playAudio.mp3play(mp3FilePath);
        } catch (IOException ex) {
            Logger.getLogger(PlayAudioThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}