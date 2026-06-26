package software.ulpgc.kata2.model.io.champion;

import software.ulpgc.kata2.model.Champion;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class GZIPChampionIterator implements ChampionIterator, AutoCloseable{
    private final BufferedReader reader;
    private final ChampionDeserializer deserializer;

    public GZIPChampionIterator(File file, ChampionDeserializer deserializer) throws IOException {
        this.deserializer = deserializer;
        this.reader = getBufferedReader(file);
        skipHeader();
    }

    private void skipHeader() throws IOException {
        reader.readLine();
    }

    private BufferedReader getBufferedReader(File file) throws IOException {
        return new BufferedReader(new InputStreamReader(getGZIPInputStream(file)));
    }

    private GZIPInputStream getGZIPInputStream(File file) throws IOException {
        return new GZIPInputStream(new FileInputStream(file));
    }

    @Override
    public Champion next() throws IOException {
        String line;
        return ((line = reader.readLine()) != null) ?
                deserializer.deserialize(line) :
                null;
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
