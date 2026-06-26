package software.ulpgc.kata2.model.io.champion;

import software.ulpgc.kata2.model.Champion;

public class CsvChampionDeserializer implements ChampionDeserializer{
    @Override
    public Champion deserialize(String line) {
        return deserialize(line.split(";"));
    }

    private Champion deserialize(String[] values) {
        return new Champion(
                values[0], 
                values[1], 
                toRole(values[2]),
                toTier(values[3]), 
                removePercentage(values[6]),
                removePercentage(values[8]),
                toDouble(values[10])
        );
    }

    private double removePercentage(String value) {
        String trimmed = value.substring(0, (value.length()-1));
        return toDouble(trimmed) / 100;
    }

    private static double toDouble(String value) {
        return Double.parseDouble(value);
    }

    private Champion.Tier toTier(String value) {
        return Champion.Tier.valueOf(value);
    }

    private Champion.Role toRole(String value) {
        return Champion.Role.valueOf(value);
    }
}
