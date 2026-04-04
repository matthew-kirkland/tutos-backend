package org.lms.backend.classroom;

import org.lms.backend.classroom.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Class, Long> {
}
