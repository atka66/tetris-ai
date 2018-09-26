package hu.atka.tetrisai.ai;

public class IdGenerator {
	private static int currentId = 0;

	public static int getNextId() {
		return currentId++;
	}
}
