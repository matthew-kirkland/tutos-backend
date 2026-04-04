package org.lms.backend.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;
import org.lms.backend.calendar.entity.ClassSession;
import org.lms.backend.classroom.entity.Class;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Tutor extends User {
    private boolean emailNotif;
    private boolean smsNotif;
    @ManyToMany(mappedBy = "tutors")
    private Set<Class> classes = new HashSet<Class>();
    @ManyToMany(mappedBy = "activeTutors")
    @OrderBy("startTime ASC")
    private List<ClassSession> activeSessions = new ArrayList<ClassSession>();
    @ManyToMany(mappedBy = "observerTutors")
    @OrderBy("startTime ASC")
    private List<ClassSession> observerSessions = new ArrayList<ClassSession>();

    public Tutor(
            String username,
            String email,
            String password,
            String phone,
            String nameFirst,
            String nameLast,
            LocalDate dob
    ) {
        super(username, email, password, phone, nameFirst, nameLast, dob);
        this.setEmailNotif(true);
        this.setSmsNotif(true);
    }

    public Tutor() {
    }

    public void addClass(Class clazz) {
        if (classes.add(clazz)) {
            clazz.addTutor(this);
        }
    }

    public void removeClass(Class clazz) {
        if (classes.remove(clazz)) {
            clazz.removeTutor(this);
        }
    }

    @Override
    public boolean isEmailNotif() {
        return emailNotif;
    }

    public void setEmailNotif(boolean emailNotif) {
        this.emailNotif = emailNotif;
    }

    @Override
    public boolean isSmsNotif() {
        return smsNotif;
    }

    public void setSmsNotif(boolean smsNotif) {
        this.smsNotif = smsNotif;
    }

    public Set<Class> getClasses() {
        return classes;
    }

    public void setClasses(Set<Class> classes) {
        this.classes = classes;
    }

    public List<ClassSession> getActiveSessions() {
        return activeSessions;
    }

    public void setActiveSessions(List<ClassSession> classSessions) {
        this.activeSessions = classSessions;
    }

    public List<ClassSession> getObserverSessions() {
        return observerSessions;
    }

    public void setObserverSessions(List<ClassSession> observerSessions) {
        this.observerSessions = observerSessions;
    }
}
