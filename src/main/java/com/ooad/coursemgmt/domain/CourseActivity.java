package com.ooad.coursemgmt.domain;

import java.sql.Date;

public class CourseActivity {
	private static final long serialVersionUID = -5527566248012296042L;
	
	private int courseNumber;
	private String semester;
	private String professorId;
	private String activityName;
	private String activityDescription;
	private Date postDate;
	private Date deadline;
	
	public Integer getCourseNumber() {
		return courseNumber;
	}
	 
	 public void setCourseNumber(Integer courseNumber) {
		 this.courseNumber = courseNumber;
	 }
	 
	 public String getSemester() {
		return semester;
	 }
	 
	 public void setSemester(String semester) {
		 this.semester = semester;
	 }
	 
	 public String getProfessorId() {
		return professorId;
	 }
	 
	 public void setProfessorId(String professorId) {
		 this.professorId = professorId;
	 }
	 
	 public String getActivityName() {
		return activityName;
	 }
	 
	 public void setActivityName(String activityName) {
		 this.activityName = activityName;
	 }
	 
	 public String getActivityDescription() {
		return activityDescription;
	 }
	 
	 public void setActivityDescription(String activityDescription) {
		 this.activityDescription = activityDescription;
	 }
	 
	 public Date getPostDate() {
		return postDate;
	 }
	 
	 public void setPostDate(Date postDate) {
		 this.postDate = postDate;
	 }
	 
	 public Date getDeadline() {
		return deadline;
	 }
	 
	 public void setDeadline(Date deadline) {
		 this.deadline = deadline;
	 }
}