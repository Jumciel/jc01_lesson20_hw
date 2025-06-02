package com.edu.educational_system.repository;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.edu.educational_system.model.*;

public class CourseRepository {

    private final String FILE_PATH = "resources/course.txt";

    public CourseRepository() {
    }

    public void saveCourse(Course course) throws RuntimeException {
        List<Course> existingCourses = loadCoursesFromFile();

        for (Course exCourse : existingCourses) {
            if (exCourse.getName().equalsIgnoreCase(course.getName())) {
                throw new IllegalArgumentException("Course with name '" + course.getName() + "' already exists");
            }
        }

        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(FILE_PATH, true)))) {
            pw.println("Course;" + course.getName());

            for (Person p : course.getParticipants()) {
                Student s = (Student) p;
                pw.println(
                        "Student;" + s.getName() + ";" + s.getEmail() + ";" + s.getGroup() + ";" + s.getAverageGrade());
            }

            for (Person p : course.getStaff()) {
                if (p.getClass() == Administrator.class) {
                    Administrator a = (Administrator) p;
                    pw.println("Administrator;" + a.getName() + ";" + a.getEmail() + ";" + a.getDepartment());
                } else if (p.getClass() == Teacher.class) {
                    Teacher t = (Teacher) p;
                    pw.println("Teacher;" + t.getName() + ";" + t.getEmail() + ";" + t.getSubject());
                }

            }
            pw.println("---");
        } catch (IOException e) {
            throw new RuntimeException("Error writing course to file: " + e.getMessage(), e);
        }
    }

    public List<Course> getAllCourses() throws RuntimeException {

        return loadCoursesFromFile();
    }

    public List<Course> loadCoursesFromFile() throws RuntimeException {

        List<Course> result = new ArrayList<Course>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            Course course = null;
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.equals("---") || line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(";");
                switch (parts[0]) {
                    case "Course" -> {
                        if (course != null)
                            result.add(course);
                        course = new Course(parts[1]);
                    }
                    case "Teacher" -> {
                        if (course != null) {
                            course.addParticipant(new Teacher(parts[1], parts[2], parts[3]));
                        }
                    }
                    case "Administrator" -> {
                        if (course != null) {
                            course.addParticipant(new Administrator(parts[1], parts[2], parts[3]));
                        }
                    }
                    case "Student" -> {
                        if (course != null) {
                            course.addParticipant(new Student(parts[1], parts[2], parts[3], Double.parseDouble(parts[4])));
                        }
                    }
                }

            }
            if (course != null)
                result.add(course);

        } catch (IOException e) {
            throw new RuntimeException("Course wasn't found" + e.getMessage());
        }
        return result;

    }

    public void obfuscateStudentInCourse(String courseName, String studentEmail) {
        List<String> lines = new ArrayList<>();
        boolean insideCourse = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Course;")) {
                    insideCourse = line.equalsIgnoreCase("Course;" + courseName);
                    lines.add(line);
                } else if (insideCourse && line.startsWith("Student;")) {
                    String[] parts = line.split(";");
                    if (parts.length >= 5 && parts[2].equalsIgnoreCase(studentEmail)) {

                        String obfuscatedLine = "Student;***;***@***;" + parts[3] + ";" + parts[4];
                        lines.add(obfuscatedLine);
                        continue;
                    }
                    lines.add(line);
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Person wasn't found" + e.getMessage());
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (String l : lines) {
                writer.println(l);
            }
        } catch (IOException e) {
            throw new RuntimeException("Person wasn't found" + e.getMessage());
        }
    }
}
