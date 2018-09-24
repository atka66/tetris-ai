package hu.atka.tetrisai.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import hu.atka.tetrisai.controller.game.Game;

public class BotClassroom {

	private static Logger logger = LoggerFactory.getLogger(BotClassroom.class);

	private BotBuilder builder = new BotBuilder();
	private Set<Bot> students;

	private final int INITIAL_NUMBER_OF_BOTS = 25;
	/**
	 * The ratio of which the bots that will be killed and multiplied.
	 */
	private final int BOT_MULTIPLIER = 4;

	public BotClassroom() {
		this.students = new HashSet<>();
		for (int i = 0; i < INITIAL_NUMBER_OF_BOTS; i++) {
			this.students.add(builder.buildBasicBot());
		}
		logger.info("Classroom brainmap: ");
		logger.info(this.students.toString());
		logger.info("Classroom initialized with " + INITIAL_NUMBER_OF_BOTS + " basic bots");
	}

	public void testStudents() {
		this.refillClassroom();
		this.trainBots();
		this.killOffWeakBots();
	}

	/**
	 * Fills the classroom, so it will have the desired amount of students.
	 */
	private void refillClassroom() {
		this.students = builder.buildMutatedBots(students, BOT_MULTIPLIER);
		logger.info("Classroom is filled with " + this.students.size() + " mutated bots");
	}

	/**
	 * The students play a round of Tetris to determine their fitness.
	 */
	private void trainBots() {
		logger.info("Training of bots begins");
		for (Bot bot : students) {
			Game game = new Game(bot);
			game.playSession();
		}
		logger.info("Training of bots ended");
	}

	/**
	 * Throws out a specified ratio of students that were just not fit enough.
	 */
	private void killOffWeakBots() {
		logger.info("FULL STUDENT SET:" + students.toString());
		int size = this.students.size();
		this.students =
			this.students.stream().sorted(Comparator.comparing(Bot::getFitness).reversed()).limit(size / BOT_MULTIPLIER).collect(Collectors.toSet());
		logger.info("PURGED STUDENT SET:" + students.toString());
		logger.info("Weaker bots are killed off, there are " + this.students.size() + " bots left");
		logger.info("Classroom brainmap: ");
		logger.info(this.students.toString());
	}

	public Bot getFittestStudent() {
		return students.stream().max(Comparator.comparing(Bot::getFitness)).get();
	}
}
