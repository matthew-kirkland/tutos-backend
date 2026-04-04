package org.tutos.backend.classroom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import org.tutos.backend.user.entity.Student;
import org.tutos.backend.user.entity.Tutor;

import java.util.HashSet;
import java.util.Set;

@Entity
public class GroupClass extends Class {
    @ManyToMany
    @JoinTable(
            name = "class_students",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<Student>();

    public GroupClass(
            String title,
            String description,
            Set<Tutor> tutors,
            Set<Student> students
    ) {
        super(title, description, tutors);
        if (students != null) this.students.addAll(students);
    }

    public GroupClass() {
    }

    @Override
    public void addStudent(Student student) {
        if (students.add(student)) {
            student.addLesson(this);
        }
    }

    @Override
    public void removeStudent(Student student) {
        if (students.remove(student)) {
            student.removeLesson(this);
        }
    }

    @Override
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
