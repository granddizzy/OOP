package arena.map;

import units.Coordinates;

public class Pit {
    public Coordinates coordinates;
    public int period;
    public int startRaund = 0;

    public Pit(Coordinates coordinates, int period) {
        this.coordinates = coordinates;
        this.period = period;
    }

    @Override
    public String toString() {
        return "Яма";
    }
}
