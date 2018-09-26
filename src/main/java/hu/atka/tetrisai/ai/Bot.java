package hu.atka.tetrisai.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import hu.atka.tetrisai.controller.game.PieceAction;

public class Bot {
	private int id;
	private Set<Neuron> neurons;
	private int fitness;

	public Bot(Set<Neuron> neurons) {
		this.id = IdGenerator.getNextId();
		this.neurons = neurons;
		this.fitness = 0;
	}

	public PieceAction determineActionAccordingToVisual(boolean[][] visual) {
		Map<PieceAction, Double> actionIntensityMap = new HashMap<>();
		for (PieceAction action : PieceAction.values()) {
			actionIntensityMap.put(action, this.determineIntensityForAction(visual, action));
		}
		return actionIntensityMap.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(PieceAction.DOWN);
	}

	private Double determineIntensityForAction(boolean[][] visual, PieceAction action) {
		Double result = 0.0;
		if (action.equals(PieceAction.DOWN)) {
			result += 0.01;
		}

		// go through neurons with the corresponding action
		for (Neuron neuron : this.getNeurons().stream().filter(neuron -> neuron.getAction().equals(action)).collect(Collectors.toList())) {
			if (visual[neuron.getWatchedY()][neuron.getWatchedX()] == neuron.isWatchedBlock()) {
				result += neuron.getIntensity();
			}
		}
		return result;
	}

	public int getId() {
		return id;
	}

	public Set<Neuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(Set<Neuron> neurons) {
		this.neurons = neurons;
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	@Override
	public String toString() {
		return neurons.toString();
	}
}
