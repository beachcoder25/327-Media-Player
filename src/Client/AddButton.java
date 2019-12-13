
package Client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author taylo
 */
public class AddButton extends Button{
    
    private PlaylistWindow pw;
    private Page page;
    
    public AddButton(Page page, int song_id, String account_id) {
        
        this.page = page;
        this.setText("+");
        
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

                pw = new PlaylistWindow(song_id, account_id);
                pw.showAndWait();
                page.getPlayerWindow().getButtonPanel().refreshDropDrown();
            }
        });
        
    }
    
    
}
