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
	private GraphicsContext gcNeurons;

	@FXML
	private Label labelGenNumber;
	@FXML
	private Label labelFittestId;
	@FXML
	private Label labelFittestFitness;
	@FXML
	private Label labelFittestLifespan;
	@FXML
	private Label labelFittestPoints;
	@FXML
	private Label labelFittestNeuronCount;
	@FXML
	private Canvas canvasField;
	@FXML
	private Canvas canvasNeurons;

	public void initialize(URL location, ResourceBundle resources) {
		classroom = new BotClassroom();
		gcField = canvasField.getGraphicsContext2D();
		gcNeurons = canvasNeurons.getGraphicsContext2D();
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
					Platform.runLater(
						() -> {
							labelGenNumber.setText(String.valueOf(classroom.getGenerationCount()));
							labelFittestId.setText(String.valueOf(fittestBot.getId()));
							labelFittestFitness.setText(String.valueOf(fittestBot.getFitness()));
							labelFittestNeuronCount.setText(String.valueOf(fittestBot.getNeurons().size()));
							labelFittestLifespan.setText(String.valueOf(classroom.getCurrentFittestStudentsGame().getTickCount()));
							labelFittestPoints.setText(String.valueOf(classroom.getCurrentFittestStudentsGame().getPoints()));
							clearAll();
							drawField();
							drawNeurons();
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

	private void drawNeuronOnGc(GraphicsContext gc, int x, int y, int[] neuron) {
		// if watched block ? white : black
		gcNeurons.setStroke(neuron[1] == 0 ? Color.WHITE : Color.DARKGRAY);
		Color neuronColor;
		switch (neuron[3]) {
			case 1:
				neuronColor = Color.CYAN;
				break;
			case 2:
				neuronColor = Color.BLUE;
				break;
			case 3:
				neuronColor = Color.ORANGE;
				break;
			case 4:
				neuronColor = Color.YELLOW;
				break;
			case 5:
				neuronColor = Color.GREEN;
				break;
			default:
				neuronColor = Color.BLACK;
				break;
		}
		gc.setFill(neuronColor);
		int radius = neuron[2] / 10;
		gc.strokeRect(x * 16, y * 16, 15, 15);
		gc.fillOval((x * 16) + (8 - radius), (y * 16) + (8 - radius), radius * 2, radius * 2);
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

	private void drawNeurons() {
		gcNeurons.setStroke(Color.BLACK);
		int[][][] neuronMap = classroom.getFittestStudent().getNeuronMap();
		for (int i = 0; i < neuronMap.length; i++) {
			for (int j = 0; j < neuronMap[i].length; j++) {
				int[] neuron = neuronMap[i][j];
				if (neuron[0] == 1) {
					drawNeuronOnGc(gcNeurons, j, i, neuron);
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

		gcNeurons.setFill(Color.BLACK);
		gcNeurons.fillRect(0, 0, 160, 320);
		gcNeurons.setStroke(Color.GRAY);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 20; j++) {
				gcNeurons.strokeRect(i * 16, j * 16, 15, 15);
			}
		}
	}
}
