package com.mybatis.app.instance.service;

import java.util.Date;
import java.util.List;

import com.mybatis.app.model.SchedulerJob;

public interface SchedulerMvcService {

	List<SchedulerJob> schedulerJobMvcList();
	String getJobState(String jobName, String groupKey);
	boolean createJob(SchedulerJob schedulerJob);
	boolean updateJob(SchedulerJob jobInfo);
	boolean isJobWithNamePresent(String jobName, String groupKey);
	boolean updateOneTimeJob(String jobName, Date date);
	boolean updateCronJob(String jobName, Date date, String cronExpression);
}
