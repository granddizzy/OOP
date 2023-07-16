package units;

import arena.Arena;
import units.abstractUnits.Equipment;
import units.abstractUnits.Unit;
import units.abstractUnits.UnitAttackingWithWeapons;
import units.abstractUnits.UnitsTypes;

/**
 * Копейщик
 */
public class Spearman extends UnitAttackingWithWeapons {
    public final int distanceSkill = 2;
    public Spearman(String name) {
        super(Equipment.spear_and_cuirass.getHealth(), Equipment.spear_and_cuirass.getAttack(),
                Equipment.spear_and_cuirass.getDefend(), UnitsTypes.Spearman,  name);
    }
    public boolean stun(Unit target){
        if (this.getAbilityPoints() == 2 && this.getPointActivites() == 1) {
//            System.out.println("Оглушаю.");
            super.clearPointAbility();
            super.performAnAttack(target);
            target.clearPointActivites();

            return true;
        }

        return false;
    }

    @Override
    public Unit findTarget(Arena arena) {
        // ищем ближайшего чужого
        return arena.findTheNearestTeamUnit(this, true);
    }

    @Override
    public boolean applyAbility(Unit targetUnit) {
        boolean res = stun(targetUnit);

        return res;
    }

    @Override
    public boolean isInDiapason(Unit targetUnit) {
        return this.distanceSkill >= this.getCoordinates().calculateDistance(targetUnit.getCoordinates());
    }

    @Override
    public String getCharacterRepresentation() {
        return "Spn";
    }
}
