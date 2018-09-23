package hu.atka.tetrisai.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BotClassroom {
  private BotBuilder botBuilder;
  private Set<Bot> students;

  public BotClassroom() {
    this.botBuilder = new BotBuilder();
    this.students = botBuilder.buildMutatedBots();
    }
  }
}
