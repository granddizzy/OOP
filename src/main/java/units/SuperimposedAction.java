package units;

public class SuperimposedAction {
    public String name;
    public int period;
    public int attackChangeValue;
    public int defendChangeValue;
    private int startRaund;
    public int speedChangeValue;

    public SuperimposedAction(String name, int period, int attackChangeValue, int defendChangeValue, int speedChangeValue) {
        this.name = name;
        this.period = period;
        this.attackChangeValue = attackChangeValue;
        this.defendChangeValue = defendChangeValue;
        this.speedChangeValue = speedChangeValue;
    }

    public void setStartRaund(int startRaund) {
        if (this.startRaund == 0) this.startRaund = startRaund;
    }

    public int getStartRaund() {
        return startRaund;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
