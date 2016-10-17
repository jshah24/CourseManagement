package com.ooad.coursemgmt.service;
import java.util.Random;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 


import javax.annotation.Resource;
import javax.sql.DataSource;
 


import org.apache.log4j.Logger;

import com.ooad.coursemgmt.domain.Course;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("courseService")
@Transactional
public class CourseService {
	 protected static Logger logger = Logger.getLogger("service");
	  
	 @SuppressWarnings("deprecation")
	 private SimpleJdbcTemplate jdbcTemplate;
	  
	 @Resource(name="dataSource")
	 public void setDataSource(DataSource dataSource) {
	     this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	 }
	 
	 /**
	  * Retrieves all persons
	  *
	  * @return a list of persons
	  */
	 public List<Course> getAll() {
	  logger.debug("Retrieving all courses");
	   
	  // Prepare our SQL statement
	  String sql = "select * from courses, offeredcourses where courses.course_number = offeredcourses.course_number";
	   
	  // Maps a SQL result to a Java object
	  RowMapper<Course> mapper = new RowMapper<Course>() { 
	         public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
		         Course course = new Course();
		         course.setCourseNumber(rs.getInt("course_number"));
		         course.setCourseName(rs.getString("course_name"));
		         course.setCourseCredits(rs.getInt("credits"));
		         course.setCourseLevel(rs.getString("course_level"));
		         course.setCourseLocation(rs.getString("course_location"));
		         course.setCoursePrefix(rs.getString("course_prefix"));
		         course.setInstructionMethod(rs.getString("instruction_method"));
		         course.setInstructor(rs.getString("course_instructor"));
		         course.setSeatsAvailable(rs.getString("seats_available"));
		         course.setMaximumSeats(rs.getString("maximum_seats"));
		         course.setCourseStatus(rs.getString("status"));
		         course.setCourseTiming(rs.getString("course_timing"));
		         return course;
	         }
	     };
	   
