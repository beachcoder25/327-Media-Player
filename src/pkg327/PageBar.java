
package pkg327;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 *
 * @author taylo
 */
public class PageBar extends HBox{
    
    private Button prev;
    private Button next;
    
    public PageBar() {
        
        this.setStyle("-fx-background-color:#000000;"
            + "-fx-border-color: #1CFF00;"
            + "-fx-border-width: 1px;");
        
        prev = this.setupColors(new Button("Prev"));
        next = this.setupColors(new Button("Next"));
        
        this.getChildren().addAll(prev, next);
        this.setMargin(next, new Insets(0, 0, 0, 10));
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
    
    public Button getPrevButton() {
        return this.prev;
    }
    
    public Button getNextButton() {
        return this.next;
    }
    
    /*
    public int getPageCount() {
        return this.page_count;
    }
    
    public void setPageCount(int page_count) {
        this.page_count = page_count;
    }
    */
}
