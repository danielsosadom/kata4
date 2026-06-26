package software.ulpgc.kata2;

import software.ulpgc.kata2.control.Command;
import software.ulpgc.kata2.control.ToggleChartCommand;
import software.ulpgc.kata2.model.Champion;
import software.ulpgc.kata2.model.io.barchart.BarchartLoader;
import software.ulpgc.kata2.model.io.barchart.MockBarchartLoader;
import software.ulpgc.kata2.model.io.champion.ChampionReader;
import software.ulpgc.kata2.model.io.champion.CsvChampionDeserializer;
import software.ulpgc.kata2.model.io.champion.FileChampionReader;
import software.ulpgc.kata2.view.MainFrame;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            List<Champion> champions = new FileChampionReader(
                    new File("League of Legends Champion Stats 12.23.csv"),
                    new CsvChampionDeserializer()
            ).read();
            MainFrame frame = new MainFrame();
            BarchartLoader loader = new MockBarchartLoader(champions);

            frame.put("toggle", new ToggleChartCommand(loader, frame.getDisplay()));
            frame.getDisplay().show(loader.load(0));
            frame.setVisible(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
