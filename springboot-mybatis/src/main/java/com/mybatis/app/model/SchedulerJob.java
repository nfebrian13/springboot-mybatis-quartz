package com.mybatis.app.model;

import java.util.Date;

public class SchedulerJob {
	
	private String jobName;
	private String groupName;
	private Date scheduleTime;
	private Date lastFiredTime;
	private Date nextFireTime;
	private String jobState;
	private String jobClass;
	private String cronExpression;
	private Long repeatTime;
	private boolean cronJob;
	
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
	public String getJobClass() {
		return jobClass;
	}
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public Long getRepeatTime() {
		return repeatTime;
	}
	public void setRepeatTime(Long repeatTime) {
		this.repeatTime = repeatTime;
	}
	public boolean isCronJob() {
		return cronJob;
	}
	public void setCronJob(boolean cronJob) {
		this.cronJob = cronJob;
	}
}
