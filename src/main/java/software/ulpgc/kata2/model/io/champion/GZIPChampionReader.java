package software.ulpgc.kata2.model.io.champion;

import software.ulpgc.kata2.model.Champion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class GZIPChampionReader implements ChampionReader{
    private final File file;
    private final ChampionDeserializer deserializer;

    public GZIPChampionReader(File file, ChampionDeserializer deserializer) {
        this.deserializer = deserializer;
        this.file = file;
    }

    @Override
    public List<Champion> read() throws IOException {
        return read(new FileInputStream(file));
    }

    private List<Champion> read(FileInputStream fileInputStream) throws IOException {
        return read(new GZIPInputStream(fileInputStream));
    }

    private List<Champion> read(GZIPInputStream gzipInputStream) throws IOException {
        return read(new InputStreamReader(gzipInputStream));
    }

    private List<Champion> read(InputStreamReader inputStreamReader) throws IOException {
        return read(new BufferedReader(inputStreamReader));
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
