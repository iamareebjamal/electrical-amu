package amu.electrical.deptt.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Faculty {
    public HashMap<String, FacultyMember> works, office, technical, supply, daily;

    public Faculty() {
    }

    @Override
    public String toString() {
        return "Works : \n" + works.values() + "\n" +
                "Office : \n" + office.values() + "\n" +
                "Technical \n: " + technical.values() + "\n" +
                "Supply : \n" + supply.values() + "\n" +
                "Daily : \n" + daily.values();
    }

    private Collection<FacultyMember> getFacultyMembers(HashMap<String, FacultyMember> map) {
        return new TreeMap<>(map).values();
    }

    public Collection<FacultyMember> getWorks() {
        return getFacultyMembers(works);
    }

    public Collection<FacultyMember> getOffice() {
        return getFacultyMembers(office);
    }

    public Collection<FacultyMember> getTechnical() {
        return getFacultyMembers(technical);
    }

    public Collection<FacultyMember> getSupply() {
        return getFacultyMembers(supply);
    }

    public Collection<FacultyMember> getDaily() {
        return getFacultyMembers(daily);
    }
}
