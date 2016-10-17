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
import com.ooad.coursemgmt.service.CourseService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class StudentHomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(StudentHomeController.class);
	 @Resource(name="courseService")
	 private CourseService courseService;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		return "home";
	}
	
	@RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String getCourses(Model model) {    
	     logger.debug("Received request to show all courses");
	     List<Course> courses = courseService.getAll();
	     model.addAttribute("courses", courses);
	     return "courses";
	 }
	
	@RequestMapping(value = "/deleteRegCourse", method = RequestMethod.GET)
	public String deleteRegisteredCourse(@RequestParam String userId, @RequestParam String courseNumber, Model model) {
		logger.debug("Received request to delete a registered course.");
		System.out.println("In Controller::" + userId + "  "+courseNumber);
		boolean result = courseService.deleteRegdCourse(userId, Integer.parseInt(courseNumber));
		System.out.println("Deletion result:" + result);
		model.addAttribute("delRes", result+"");
	     List<Course> courses = courseService.getRegisteredCourses(userId);
	     model.addAttribute("courses", courses);
		model.addAttribute("diplayResults", "None");
	     model.addAttribute("userId", userId);
		return "StudentHome";
	}
	
	@RequestMapping(value = "/studentHome", method = RequestMethod.GET)
    public String getStudentHome(Model model) {      
	     logger.debug("Received request to show student home.");	      
	     return "StudentHome";
	 }
	
	@RequestMapping(value = "/CourseMgmt", method = RequestMethod.GET)
    public String courseMgmt(Model model) {   
	     logger.debug("Received request to show CourseMgmt");	      
	     return "CourseMgmt";
	 }
	
	@RequestMapping(value = "/searchCourses", method = RequestMethod.GET)
	public String searchCourses(@RequestParam String uId, @RequestParam String cPrefix,
			@RequestParam String cNum, @RequestParam String cName, @RequestParam String instructor,
				@RequestParam String cStatus, @RequestParam String cLevel, Model model) {
		
		logger.debug("Received request to search courses.");
		System.out.println("In Controller::" + uId + "  num" + cNum + " Prefix:" + cPrefix);
		Course courseDetails = new Course();
		if (!cNum.equals(""))
			courseDetails.setCourseNumber(Integer.parseInt(cNum));
		else
			courseDetails.setCourseNumber(-99);
		courseDetails.setCourseName(cName);
		courseDetails.setCoursePrefix(cPrefix);
		courseDetails.setInstructor(instructor);
		courseDetails.setCourseStatus(cStatus);
		courseDetails.setCourseLevel(cLevel);
		List<Course> searchedCourses = null;
		if(cNum.equals("") && cName.equals("") && cPrefix.equals("") && instructor.equals("") &&
				cStatus.equals("") && cLevel.equals("")){
			    System.out.println("Getting all the courses.");
			    searchedCourses = courseService.getAll();
		}
		else{
			System.out.println("Getting restricted courses.");
			searchedCourses = courseService.searchCourses(courseDetails);
		}
		model.addAttribute("diplayResults", "display");
		model.addAttribute("searchedCourses", searchedCourses);
	    List<Course> courses = courseService.getRegisteredCourses(uId);
	    model.addAttribute("courses", courses);
	    model.addAttribute("userId", uId);
		return "StudentHome";
	}
	
	@RequestMapping(value = "/registerCourses", method = RequestMethod.GET)
	public String registerCourses(@RequestParam String uId, @RequestParam String courseNum1, Model model){
		logger.debug("Received request to register courses.");
		System.out.println("In Controller::" + uId + "  num" + courseNum1);
		model.addAttribute("diplayResults", "None");
		String registerResults = courseService.registerCourses(uId, Integer.parseInt(courseNum1));
		model.addAttribute("registerResults", registerResults);
	    List<Course> courses = courseService.getRegisteredCourses(uId);
	    model.addAttribute("courses", courses);
	    model.addAttribute("userId", uId);
		return "StudentHome";
	}
	
	@RequestMapping(value = "/swapCourses", method = RequestMethod.GET)
	public String swapCourses(@RequestParam String uId, @RequestParam String courseNum1,
			@RequestParam String courseNum2, Model model){
		logger.debug("Received request to swap courses.");
		System.out.println("In Controller::" + uId + "  num" + courseNum1);
		model.addAttribute("diplayResults", "None");
		String swapResults = courseService.swapCourses(uId, Integer.parseInt(courseNum1), Integer.parseInt(courseNum2));
		model.addAttribute("swapResults", swapResults);
	    List<Course> courses = courseService.getRegisteredCourses(uId);
	    model.addAttribute("courses", courses);
	    model.addAttribute("userId", uId);
		return "StudentHome";
	}
	
}
