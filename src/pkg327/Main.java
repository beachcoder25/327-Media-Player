
package pkg327;

import java.io.IOException;
import java.io.InputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

public class Main {
   
    Player mp3player;
    
      /**
     * Play a given audio file.
     * @param audioFilePath Path of the audio file.
     */
    void mp3play(String file) throws IOException {
        try {
            // It uses CECS327InputStream as InputStream to play the song 
             InputStream is = new CECS327InputStream(file);
             mp3player = new Player(is);
             mp3player.play();
	     }
	     catch (JavaLayerException exception) 
            {
                exception.printStackTrace();
            }
         
    }
    
    void stopAudo()
    {
        mp3player.close();
    }
     
    public static void main(String[] args) throws IOException {
        Integer i;
        Main player = new Main();
        player.mp3play("imperial.mp3");
    }
 
}

