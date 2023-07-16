package View;

import arena.ArenaMessage;
import units.*;
import units.abstractUnits.Unit;

import java.util.ArrayList;
import java.util.Collections;

import arena.map.Map;

public class View {
    private Map map;

    public View(Map map) {
        this.map = map;
    }

    private static void tabSetter(int cnt, int max) {
        int dif = max - cnt + 2;
        if (dif > 0) System.out.printf("%" + dif + "s", ":\t");
        else System.out.print(":\t");
    }

    private static String formatDiv(String str) {
        return str.replace('a', '┌')
                .replace('b', '┬')
                .replace('c', '┐')
                .replace('d', '├')
                .replace('e', '┼')
                .replace('f', '┤')
                .replace('g', '└')
                .replace('h', '┴')
                .replace('i', '┘')
                .replace('-', '─');
    }

    private String getChar(int x, int y) {
        String out = "|   ";

        Object field = this.map.getField(x, y);

        String sym = "   ";

        if (field instanceof Unit) {
            sym = ((Unit) field).getCharacterRepresentation();
        }

        if (field instanceof Unit) {
            out = "|" + (getAnsiColor(((Unit) field).getTeam().color) + sym + AnsiColors.ANSI_RESET);
        } else {
            out = "|" + sym;
        }

        return out;
    }

    public void view(Integer step, ArrayList<Team> teams, ArrayList<ArenaMessage> arenaMessages) {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }

        int maxLengthInfo = 55;

        System.out.print(AnsiColors.ANSI_YELLOW + "Step " + step + AnsiColors.ANSI_RESET);
        System.out.println();

        //вывод верха таблицы
        System.out.print(formatDiv("a") + formatDiv("-") + String.join("", Collections.nCopies(map.sizeX - 1, formatDiv("-") + formatDiv("-b") + formatDiv("-"))) + formatDiv("-") + formatDiv("-c") + "    ");

        for (Team team : teams) {
            System.out.print(getAnsiColor(team.color) + team.name + AnsiColors.ANSI_RESET);
            tabSetter(team.name.length(), maxLengthInfo);
        }
        System.out.println();

        for (int i = 0; i < map.sizeX; i++) {
            System.out.print(getChar(i, 0));
        }
        System.out.print("|    ");
        System.out.println();

        // вывод середины таблицы
        System.out.println(formatDiv("d") + formatDiv("-") + String.join("", Collections.nCopies(map.sizeX - 1, formatDiv("-") + formatDiv("-e") + formatDiv("-"))) + formatDiv("-") + formatDiv("-f"));

        for (int i = 1; i < map.sizeY - 1; i++) {
            for (int j = 0; j < map.sizeX; j++) {
                System.out.print(getChar(j, i));
            }
            System.out.print("|    ");

            for (Team team : teams) {
                Unit u = team.getUnit(i - 1);
                if (u != null) {
                    System.out.print(getAnsiColor(team.color) + u.getInfo() + AnsiColors.ANSI_RESET);
                    tabSetter(u.getInfo().length(), maxLengthInfo);
                } else {
                    tabSetter(0, maxLengthInfo);
                }
            }
            System.out.println();

            //вывод серидины таблицы
            System.out.println(formatDiv("d") + formatDiv("-") + String.join("", Collections.nCopies(map.sizeX - 1, formatDiv("-") + formatDiv("-e") + formatDiv("-"))) + formatDiv("-") + formatDiv("-f"));
        }

        for (int j = 0; j < map.sizeX; j++) {
            System.out.print(getChar(j, map.sizeY - 1));
        }
        System.out.println("|    ");
        //вывод низа таблицы
        System.out.println(formatDiv("g") + formatDiv("-") + String.join("", Collections.nCopies(map.sizeX - 1, formatDiv("-") + formatDiv("-h") + formatDiv("-"))) + formatDiv("-") + formatDiv("-i"));

        for (ArenaMessage msg : arenaMessages) {
            if (msg.unit != null)
                System.out.print(getAnsiColor(msg.unit.getTeam().color) + msg.unit.getType() + " " + msg.unit.getName() + AnsiColors.ANSI_RESET);
            if (msg.message != null) System.out.print(msg.message);
            if (msg.target != null)
                System.out.print(getAnsiColor(msg.target.getTeam().color) + msg.target.getType() + " " + msg.target.getName() + AnsiColors.ANSI_RESET);
            System.out.println();
        }
        arenaMessages.clear();
    }

    private String getAnsiColor(String color) {
        return switch (color) {
            case "RED" -> AnsiColors.ANSI_RED;
            case "BLUE" -> AnsiColors.ANSI_BLUE;
            case "GREEN" -> AnsiColors.ANSI_GREEN;
            case "CYAN" -> AnsiColors.ANSI_CYAN;
            case "PURPLE" -> AnsiColors.ANSI_PURPLE;
            case "YELLOW" -> AnsiColors.ANSI_YELLOW;
            default -> null;
        };
    }
}
