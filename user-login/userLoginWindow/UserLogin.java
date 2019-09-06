import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserLogin extends Application {
 Stage window;
 public static void main(String[] args) {
  // TODO Auto-generated method stub
  
  launch(args);
 }

 @Override
 public void start(Stage primaryStage) throws Exception {
    // TODO Auto-generated method stub
    window = primaryStage;
    primaryStage.setTitle("Login Window");

    GridPane grid = new GridPane();

    grid.setAlignment(Pos.CENTER);
    grid.setVgap(10);
    grid.setHgap(10);
    grid.setPadding(new Insets(10));

    Text welcometext = new Text("Welcome");
    welcometext.setFont(Font.font("Tahoma",FontWeight.LIGHT,25));
    grid.add(welcometext,0, 0);

    Label lb = new Label("Username");
    grid.add(lb, 0, 1);

    TextField txuser = new TextField();
    txuser.setPromptText("username");
    grid.add(txuser, 1, 1);

    Label lb2=new Label("Password");
    grid.add(lb2, 0, 2);

    PasswordField pbox=new PasswordField();
    pbox.setPromptText("password");
    grid.add(pbox, 1, 2);

    Button btn = new Button("Login");
    grid.add(btn, 1, 3);
    
    Scene scene = new Scene(grid,500,500);
    window.setScene(scene);
    
    window.show();
   }
}