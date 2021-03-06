package hu.atka.tetrisai.controller.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The representation of the playing area and addictional methods to simulate
 * proper {@link Piece} behavior.
 *
 * @author Atka
 */
public class Field {

	/**
	 * The logger of the object.
	 */
	private static Logger logger = LoggerFactory.getLogger(Field.class);

	/**
	 * The 10x20 block playing area of a game.
	 */
	private int[][] map;

	/**
	 * Constructor to initialize a field.
	 */
	public Field() {
		setMap(new int[20][10]);
		for (int i = 0; i < getMap().length; i++) {
			for (int j = 0; j < getMap()[i].length; j++) {
				getMap()[i][j] = 0;
			}
		}
	}

	public boolean[][] getCurrentVisual(Piece currentPiece) {
		boolean[][] currentVisual = new boolean[20][10];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (this.getMap()[i][j] == 0) {
					currentVisual[i][j] = false;
				} else {
					currentVisual[i][j] = true;
				}
			}
		}
		int[][] figure = currentPiece.getFigure();
		for (int i = 0; i < figure.length; i++) {
			for (int j = 0; j < figure[i].length; j++) {
				if (figure[i][j] != 0) {
					currentVisual[currentPiece.getY() + i][currentPiece.getX() + j] = true;
				}
			}
		}
		return currentVisual;
	}

	/**
	 * Checks if a {@link Piece} has enough space to spawn in to the top-center
	 * part of the map.
	 *
	 * @param piece
	 *            the piece to check
	 * @return true if the piece can be spawn, false otherwise
	 */
	public boolean isBadSpawn(Piece piece) {
		int[][] pieceFigure = piece.getFigure();
		for (int i = 0; i < pieceFigure.length; i++) {
			for (int j = 0; j < pieceFigure[i].length; j++) {
				if (pieceFigure[i][j] != 0 && getMap()[piece.getY() + i][piece.getX() + j] != 0) {
					logger.info("GAME OVER! New piece cannot spawn");
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if a {@link Piece} collides with already settled pieces or the
	 * sides of the map while performing a {@link PieceAction}.
	 *
	 * @param piece
	 *            the piece to check
	 * @param action
	 *            the action to check
	 * @return true if the piece will collide while performing the action, false
	 *         otherwise
	 */
	public boolean isPieceCollide(Piece piece, PieceAction action) {
		int[][] figure = new int[piece.getFigure().length][piece.getFigure()[0].length];
		for (int i = 0; i < figure.length; i++) {
			for (int j = 0; j < figure[i].length; j++) {
				figure[i][j] = piece.getFigure()[i][j];
			}
		}
		int xx = 0;
		int yy = 0;
		switch (action) {
		case DOWN: {
			yy = 1;
			break;
		}
		case LEFT: {
			xx = -1;
			break;
		}
		case RIGHT: {
			xx = 1;
			break;
		}
		case ROTATE_LEFT: {
			int[][] figureTemp = new int[figure.length][figure[0].length];
			for (int i = 0; i < figureTemp.length; i++) {
				for (int j = 0; j < figureTemp[i].length; j++) {
					figureTemp[i][j] = figure[j][(figure[i].length - 1) - i];
				}
			}
			figure = figureTemp;
			break;
		}
		case ROTATE_RIGHT: {
			int[][] figureTemp = new int[figure.length][figure[0].length];
			for (int i = 0; i < figureTemp.length; i++) {
				for (int j = 0; j < figureTemp[i].length; j++) {
					figureTemp[i][j] = figure[(figure.length - 1) - j][i];
				}
			}
			figure = figureTemp;
			break;
		}
		}
		for (int i = 0; i < figure.length; i++) {
			for (int j = 0; j < figure[i].length; j++) {
				if (figure[i][j] != 0) {
					try {
						if (getMap()[piece.getY() + i + yy][piece.getX() + j + xx] != 0) {
							//logger.info("Piece Collides with an already settled piece");
							return true;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						//logger.info("Piece Collides with the border");
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Settles a {@link Piece} on the map by setting the values at the proper
	 * coordinates with the values of the piece's figure. This method also
	 * clears the rows that got filled by the settling.
	 *
	 * @param piece
	 *            the piece to settle
	 * @return the number of rows cleared
	 */
	public int settlePiece(Piece piece) {
		int[][] figure = piece.getFigure();
		for (int i = 0; i < figure.length; i++) {
			for (int j = 0; j < figure[i].length; j++) {
				if (figure[i][j] != 0) {
					getMap()[piece.getY() + i][piece.getX() + j] = figure[i][j];
				}
			}
		}
		logger.info("Piece settled");
		int rowsFull = 0;
		for (int i = 0; i < getMap().length; i++) {
			if (rowFull(i)) {
				rowsFull++;
				for (int ii = i; ii >= 1; ii--) {
					for (int j = 0; j < getMap()[ii].length; j++) {
						getMap()[ii][j] = getMap()[ii - 1][j];
					}
				}
			}
		}
		return rowsFull;
	}

	/**
	 * Checks if the specified row of the map is filled with blocks.
	 *
	 * @param i
	 *            the row of the map to check
	 * @return true if the row is full, false otherwise
	 */
	public boolean rowFull(int i) {
		for (int j = 0; j < getMap()[i].length; j++) {
			if (getMap()[i][j] == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Getter method for the map.
	 *
	 * @return the map
	 */
	public int[][] getMap() {
		return map;
	}

	/**
	 * Setter method for the map.
	 *
	 * @param map
	 *            the map to set
	 */
	public void setMap(int[][] map) {
		this.map = map;
	}
}
