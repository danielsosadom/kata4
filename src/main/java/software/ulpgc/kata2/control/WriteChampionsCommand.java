package software.ulpgc.kata2.control;

import software.ulpgc.kata2.model.Champion;
import software.ulpgc.kata2.model.io.champion.*;
import software.ulpgc.kata2.view.ImportDialog;

import java.io.IOException;

public class WriteChampionsCommand implements Command{
    private final ImportDialog input;
    private final ImportDialog output;

    public WriteChampionsCommand(ImportDialog input, ImportDialog output) {
        this.input = input;
        this.output = output;
    }


    @Override
    public void execute() {
        try(GZIPChampionIterator iterator = new GZIPChampionIterator(input.get(),  new CsvChampionDeserializer());
            DatabaseChampionWriter writer = DatabaseChampionWriter.open(output.get());
        ) {
            while(true) {
                Champion champion = iterator.next();
                if (champion == null) break;
                writer.write(champion);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
