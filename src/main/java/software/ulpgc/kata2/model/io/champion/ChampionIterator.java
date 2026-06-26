package software.ulpgc.kata2.model.io.champion;

import software.ulpgc.kata2.model.Champion;

import java.io.IOException;

public interface ChampionIterator {
    Champion next() throws IOException;
}
