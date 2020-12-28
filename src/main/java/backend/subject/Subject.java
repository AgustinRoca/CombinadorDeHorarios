package backend.subject;

import java.util.List;

public class Subject {
    private final String code;
    private final String name;
    private final List<SubjectPlan> plans;

    public Subject(String code, String name, List<SubjectPlan> plans) {
        this.code = code;
        this.name = name;
        this.plans = plans;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<SubjectPlan> getPlans() {
        return plans;
    }
}
