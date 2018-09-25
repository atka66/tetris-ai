package hu.atka.tetrisai.view.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import hu.atka.tetrisai.ai.BotClassroom;
import javafx.fxml.Initializable;

public class ClassroomController implements Initializable {

	BotClassroom classroom;

	public void initialize(URL location, ResourceBundle resources) {
		classroom = new BotClassroom();
	}
}
