package com.ooad.coursemgmt.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ooad.coursemgmt.domain.Course;

@Service("professorService")
@Transactional
public class ProfessorService {
	protected static Logger logger = Logger.getLogger("service");
	
	@SuppressWarnings("deprecation")
	 private SimpleJdbcTemplate jdbcTemplate;
	  
	 @Resource(name="dataSource")
	 public void setDataSource(DataSource dataSource) {
	     this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	 }
	 
	 public List<Course> getOfferedCourses(String professorId, String semester) {
		  logger.debug("Retrieving all courses offered by professor " + professorId);
		   
		  // Prepare our SQL statement , change it to the appropriate one.
		  String sql = "select oc.course_number, c.course_name from offeredcourses oc, courses c where oc.course_number = c.course_number and "
				  		+"oc.professor_id = '"+professorId+"' and semester ='"+ semester +"';";
		   
		  // Maps a SQL result to a Java object
		  RowMapper<Course> mapper = new RowMapper<Course>() { 
		         public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
		          Course course = new Course();
		          course.setCourseNumber(rs.getInt("course_number"));
		          course.setCourseName(rs.getString("course_name"));
		          return course;
		         }
		     };
		   
		  // Retrieve all
		  return jdbcTemplate.query(sql, mapper);
		 }
}
