package units.abstractUnits;

import arena.Arena;

public abstract class UnitAttacking extends Unit {
    private float increasingAttack;
    private int abilityPoints;

    public UnitAttacking(int health, int defense, int attack, UnitsTypes type, String name) {
        super(health, defense, attack, type, name);
        this.abilityPoints = 0;
    }

    public UnitAttacking(UnitsTypes type, String name) {
        this(0, 0, 0, type, name);
    }

    public boolean concentration() {
        //super.skipAMove();
        if (abilityPoints < 2) {
            abilityPoints += 1;
        }
        this.addSuperimposedAction("Концентрация", 1, this.getAttack() / 4, 0,0);
        return true;
    }
    public int getAbilityPoints() {
        return abilityPoints;
    }

    public void clearPointAbility() {
        abilityPoints = 0;
    }

    @Override
    public void increaseAttack(int increasingAttack) {
        super.increaseAttack(increasingAttack);
    }

    public float getIncreasingAttack() {
        return increasingAttack;
    }

    @Override
    public void actionInDiapason(Arena arena, Unit targetUnit, boolean moveMade) {

        if (!this.applyAbility(targetUnit)) {
            // если не смогли применить спобосность
            if (this.performAnAttack(targetUnit)) {
                // если смогли атаковать
                arena.addArenaMessage(this, targetUnit, " атака на ");

                //проверяем убили ли
                if (targetUnit.getHealth() == 0) {
                    // выносим труп
                    arena.removeTheCorpse(targetUnit);
                }

                if (!moveMade) {
                    // если шаг НЕ сделан
                    if (this.concentration()) {
                        arena.addArenaMessage(this, null, " сконцентрировался.");
                    }
                }
            } else {
                // если не смогли атаковать
                if (!moveMade) {
                    arena.addArenaMessage(this, null, " пропустил ход");
                    this.skipAMove();
                } else {
                    arena.addArenaMessage(this, targetUnit, " подошел к ");
                }
            }
        } else {
            arena.addArenaMessage(this, targetUnit, " способности на ");
        }
    }

    @Override
    public void actionNotInDiapason(Arena arena, Unit targetUnit, boolean moveMade) {
        if (moveMade) {
            // если шаг сделан
            arena.addArenaMessage(this, targetUnit, " подошел к ");

            if (this.concentration()) {
                arena.addArenaMessage(this, null, " сконцентрировался ");
            }
        } else {
            arena.addArenaMessage(this, null,  " пропустил ход");
            this.skipAMove();
        }
    }


}
