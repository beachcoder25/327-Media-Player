package pkg327;

import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Taylor Meyer
 */
public class RemoveButton extends Button{
    
    public RemoveButton(Page page, Playlist p, int song_id, String account_id) {
        
        this.setText("-");
        
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
        
        
        
        
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                // remove logic
                Proxy pr = new Proxy();
                String[] params = {Integer.toString(song_id),p.getName(), account_id};
                String json = pr.synchExecution("removeSongFromPlaylist", params).get("ret").getAsString();
                page.getPlayerWindow().getButtonPanel().refreshDropDrown();
                p.removeSong(song_id);
                page.showPlaylist(p);
            }
        });
    }
}
