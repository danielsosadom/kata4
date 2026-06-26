package software.ulpgc.kata2;

import software.ulpgc.kata2.control.ToggleChartCommand;
import software.ulpgc.kata2.control.WriteChampionsCommand;
import software.ulpgc.kata2.model.Champion;
import software.ulpgc.kata2.model.io.barchart.BarchartLoader;
import software.ulpgc.kata2.model.io.barchart.MockBarchartLoader;
import software.ulpgc.kata2.model.io.champion.*;
import software.ulpgc.kata2.view.ImportDialog;
import software.ulpgc.kata2.view.MainFrame;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            File dataset = new File("League of Legends Champion Stats 12.23.csv.gz");
            List<Champion> champions = readChampions(dataset);

            BarchartLoader loader = new MockBarchartLoader(champions);

            MainFrame frame = new MainFrame();
            frame.put("toggle", new ToggleChartCommand(loader, frame.getChartDisplay()));
            frame.put("save", new WriteChampionsCommand(
                    getInput(),
                    getOutput(),
                    frame.getMessageDisplay()));
            frame.getChartDisplay().show(loader.load(0));
            frame.setVisible(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static ImportDialog getInput() {
        return () -> new File("League of Legends Champion Stats 12.23.csv.gz");
    }

    private static ImportDialog getOutput(){
        return () -> new File("champions.db");
    }

    private static List<Champion> readChampions(File dataset) throws IOException {
        return new GZIPChampionReader(dataset,
                new CsvChampionDeserializer()
        ).read();
    }
}
