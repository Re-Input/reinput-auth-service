package info.reinput.reminder.domain;

import info.reinput.global.util.IntegerListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor
public class ReminderCycle {
    @Enumerated(EnumType.STRING)
    @Column(name = "reminder_type")
    protected ReminderType type;

    @Convert(converter = IntegerListConverter.class)
    @Column(name = "reminder_days")
    protected List<Integer> days;

    private static final List<Integer> DEFAULT_DAYS = List.of(1, 7, 30);

    public ReminderCycle(ReminderType cycleType, List<Integer> days) {
        this.type = cycleType;
        this.days = (cycleType == ReminderType.DEFAULT) ? null : days;
    }

    @PostLoad
    @PrePersist
    private void initDefaultDays() {
        if (type == ReminderType.DEFAULT) {
            days = DEFAULT_DAYS;
        }
    }

    public List<Integer> getDays() {
        return (type == ReminderType.DEFAULT) ? DEFAULT_DAYS : days;
    }

    public void setDays(List<Integer> days) {
        if (this.type == ReminderType.DEFAULT) {
            throw new IllegalStateException("Cannot set days for DEFAULT type");
        }
        this.days = days;
    }

    public static ReminderCycle createDefaultCycle() {
        return new ReminderCycle(ReminderType.DEFAULT, null);
    }

    public static ReminderCycle createWeekCycle(List<Integer> days) {
        return new ReminderCycle(ReminderType.WEEK, days);
    }

    public static ReminderCycle createMonthCycle(List<Integer> days) {
        return new ReminderCycle(ReminderType.MONTH, days);
    }
}