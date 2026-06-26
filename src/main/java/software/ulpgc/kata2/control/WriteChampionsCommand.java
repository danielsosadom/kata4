package software.ulpgc.kata2.control;

import software.ulpgc.kata2.model.Champion;
import software.ulpgc.kata2.model.io.champion.ChampionIterator;
import software.ulpgc.kata2.model.io.champion.ChampionWriter;

import java.io.IOException;

public class WriteChampionsCommand implements Command{
    private final ChampionIterator iterator;
    private final ChampionWriter writer;

    public WriteChampionsCommand(ChampionIterator iterator, ChampionWriter writer) {
        this.iterator = iterator;
        this.writer = writer;
    }

    @Override
    public void execute() {
        while (true){
            try {
                Champion champion = iterator.next();
                if (champion == null) break;
                writer.write(champion);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
