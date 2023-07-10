package units.abstractUnits;

public abstract class UnitAttackingWithWeapons extends UnitAttacking {

    private int stamina;
    private static final int STAMINACONC = 20;
    private int baseWeapon;

    public UnitAttackingWithWeapons(int health, int attack, int defence, UnitsTypes type, String name) {
        super(health, attack, defence, type, name);
        this.stamina = 100;
        this.baseWeapon = 15;
    }

    public UnitAttackingWithWeapons(UnitsTypes type, String name) {
        this(0, 0, 0, type, name);
    }

    @Override
    public boolean performAnAttack(Unit unit) {
        if (stamina - baseWeapon > 0) {
            if (super.performAnAttack(unit)) {
                stamina -= baseWeapon;
                return true;
            }
//        } else {
//            System.out.println("stamina - baseWeapon <= 0");
        }

        return false;
    }

    @Override
    public boolean concentration() {
        if (super.concentration()) {
            if (stamina + STAMINACONC < 100) {
                stamina += STAMINACONC;
            } else {
                stamina = 100;
            }
        }

        return false;
    }

    public int getStamina() {
        return stamina;
    }

    public void addStamina(int value) {
        stamina += value;
    }

    public void decreaseStamina(int value) {
        stamina -= value;
    }
}
