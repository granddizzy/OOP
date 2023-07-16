package units.abstractUnits;

import java.util.Random;

public abstract class UnitSupportiveHealer extends UnitSupportive {

    private int baseSpellHeal;

    public UnitSupportiveHealer(UnitsTypes type, String name) {
        this(0,0,0,type, name);
    }

    public UnitSupportiveHealer(int health, int defense, int attack, UnitsTypes type, String name) {
        super(health, defense, attack, type, name);
        this.baseSpellHeal = new Random().nextInt(10,21);
    }

    /**
     * лечить
     *
     * @param unit
     */
    public boolean smallHeal(Unit unit) {
        unit.addHealth(baseSpellHeal);
        return true;
    }
}
