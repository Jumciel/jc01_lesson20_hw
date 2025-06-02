package com.edu.educational_system.model;

public class Administrator extends Person {
    private final String department;

    public Administrator(String name, String email, String department) {
        super(name, email);
        this.department = department;
    }

    @Override
    public void performRole() {
        ensureProcess();
    }

    private void ensureProcess() {
        System.out.println(name + " from " + department + " is organizing the classroom.");
    }
    
    @Override
    public String getRoleDescription() {
        return "Administrator of department: " + department;
    }

    public String getDepartment() { return department; }
}