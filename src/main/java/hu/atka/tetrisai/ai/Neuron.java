package hu.atka.tetrisai.ai;

import hu.atka.tetrisai.controller.game.PieceAction;

public class Neuron {
  private int watchedX;
  private int watchedY;
  private boolean watchedBlock;
  private PieceAction action;
  private double intensity;

  public Neuron(int watchedX, int watchedY, boolean watchedBlock, PieceAction action, double intensity) {
    this.watchedX = watchedX;
    this.watchedY = watchedY;
    this.watchedBlock = watchedBlock;
    this.action = action;
    this.intensity = intensity;
  }

  public int getWatchedX() {
    return watchedX;
  }

  public void setWatchedX(int watchedX) {
    this.watchedX = watchedX;
  }

  public int getWatchedY() {
    return watchedY;
  }

  public void setWatchedY(int watchedY) {
    this.watchedY = watchedY;
  }

  public boolean isWatchedBlock() {
    return watchedBlock;
  }

  public void setWatchedBlock(boolean watchedBlock) {
    this.watchedBlock = watchedBlock;
  }

  public PieceAction getAction() {
    return action;
  }

  public void setAction(PieceAction action) {
    this.action = action;
  }

  public double getIntensity() {
    return intensity;
  }

  public void setIntensity(double intensity) {
    this.intensity = intensity;
  }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Neuron{");
		sb.append("X=").append(watchedX);
		sb.append(" -- Y=").append(watchedY);
		sb.append(" -- Block=").append(watchedBlock);
		sb.append(" -- action=").append(action);
		sb.append(" -- intensity=").append(intensity);
		sb.append('}');
		return sb.toString();
	}
}
