package org.tutos.backend.calendar;

import org.tutos.backend.calendar.entity.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClassCalendarRepository extends JpaRepository<ClassSchedule, UUID> {
}
