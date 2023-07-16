package arena;

import View.View;
import arena.map.Map;
import units.*;
import units.abstractUnits.*;
import Log.Log;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Arena implements ArenaInterface {
    int round;
    private final ArrayList<Team> teams = new ArrayList<>();
    private arena.map.Map map;

    public ArrayList<Team> getTeams() {
        return teams;
    }

    private ArrayList<Unit> initiative = new ArrayList<>();

    private Log log;

    private View view;

    private ArrayList<ArenaMessage> arenaMesagges = new ArrayList<>();

    public Arena(Map map, View view, Log log) {
        this.log = log;
        this.view = view;
        this.map = map;
    }

    @Override
    public void createTeam(String name, int teamSize, String color) {
        teams.add(new Team(name, color));
        Team team = teams.get(teams.size() - 1);
        generateTeam(team, teamSize);
        this.placeUnits(team);
    }

    /**
     * генерирует команду заданного размера из случайных юнитов
     *
     * @param team     команда
     * @param teamSize размер
     */
    private static void generateTeam(Team team, int teamSize) {
        for (int i = 0; i < teamSize; i++) {
            switch (new Random().nextInt(10)) {
                case 0 -> team.addUnit(new Crossbowman(Unit.getRandomUnitName()));
                case 1 -> team.addUnit(new Druid(Unit.getRandomUnitName()));
                case 2 -> team.addUnit(new Monk(Unit.getRandomUnitName()));
                case 3 -> team.addUnit(new Palladine(Unit.getRandomUnitName()));
                case 4 -> team.addUnit(new Peasant(Unit.getRandomUnitName()));
                case 5 -> team.addUnit(new Robber(Unit.getRandomUnitName()));
                case 6 -> team.addUnit(new Sniper(Unit.getRandomUnitName()));
                case 7 -> team.addUnit(new Spearman(Unit.getRandomUnitName()));
                case 8 -> team.addUnit(new Wizard(Unit.getRandomUnitName()));
                case 9 -> team.addUnit(new Sorcerer(Unit.getRandomUnitName()));
            }
        }
    }

    /**
     * расставляет команду на карте
     * задает координаты юнитам
     *
     * @param team
     */
    private void placeUnits(Team team) {
        for (Unit unit : team) {
            map.addUnit(unit, map.getStartCoordinates(teams.indexOf(team)));
        }
    }

    @Override
    public void startTheBattle() throws InterruptedException {
        if (teams.size() < 2 || teams.size() > 4) {
            log.addLogMessage("Не верное количество команд.");
            return;
        }

        view.view(round, teams, arenaMesagges);

        // пауза для наглядности
        TimeUnit.SECONDS.sleep(1);

        //если нужен следующий раунд то запускаем
        while (checkTheNeedForTheNextRound()) {
            round += 1;

            this.setInitiative();
            //каждый юнит делает ход в порядке уменьшения инициативы
            for (Unit unit : initiative) {
                if (unit.getHealth() == 0) continue;

                //восстанавливаем очки активности
                //одно очко тратит на ходьбу
                //второе очко тратит на действие
                unit.setPointActivites(2);

                // персонаж делает ход в игре
                unit.step(this);
            }

            this.analyzeSuperimposedActions();

            view.view(round, teams, arenaMesagges);

            // пауза для наглядности
            TimeUnit.SECONDS.sleep(3);
        }

        // определяем победителя
        Team winner = getWinner();
        if (winner != null) {
            this.addArenaMessage(null, null, "Победила команда: " + winner);
            view.view(round, teams, arenaMesagges);
        }
    }

    /**
     * проверяет количество активных команд
     *
     * @return
     */
    private boolean checkTheNeedForTheNextRound() {
        int numberOfActiveTeams = 0;
        for (Team team : teams) {
            if (team.getNumberOfUnits() > 0) {
                numberOfActiveTeams += 1;
            }
        }

        return numberOfActiveTeams > 1;
    }

    /**
     * определяет победителя
     *
     * @return
     */
    private Team getWinner() {
        for (Team team : teams) {
            if (team.getNumberOfUnits() > 0) {
                return team;
            }
        }
        return null;
    }

    public Unit findTheNearestTeamUnit(Unit unit, boolean alien) {
        Unit nearestUnit = null;
        double minDistance = Math.sqrt(this.map.sizeX*this.map.sizeX + this.map.sizeY*this.map.sizeY) + 1;

        for (Team targetTeam : teams) {
            if ((alien && targetTeam.equals(unit.getTeam())) || (!alien && !targetTeam.equals(unit.getTeam()))) {
                continue;
            }

            for (Unit targetUnit : targetTeam) {
                //главное в любом расследовании не выйти на самого себя
                if (targetUnit.equals(unit)) continue;

                double distance = unit.getCoordinates().calculateDistance(targetUnit.getCoordinates());
                if (distance < minDistance) {
                    // если ищем свого или проверяем сможем ли атаковать
                    //if (!alien || unit.getAttack() - targetUnit.getDefense() > 0) {
                    //if (!alien) {
                        minDistance = distance;
                        nearestUnit = targetUnit;
                   //}
                }
            }
        }
        return nearestUnit;
    }

    /**
     * Находит ближайшего персонажа с минимальным здоровьем
     *
     * @return
     */
    public Unit findAUnitWithMinimumHealth(Unit unit, boolean alien) {
        Unit minHealthUnit = null;
        int minHealth = 101;

        for (Team targetTeam : teams) {
            if ((alien && targetTeam.equals(unit.getTeam())) || (!alien && !targetTeam.equals(unit.getTeam()))) {
                continue;
            }

            for (Unit target : targetTeam) {
                //главное в любом расследовании не выйти на самого себя
                if (target.equals(unit)) continue;

                if (target.getHealth() < minHealth) {
                    // если ищем свого или проверяем сможем ли атаковать
                    //if (!alien || unit.getAttack() - target.getDefense() > 0) {
                        minHealth = target.getHealth();
                        minHealthUnit = target;
                }
            }
        }

        return minHealthUnit;
    }

    public void removeTheCorpse(Unit unit) {
        for (Team team : teams) {
            if (team.contains(unit)) {
                map.clearField(unit.getCoordinates().x, unit.getCoordinates().y);
                team.removeUnit(unit);
                this.addArenaMessage(unit, null, " убит");
                break;
            }
        }
    }

    public Coordinates getNextStepPosition(Coordinates beginCoordinates, Coordinates endCoordinates) {
        Coordinates stepCoordinates = null;

        //если цель перед тобой
        if (beginCoordinates.calculateDistance(endCoordinates) < 1.5) {
            return beginCoordinates;
        }

        // определяем направление движения
        int direction = -1;

        //если по прямой
        if (beginCoordinates.x == endCoordinates.x) {
            if (endCoordinates.y > beginCoordinates.y) {
                direction = 0;
            } else {
                direction = 4;
            }
        } else if (beginCoordinates.y == endCoordinates.y) {
            if (endCoordinates.x > beginCoordinates.x) {
                direction = 2;
            } else {
                direction = 6;
            }
        } else {
            //вычисляем угол
            double angle = Math.asin(Math.abs(endCoordinates.y - beginCoordinates.y) / beginCoordinates.calculateDistance(endCoordinates)) * 180 / Math.PI;

            if (endCoordinates.y > beginCoordinates.y && endCoordinates.x > beginCoordinates.x) {
                // 1 четверть

                if (angle <= 33) {
                    direction = 0;
                } else if (angle >= 66) {
                    direction = 2;
                } else {
                    direction = 1;
                }
            } else if (endCoordinates.y > beginCoordinates.y && endCoordinates.x < beginCoordinates.x) {
                // 2 четверть

                if (angle <= 33) {
                    direction = 0;
                } else if (angle >= 66) {
                    direction = 6;
                } else {
                    direction = 7;
                }
            } else if (endCoordinates.y < beginCoordinates.y && endCoordinates.x < beginCoordinates.x) {
                // 3 четверть

                if (angle <= 33) {
                    direction = 6;
                } else if (angle >= 66) {
                    direction = 4;
                } else {
                    direction = 5;
                }
            } else if (endCoordinates.y < beginCoordinates.y && endCoordinates.x > beginCoordinates.x) {
                // 4 четверть

                if (angle <= 33) {
                    direction = 2;
                } else if (angle >= 66) {
                    direction = 4;
                } else {
                    direction = 3;
                }
            }
        }

        boolean checkCoordinates = false;
        int countTurn = 0;

        while (!checkCoordinates) {
            stepCoordinates = new Coordinates(beginCoordinates.x, beginCoordinates.y);

            // определяем координаты по напрвлению
            switch (direction) {
                case 0 -> stepCoordinates.y += 1;
                case 1 -> {
                    stepCoordinates.x += 1;
                    stepCoordinates.y += 1;
                }
                case 2 -> stepCoordinates.x += 1;
                case 3 -> {
                    stepCoordinates.x += 1;
                    stepCoordinates.y -= 1;
                }
                case 4 -> stepCoordinates.y -= 1;
                case 5 -> {
                    stepCoordinates.x -= 1;
                    stepCoordinates.y -= 1;
                }
                case 6 -> stepCoordinates.x -= 1;
                case 7 -> {
                    stepCoordinates.x -= 1;
                    stepCoordinates.y += 1;
                }
            }

            if (stepCoordinates.x >= 0 && stepCoordinates.y >= 0 && stepCoordinates.y < map.sizeY && stepCoordinates.x < map.sizeX) {
                //проверить препятствин
                checkCoordinates = map.isTheFieldEmpty(stepCoordinates);

                if (!checkCoordinates) {
//                    System.out.print(" -> " + stepCoordinates + " Занято. Иду в обход.");
                }
            }

            // меняем направление
            if (!checkCoordinates) {
                direction += 1;

                if (direction == 8) {
                    direction = 0;
                }
                countTurn += 1;
            }

            // проверяем если ходить некуда
            if (countTurn > 7) {
//                System.out.print(" Некуда ходить. ");
                stepCoordinates = beginCoordinates;
                break;
            }
        }

        return stepCoordinates;
    }

    /**
     * Возвращает команду заданного юнита
     *
     * @param unit
     * @return
     */
    public Team getUnitTeam(Unit unit) {
        for (Team team : teams) {
            if (team.contains(unit)) return team;
        }
        return null;
    }

    @Override
    public void doMove(Unit unit, Coordinates coordinates) {
        for (int i = 1; i <= unit.getSpeed(); i++) {
            Coordinates stepCoordinates = this.getNextStepPosition(unit.getCoordinates(), coordinates);
            map.moveUnit(unit, stepCoordinates);
        }
    }

    public void setInitiative() {
        this.initiative.clear();

        for (Team team : teams) {
            for (Unit unit : team) {
                this.initiative.add(unit);
            }
        }

        Collections.shuffle(initiative);
    }

    public void addArenaMessage(Unit unit, Unit target, String message) {
        arenaMesagges.add(new ArenaMessage(unit, target, message));
    }

    private void analyzeSuperimposedActions() {
        ArrayList<SuperimposedAction> tmp = new ArrayList<>();
        for (Unit unit : initiative) {
            for (SuperimposedAction act : unit.getSuperimposedActions()) {
                if (act.getStartRaund() == 0) act.setStartRaund(round);
                else if (round - act.getStartRaund() > act.period) {
                    tmp.add(act);
                }
            }
            for (SuperimposedAction act: tmp) {
                unit.removeSuperimposedAction(act);
            }
            tmp.clear();
        }
    }
}