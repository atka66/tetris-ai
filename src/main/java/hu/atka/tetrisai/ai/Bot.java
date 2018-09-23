package hu.atka.tetrisai.ai;

public class Bot {
  private Brain brain;

  public Bot(Brain brain) {
    this.brain = brain;
  }

  public Brain getBrain() {
    return brain;
  }
}
