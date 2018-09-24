//CHECKSTYLE:OFF
package hu.atka.tetrisai.view.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import hu.atka.tetrisai.ai.BotClassroom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MenuController implements Initializable {

	@FXML
	Label labelTetris;
	@FXML
	Label labelCreator;
	@FXML
	Button buttonStartGame;

	BotClassroom classroom;

	@FXML
	private void handleButtonStartGame(ActionEvent event) {
		for(int i = 0; i < 10000; i++) {
			classroom.testStudents();
			System.out.println("---- Generation: " + (i+1));
			System.out.println("- Fitness: " + classroom.getFittestStudent().getFitness());
			System.out.println("- Number of neurons: " + classroom.getFittestStudent().getNeurons().size());
			System.out.println("- Neurons: " + classroom.getFittestStudent().getNeurons());
		}
		/*
		Stage stage;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameFXML.fxml"));
			root = loader.load();
			loader.<GameController> getController();
			stage = (Stage) buttonStartGame.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}

	public void initialize(URL location, ResourceBundle resources) {
		classroom = new BotClassroom();
	}
}
