package backend.subject;

import org.joda.time.LocalTime;

import java.time.DayOfWeek;

public class Period {
    private final String subjectName;
    private final DayOfWeek dayOfWeek;
    private final TimeInterval timeInterval;
    private final String classroom;

    public Period(String subjectName, DayOfWeek dayOfWeek, LocalTime start, LocalTime finish, String classroom) {
        this.subjectName = subjectName;
        this.dayOfWeek = dayOfWeek;
        this.timeInterval = new TimeInterval(start, finish);
        this.classroom = classroom;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStart() {
        return timeInterval.getFrom();
    }

    public LocalTime getFinish() {
        return timeInterval.getTo();
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public String getClassroom() {
        return classroom;
    }
}
