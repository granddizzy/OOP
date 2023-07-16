package units.abstractUnits;

import arena.Arena;

public abstract class UnitSupportive extends Unit {

    private int speed;
    //private int pointActivites;

    public UnitSupportive(int health, int defense, int attack, UnitsTypes type, String name) {
        super(health, defense, attack, type, name);
        speed = super.getSpeed() * 2;
    }

    public UnitSupportive(UnitsTypes type, String name) {
        this(0, 0, 0, type, name);
    }

    public void actionInDiapason(Arena arena, Unit targetUnit, boolean moveMade) {
        if (!this.applyAbility(targetUnit)) {
            // всегда применяет

        } else {
            arena.addArenaMessage(this, targetUnit, " способности на ");

            // убежать
            //arena.doMove();
        }
    }

    @Override
    public void actionNotInDiapason(Arena arena, Unit targetUnit, boolean moveMade) {
        if (moveMade) {
            // если шаг сделаня
            arena.addArenaMessage(this, targetUnit, " подошел к ");
        } else {
            arena.addArenaMessage(this, null, " пропустил ход");
            this.skipAMove();
        }
    }
}
