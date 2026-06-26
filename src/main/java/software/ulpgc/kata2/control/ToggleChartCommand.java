package software.ulpgc.kata2.control;

import software.ulpgc.kata2.model.io.barchart.BarchartLoader;
import software.ulpgc.kata2.view.BarchartDisplay;

public class ToggleChartCommand implements Command{
    private final BarchartLoader loader;
    private final BarchartDisplay display;
    private int i = 0;

    public ToggleChartCommand(BarchartLoader loader, BarchartDisplay display) {
        this.loader = loader;
        this.display = display;
    }

    @Override
    public void execute() {
        display.show(loader.load((++i)%2));
    }
}
