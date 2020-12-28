package scheduler;

import subject.Subject;
import subject.SubjectPlan;
import subject.Period;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public class WeekSchedule {
    private final Map<DayOfWeek, DaySchedule> weekSchedule;
    private final Map<Subject, String> subjectPlans;

    public WeekSchedule() {
        weekSchedule = new HashMap<>();
        for (DayOfWeek day: DayOfWeek.values()) {
            weekSchedule.put(day, new DaySchedule());
        }
        subjectPlans = new HashMap<>();
    }

    public WeekSchedule(Map<DayOfWeek, DaySchedule> weekSchedule, Map<Subject, String> subjectPlans) {
        this.weekSchedule = weekSchedule;
        this.subjectPlans = subjectPlans;
    }

    public boolean addIfCan(Subject subject, SubjectPlan subjectPlan){
        for (Period period: subjectPlan.getPeriods()) {
            if(subject.getName().equals("Física III")){
            }
            if(!weekSchedule.get(period.getDayOfWeek()).canAdd(period.getTimeInterval())){
                return false;
            }
        }
        for (Period period: subjectPlan.getPeriods()) {
            weekSchedule.get(period.getDayOfWeek()).addIfCan(period);
            subjectPlans.put(subject, subjectPlan.getName());
        }
        return true;
    }

    public Map<DayOfWeek, DaySchedule> getWeekSchedule() {
        return weekSchedule;
    }

    public Map<Subject, String> getSubjectPlans() {
        return subjectPlans;
    }
}
