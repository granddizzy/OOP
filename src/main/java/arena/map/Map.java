package arena.map;

import units.Coordinates;
import units.abstractUnits.Unit;

import java.util.ArrayList;
import java.util.Random;

public class Map {
    private final Object[][] matrix;

    public int sizeX;

    public int sizeY;

    private ArrayList<Pit> pitsList = new ArrayList<>();

    public Map(int sizeX, int sizeY) {
        this.matrix = new Object[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public Object[][] getMatrix() {
        return matrix;
    }

    public void addPit(Pit pit) {
        pitsList.add(pit);
        matrix[pit.coordinates.x][pit.coordinates.y] = pit;
    }

    public void removePit(Pit pit) {
        pitsList.remove(pit);
        matrix[pit.coordinates.x][pit.coordinates.y] = null;
    }

    public void addUnit(Unit unit, Coordinates coordinates) {
        matrix[coordinates.x][coordinates.y] = unit;
        unit.setCoordinates(coordinates);
    }

    public boolean isTheFieldEmpty(Coordinates coordinates) {
        return matrix[coordinates.x][coordinates.y] == null;
    }

    public void moveUnit(Unit unit, Coordinates coordinates) {
        matrix[unit.getCoordinates().x][unit.getCoordinates().y] = null;
        matrix[coordinates.x][coordinates.y] = unit;
        unit.setCoordinates(coordinates);
    }

    public Coordinates getStartCoordinates(int side) {
        Coordinates coordinates = null;

        boolean flag = false;
        while (!flag) {
            flag = true;

            if (side == 0) {
                coordinates = new Coordinates(0, new Random().nextInt(this.sizeY));
            } else if (side == 1) {
                coordinates = new Coordinates(this.sizeX - 1, new Random().nextInt(this.sizeY));
            } else if (side == 2) {
                coordinates = new Coordinates(new Random().nextInt(this.sizeX), 0);
            } else if (side == 3) {
                coordinates = new Coordinates(new Random().nextInt(this.sizeX), this.sizeY - 1);
            } else {
                return null;
            }

            // проверяем пустое ли это поле
            if (!this.isTheFieldEmpty(coordinates)) {
                flag = false;
            }
        }

        return coordinates;
    }

    public Object getField(int x, int y) {
        return matrix[x][y];
    }

    public void clearField(int x, int y) {
        matrix[x][y] = null;
    }
}
