package software.ulpgc.kata2.model;

public class Champion {
    private final String name;
    private final String classType;
    private final Role role;
    private final Tier tier;
    private final double winRate;
    private final double pickRate;
    private final double kda;

    public Champion(String name, String classType, Role role, Tier tier, double winRate, double pickRate, double kda) {
        this.name = name;
        this.classType = classType;
        this.role = role;
        this.tier = tier;
        this.winRate = winRate;
        this.pickRate = pickRate;
        this.kda = kda;
    }

    public enum Role {
        TOP,
        JUNGLE,
        MID,
        ADC,
        SUPPORT
    }

    public enum Tier {
        God,
        S,
        A,
        B,
        C,
        D
    }

    public String getName() {
        return name;
    }

    public String getClassType() {
        return classType;
    }

    public Role getRole() {
        return role;
    }

    public Tier getTier() {
        return tier;
    }

    public double getWinRate() {
        return winRate;
    }

    public double getPickRate() {
        return pickRate;
    }

    public double getKda() {
        return kda;
    }

    @Override
    public String toString() {
        return "Champion{" +
                "name='" + name + '\'' +
                ", classType='" + classType + '\'' +
                ", role=" + role +
                ", tier=" + tier +
                ", winRate=" + String.format("%.2f", winRate) +
                ", pickRate=" + String.format("%.2f", pickRate) +
                ", KDA=" + kda +
                '}';
    }
}
