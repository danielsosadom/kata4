package software.ulpgc.kata2.model.io.champion;

import software.ulpgc.kata2.model.Champion;

import java.io.IOException;

public interface ChampionWriter {
    void write(Champion champion) throws IOException;
}
