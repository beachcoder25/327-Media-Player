
package pkg327;

// Imports
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Taylor Meyer
 */
public class LoginWindow extends Stage{
    
    // Handles account registration and login
    //private LoginServices ls;
    
    // Grid to lay onto Stage
    private GridPane gp;
    
    // Text
    private Text login_text;
    private Text username_text;
    private Text password_text;
    
    // Notifications
    private Text invalid_login;
    private Text account_registered;
    private Text account_already_exists;
    
    // Text Fields
    private TextField username_textfield;
    private TextField password_textfield;
    
    // Buttons
    private Button login_button;
    private Button exit_button;
    private Button register_button;
    
    
    private String validated_user = null;
    
    private Proxy p;
    
    public LoginWindow() {
        p = new Proxy();
        
        // Setup all components/nodes
        setupComponents();
        this.setTitle("Media Player");
        this.showAndWait();
    }
    
    public void processLogin(String username, String password)
    {
        
        String[] params = {username, password};
        
        JsonObject result = p.synchExecution("validateAccount", params);
        String s = result.get("ret").getAsString();
        boolean answer = Boolean.parseBoolean(s);
        
        if(Boolean.parseBoolean(s))
        {
            System.out.println("Return from proxy: " + answer + ": Account found");
            this.validated_user = username;
            this.close();
        }
        else
        {
            System.out.println("Return from proxy: " + answer + ": Account not found");
            showMessage(invalid_login);
        }
    }
    
    
    
    private void showMessage(Text text) {
        
        if(gp.getChildren().contains(invalid_login))
            gp.getChildren().remove(invalid_login);
        if(gp.getChildren().contains(account_registered))
            gp.getChildren().remove(account_registered);
        if(gp.getChildren().contains(account_already_exists))
            gp.getChildren().remove(account_already_exists);
        
        gp.add(text, 0, 3);
        
    }
    
    private void setupComponents() {
        
        /* TEXT */
        login_text = new Text("Login:");
        login_text.setFont(Font.font("Tahoma",FontWeight.LIGHT,25));
        login_text.setFill(Color.web("0x1CFF00"));
        
        username_text = new Text("Username");
        username_text.setFill(Color.web("0x1CFF00"));
        
        password_text = new Text("Password");
        password_text.setFill(Color.web("0x1CFF00"));
        
        /* NOTIFICATIONS */
        // Invalid notification
        invalid_login = new Text("Invalid usr/pw");
        invalid_login.setFill(Color.web("0x1CFF00"));

        // Successful registration notification
        account_registered = new Text("Success");
        account_registered.setFill(Color.web("0x1CFF00"));

        // Already exists
        account_already_exists = new Text("Account already exists!");
        account_already_exists.setFill(Color.web("0x1CFF00"));
        
        /* TEXT FIELDS */
        username_textfield = new TextField();
        username_textfield.setPromptText("username");
        username_textfield.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                processLogin(username_textfield.getText(),
                        password_textfield.getText());
                }catch(Exception e){}
            }
        });
        
        password_textfield = new PasswordField();
        password_textfield.setPromptText("password");
        password_textfield.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                processLogin(username_textfield.getText(),
                        password_textfield.getText());
                }catch(Exception e){}
            }
        });
        
        /* BUTTONS */
        // Login Button
        login_button = new Button("Login");
        login_button.setStyle("-fx-border-color: #1CFF00;"
                + "-fx-border-width: 1px;"
                + "-fx-text-fill: #1CFF00;"
                + "-fx-background-color: #000000");
        login_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                processLogin(username_textfield.getText(),
                        password_textfield.getText());
                }catch(Exception e){}
            }
        });
        
        // Register Button
        register_button = new Button("Register");
        register_button.setStyle("-fx-border-color: #1CFF00;"
                + "-fx-border-width: 1px;"
                + "-fx-text-fill: #1CFF00;"
                + "-fx-background-color: #000000");
        register_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("You hit the register button.");
                registerAccount(username_textfield.getText(),
                        password_textfield.getText());
            }
        });
        
        // Exit Button
        exit_button = new Button("Exit");
        exit_button.setStyle("-fx-border-color: #1CFF00;"
                + "-fx-border-width: 1px;"
                + "-fx-text-fill: #1CFF00;"
                + "-fx-background-color: #000000");
        exit_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });

        
        
        
        
        
        /* GRID PANE */
        gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(10));
        gp.setBackground(new Background(
            new BackgroundFill(Color.rgb(0, 0, 0),
            CornerRadii.EMPTY, Insets.EMPTY)));
        gp.setStyle("-fx-background-color:#000000;"
            + "-fx-border-color: #1CFF00;"
            + "-fx-border-width: 1px;");
        
        // Add it all to GridPane
        gp.add(login_text,0, 0);
        gp.add(username_text, 0, 1);
        gp.add(password_text, 0, 2);
        gp.add(username_textfield, 1, 1);
        gp.add(password_textfield, 1, 2);
        gp.add(login_button, 1, 3);
        gp.add(register_button, 1, 4);
        gp.add(exit_button, 1, 5);
        
        this.setScene(new Scene(gp,500,500));
    }
    
    private void registerAccount(String username, String password) {

        String[] params = {username, password};

        JsonObject result = p.synchExecution("registerAccount", params);
        String s = result.get("ret").getAsString();

        if(Boolean.parseBoolean(s))
        {
            System.out.println("Register success");
            showMessage(account_registered);
        }
        else
        {
            System.out.println("Register failed");
            showMessage(account_already_exists);
        }
    }
    
    public String getValidatedUser() {
        return this.validated_user;
    }
}