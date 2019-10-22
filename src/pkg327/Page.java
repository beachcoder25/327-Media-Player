
package pkg327;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author taylo
 */
public class Page extends VBox{
    
    private int page_number;
    //private PageBar pb;
    private String account_id;
    
    public Page(String account_id) {
        this.account_id = account_id;
        this.setStyle("-fx-background-color:#000000;"
            + "-fx-border-color: #1CFF00;"
            + "-fx-border-width: 1px;");
        
        //page_count = 0;
        //pb = new PageBar(page_count);
        
        //this.getChildren().add(pb);
    }
    
    public void makeNormalPages(int index) {
        
        this.page_number = index;
        
        this.getChildren().clear();
        
        
        
        
        Proxy p = new Proxy();

        String[] params = {Integer.toString(index)};

        String json = p.synchExecution("getNormalPage", params).get("ret").getAsString();
        System.out.println("page: " + json);
        
        TypeToken<List<MusicMeta>> token = new TypeToken<List<MusicMeta>>() {};
        
        ArrayList<MusicMeta> meta = new Gson().fromJson(json, token.getType());
        
        for(MusicMeta M : meta) {
            
            HBox row = new HBox();
            row.getChildren().addAll(
                    new AddButton(M.getRelease().getId(), this.account_id),
                    new PlayButton("Play"),
                    this.makeLine(M)
                    );
            this.getChildren().add(row);
            this.setMargin(row, new Insets(0, 0, 10, 0));
        }
        
        PageBar pb = new PageBar();
        
        pb.getPrevButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                    if (page_number > 0) {
                        makeNormalPages(--page_number);
                    }
                    
                }
            });
        
        pb.getNextButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                    if (page_number < 666) {
                        makeNormalPages(++page_number);
                    }
                    
                }
            });
     
        this.getChildren().add(pb);
    }
    
    public void makeAlphaPages(int index) {
        
        this.page_number = index;
        
        this.getChildren().clear();
        
        Proxy p = new Proxy();

        String[] params = {Integer.toString(index)};

        String json = p.synchExecution("getAlphaPage", params).get("ret").getAsString();
        System.out.println("page: " + json);
        
        TypeToken<List<MusicMeta>> token = new TypeToken<List<MusicMeta>>() {};
        
        ArrayList<MusicMeta> meta = new Gson().fromJson(json, token.getType());

        
        for(MusicMeta M : meta) {
            
            HBox row = new HBox();
            row.getChildren().addAll(
                    new AddButton(M.getRelease().getId(), this.account_id),
                    new PlayButton("Play"),
                    this.makeLine(M)
                    );
            this.getChildren().add(row);
            this.setMargin(row, new Insets(0, 0, 10, 0));
        }
        
        PageBar pb = new PageBar();
        
        pb.getPrevButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                    if (page_number > 0) {
                        makeAlphaPages(--page_number);
                    }
                }
            });
        
        pb.getNextButton().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                    if (page_number < 666) {
                        makeAlphaPages(++page_number);
                    }
                }
            });
        this.getChildren().add(pb);
    }
    
    public void makeRandPage() {
        
        this.getChildren().clear();
        
        Proxy p = new Proxy();

        String[] params = {};

        String json = p.synchExecution("getRandomPage", params).get("ret").getAsString();
        System.out.println("page: " + json);
        
        TypeToken<List<MusicMeta>> token = new TypeToken<List<MusicMeta>>() {};
        
        ArrayList<MusicMeta> meta = new Gson().fromJson(json, token.getType());

        
        for(MusicMeta M : meta) {
            
            HBox row = new HBox();
            row.getChildren().addAll(
                    new AddButton(M.getRelease().getId(), this.account_id),
                    new PlayButton("Play"),
                    this.makeLine(M)
                    );
            this.getChildren().add(row);
            this.setMargin(row, new Insets(0, 0, 10, 0));
        }   
    }
    
    public void showSearchResults(String s, boolean song) {
        
        this.getChildren().clear();
        
        Proxy p = new Proxy();

        String[] params = {s};
        String json;
        if (song)
            json = p.synchExecution("getSong", params).get("ret").getAsString();
        else
            json = p.synchExecution("getArtist", params).get("ret").getAsString();
//System.out.println("page: " + json);
        
        TypeToken<List<MusicMeta>> token = new TypeToken<List<MusicMeta>>() {};
        
        ArrayList<MusicMeta> meta = new Gson().fromJson(json, token.getType());
        
        for(MusicMeta M : meta) {
            
            HBox row = new HBox();
            row.getChildren().addAll(
                    new AddButton(M.getRelease().getId(), this.account_id),
                    new PlayButton("Play"),
                    this.makeLine(M)
                    );
            this.getChildren().add(row);
            this.setMargin(row, new Insets(0, 0, 10, 0));
        }
        
    }
    
    private Text makeLine(MusicMeta M) {
        
        int seconds = ((int)M.getSong().getDuration())%60;
        int min = ((int)M.getSong().getDuration())/60;
        
        String line =  " " + M.getSong().getTitle() + " - " + M.getArtist().getName()
                + " (" + min + ":" + seconds + ")";
        
        Text text = new Text(line);
        text.setFill(Color.web("#1CFF00"));
        return text;
    }
    
    
    
    
}