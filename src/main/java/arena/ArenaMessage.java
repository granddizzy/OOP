package arena;

import units.Team;
import units.abstractUnits.Unit;

public class ArenaMessage {
    public final Unit unit;
    public final Unit target;
    public final String message;

    public ArenaMessage(Unit unit, Unit target, String message) {
        this.unit = unit;
        this.target = target;
        this.message = message;
    }
}
