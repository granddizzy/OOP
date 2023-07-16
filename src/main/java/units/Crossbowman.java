package units;

import arena.Arena;
import units.abstractUnits.*;


/**
 * Арбалетчик
 */
public class Crossbowman extends UnitAttackingWithWeapons {
    int extraActivities = 1;
    public final int distanceSkill = 9;

    public Crossbowman(String name) {
        super(Equipment.crossbow_and_helmet.getHealth(), Equipment.crossbow_and_helmet.getAttack(),
                Equipment.crossbow_and_helmet.getDefend(), UnitsTypes.Crossbowman, name);
    }

    @Override
    public boolean performAnAttack(Unit targetUnit) {
        super.performAnAttack(targetUnit);
        if (extraActivities > 0) {
            if (this.decreaseStamina(BASEWEAPON) > 0){
                if (getAttack() - getDefense() > 0) {
                    targetUnit.decreaseHealth(getAttack() - getDefense());
                    this.decreaseStamina(BASEWEAPON);
                    return true;
                }
            }
            ;
//        } else {
//            System.out.println("extraActivites <= 0");
        }
        return false;
    }

    public boolean arrowToTheKnee(Unit target) {
        if (getAbilityPoints() == 2) {
            super.clearPointAbility();
            //decreaseSpeed(1);
            target.addSuperimposedAction("Стрела в колено", 2, 0, 0, -1);

            return true;
        }

        return false;
    }

    @Override
    public boolean applyAbility(Unit targetUnit) {
        return arrowToTheKnee(targetUnit);
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
        return "Crb";
    }
}
