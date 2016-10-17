package com.ooad.coursemgmt.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 

import javax.annotation.Resource;
import javax.sql.DataSource;
 

import org.apache.log4j.Logger;

import com.ooad.coursemgmt.domain.User;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserService {
	 protected static Logger logger = Logger.getLogger("service");
	  
	 @SuppressWarnings("deprecation")
	 private SimpleJdbcTemplate jdbcTemplate;
	  
	 @Resource(name="dataSource")
	 public void setDataSource(DataSource dataSource) {
	     this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	 }
	 
	 /**
	  * Checks if the person exists in the database.
	  *
	  * @return a boolean
	  */
	 public String verifyUser(String userId, String userPwd) {
      String result = null;
	  logger.debug("Retrieving all courses");
	   System.out.println("Received User name:::"+userId+"   User Password:::"+userPwd);
	  // Prepare our SQL statement
	  String sql = "select user_id, user_pwd, user_role from user where user_id = '"+userId+"'";
	   
	  // Maps a SQL result to a Java object
	  RowMapper<User> mapper = new RowMapper<User>() { 
	         public User mapRow(ResultSet rs, int rowNum) throws SQLException {
	          User user = new User();
	          user.setUserId(rs.getString("user_id"));
	          user.setUserPwd(rs.getString("user_pwd"));
	          user.setUserRole(rs.getString("user_role"));
	          
	          return user;
	         }
	     };
	   
	  // Retrieve all
	  List<User>userList = jdbcTemplate.query(sql, mapper);
	  System.out.println("Size::" + userList.size());
	  if(userList.size() == 1){
		  User userDetails = userList.get(0);
		  System.out.println("Retrieved user name::"+userDetails.getUserId());
		  System.out.println("Retrieved user password::"+userDetails.getUserPwd());
		  System.out.println("Retrieved user role::"+userDetails.getUserRole());
		  if (userPwd.equals(userDetails.getUserPwd())){
			  result = userDetails.getUserRole();
		  }
	  }
	  return result;
	 }
}
