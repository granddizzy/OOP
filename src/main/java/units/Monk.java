package units;

import arena.Arena;
import units.abstractUnits.Equipment;
import units.abstractUnits.Unit;
import units.abstractUnits.UnitProtectiveWithoutShild;
import units.abstractUnits.UnitsTypes;

import java.util.Random;

/**
 * Монах
 */
public class Monk extends UnitProtectiveWithoutShild {
    int distanceSkill = 9;

    public Monk(String name) {
        super(Equipment.kesa_and_beads.getHealth(), Equipment.kesa_and_beads.getAttack(),
                Equipment.kesa_and_beads.getDefend(), UnitsTypes.Monk, name);
    }

    /**
     * Мысли монаха
     * @param target
     * @return
     */
    public boolean mindMonk(Unit target) {
        if (getAbilityPoints() == 2) {
            super.clearAbilityPoints();
            //super.decreaseDamage(target.getDefense() * 1);   // вот здесь как-то определить тип атаки

            target.addSuperimposedAction("Мысли монаха", 1,0, target.getDefense(), 0);
            return true;
        }
        return false;
    }

    public boolean monkSHand(Unit target) {
        if (getAbilityPoints() == 2) {
            super.clearAbilityPoints();
            super.decreaseDamage(target.getDefense() * 1);
            target.addSuperimposedAction("Рука монаха", 1,0, 100, 0);
            return true;
        }
        return false;
    }

    @Override
    public Unit findTarget(Arena arena) {
        // ищем своего с минимальным здоровьем
        return arena.findAUnitWithMinimumHealth(this, false);
    }

    @Override
    public Unit findTarget2(Arena arena) {
        return arena.findAUnitWithMinimumHealth(this, true);
    }

    @Override
    public boolean applyAbility(Unit targetUnit) {
        switch (new Random().nextInt(2)) {
            case 0 -> {
                return mindMonk(targetUnit);
            }
            case 1 -> {
                return monkSHand(targetUnit);
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
        return "Mnk";
    }
}
