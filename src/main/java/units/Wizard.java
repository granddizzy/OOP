package units;

import arena.Arena;
import units.abstractUnits.Equipment;
import units.abstractUnits.Unit;
import units.abstractUnits.UnitAttackingWithMagician;
import units.abstractUnits.UnitsTypes;

/**
 * Маг
 */
public class Wizard extends UnitAttackingWithMagician {
    private boolean using_lightning_storm;
    public final int distanceSkill = 9;

    public Wizard(String name) {
        super(Equipment.mantle_and_wand.getHealth(), Equipment.mantle_and_wand.getAttack(),
                Equipment.mantle_and_wand.getDefend(), UnitsTypes.Wizard, name);
    }

    public boolean lightning_storm(Unit target1, Unit target2, Unit target3) {
        if (getAbilityPoints() == 2) {
            if (using_lightning_storm == false) {
//                System.out.println("Шторм молний");
                super.clearPointAbility();
                target1.decreaseHealth(getHealth() / 2);
                //target2.decreaseHealth(getHealth() / 3);
                //target3.decreaseHealth(getHealth() / 4);
                using_lightning_storm = true;

                return true;
            }
        }

        return false;
    }

    @Override
    public Unit findTarget(Arena arena) {

        // ищем чужого с минимальным здоровьем
        return arena.findAUnitWithMinimumHealth(this, true);
    }

    public boolean applyAbility(Unit targetUnit) {
        boolean res = lightning_storm(targetUnit, targetUnit, targetUnit);

        return res;
    }

    @Override
    public boolean isInDiapason(Unit targetUnit) {
        return this.distanceSkill >= this.getCoordinates().calculateDistance(targetUnit.getCoordinates());
    }

    @Override
    public String getCharacterRepresentation() {
        return "Wzd";
    }
}
