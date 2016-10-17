package com.ooad.coursemgmt.domain;

import java.io.Serializable;

public class Course implements Serializable {
	 
	 private static final long serialVersionUID = -5527566248002296042L;
	  
	 private Integer courseNumber;
	 private String courseName;
	 private Integer courseCredits;
	 private String courseLevel;
	 private String regStatus;
	 private String studentGrade;
	 private String courseStatus;
	 private String instructor;
	 private String coursePrefix;
	 private Integer credits;
	 private String semester;
	 private String instructionMethod;
	 private String courseTiming;
	 private String courseLocation;
	 private String seatsAvailable;
	 private String maximumSeats;
	 
	 public Integer getCourseNumber() {
	  return courseNumber;
	 }
	 
	 public void setCourseNumber(Integer courseNumber) {
	  this.courseNumber = courseNumber;
	 }
	 
	 public String getCourseName() {
	  return courseName;
	 }
	 
	 public void setCourseName(String courseName) {
	  this.courseName = courseName;
	 }
	 
	 public Integer getCourseCredits() {
	  return courseCredits;
	 }
	 
	 public void setCourseCredits(Integer courseCredits) {
	  this.courseCredits = courseCredits;
	 }
	 
	 public String getCourseLevel() {
	  return courseLevel;
	 }
	 
	 public void setCourseLevel(String courseLevel) {
	  this.courseLevel = courseLevel;
	 }
	 
	 public String getRegStatus() {
	  return regStatus;
	 }
	 
	 public void setRegStatus(String regStatus) {
	  this.regStatus = regStatus;
	 }
	 
	 public String getGrade() {
	  return studentGrade;
	 }
	 
	 public void setGrade(String courseGrade) {
	  this.studentGrade = courseGrade;
	 }
	 
	 public String getCourseStatus() {
	  return courseStatus;
	 }
	 
	 public void setCourseStatus(String courseStatus) {
	  this.courseStatus = courseStatus;
	 }
	 
	 public String getCoursePrefix() {
	  return coursePrefix;
	 }
	 
	 public void setCoursePrefix(String coursePrefix) {
	  this.coursePrefix = coursePrefix;
	 }
	 
	 public String getInstructor() {
	  return instructor;
	 }
	 
	 public void setInstructor(String instructor) {
	  this.instructor = instructor;
	 } 
	 public String getSemester(){
		 return semester;
	 }
	 public void setSemester(String semester){
		 this.semester = semester;
	 }
	 public String getInstructionMethod(){
		 return instructionMethod;
	 }
	 public void setInstructionMethod(String instructionMethod){
		 this.instructionMethod = instructionMethod; 
	 }	 
	 public String getCourseTiming(){
		 return courseTiming;
	 }
	 public void setCourseTiming(String courseTiming){
		 this.courseTiming = courseTiming;
	 }
	 public String getCourseLocation(){
		 return courseLocation;
	 }
	 public void setCourseLocation(String courseLocation){
		 this.courseLocation = courseLocation;
	 }
	 public String getSeatsAvailable(){
		 return seatsAvailable;
	 }
	 public void setSeatsAvailable(String seatsAvailable){
		 this.seatsAvailable = seatsAvailable;
	 } 
	 public String getMaximumSeats(){
		 return maximumSeats;
	 }	 
	 public void setMaximumSeats(String maxSeats){
		this.maximumSeats = maxSeats; 
	 }
}
