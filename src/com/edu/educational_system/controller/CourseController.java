package com.edu.educational_system.controller;

import java.util.List;

import com.edu.educational_system.model.Course;
import com.edu.educational_system.model.Person;
import com.edu.educational_system.service.CourseService;

public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    public void createCourse(Course course) throws RuntimeException {
        courseService.createCourse(course);
    }

    public boolean registerPerson(Course course, Person person) {
        return courseService.enrollPerson(course, person);
    }

    public void startLesson(Course course) {
        courseService.conductLesson(course);
    }

    public List<Person> getParticipants(Course course) {
        return courseService.getParticipants(course);
    }

    public List<Person> getStaff(Course course) {
        return courseService.getStaff(course);
    }

    public double getAverageGrade(Course course) {
        return courseService.getAverageGrade(course);
    }

    public List<Course> getAllCourses() throws RuntimeException {
        return courseService.getAllCourses();
    }

    public void obfuscateData(String nameOfCourse, String email) {
        courseService.obfuscateData(nameOfCourse, email);
    }
}
