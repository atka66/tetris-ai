package hu.atka.tetrisai.ai;

import java.util.Set;

public class Brain {
  private Set<Neuron> neurons;

  public Brain(Set<Neuron> neurons) {
    this.neurons = neurons;
  }

  public Set<Neuron> getNeurons() {
    return neurons;
  }
}
