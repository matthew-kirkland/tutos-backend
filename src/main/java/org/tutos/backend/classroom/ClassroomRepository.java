package org.tutos.backend.classroom;

import org.tutos.backend.classroom.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Class, Long> {
}
