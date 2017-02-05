package amu.electrical.deptt.model;

public class FacultyMember {
    public String name, responsibility, designation, mobile, intext;

    public FacultyMember() {
    }

    public FacultyMember(String name, String designation, String responsibility, String mobile, String intext) {
        this.name = name;
        this.designation = designation;
        this.responsibility = responsibility;
        this.mobile = mobile;
        this.intext = intext;
    }

    @Override
    public String toString() {
        return "\nName -> " + name;
    }
}
