package com.ooad.coursemgmt;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ooad.coursemgmt.domain.CourseActivity;
import com.ooad.coursemgmt.service.CourseActivityService;

@Controller
public class ProfessorHomeController {
	private static final Logger logger = LoggerFactory.getLogger(StudentHomeController.class);
	 @Resource(name="courseActivityService")
	 private CourseActivityService courseActivityService;
	 
	 @RequestMapping(value = "/coursesActivities", method = RequestMethod.GET)
	    public String getCourseActivities(@RequestParam Integer courseNum, @RequestParam String semester, @RequestParam String professorId, Model model) {    
		     logger.debug("Received request to get course activities.");
		     List<CourseActivity> courseActivities = courseActivityService.getCourseActivities(courseNum, semester, professorId);
		     model.addAttribute("coursesActvities", courseActivities);
		     return "ProfessorHome";
		 }
}
