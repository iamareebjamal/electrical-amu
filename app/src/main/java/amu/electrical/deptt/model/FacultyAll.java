package amu.electrical.deptt.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

public class FacultyAll {
    public HashMap<String, FacultyMember> works, supply;

    public FacultyAll() {
    }

    @Override
    public String toString() {
        return  "Works : \n" + works.values() + "\n" +
                "Supply : \n" + supply.values();
    }

    private Collection<FacultyMember> getFacultyMembers(HashMap<String, FacultyMember> map) {
        return new TreeMap<>(map).values();
    }

    public Collection<FacultyMember> getWorks() {
        return getFacultyMembers(works);
    }

    public Collection<FacultyMember> getSupply() {
        return getFacultyMembers(supply);
    }
}
