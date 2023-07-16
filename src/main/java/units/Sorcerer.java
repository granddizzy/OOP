package units;

import arena.Arena;
import units.abstractUnits.*;

import java.util.Random;

public class Sorcerer extends UnitAttackingWithMagician {
    int extraActivities = 1;
    public final int distanceSkill = 8;

    public Sorcerer(String name) {
        super(Equipment.runes_and_powders.getHealth(), Equipment.runes_and_powders.getAttack(),
                Equipment.runes_and_powders.getDefend(), UnitsTypes.Sorcerer, name);
    }

    public boolean performAnAttack(Unit unit) {

        if (extraActivities > 0) {
//            if (getAttack() - getDefense() > 0) {
//                unit.decreaseHealth(getAttack() - getDefense());
//            }

            if (super.performAnAttack(unit)) {
                return true;
            };
//        } else {
//            System.out.println("extraActivites <= 0");
        }

        return false;
    }

    public boolean tricks(Unit target) {
        if (getAbilityPoints() == 2) {
//            System.out.println("Трюки.");
            super.clearPointAbility();
            switch (new Random().nextInt(1, 3)) {
                case 1 -> target.decreaseSpeed(1);
                case 2 -> target.decreasePointActivities();
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean applyAbility(Unit targetUnit) {
        boolean res = this.tricks(targetUnit);

        return res;
    }

    @Override
    public Unit findTarget(Arena arena) {

        // ищем чужого с минимальным здоровьем
        return arena.findAUnitWithMinimumHealth(this, true);
    }

    @Override
    public boolean isInDiapason(Unit targetUnit) {
        return this.distanceSkill >= this.getCoordinates().calculateDistance(targetUnit.getCoordinates());
    }

    @Override
    public String getCharacterRepresentation() {
        return "Srr";
    }
}
