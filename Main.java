package sample;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception
  {
      Parent root = FXMLLoader.load(getClass().getResource("/sample/view/main_menu.fxml"));
      Scene scene = new Scene(root);
      primaryStage.setTitle("Inventory Management System");
      primaryStage.setScene(scene);
      primaryStage.show();
  }
    public static void main(String[] args) {
        launch(args);
    }
}
