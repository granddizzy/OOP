package units;

import arena.Arena;
import units.abstractUnits.Equipment;
import units.abstractUnits.Unit;
import units.abstractUnits.UnitProtectiveWithShield;
import units.abstractUnits.UnitsTypes;

import java.util.Random;

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
            super.clearAbilityPoints();
            target.addSuperimposedAction("Плащ защитника", 1, 0, target.getDefense(), 0);
            return true;
        }

        return false;
    }

    public boolean heavenlyShield(Unit target) {
        if (getAbilityPoints() == 2) {
            super.clearAbilityPoints();
            super.decreaseDamage(target.getDefense() * 1);
            target.addSuperimposedAction("Небесный щит", 1, 0, 100, 0);
            return true;
        }

        return false;
    }

    @Override
    public Unit findTarget(Arena arena) {
        // ищем ближайшего своего
        return arena.findTheNearestTeamUnit(this, false);
    }

    public Unit findTarget2(Arena arena) {
        return arena.findTheNearestTeamUnit(this, true);
    }

    @Override
    public boolean applyAbility(Unit targetUnit) {
        switch (new Random().nextInt(2)) {
            case 0 -> {
                return defenceCape(targetUnit);
            }
            case 1 -> {
                return heavenlyShield(targetUnit);
            }
        }
        return false;
    }

    @Override
    public boolean isInDiapason(Unit targetUnit) {
        return this.distanceSkill >= this.getCoordinates().calculateDistance(targetUnit.getCoordinates());
    }

    @Override
    public String getCharacterRepresentation() {
        return "Pld";
    }
}
