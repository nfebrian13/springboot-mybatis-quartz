package com.mybatis.app.model;

import java.util.Date;

public class SchedulerJob {
	
	private String jobName;
	private String groupName;
	private Date scheduleTime;
	private Date lastFiredTime;
	private Date nextFireTime;
	private String jobState;
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Date getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public Date getLastFiredTime() {
		return lastFiredTime;
	}
	public void setLastFiredTime(Date lastFiredTime) {
		this.lastFiredTime = lastFiredTime;
	}
	public Date getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public String getJobState() {
		return jobState;
	}
	public void setJobState(String jobState) {
		this.jobState = jobState;
	}

	
}
