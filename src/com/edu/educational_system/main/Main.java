package com.edu.educational_system.main;

import com.edu.educational_system.controller.CourseController;
import com.edu.educational_system.repository.CourseRepository;
import com.edu.educational_system.service.CourseService;
import com.edu.educational_system.ui.CourseConsoleView;

public class Main {

	public static void main(String[] args) {
		CourseConsoleView view = new CourseConsoleView(
				new CourseController(new CourseService(new CourseRepository())));
		
		view.displayMenu();

	}

}
