package units;

import arena.Arena;
import units.abstractUnits.Equipment;
import units.abstractUnits.Unit;
import units.abstractUnits.UnitAttackingWithWeapons;
import units.abstractUnits.UnitsTypes;

/**
 * Снайпер
 */
public class Sniper extends UnitAttackingWithWeapons {
    public final int distanceSkill = 9;
    private boolean using_Hit_the_bulls_eye = false;
    public Sniper(String name) {
        super(Equipment.bow_and_arrows.getHealth(), Equipment.bow_and_arrows.getAttack(),
                Equipment.bow_and_arrows.getDefend(), UnitsTypes.Sniper, name);
    }
    public boolean hitTheBullsEye(Unit target){
        if (getAbilityPoints() == 2){
            if (using_Hit_the_bulls_eye == false) {
//                System.out.println("Попал в яблочко.");
                super.clearPointAbility();
                target.decreaseHealth(getHealth()/2);
                using_Hit_the_bulls_eye = true;

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

    @Override
    public boolean applyAbility(Unit targetUnit) {
        boolean res = hitTheBullsEye(targetUnit);

        return res;
    }

    @Override
    public boolean isInDiapason(Unit targetUnit) {
        return this.distanceSkill >= this.getCoordinates().calculateDistance(targetUnit.getCoordinates());
    }

    @Override
    public String getCharacterRepresentation() {
        return "Snr";
    }
}
