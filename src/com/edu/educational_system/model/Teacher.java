package com.edu.educational_system.model;

public class Teacher extends Person {
    private final String subject;

    public Teacher(String name, String email, String subject) {
        super(name, email);
        this.subject = subject;
    }

    @Override
    public void performRole() {
        teach();
    }

    private void teach() {
        System.out.println(name + " is explaining " + subject + ".");
    }
    
    @Override
    public String getRoleDescription() {
        return "Teacher of subject: " + subject;
    }

    public String getSubject() { return subject; }
}
