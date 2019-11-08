package Client;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author taylo
 */
public class Driver extends Application {
    
    private LoginWindow lw;
    private PlayerWindow pw;
    
    @Override
    public void start(Stage primaryStage) {
        
        // DRIVER SHOULD BE THIS 
        lw = new LoginWindow();
        String user = lw.getValidatedUser();
        System.out.println("Driver validated user:" + user);
        
        if (user == null) // didnt validate and window closed -> exit
            System.exit(0);
        else // open player
            pw = new PlayerWindow(user);
        System.exit(0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}