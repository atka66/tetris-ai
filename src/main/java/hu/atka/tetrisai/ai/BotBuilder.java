package hu.atka.tetrisai.ai;

import java.util.HashSet;
import java.util.Set;
import hu.atka.tetrisai.controller.game.PieceAction;

public class BotBuilder {

  private BrainMutator mutator;

  public BotBuilder() {
    mutator = new BrainMutator();
  }

  public Set<Bot> buildMutatedBots(Bot template, int numberOfBots) {
    Set<Bot> result = new HashSet<>();
    for (int i = 0; i < numberOfBots; i++) {
      result.add(this.buildMutatedBot(template));
    }
    return result;
  }

  public Bot buildMutatedBot(Bot template) {
    Bot mutatedBot = new Bot(new Brain(this.copyNeuronSet(template.getBrain().getNeurons())));
    mutator.mutateBot(mutatedBot);
    return mutatedBot;
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
