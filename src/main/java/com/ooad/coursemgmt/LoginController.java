package com.ooad.coursemgmt;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ooad.coursemgmt.domain.Course;
import com.ooad.coursemgmt.domain.User;
import com.ooad.coursemgmt.domain.CourseActivity;
import com.ooad.coursemgmt.service.UserService;
import com.ooad.coursemgmt.service.CourseService;
import com.ooad.coursemgmt.service.CourseActivityService;
import com.ooad.coursemgmt.service.ProfessorService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	 @Resource(name="userService")
	 private UserService userService;
	 
	 @Resource(name="courseService")
	 private CourseService courseService;
	 
	 @Resource(name="professorService")
	 private ProfessorService professorService;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/verifyUser", method = RequestMethod.GET)
	public String userAuth(@RequestParam String userId,@RequestParam String userPwd, Model model) {	
		
		String user_role = userService.verifyUser(userId, userPwd);
		System.out.println("User role::" + user_role);
	    if (user_role != null){
	    	model.addAttribute("userId", userId);
			if (user_role.equalsIgnoreCase("student")){
			     // Retrieve all persons by delegating the call to PersonService
			     List<Course> courses = courseService.getRegisteredCourses(userId);
			      
			     // Attach persons to the Model
			     model.addAttribute("diplayResults", "None");
			     model.addAttribute("courses", courses);
				return "StudentHome";
			}
			if (user_role.equalsIgnoreCase("professor")){
				List<Course> offeredCourses = professorService.getOfferedCourses(userId, "Fall 2014");
				model.addAttribute("offeredCourses", offeredCourses);
				return "ProfessorHome";
			}
	    }
		model.addAttribute("loginRes", "0");		
		return "home";
	}
}