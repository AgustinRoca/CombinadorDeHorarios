package subject;

import org.joda.time.LocalTime;

import java.time.DayOfWeek;

public class Period {
    private final DayOfWeek dayOfWeek;
    private final TimeInterval timeInterval;
    private final String classroom;

    public Period(DayOfWeek dayOfWeek, LocalTime start, LocalTime finish, String classroom) {
        this.dayOfWeek = dayOfWeek;
        this.timeInterval = new TimeInterval(start, finish);
        this.classroom = classroom;
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
