package hu.atka.tetrisai.controller.game;

/**
 * The enumeration of the several actions a piece can do including three
 * movement, and two rotating actions.
 *
 * @author Atka
 */
public enum PieceAction {
	/**
	 * Downward movement.
	 */
	DOWN(1),
	/**
	 * Movement to the left.
	 */
	LEFT(2),
	/**
	 * Movement to the right.
	 */
	RIGHT(3),
	/**
	 * Rotation to the left.
	 */
	ROTATE_LEFT(4),
	/**
	 * Rotation to the right.
	 */
	ROTATE_RIGHT(5);

	private int intValue;

	PieceAction(int intValue) {
		this.intValue = intValue;
	}

	public int getIntValue() {
		return intValue;
	}
}
