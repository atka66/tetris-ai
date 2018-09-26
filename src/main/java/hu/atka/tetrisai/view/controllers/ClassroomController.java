package hu.atka.tetrisai.view.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import hu.atka.tetrisai.ai.Bot;
import hu.atka.tetrisai.ai.BotClassroom;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ClassroomController implements Initializable {

	private BotClassroom classroom;
	private GraphicsContext gcField;

	@FXML
	private Label labelCurrentGenerationNumber;
	@FXML
	private Label labelCurrentGenerationFittestBotFitness;
	@FXML
	private Label labelCurrentGenerationFittestBotNeuronCount;
	@FXML
	private Label labelCurrentGenerationFittestBotLifespan;
	@FXML
	private Label labelCurrentGenerationFittestBotPoints;
	@FXML
	private Canvas canvasField;

	public void initialize(URL location, ResourceBundle resources) {
		classroom = new BotClassroom();
		gcField = canvasField.getGraphicsContext2D();
	}

	@FXML
	private void testStudents(ActionEvent event) {
		Node node = (Node) event.getSource();
		String data = (String) node.getUserData();
		int value = Integer.parseInt(data);
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() {
				for (int i = 0; i < value; i++) {
					classroom.testStudents();
					Bot fittestBot = classroom.getFittestStudent();
					// Update the GUI on the JavaFX Application Thread
					Platform.runLater(
						() -> {
							labelCurrentGenerationNumber.setText(String.valueOf(classroom.getGenerationCount()));
							labelCurrentGenerationFittestBotFitness.setText(String.valueOf(fittestBot.getFitness()));
							labelCurrentGenerationFittestBotNeuronCount.setText(String.valueOf(fittestBot.getNeurons().size()));
							labelCurrentGenerationFittestBotLifespan.setText(String.valueOf(classroom.getCurrentFittestStudentsGame().getTickCount()));
							labelCurrentGenerationFittestBotPoints.setText(String.valueOf(classroom.getCurrentFittestStudentsGame().getPoints()));
							clearAll();
							drawField();
						});
				}
				return null;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	private Paint getBlockColorFromInt(int color) {
		switch (color) {
			case 1:
				return Color.CYAN;
			case 2:
				return Color.BLUE;
			case 3:
				return Color.ORANGE;
			case 4:
				return Color.YELLOW;
			case 5:
				return Color.GREEN;
			case 6:
				return Color.MAGENTA;
			case 7:
				return Color.RED;
			default:
				return Color.BLACK;
		}
	}

	private void drawBlockOnGc(GraphicsContext gc, int x, int y, int color) {
		gc.setFill(getBlockColorFromInt(color));
		gc.fillRect(x * 16, y * 16, 15, 15);
		gc.strokeRect(x * 16, y * 16, 15, 15);
	}

	private void drawField() {
		gcField.setStroke(Color.BLACK);
		int[][] fieldMap = classroom.getCurrentFittestStudentsGame().getField().getMap();
		for (int i = 0; i < fieldMap.length; i++) {
			for (int j = 0; j < fieldMap[i].length; j++) {
				if (fieldMap[i][j] != 0) {
					drawBlockOnGc(gcField, j, i, fieldMap[i][j]);
				}
			}
		}
	}
/*
	private void drawCurrentPiece() {
		gcField.setStroke(Color.LIGHTGRAY);
		Piece currentPiece = game.getCurrentPiece();
		int[][] currentPieceFigure = currentPiece.getFigure();
		for (int i = 0; i < currentPieceFigure.length; i++) {
			for (int j = 0; j < currentPieceFigure[i].length; j++) {
				if (currentPieceFigure[i][j] != 0) {
					drawBlockOnGc(gcField, j + currentPiece.getX(), i + currentPiece.getY(), currentPieceFigure[i][j]);
				}
			}
		}
	}
	*/

	private void clearAll() {
		gcField.setFill(Color.BLACK);
		gcField.fillRect(0, 0, 160, 320);
		gcField.setStroke(Color.GRAY);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 20; j++) {
				gcField.strokeRect(i * 16, j * 16, 15, 15);
			}
		}
	}
}
