package units;

import org.jetbrains.annotations.NotNull;
import units.abstractUnits.Unit;

import java.util.ArrayList;
import java.util.Iterator;

public class Team implements Iterator<Unit>, Iterable<Unit> {
    public final String name;
    public final String color;
    private ArrayList<Unit> teamList = new ArrayList<>();

    public Team(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public ArrayList<Unit> getUnits() {
        return teamList;
    }

    public void addUnit(Unit unit) {
        teamList.add(unit);
        unit.setTeam(this);
    }

    public int getNumberOfUnits() {
        return teamList.size();
    }

    int index = -1;

    @Override
    public boolean hasNext() {
        index++;
        return index < teamList.size() - 1;
    }

    @Override
    public Unit next() {
        return teamList.get(index);
    }

    @NotNull
    @Override
    public Iterator iterator() {
        Iterator<Unit> it = new Iterator<Unit>() {

            private int index = -1;

            @Override
            public boolean hasNext() {
                index++;
                return index < teamList.size();
            }

            @Override
            public Unit next() {
                return teamList.get(index);
            }
        };

        return it;
    }

    public boolean contains(Unit unit) {
        return teamList.contains(unit);
    }

    public int getSize() {
        return teamList.size();
    }

    public void removeUnit(Unit unit) {
        teamList.remove(unit);
    }

    public Unit getUnit(int index) {
        if (index <= teamList.size() - 1) return teamList.get(index);

        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
