package units.abstractUnits;

public abstract class UnitAttackingWithMagician extends UnitAttacking {

    private int mana;
    public int baseSpell;

    private static final int MANACONC = 20;

    public UnitAttackingWithMagician(int health, int defense, int attack, UnitsTypes type, String name) {
        super(health, defense, attack, type, name);
        this.mana = 100;
        this.baseSpell = 15;
    }

    public UnitAttackingWithMagician(UnitsTypes type, String name, int mana, int baseSpell) {
        this(0, 0, 0, type, name);
    }

    @Override
    public boolean concentration() {
        if (super.concentration()) {
            if (mana + MANACONC < 100) {
                mana += MANACONC;
            } else {
                mana = 100;
            }
        }

        return false;
    }

    @Override
    public boolean performAnAttack(Unit unit) {
        if (mana - baseSpell > 0) {
            if (super.performAnAttack(unit)) {
                mana -= baseSpell;

                return true;
            }
//        } else {
//            System.out.println("mana - baseSpell <= 0");
//            this.skipAMove();
        }
        return false;
    }

    public abstract void restoringParameters();
}

