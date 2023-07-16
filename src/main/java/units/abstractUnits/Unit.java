package units.abstractUnits;

import arena.Arena;
import units.*;

import java.util.ArrayList;
import java.util.Random;

public abstract class Unit implements UnitInterface {   //implements AutoCloseable  попробовать?
    private final String name;
    private final UnitsTypes type;
    private int health;
    private int pointActivites;
    private int defense;
    private int speed;
    private int attack;
    private boolean defended;
    private boolean attacked;
    private boolean done_move;
    private boolean done_attack;
    private int initiative;
    private Coordinates coordinates;

    public int distanceSkill = 1;

    protected KindOfBattle kindOfBattle;

    public final static int baseAtack = 10;
    public final static int baseDefence = 2;

    private Team team;

    private ArrayList<SuperimposedAction> superimposedActions = new ArrayList<>();

    public Unit(int health, int defense, int attack, UnitsTypes type, String name) {
        this.health = 50 + health;
        this.defense = baseDefence + defense;
        this.attack = baseAtack + attack;
        speed = 3;
        pointActivites = 1;
        defended = false;
        attacked = false;
        done_move = false;
        done_attack = false;
        this.name = name;
        this.type = type;

        if (this instanceof Crossbowman || this instanceof Sorcerer || this instanceof Sniper || this instanceof Wizard || this instanceof Monk) {
            this.kindOfBattle = KindOfBattle.distant;
        } else if (this instanceof Robber || this instanceof Spearman || this instanceof Druid || this instanceof Palladine || this instanceof Peasant) {
            this.kindOfBattle = KindOfBattle.near;
        }
    }

    public Unit(UnitsTypes type, String name) {
        this(0, 0, 0, type, name);
    }

    /**
     * Атака
     *
     * @param target
     */
    public boolean performAnAttack(Unit target) {
        //атакуем
        //* Ближники (не дальше 1 клетки)
        //* Дальники (5 клеток - 100% урон, до 7 клеток - 75% урона, до 9 клеток - 50%, 10 и более - не может атаковать)

        // нельзя атаковать своего
        if (target.team.equals(this.team)) return false;

        if (this.getPointActivites() > 0) {
            if (this.getAttack() - target.getDefense() > 0) {
                target.decreaseHealth(this.getAttack() - target.getDefense());
                this.decreasePointActivities();
            }
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type.toString();
    }

    public void clearPointActivites() {
//        System.out.println("Сброс очков активности");
        pointActivites = 0;
    }

    public void skipActivity() {
        pointActivites -= 1;
    }

    public int getAttack() {
        int calculatedAttack = attack;
        for (SuperimposedAction act : superimposedActions) {
            calculatedAttack += act.attackChangeValue;
        }

        if (calculatedAttack < 0) calculatedAttack = 0;
        else if (calculatedAttack > 100) calculatedAttack = 100;

        return calculatedAttack;
    }

    public void increaseAttack(int value) {
        attack += value;
    }

    public void decreaseAttack(int value) {
        attack -= value;

        if (attack < 0) {
            attack = 0;
        }
    }

    public void setPointActivites(int value) {
        pointActivites = value;
    }

    public void addPointActivites(int value) {
        pointActivites += value;
    }

    public void decreasePointActivities() {
        pointActivites -= 1;

        if (pointActivites < 0) {
            pointActivites = 0;
        }
    }

    public void addHealth(int value) {
        health += value;

        if (health > 100) {
            health = 100;
        }
    }

    public void decreaseHealth(int value) {
        if (health - value > 0) {
            health -= value;
        } else {
            die(); // умер
        }
    }

    public void die() {
        health = 0;
    }

    public void increaseDefence(int value) {
        defense += value;
    }

    public void decreaseDefence(int value) {
        defense -= value;

        if (defense < 0) {
            defense = 0;
        }
    }

    public void addSpeed(int value) {
        defense += value;
    }

    public void decreaseSpeed(int value) {
        speed -= value;

        if (speed < 0) {
            speed = 0;
        }
    }

    public String getUnitBaseInfo() {
        return "" + type.toString() + " " + name + " Ж:" + this.getHealth() + " А:" + this.getAttack() + " З:" + this.getDefense() + " С:" + this.getSpeed();
    }

    @Override
    public String toString() {
        return getUnitBaseInfo();
    }

    @Override
    public String getInfo() {
        return this.toString();
    }


    public String getShortInfo() {
        return this.type.toString() + " " + this.name;
    }

    @Override
    public void step(Arena arena) {
        Unit targetUnit = this.findTarget(arena);

        //arena.addArenaMessage(null, this,  "ход ");

        if (targetUnit == null) {
            arena.addArenaMessage(this, null, " Цель не найдена ");
        } else {
            if (this.isInDiapason(targetUnit)) {
                this.actionInDiapason(arena, targetUnit, false);
            } else {
                this.decreasePointActivities();
                arena.doMove(this, targetUnit.getCoordinates());
                if (this.isInDiapason(targetUnit)) {
                    this.actionInDiapason(arena, targetUnit, true);
                } else {
                    this.actionNotInDiapason(arena, targetUnit, true);
                }
            }
        }
    }

    // Все геттеры и сеттеры:
    public int getDefense() {
        int calculatedDefense = defense;
        for (SuperimposedAction act : superimposedActions) {
            calculatedDefense += act.defendChangeValue;
        }

        if (calculatedDefense < 0) calculatedDefense = 0;
        else if (calculatedDefense > 100) calculatedDefense = 100;

        return calculatedDefense;
    }

    public void setAttack(int value) {
        attack = value;
    }

    public int getPointActivites() {
        return pointActivites;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getSpeed() {
        int calculatedSpeed = speed;
        for (SuperimposedAction act : superimposedActions) {
            calculatedSpeed += act.speedChangeValue;
        }

        if (calculatedSpeed < 0) calculatedSpeed = 0;
        else if (calculatedSpeed > 100) calculatedSpeed = 100;

        return calculatedSpeed;
    }

    public int getHealth() {
        return health;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public static String getRandomUnitName() {
        return Names.values()[new Random().nextInt(Names.values().length)].toString();
    }

    /**
     * Возвращает команду персонажа
     *
     * @return
     */
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Добавляет наложенное временное воздействие
     *
     * @param name
     * @param period
     * @param attackChangeValue
     * @param defendChangeValue
     * @param speedChangeValue
     */
    public void addSuperimposedAction(String name, int period, int attackChangeValue, int defendChangeValue, int speedChangeValue) {
        this.superimposedActions.add(new SuperimposedAction(name, period, attackChangeValue, defendChangeValue, speedChangeValue));
    }

    /**
     * Получает список временных воздействий
     *
     * @return
     */
    public ArrayList<SuperimposedAction> getSuperimposedActions() {
        return superimposedActions;
    }

    /**
     * Удаляет наложенное временное воздействие
     *
     * @param act
     */
    public void removeSuperimposedAction(SuperimposedAction act) {
        superimposedActions.remove(act);
    }

    public void skipAMove() {
        this.clearPointActivites();
    }
}
