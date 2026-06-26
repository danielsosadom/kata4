package software.ulpgc.kata2.model.io.barchart;

import software.ulpgc.kata2.model.Barchart;
import software.ulpgc.kata2.model.Champion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MockBarchartLoader implements BarchartLoader{
    private final List<Champion> champions;

    public MockBarchartLoader(List<Champion> champions) {
        this.champions = champions;
    }

    @Override
    public Barchart load(int id) {
        return switch (id) {
            case 0 -> barchart0();
            case 1 -> barchart1();
            default -> null;
        };
    }

    private Barchart barchart0() {
        Barchart barchart = new Barchart("Champions by Role", "Role", "Amount");

        Map<Champion.Role, Long> collect = champions.stream()
                .collect(Collectors.groupingBy(
                        Champion::getRole,
                        Collectors.counting()
                ));
        collect.forEach((role, count) -> {
            barchart.put(role.name(), count.intValue());
        });

        return barchart;
    }


    public record PickRateInterval(
            double start,
            double end
    ){
        public String getTag(){
            return String.valueOf(start) + " to " + String.valueOf(end);
        }

        public boolean contains(double value){
            return value >= start && value < end;
        }
    }
    private Barchart barchart1() {
        List<PickRateInterval> intervals = List.of(
                new PickRateInterval(0.01, 0.05),
                new PickRateInterval(0.05, 0.1),
                new PickRateInterval(0.1, 0.15),
                new PickRateInterval(0.15, 0.2)
        );

        Barchart barchart = new Barchart("Champions by Pick Rate", "Pick Rate", "Amount");

        for (Champion champion: champions){
            for (PickRateInterval interval: intervals){
                if (interval.contains(champion.getPickRate())){
                    increaseCount(barchart, interval);
                    break;
                }
            }
        }

        return barchart;
    }

    private void increaseCount(Barchart barchart, PickRateInterval interval) {
        barchart.put(interval.getTag(),
                (barchart.get(interval.getTag()) != null) ?
                        barchart.get(interval.getTag()) + 1 :
                        1
                );
    }
}
