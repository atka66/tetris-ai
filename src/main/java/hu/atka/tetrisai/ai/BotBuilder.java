package hu.atka.tetrisai.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import hu.atka.tetrisai.controller.game.PieceAction;

public class BotBuilder {

	private static Logger logger = LoggerFactory.getLogger(BotBuilder.class);

	private NeuronMutator mutator;

	public BotBuilder() {
		mutator = new NeuronMutator();
	}

	public Bot buildBasicBot() {
		return new Bot(new HashSet<>());
	}

	public Bot buildMutatedBot(Bot template) {
		Bot mutatedBot = new Bot(this.copyNeuronSet(template.getNeurons()));
		mutator.mutateBot(mutatedBot);
		logger.info(mutatedBot.toString());
		return mutatedBot;
	}

	/**
	 * Returns a set of bots that are mutated from the template bots given as parameter.
	 * The number of mutated bots returned matches the number of bots given as parameter times a multiplier
	 * given as parameter.
	 * @param templateBots the set of bots that will be mutated
	 * @param multiplier the number of times the bots will be multiplied
	 */
	public Set<Bot> buildMutatedBots(Set<Bot> templateBots, int multiplier) {
		Set<Bot> result = new HashSet<>();

		for (Bot templateBot : templateBots) {
			for (int i = 0; i < multiplier; i++) {
				result.add(this.buildMutatedBot(templateBot));
			}
		}

		return result;
	}

	private Set<Neuron> copyNeuronSet(Set<Neuron> template) {
		Set<Neuron> result = new HashSet<>();
		template.forEach(neuron -> result.add(new Neuron(
			neuron.getWatchedX(),
			neuron.getWatchedY(),
			neuron.isWatchedBlock(),
			PieceAction.valueOf(neuron.getAction().name()),
			neuron.getIntensity()
		)));
		return result;
	}
}
