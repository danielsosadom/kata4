package software.ulpgc.kata2.view;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import software.ulpgc.kata2.model.Barchart;
import software.ulpgc.kata2.view.BarchartDisplay;
import software.ulpgc.kata2.view.io.JFreeChartAdapter;

import javax.swing.*;
import java.awt.*;

public class JFreeChartDisplay extends JPanel implements BarchartDisplay {
    public JFreeChartDisplay() {
        setLayout(new BorderLayout());
    }

    @Override
    public void show(Barchart barchart) {
        removeAll();
        add(new ChartPanel(adapt(barchart)));
        revalidate();
    }

    private JFreeChart adapt(Barchart barchart) {
        return JFreeChartAdapter.adapt(barchart);
    }
}
