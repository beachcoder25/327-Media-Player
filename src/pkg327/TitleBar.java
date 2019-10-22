
package pkg327;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author taylo
 */
public class TitleBar extends HBox{
    
    public TitleBar(String s) {
        
        
        Text title = new Text("Not-ify");
        title.setFont(Font.font ("Verdana", 20));
        title.setFill(Color.RED);
        
        Text username = new Text("Current User: " + s);
        username.setFont(Font.font ("Verdana", 20));
        username.setFill(Color.RED);
        
        this.setStyle("-fx-background-color:#000000;"
            + "-fx-text-fill: #1CFF00;"
            + "-fx-border-color: #1CFF00;"
            + "-fx-border-width: 1px;");
        
        this.getChildren().addAll(title, username);
        this.setMargin(username, new Insets(0, 0, 0, 99));
    }
}
