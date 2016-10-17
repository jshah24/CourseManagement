package com.ooad.coursemgmt.domain;

import java.io.Serializable;

public class User implements Serializable {
	 
	 private static final long serialVersionUID = -5527566248012296042L;
	  
	 private String userId;
	 private String userPwd;
	 private String userRole;
	 
	 public String getUserId() {
	  return userId;
	 }
	 
	 public void setUserId(String userId) {
	  this.userId = userId;
	 }
	 
	 public String getUserPwd() {
	  return userPwd;
	 }
	 
	 public void setUserPwd(String userPwd) {
	  this.userPwd = userPwd;
	 }

	 public String getUserRole() {
		 return userRole;
	 }
	 
	 public void setUserRole(String userRole) {
		 this.userRole = userRole;
	 }
}
