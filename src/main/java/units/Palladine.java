package units;

import arena.Arena;
import units.abstractUnits.Equipment;
import units.abstractUnits.Unit;
import units.abstractUnits.UnitProtectiveWithShield;
import units.abstractUnits.UnitsTypes;

/**
 * Палладин
 */
public class Palladine extends UnitProtectiveWithShield {

    public Palladine(String name) {
        super(Equipment.shield_and_sword.getHealth(), Equipment.shield_and_sword.getAttack(),
                Equipment.shield_and_sword.getDefend(), UnitsTypes.Palladine, name);
    }

    public boolean defenceCape(Unit target) {
        if (getAbilityPoints() == 2) {
            System.out.println("Плащ защиты");
            super.clearAbilityPoints();
            super.decreaseDamage(target.getDefense() * 1);
            return true;
        }

        return false;
    }

    @Override
    public Unit findTarget(Arena arena) {
        // ищем ближайшего своего
        return arena.findTheNearestTeamUnit(this, false);
    }

    @Override
    public boolean applyAbility(Unit targetUnit) {
        return defenceCape(targetUnit);
    }
}
