package software.ulpgc.kata2.control;

import software.ulpgc.kata2.model.Champion;
import software.ulpgc.kata2.model.io.champion.*;
import software.ulpgc.kata2.view.ImportDialog;
import software.ulpgc.kata2.view.MessageDisplay;

public class WriteChampionsCommand implements Command{
    private final ImportDialog input;
    private final ImportDialog output;
    private final MessageDisplay display;

    public WriteChampionsCommand(ImportDialog input, ImportDialog output, MessageDisplay display) {
        this.input = input;
        this.output = output;
        this.display = display;
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
            System.out.println("Saved successfully!");
            display.show("Saved successfully!");
        } catch (Exception e) {
            display.show("Error while loading!");
            throw new RuntimeException(e);
        }
    }
}
