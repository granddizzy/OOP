import View.View;
import arena.Arena;
import arena.map.Map;
import Log.Log;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Log log = new Log();
        Map map = new Map(15, 15);
        View view = new View(map);

        //контроллер
        Arena arena = new Arena(map, view, log);

        arena.createTeam("Команда Добра", 10, "GREEN");
        arena.createTeam("Команда Зла", 10 , "RED");

        arena.startTheBattle();
    }
}
