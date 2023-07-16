package units;

import arena.Arena;
import units.abstractUnits.Unit;

import java.util.ArrayList;

public interface UnitInterface {

    /**
     * Получает информацию о персонаже
     * @return
     */
    String getInfo();

    /**
     * Устанавливает здоровеье персонажа в 0
     */
    void die();

    /**
     * Выполняет ход песонажа в игре
     * @param arena
     */
    void step(Arena arena);

    /**
     * Находит цель
     * переопределяется каждому персонажу отдельно
     *
     * @param arena
     * @return
     */
    Unit findTarget(Arena arena);

    /**
     * Выполняет действия песонажа если цель в диапазоне
     * может выполнятся До перемещения и После
     *
     * @param arena
     * @param targetUnit
     * @param moveMade   сделал ли персонаж перемещение
     */
    void actionInDiapason(Arena arena, Unit targetUnit, boolean moveMade);

    /**
     * Выполняет действия песонажа если цель в не в диапазоне
     * может выполнятся До перемещения и После
     *
     * @param arena
     * @param targetUnit
     * @param moveMade   сделал ли персонаж перемещение
     */
    void actionNotInDiapason(Arena arena, Unit targetUnit, boolean moveMade);

    /**
     * Выполняет применение способностей к цели
     * @param targetUnit
     * @return
     */
    boolean applyAbility(Unit targetUnit);

    boolean isInDiapason(Unit targetUnit);

    String getCharacterRepresentation();
}
