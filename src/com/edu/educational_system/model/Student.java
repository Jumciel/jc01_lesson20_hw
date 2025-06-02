package com.edu.educational_system.model;

public class Student extends Person {
    private final String group;
    private final double averageGrade;

    public Student(String name, String email, String group, double averageGrade) {
        super(name, email);
        this.group = group;
        this.averageGrade = averageGrade;
    }

    @Override
    public void performRole() {
        study();
    }

    private void study() {
        System.out.println(name + " is attending the class.");
    }
    
    @Override
    public String getRoleDescription() {
        return "Student from group " + group + " with average grade: " + averageGrade;
    }

    public String getGroup() { return group; }
    public double getAverageGrade() { return averageGrade; }


}