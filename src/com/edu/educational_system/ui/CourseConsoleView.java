package com.edu.educational_system.ui;

import java.util.List;
import java.util.Scanner;

import com.edu.educational_system.controller.CourseController;
import com.edu.educational_system.model.Administrator;
import com.edu.educational_system.model.Course;
import com.edu.educational_system.model.Person;
import com.edu.educational_system.model.Student;
import com.edu.educational_system.model.Teacher;

public class CourseConsoleView {
    private final Scanner scanner = new Scanner(System.in);
    private final CourseController controller;
    private List<Course> existingCourses;
    private Course currentCourse;

    public CourseConsoleView(CourseController controller) throws RuntimeException {
        this.controller = controller;
        this.existingCourses = this.controller.getAllCourses();
    }

    public void displayMenu() throws RuntimeException {
        while (true) {
            System.out.println("\n==== Course Management Menu ====");
            System.out.println("1. Create Course");
            System.out.println("2. Add Participant");
            System.out.println("3. Start Lesson");
            System.out.println("4. Show Special Course Info");
            System.out.println("5. Exit");
            System.out.println("6. Show All Courses");
            System.out.println("7. Obfuscate data for person");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> createCourse();
                case 2 -> {
                    selectCurrentCourse();
                    addParticipant();
                }
                case 3 -> startLesson();
                case 4 -> showCourseInfo();
                case 5 -> {
                    System.out.println("Exiting.");
                    return;
                }
                case 6 -> showAllCourses();
                case 7 -> obfuscateData();
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void createCourse() throws RuntimeException {
        System.out.print("Course name: ");
        String courseName = scanner.nextLine();
        currentCourse = new Course(courseName);
        while (true) {
            System.out.println("Would you like to add participant?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addParticipant();
                case 2 -> {
                    controller.createCourse(currentCourse);
                    System.out.println("Course created.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void addParticipant() {
        if (currentCourse == null) {
            System.out.println("Please create a course first.");
            return;
        }

        System.out.println("Select role: 1 - Student, 2 - Teacher, 3 - Administrator");
        int role = Integer.parseInt(scanner.nextLine());

        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Person person = null;

        switch (role) {
            case 1 -> {
                System.out.print("Group: ");
                String group = scanner.nextLine();
                System.out.print("Average grade: ");
                double grade = Double.parseDouble(scanner.nextLine());
                person = new Student(name, email, group, grade);
            }
            case 2 -> {
                System.out.print("Subject: ");
                String subject = scanner.nextLine();
                person = new Teacher(name, email, subject);
            }
            case 3 -> {
                System.out.print("Department: ");
                String dept = scanner.nextLine();
                person = new Administrator(name, email, dept);
            }
            default -> System.out.println("Invalid role.");
        }

        if (person != null) {
            boolean added = controller.registerPerson(currentCourse, person);
            System.out.println(added ? "Participant added." : "Participant already exists.");
        }
    }

    private void startLesson() {
        selectCurrentCourse();
        if (currentCourse == null) {
            System.out.println("Please select an existing course or create the new one");
            return;
        }
        controller.startLesson(currentCourse);
    }

    private void showCourseInfo() throws RuntimeException {
        this.existingCourses = this.controller.getAllCourses();
        selectCurrentCourse();
        if (currentCourse == null) {
            System.out.println("No course with provided name available. Try again");
            return;
        }
        printCourseInfo();
    }

    private void selectCurrentCourse() {
        System.out.println("Name of the course ");
        String nameOfCourse = scanner.nextLine();
        for (Course course : existingCourses) {
            if (course.getName().equalsIgnoreCase(nameOfCourse)) {
                currentCourse = course;

            }
        }
    }

    private void showAllCourses() throws RuntimeException {
        this.existingCourses = this.controller.getAllCourses();
        for (Course course : existingCourses) {
            currentCourse = course;
            printCourseInfo();
        }
    }

    private void printCourseInfo() {
        System.out.println("\nCourse: " + currentCourse.getName());
        List<Person> staff = controller.getStaff(currentCourse);
        for (Person p : staff) {
            System.out.println("- " + p.getName() + " | " + p.getRoleDescription());
        }
        List<Person> participants = controller.getParticipants(currentCourse);
        for (Person p : participants) {
            System.out.println("- " + p.getName() + " | " + p.getRoleDescription());
        }
        System.out.printf("Average Student Grade: %.2f\n", controller.getAverageGrade(currentCourse));
    }

    private void obfuscateData() {
        selectCurrentCourse();
        System.out.println("Provide email of the person you would like to obfuscate");
        String email = scanner.nextLine();
        String nameOfCourse = currentCourse.getName();
        this.controller.obfuscateData(nameOfCourse, email);
    }


}
