package units;

import arena.Arena;
import units.abstractUnits.Equipment;
import units.abstractUnits.Unit;
import units.abstractUnits.UnitSupportiveBasic;
import units.abstractUnits.UnitsTypes;

/**
 * Крестьянин
 */
public class Peasant extends UnitSupportiveBasic {
    public Peasant(String name) {
        super(Equipment.pitchfork_and_robe.getHealth(), Equipment.pitchfork_and_robe.getAttack(),
                Equipment.pitchfork_and_robe.getDefend(), UnitsTypes.Peasant, name);
    }

    @Override
    public Unit findTarget(Arena arena) {
        // ищем ближайшего чужого
        return arena.findTheNearestTeamUnit(this, true);
    }

    @Override
    public boolean applyAbility(Unit targetUnit) {
        return pokeWithAPitchfork(targetUnit);
    }

    public boolean pokeWithAPitchfork(Unit targetUnit) {
//        if (getA) {
//
//        }
        return false;
    }

    @Override
    public void restoringParameters() {
        super.restoringParameters(Unit.baseAtack + Equipment.pitchfork_and_robe.getAttack(), Unit.baseDefence + Equipment.pitchfork_and_robe.getDefend());
    }
}
