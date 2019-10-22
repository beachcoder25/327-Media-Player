
package pkg327;

import com.google.gson.JsonObject;
import java.net.SocketException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static pkg327.PlayButton.thread;

/**
 *
 * @author taylo
 */
public class ButtonPanel extends VBox{
    
    private PlayerWindow pw;
    private String playlist_name;
    Proxy p = new Proxy();
    
    public ButtonPanel(String account_id, PlayerWindow pw) {
        
        this.pw = pw;
        
        this.setStyle("-fx-background-color:#000000;"
            + "-fx-border-color: #1CFF00;"
            + "-fx-border-width: 1px;");
        
        
        Button norm = this.setupColors(new Button("Norm"));
        norm.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                    pw.getPage().makeNormalPages(0);
                    
                }
            });
        
        Button alpha = this.setupColors(new Button("Alpha"));
        alpha.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                    pw.getPage().makeAlphaPages(0);
                    
                }
            });
        
        Button rand = this.setupColors(new Button("Rand"));
        rand.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                    pw.getPage().makeRandPage();
                    
                }
            });
        
        
        Button stop = this.setupColors(new PlayButton("Stop"));
        
        
        TextField create_playlist_field = new TextField();
        create_playlist_field.setPromptText("enter playlist name..");
        create_playlist_field.setStyle("-fx-background-color:#323232;");
        create_playlist_field.setStyle("-fx-text-inner-color: red;");
        Button create_playlist = this.setupColors(new Button("New Playlist"));
        create_playlist.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                    // creates playlist
                    String[] params = {create_playlist_field.getText(), account_id};
                    JsonObject result = p.synchExecution("createPlaylist", params);
                    
                }
            });
        
        TextField delete_playlist_field = new TextField();
        delete_playlist_field.setPromptText("enter playlist name..");
        delete_playlist_field.setStyle("-fx-background-color:#323232;");
        delete_playlist_field.setStyle("-fx-text-inner-color: red;");
        Button delete_playlist = this.setupColors(new Button("Del Playlist"));
        delete_playlist.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("new playlist button");
                    // deletes playlist
                    String[] params = {delete_playlist_field.getText(), account_id};
                    JsonObject result = p.synchExecution("deletePlaylist", params);
                    
                }
            });
        
        
        
        TextField song_search_field = new TextField();
        song_search_field.setPromptText("enter song name..");
        song_search_field.setStyle("-fx-background-color:#323232;");
        song_search_field.setStyle("-fx-text-inner-color: red;");
        Button song_search_button = this.setupColors(new Button("Search Song"));
        song_search_button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                    //song search
                    pw.getPage().showSearchResults(song_search_field.getText(), true);
                    
                }
            });
        
        
        TextField artist_search_field = new TextField();
        artist_search_field.setPromptText("enter artist name..");
        artist_search_field.setStyle("-fx-background-color:#323232;");
        artist_search_field.setStyle("-fx-text-inner-color: red;");
        Button artist_search_button = this.setupColors(new Button("Search Artist"));
        artist_search_button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                    //artist search
                    pw.getPage().showSearchResults(artist_search_field.getText(), false);
                    
                }
            });
        
        
        Button exit = this.setupColors(new Button("Exit"));
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        
    
        
        
        
        
        
        ChoiceBox<String> dropDown = new ChoiceBox();
        
        //dropDown.getItems().add("Apples");
        
        
        
        System.out.println(dropDown.getValue());
        
        PlaylistServices p = new PlaylistServices();
        
        String[] userPlaylists = p.getPlaylistNames("1234");
        
        for (int i = 0; i < userPlaylists.length; i++) {
            dropDown.getItems().add(userPlaylists[i]);
        }
        
        if(dropDown.getChildrenUnmodifiable().size() != 0)
        dropDown.setValue(userPlaylists[0]);
        
        dropDown.setStyle("-fx-background-color:#000000;"
            + "-fx-text-fill: #1CFF00;"
            + "-fx-border-color: #1CFF00;"
            + "-fx-border-width: 1px;");
        
        this.getChildren().addAll(
                norm,
                alpha,
                rand,
                stop,
                song_search_field,
                song_search_button,
                artist_search_field,
                artist_search_button,
                create_playlist_field,
                create_playlist,
                delete_playlist_field,
                delete_playlist,
                dropDown,
                exit);
    }
    
    private Button setupColors(Button b) {
        
        b.setStyle("-fx-background-color:#000000;"
            + "-fx-text-fill: #1CFF00;"
            + "-fx-border-color: #1CFF00;"
            + "-fx-border-width: 1px;");
        
        b.addEventHandler(MouseEvent.MOUSE_ENTERED,
        new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                b.setStyle("-fx-background-color:#000000;"
                + "-fx-text-fill: #E300FF;"
                + "-fx-border-color: #E300FF;"
                + "-fx-border-width: 1px;");
              }
        });

        b.addEventHandler(MouseEvent.MOUSE_EXITED,
            new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                b.setStyle("-fx-background-color:#000000;"
                + "-fx-text-fill: #1CFF00;"
                + "-fx-border-color: #1CFF00;"
                + "-fx-border-width: 1px;");
            }
        });
        return b;
    }
    
    public static void main(String[] args) throws SocketException {
        Proxy p = new Proxy();
        // creates playlist
            String[] params = {"a", "1234"};
        JsonObject result = p.synchExecution("createPlaylist", params);
    }
            
            
}