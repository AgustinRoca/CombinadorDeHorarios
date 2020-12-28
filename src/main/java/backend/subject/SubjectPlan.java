package backend.subject;

import java.util.List;

public class SubjectPlan {
    private final String name;
    private final List<Period> periods;
    private final List<Teacher> teachers;
    private final int currentStudents;
    private final Integer maxStudents; // null if infinite

    public SubjectPlan(String name, List<Period> periods, List<Teacher> teachers, int currentStudents, Integer maxStudents) {
        this.name = name;
        this.periods = periods;
        this.teachers = teachers;
        this.currentStudents = currentStudents;
        this.maxStudents = maxStudents;
    }

    public String getName() {
        return name;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public int getCurrentStudents() {
        return currentStudents;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public boolean hasSpace(){
        return maxStudents != null && currentStudents < maxStudents;
    }
}
