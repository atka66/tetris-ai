package hu.atka.tetrisai.ai;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import hu.atka.tetrisai.controller.game.PieceAction;

public class BrainMutator {
  private Random rand;
  private final double CHANCE_TO_ADD_NEURON = 0.03;
  private final double CHANCE_TO_MODIFY_POSITION_NEURON = 0.05;
  private final double CHANCE_TO_MODIFY_BLOCK_NEURON = 0.005;
  private final double CHANCE_TO_MODIFY_ACTION_NEURON = 0.008;
  private final double CHANCE_TO_MODIFY_INTENSITY_NEURON = 0.1;
  private final double CHANCE_TO_REMOVE_NEURON = 0.01;

  public BrainMutator() {
    this.rand = new Random();
  }

  public void mutateBot(Bot bot) {
    Set<Neuron> neurons = bot.getBrain().getNeurons();

    // Removing neuron
    neurons = neurons.stream().filter(neuron -> !(rand.nextDouble() <= CHANCE_TO_REMOVE_NEURON)).collect(Collectors.toSet());

    // Modifying neuron
    neurons.forEach(neuron -> {
      if (rand.nextDouble() <= CHANCE_TO_MODIFY_POSITION_NEURON) {
        this.modifyNeuronPosition(neuron);
      }
      if (rand.nextDouble() <= CHANCE_TO_MODIFY_BLOCK_NEURON) {
        this.modifyNeuronBlock(neuron);
      }
      if (rand.nextDouble() <= CHANCE_TO_MODIFY_ACTION_NEURON) {
        neuron.setAction(this.getRandomPieceAction());
      }
      if (rand.nextDouble() <= CHANCE_TO_MODIFY_INTENSITY_NEURON) {
        this.modifyNeuronIntensity(neuron);
      }
    });

    // Adding neuron
    if (rand.nextDouble() <= CHANCE_TO_ADD_NEURON) {
      neurons.add(this.buildRandomNeuron());
    }
  }

  private void modifyNeuronPosition(Neuron neuron) {
    int y = neuron.getWatchedY();
    int x = neuron.getWatchedX();
    switch (rand.nextInt(4)) {
      case 0:
        if (y > 0) {
          neuron.setWatchedY(y - 1);
        }
        break;
      case 1:
        if (y < 19) {
          neuron.setWatchedY(y + 1);
        }
        break;
      case 2:
        if (x > 0) {
          neuron.setWatchedX(x - 1);
        }
        break;
      default:
        if (x < 9) {
          neuron.setWatchedX(x + 1);
        }
        break;
    }
  }

  private void modifyNeuronBlock(Neuron neuron) {
    neuron.setWatchedBlock(!neuron.isWatchedBlock());
  }

  private PieceAction getRandomPieceAction() {
    switch (rand.nextInt(5)) {
      case 0:
        return PieceAction.DOWN;
      case 1:
        return PieceAction.LEFT;
      case 2:
        return PieceAction.RIGHT;
      case 3:
        return PieceAction.ROTATE_LEFT;
      default:
        return PieceAction.ROTATE_RIGHT;
    }
  }

  private void modifyNeuronIntensity(Neuron neuron) {
    neuron.setIntensity(neuron.getIntensity() + ((rand.nextDouble() - 0.5) / 5));
  }

  private Neuron buildRandomNeuron() {
    return new Neuron(
        rand.nextInt(10),
        rand.nextInt(20),
        rand.nextBoolean(),
        this.getRandomPieceAction(),
        rand.nextDouble()
    );
  }
}
