package units;

import arena.Arena;
import units.abstractUnits.Equipment;
import units.abstractUnits.Unit;
import units.abstractUnits.UnitAttackingWithWeapons;
import units.abstractUnits.UnitsTypes;

/**
 * Разбойник
 */
public class Robber extends UnitAttackingWithWeapons {
    public Robber(String name) {
        super(Equipment.knives_and_cloak.getHealth(), Equipment.knives_and_cloak.getAttack(),
                Equipment.knives_and_cloak.getDefend(), UnitsTypes.Robber, name);
    }
    public boolean theft(Unit target){
        if (getAbilityPoints() == 2) {
            System.out.println("Ворую.");
            super.clearPointAbility();
            for (int i = 0; i < 2; i++) {
                target.decreasePointActivities();
            }

            return true;
        }

        return false;
    }
    public boolean jab(Unit target){
        if (getAbilityPoints() == 2){
            System.out.println("jab");
            super.clearPointAbility();
            target.decreasePointActivities();
            target.decreaseHealth(10);

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
        boolean res = theft(targetUnit);

        return res;
    }

    @Override
    public void restoringParameters() {
        super.restoringParameters(Unit.baseAtack + Equipment.knives_and_cloak.getAttack(), Unit.baseDefence + Equipment.knives_and_cloak.getDefend());
    }
}
