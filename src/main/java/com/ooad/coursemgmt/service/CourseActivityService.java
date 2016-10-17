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

import com.ooad.coursemgmt.domain.CourseActivity;

@Service("courseActivityService")
@Transactional
public class CourseActivityService {
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
	 public List<CourseActivity> getCourseActivities(Integer courseNum, String semester, String professorId) {
	  logger.debug("Retrieving all courses");
	   
	  // Prepare our SQL statement
	  String sql = "select * from courseactivities where course_number = " + courseNum + " and semester = '"+ semester
			  		+ "' and professor_id = '" + professorId + "'";
	   
	  // Maps a SQL result to a Java object
	  RowMapper<CourseActivity> mapper = new RowMapper<CourseActivity>() { 
	         public CourseActivity mapRow(ResultSet rs, int rowNum) throws SQLException {
		         CourseActivity courseActivity = new CourseActivity();
		         courseActivity.setCourseNumber(rs.getInt("course_number"));
		         courseActivity.setSemester(rs.getString("semester"));
		         courseActivity.setSemester(rs.getString("professor_id"));
		         courseActivity.setActivityName(rs.getString("activity_name"));
		         courseActivity.setActivityDescription(rs.getString("activity_description"));
		         courseActivity.setPostDate(rs.getDate("posted_on"));
		         courseActivity.setDeadline(rs.getDate("deadline"));
		         return courseActivity;
	         }
	     };
	   
	  // Retrieve all
	  return jdbcTemplate.query(sql, mapper);
	 }
}
