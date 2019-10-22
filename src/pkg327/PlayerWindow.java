
package pkg327;

import com.google.gson.JsonObject;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author taylo
 */
public class PlayerWindow extends BorderPane{
    
    private TitleBar top;
    private ButtonPanel left;
    private Page center;
    private String account_id;
    private Proxy p = new Proxy();
    
    public PlayerWindow(String username) {
        
        //System.out.println("New player window");
        this.account_id = "1234";
        // get id from login services
//        String[] params = {username};
//        JsonObject result = p.synchExecution("getValidatedID", params);
//        this.account_id = result.get("ret").getAsString();
//        System.out.println("New player window got past synch");
        
        
        
        
        
        this.setStyle("-fx-background-color:#000000;"
            + "-fx-border-color: #1CFF00;"
            + "-fx-border-width: 1px;");
        
        this.top = new TitleBar(username);
        this.left = new ButtonPanel(account_id, this);
        
        this.center = new Page(account_id);
        //this.center.makeAlphaPages(0);
        
        this.setTop(top);
        this.setLeft(left);
        this.setCenter(center);
        
        Stage stage = new Stage();
        stage.setTitle("Media Player");
        stage.setScene(new Scene(this, 1240, 800));
        stage.show();
    }
    
    public Page getPage() {
        return this.center;
    }
}
