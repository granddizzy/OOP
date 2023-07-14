package units;

import arena.Arena;
import units.abstractUnits.Equipment;
import units.abstractUnits.Unit;
import units.abstractUnits.UnitSupportiveHealer;
import units.abstractUnits.UnitsTypes;

import java.util.ArrayList;
import java.util.Random;

/**
 * Друид
 */
public class Druid extends UnitSupportiveHealer {
    private int bigSpellHeal;
    private  boolean usingBigHeal = false;
    private int middleSpellHeal;
    private int usingMiddleHeal = 0;
    public Druid(String name) {
        super(Equipment.frogfoot_and_bearskin.getHealth(), Equipment.frogfoot_and_bearskin.getAttack(),
                Equipment.frogfoot_and_bearskin.getDefend(), UnitsTypes.Druid, name);
    }

    @Override
    public Unit findTarget(Arena arena) {
        // ищем ближайшего своего
        return arena.findTheNearestTeamUnit(this, false);
    }

    @Override
    public boolean applyAbility(Unit targetUnit) {
        return ability();
    }

    public boolean ability() {

        return false;
    }
    public void middleHeal(Unit targetUnit){
        if (usingMiddleHeal < 2) {
            targetUnit.addHealth(middleSpellHeal = new Random().nextInt(15, 30));
            usingMiddleHeal +=1;
        }
    }
    public void bigHeal(Unit targetUnit){
        if (usingBigHeal == false){
            targetUnit.addHealth(bigSpellHeal = new Random().nextInt(15, 30)*2);
        }
    }
}
