package units;

import arena.Arena;
import units.abstractUnits.Equipment;
import units.abstractUnits.Unit;
import units.abstractUnits.UnitSupportiveBasic;
import units.abstractUnits.UnitsTypes;

import java.util.Random;

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
        switch (new Random().nextInt(3)) {
            case 0 -> {
                return pokeWithAPitchfork(targetUnit);
            }
            case 1 -> {
                return digAPit(targetUnit);
            }
            case 2 -> {
                return kickInTheLegs(targetUnit);
            }
        }
        return false;
    }

    public boolean pokeWithAPitchfork(Unit targetUnit) {
        //тычек вилами
        this.performAnAttack(targetUnit);

        return true;
    }

    public boolean digAPit(Unit targetUnit) {
        return false;
    }

    public boolean kickInTheLegs(Unit targetUnit) {
        targetUnit.addSuperimposedAction("Удар по ногам", 3, 0,0,-1);
        return true;
    }

    @Override
    public boolean isInDiapason(Unit targetUnit) {
        return this.distanceSkill >= this.getCoordinates().calculateDistance(targetUnit.getCoordinates());
    }

    @Override
    public String getCharacterRepresentation() {
        return "Pst";
    }
}
