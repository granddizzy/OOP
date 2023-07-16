package arena;

import units.Coordinates;
import units.Team;
import units.abstractUnits.Unit;

public interface ArenaInterface {
    /**
     * Создает команду с заданным именем и размером
     * @param name
     * @param teamSize
     */
    void createTeam(String name, int teamSize, String color);

    /**
     * Запускает бой
     * @throws InterruptedException
     */
    void startTheBattle() throws InterruptedException;

    /**
     * Находит ближайшего персонажа по условию
     * @param unit персонаж который ищет
     * @param alien свой или чужой
     * @return
     */
    Unit findTheNearestTeamUnit(Unit unit, boolean alien);

    /**
     * Находит персонажа с минимальным здоровьем по условиж
     * @param unit персонаж который ищет
     * @param alien свой или чужой
     * @return
     */
    Unit findAUnitWithMinimumHealth(Unit unit, boolean alien);

    /**
     * Удаляет труп
     * @param unit
     */
    void removeTheCorpse(Unit unit);

    /**
     * Выполняет перемещение персонажа
     *
     * @param unit кого перемещаем
     * @param coordinates куда перемещаем
     */
    void doMove(Unit unit, Coordinates coordinates);
}