	  // Retrieve all
	  return jdbcTemplate.query(sql, mapper);
	 }
	 public List<Course> searchCourses(Course courseFilter) {
		  logger.debug("Retrieving all courses");
		   
		  // Prepare our SQL statement
		  String sql = "select * from courses, offeredcourses where courses.course_number = offeredcourses.course_number";
		  if (! courseFilter.getCoursePrefix().equals("")){
			  sql = sql + " and offeredcourses.course_prefix = '" + courseFilter.getCoursePrefix() + "'";
		  }
		  if (! courseFilter.getCourseName().equals("")){
			  sql = sql + " and courses.course_name = '" + courseFilter.getCourseName() + "'";
		  }
		  if (! (courseFilter.getCourseNumber() == -99)){
			  sql = sql + " and courses.course_number = " + courseFilter.getCourseNumber();
		  }
		  if (! courseFilter.getCourseLevel().equals("")){
			  sql = sql + " and offeredcourses.course_level = '" + courseFilter.getCourseLevel() + "'";
		  }
		  if (! courseFilter.getCourseStatus().equals("")){
			  sql = sql + " and offeredcourses.status = '" + courseFilter.getCourseStatus() + "'";
		  }
		  if (! courseFilter.getInstructor().equals("")){
			  sql = sql + " and offeredcourses.course_instructor = '" + courseFilter.getInstructor() + "'";
		  }
		  System.out.println("Sql query is "+sql);
		  // Maps a SQL result to a Java object
		  RowMapper<Course> mapper = new RowMapper<Course>() { 
		         public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
			         Course course = new Course();
			         course.setCourseNumber(rs.getInt("course_number"));
			         course.setCourseName(rs.getString("course_name"));
			         course.setCourseCredits(rs.getInt("credits"));
			         course.setCourseLevel(rs.getString("course_level"));
			         course.setCourseLocation(rs.getString("course_location"));
			         course.setCoursePrefix(rs.getString("course_prefix"));
			         course.setInstructionMethod(rs.getString("instruction_method"));
			         course.setInstructor(rs.getString("course_instructor"));
			         course.setSeatsAvailable(rs.getString("seats_available"));
			         course.setMaximumSeats(rs.getString("maximum_seats"));
			         course.setCourseStatus(rs.getString("status"));
			         course.setCourseTiming(rs.getString("course_timing"));
			         return course;
		         }
		     };
		   
		  // Retrieve all
		  return jdbcTemplate.query(sql, mapper);
		 }
	 public List<Course> getRegisteredCourses(String userId) {
		  logger.debug("Retrieving all courses");
		   
		  // Prepare our SQL statement
		  String sql = "select rc.course_number, c.course_name, rc.reg_status, rc.student_grade from registeredcourses rc, courses c where rc.course_number = c.course_number and "
				  		+"rc.student_id = '"+userId+"';";
		   
		  // Maps a SQL result to a Java object
		  RowMapper<Course> mapper = new RowMapper<Course>() { 
		         public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
		          Course course = new Course();
		          course.setCourseNumber(rs.getInt("course_number"));
		          course.setCourseName(rs.getString("course_name"));
		          course.setRegStatus(rs.getString("reg_status"));
		          course.setGrade(rs.getString("student_grade"));
		          return course;
		         }
		     };
		   
		  // Retrieve all
		  return jdbcTemplate.query(sql, mapper);
		 }
	 public boolean deleteRegdCourse(String userId, int courseNum){
		 boolean return_value = true;
		 try{
		 logger.debug("Deleting registered course.");
		   
		 String sql = "delete from registeredcourses where student_id = :studentId" +
				    " and course_number = :courseNum";
				   
				  // Assign values to parameters
				  Map<String, Object> parameters = new HashMap<String, Object>();
				  parameters.put("studentId", userId);
				  parameters.put("courseNum", courseNum);
				   
				  // Save
				  
				  int rowsAffected = jdbcTemplate.update(sql, parameters);
				  if(rowsAffected == 0){
					  return false;
				  }
				  updateCourseStatus(courseNum, "drop");
		 }catch(Exception e){
			 logger.debug(e);
			 return_value = false;
		 }
		 return return_value;
	 }
	 
	 
	 /**
	  * Adds a new course
	  *
	  * @param courseName the name of the course
	  * @param courseCredits the credits gained by course
	  * @param courseLevel the level of the course
	  */
	 public String registerCourses(String userId, Integer courseNumber) {
	  logger.debug("Registering new course");
	 
	  String semester = "Fall 2014";//((Course)retrieveSemester(courseNumber)).getSemester();
	  if(! checkCourseStatus(courseNumber)){
		  return "Sorry could not register, course is closed.";
	  }
	  String regStatus = getRegistrationStatus(userId, courseNumber);
	  String dbRegStatus = "registered";
	  if (!regStatus.equals("Successfully registered.")){
		  dbRegStatus = "pending";
	  }
	  // Prepare our SQL statement using Named Parameters style
	  String sql = "insert into registeredcourses(student_id, course_number, course_sem, reg_status, student_grade) values " +
	    "(:studentId, :courseNumber, :courseSem, :regStatus, 'NA')";
	   
	  // Assign values to parameters
	  Map<String, Object> parameters = new HashMap<String, Object>();
	  parameters.put("studentId", userId);
	  parameters.put("courseNumber", courseNumber);
	  parameters.put("courseSem", semester);
	  parameters.put("regStatus", dbRegStatus);
	   
	  // Save
	  int rowsAffected = jdbcTemplate.update(sql, parameters);
	  if(! dbRegStatus.equals("pending")){
		  updateCourseStatus(courseNumber, "add");
		  return "Successfully registered.";
	  }
	  return regStatus;
	 }
	 public boolean checkCourseStatus(Integer courseNumber){
		 boolean result = true;
		 String sql = "select status from offeredcourses where course_number="+courseNumber;
		 RowMapper<Course> mapper = new RowMapper<Course>() { 
	         public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	Course course = new Course();
	        	course.setCourseStatus(rs.getString("status"));
	            return course;
	          }};
	     List<Course>courseStatusList = jdbcTemplate.query(sql,mapper);
	     if(courseStatusList.get(0).getCourseStatus().equalsIgnoreCase("closed")){
	    	 result = false;
	     }
		 return result;
	 }
	 public String getRegistrationStatus(String userId, Integer courseNumber){
		 String regStatus = "Successfully registered.";
		 // Get course prerequisites
		 String sql = "select course_prefix from offeredcourses where course_number="+courseNumber;
		 RowMapper<Course> mapper = new RowMapper<Course>() { 
	         public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	Course course = new Course();
	        	course.setCoursePrefix(rs.getString("course_prefix"));
	            return course;
	          }};
	     List<Course>coursePrefixList = jdbcTemplate.query(sql,mapper); 
	     
	     // Get student prerequisites
	     sql = "select program_prefix, student_advisor from student, EnrolledPrograms, programs " + 
	    	   "where student.student_netid = EnrolledPrograms.student_id " + 
			   "and EnrolledPrograms.program_id = programs.program_id " +
			   "and student.student_netid = '"+ userId +"'";
		 RowMapper<Course> mapper1 = new RowMapper<Course>() { 
	         public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	Course course = new Course();
	        	course.setCoursePrefix(rs.getString("program_prefix"));
	        	course.setCourseNumber(rs.getInt("student_advisor"));
	            return course;
	          }};
	     List<Course>programPrefixList = jdbcTemplate.query(sql,mapper1);
	     String coursePrefix = coursePrefixList.get(0).getCoursePrefix();
	     String programPrefix = programPrefixList.get(0).getCoursePrefix();
	     System.out.println("Course Pre:" + coursePrefix + "Program Pre:" + programPrefix);
	     if(!coursePrefix.equals(programPrefix)){
	    	 
	    	 addStudentRequest(userId, courseNumber, "Registration request.", programPrefixList.get(0).getCourseNumber());
	    	 return "Registered for course outside " + programPrefix + "program. Waiting for advisors approval.";
	     }
		 return regStatus;
	 }
	 
	 private void addStudentRequest(String userId, Integer courseNumber,
			String requestDesc, int advisorId) {
		// TODO Auto-generated method stub
		  logger.debug("Adding student request.");
		  Random rgen = new Random();
		  int requestId = rgen.nextInt();
		  // Prepare our SQL statement
		  String sql = "insert into studentrequests values( " +
		    ":requestId, :studentId, :advisorId, :requestDesc, :courseNum)";
		   
		  // Assign values to parameters
		  Map<String, Object> parameters = new HashMap<String, Object>();
		  parameters.put("requestId", requestId);
		  parameters.put("studentId", userId);
		  parameters.put("advisorId", advisorId);
		  parameters.put("requestDesc", requestDesc);
		  parameters.put("courseNum", courseNumber);
		   
		  // Edit
		  jdbcTemplate.update(sql, parameters);
	}
	 
	 private void updateCourseStatus (Integer courseNumber, String updateType){
		 String sql = "select seats_available, maximum_seats from offeredcourses where course_number="+courseNumber;
		 RowMapper<Course> mapper = new RowMapper<Course>() { 
	         public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	Course course = new Course();
	        	course.setSeatsAvailable(""+rs.getInt("seats_available"));
	        	course.setMaximumSeats(""+rs.getInt("maximum_seats"));
	            return course;
	          }};
	     List<Course>seatDetails = jdbcTemplate.query(sql,mapper);
	     String saStr = seatDetails.get(0).getSeatsAvailable();
	     String msStr = seatDetails.get(0).getMaximumSeats();
	     int seatsAvailable = Integer.parseInt(saStr);
	     int maxSeats = Integer.parseInt(msStr);
	     String status = "";	     
	     if(updateType.equals("drop")){
	    	 if(seatsAvailable == 0){
	    		 status = "open";
	    	 }
	    	 seatsAvailable++;
	     }
	     else{
	    	 seatsAvailable--;
	    	 if(seatsAvailable == 0){
	    		 status = "closed";
	    	 }    	 
	     }
	     sql = "update offeredcourses set seats_available=:seatsAvailable, maximum_seats=:maxSeats";
	     if(! status.equals("")){
	    	 sql = sql + ", status=:status";
	     }
	     sql = sql + " where course_number=" + courseNumber;
	     System.out.println("UPdate query:"+sql);
	     Map<String, Object> parameters = new HashMap<String, Object>();
		  parameters.put("seatsAvailable", seatsAvailable);
		  parameters.put("maxSeats", maxSeats);
		  if(! status.equals("")){
			  parameters.put("status", status);
		  }
		   
		  // Edit
		  jdbcTemplate.update(sql, parameters);
	 }

	 
	 public String swapCourses(String userId, Integer courseNum1, Integer courseNum2){
		 String swapResult = "Swapped successfully.";
		 if (! checkCourseStatus(courseNum2)){
			 swapResult = "Sorry could not register, course is closed.";
			 return swapResult;
		 }
		 boolean deletion = deleteRegdCourse(userId, courseNum1);
		 if(!deletion){
			 swapResult = "Failed to drop the registered course:" + courseNum1;
		 }
		 else{
			 swapResult = registerCourses(userId, courseNum2);
		 }
		 return swapResult;
	 }
	/**
	  * Deletes an existing course
	  * @param courseNumber the number of the existing course
	  */
	 public void delete(Integer courseNumber) {
	  logger.debug("Deleting existing course");
	   
	  // Prepare our SQL statement using Unnamed Parameters style
	  String sql = "delete from courses where course_number = ?";
	   
	  // Assign values to parameters
	  Object[] parameters = new Object[] {courseNumber};
	   
	  // Delete
	  jdbcTemplate.update(sql, parameters);
	 }
	  
	 /**
	  * Edits an existing course
	  * @param courseNumber the number assigned to the course
	  * @param courseName the course name of the existing course
	  * @param courseCredits the credits earned of the existing course
	  * @param courseLevel the level of the existing course
	  */
	 public void edit(Integer courseNumber, String courseName, Integer courseCredits, Integer courseLevel) {
	  logger.debug("Editing existing course");
	   
	  // Prepare our SQL statement
	  String sql = "update courses set course_name = :courseName, " +
	    "course_credits = :courseCredits, course_level = :courseLevel where course_number = :courseNumber";
	   
	  // Assign values to parameters
	  Map<String, Object> parameters = new HashMap<String, Object>();
	  parameters.put("course_number", courseNumber);
	  parameters.put("course_name", courseName);
	  parameters.put("credits", courseCredits);
	  parameters.put("course_level", courseLevel);
	   
	  // Edit
	  jdbcTemplate.update(sql, parameters);
	   
	 }
}
