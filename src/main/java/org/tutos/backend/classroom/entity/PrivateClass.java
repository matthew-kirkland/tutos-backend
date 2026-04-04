package org.tutos.backend.classroom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.tutos.backend.classroom.exception.ClassFullException;
import org.tutos.backend.user.entity.Student;
import org.tutos.backend.user.entity.Tutor;

import java.util.Set;

@Entity
public class PrivateClass extends Class {
    @ManyToOne(optional = true)
    private Student student;

    public PrivateClass(
            String title,
            String description,
            Set<Tutor> tutors,
            Student student
    ) {
        super(title, description, tutors);
        this.student = student;
    }

    public PrivateClass() {
    }

    @Override
    public void addStudent(Student student) {
        if (this.student != null) {
            throw new ClassFullException("Class " + getClassId() + " has no more student capacity");
        }

        this.student = student;
        student.addLessonInternal(this);
    }

    @Override
    public void removeStudent(Student student) {
        if (this.student != null && this.student.equals(student)) {
            this.student = null;
            student.removeLessonInternal(this);
        }
    }

    @Override
    public Set<Student> getStudents() {
        return Set.of(student);
    }
}
