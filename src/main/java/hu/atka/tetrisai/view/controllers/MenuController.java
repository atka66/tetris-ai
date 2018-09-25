//CHECKSTYLE:OFF
package hu.atka.tetrisai.view.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuController {

	@FXML
	Label labelTetris;
	@FXML
	Label labelCreator;
	@FXML
	Button buttonStartGame;

	@FXML
	private void handleButtonStartGame(ActionEvent event) {
		Stage stage;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClassroomFXML.fxml"));
			root = loader.load();
			loader.<ClassroomController> getController();
			stage = (Stage) buttonStartGame.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
