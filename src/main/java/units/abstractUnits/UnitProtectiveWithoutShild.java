package units.abstractUnits;

import units.UnitsTypes;

public abstract class UnitProtectiveWithoutShild extends UnitProtective {
    public UnitProtectiveWithoutShild(int health, int defense, int attack, UnitsTypes type, String name) {
        super(health, defense, attack, type, name);
    }
}
