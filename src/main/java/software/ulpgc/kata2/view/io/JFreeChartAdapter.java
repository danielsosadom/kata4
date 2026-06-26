package software.ulpgc.kata2.view.io;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import software.ulpgc.kata2.model.Barchart;

public class JFreeChartAdapter {
    public static JFreeChart adapt(Barchart barchart){
        return ChartFactory.createBarChart(
                barchart.getTitle(),
                barchart.getxAxisTitle(),
                barchart.getyAxisTitle(),
                datasetOf(barchart)
        );
    }

    private static CategoryDataset datasetOf(Barchart barchart) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String category: barchart.keySet()){
            dataset.addValue(barchart.get(category), "", category);
        }
        return dataset;
    }
}
