
package Client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import Server.PlayAudioThread;

/**
 *
 * @author taylo
 */
public class PlayButton extends Button{
    
    static PlayAudioThread thread = null;
    
    public PlayButton(String text) {
        
        this.setText(text);
        
        this.setStyle("-fx-background-color:#000000;"
            + "-fx-text-fill: #1CFF00;"
            + "-fx-border-color: #1CFF00;"
            + "-fx-border-width: 1px;");

        this.addEventHandler(MouseEvent.MOUSE_ENTERED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
            setStyle("-fx-background-color:#000000;"
            + "-fx-text-fill: #E300FF;"
            + "-fx-border-color: #E300FF;"
            + "-fx-border-width: 1px;");
          }
        });

        this.addEventHandler(MouseEvent.MOUSE_EXITED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
            setStyle("-fx-background-color:#000000;"
            + "-fx-text-fill: #1CFF00;"
            + "-fx-border-color: #1CFF00;"
            + "-fx-border-width: 1px;");
          }
        });
        
        if (this.getText().equals("Stop")) {
            
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                    if (thread != null)
                            thread.stopRunning();
                }
            });
            
        }
        else {
            this.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (thread != null)
                            thread.stopRunning();
                        thread = new PlayAudioThread();
                        thread.setDaemon(true);
                        System.out.println("D: " + thread.isDaemon());
                        thread.start();
                    }
                });
            }
        }   
}
