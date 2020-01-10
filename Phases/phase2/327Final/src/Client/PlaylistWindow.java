package Client;


import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author taylo
 */
public class PlaylistWindow extends Stage{
    
    Proxy p = new Proxy();
    
    public PlaylistWindow(int song_id, String account_id) {
        GridPane gp = new GridPane();
        gp.setStyle("-fx-background-color:#000000;"
            + "-fx-border-color: #1CFF00;"
            + "-fx-border-width: 1px;");
        
        TextField field = new TextField();
        field.setPromptText("enter playlist name..");
        field.setStyle("-fx-background-color:#323232;");
        field.setStyle("-fx-text-inner-color: red;");
        
        Button okay = new Button();
        okay.setText("Okay");
        okay.setStyle("-fx-background-color:#000000;"
            + "-fx-text-fill: #1CFF00;"
            + "-fx-border-color: #1CFF00;"
            + "-fx-border-width: 1px;");
        okay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                //synch execute add to playlist
                String[] params = {
                                Integer.toString(song_id),
                                field.getText(),
                                account_id
                                };
                JsonObject result = p.synchExecution("addSongToPlaylist", params);
                //makeCallToSave();
                close();
            }
        });
        
        gp.add(field, 1, 1);
        gp.add(okay, 1, 2);
        
        this.setScene(new Scene(gp, 100, 100));
        //this.show();
    }
    
    private void makeCallToSave() {
        
        String[] params = {};
        String json = p.synchExecution("save", params).get("ret").getAsString();
    }
    
}
