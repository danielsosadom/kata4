package software.ulpgc.kata2.model.io.champion;

import software.ulpgc.kata2.model.Champion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileChampionReader implements ChampionReader{
    private final File file;
    private final ChampionDeserializer deserializer;

    public FileChampionReader(File file, ChampionDeserializer deserializer) {
        this.file = file;
        this.deserializer = deserializer;
    }

    @Override
    public List<Champion> read() throws IOException {
        return read(new FileReader(file));
    }

    private List<Champion> read(FileReader fileReader) throws IOException {
        return read(new BufferedReader(fileReader));
    }

    private List<Champion> read(BufferedReader bufferedReader) throws IOException {
        skipHeader(bufferedReader);

        List<Champion> champions = new ArrayList<>();
        String line;

        while ((line = bufferedReader.readLine()) != null){
            champions.add(deserializer.deserialize(line));
        }

        return champions;
    }

    private void skipHeader(BufferedReader bufferedReader) throws IOException {
        bufferedReader.readLine();
    }
}
