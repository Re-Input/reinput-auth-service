package info.reinput.reminder.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReminderInfo {

    @Column(name = "reminded_at")
    private LocalDateTime remindedAt;
    @Column
    private LocalDate reminderDate;
}
