package com.mybatis.app.instance.job.conf;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class JobScheduleCreator {

	/**
	 * Create Quartz Job.
	 *
	 * @param jobClass  Class whose executeInternal() method needs to be called.
	 * @param isDurable Job needs to be persisted even after completion. if true,
	 *                  job will be persisted, not otherwise.
	 * @param context   Spring application context.
	 * @param jobName   Job name.
	 * @param jobGroup  Job group.
	 * @return JobDetail object
	 */
	public JobDetail createJob(Class<? extends QuartzJobBean> jobClass, boolean isDurable, ApplicationContext context,
			String jobName, String jobGroup) {

		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(jobClass);
		factoryBean.setDurability(isDurable);
		factoryBean.setApplicationContext(context);
		factoryBean.setName(jobName);
		factoryBean.setGroup(jobGroup);
		// set job data map
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(jobName + jobGroup, jobClass.getName());
		factoryBean.setJobDataMap(jobDataMap);
		factoryBean.afterPropertiesSet();
		return factoryBean.getObject();
	}

	public JobDetail createJob(Class<? extends QuartzJobBean> jobClass, boolean isDurable, ApplicationContext context,
			String jobName, String jobGroup, HashMap<String, Object> params) {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(jobClass);
		factoryBean.setDurability(isDurable);
		factoryBean.setApplicationContext(context);
		factoryBean.setName(jobName);
		factoryBean.setGroup(jobGroup);
		// set job data map
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(jobName + jobGroup, jobClass.getName());
		jobDataMap.put("params", params);
		factoryBean.setJobDataMap(jobDataMap);
		factoryBean.afterPropertiesSet();
		return factoryBean.getObject();
	}

	public JobDetail createJob(Class<? extends QuartzJobBean> jobClass, boolean isDurable, ApplicationContext context,
			String jobName, String jobGroup, HashMap<String, Object> params, String restUrl) {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(jobClass);
		factoryBean.setDurability(isDurable);
		factoryBean.setApplicationContext(context);
		factoryBean.setName(jobName);
		factoryBean.setGroup(jobGroup);
		// set job data map
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(jobName + jobGroup, jobClass.getName());
		/*
		 * if(params != null) jobDataMap.puts jobDataMap.put("params", params);
		 */ if (restUrl != null)
			jobDataMap.put("url", restUrl);
		factoryBean.setJobDataMap(jobDataMap);
		factoryBean.afterPropertiesSet();
		return factoryBean.getObject();
	}

	/**
	 * Create cron trigger.
	 *
	 * @param triggerName        Trigger name.
	 * @param startTime          Trigger start time.
	 * @param cronExpression     Cron expression.
	 * @param misFireInstruction Misfire instruction (what to do in case of misfire
	 *                           happens).
	 * @return {@link CronTrigger}
	 */
	public CronTrigger createCronTrigger(String triggerName, Date startTime, String cronExpression,
			int misFireInstruction) {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setName(triggerName);
		factoryBean.setStartTime(startTime);
		factoryBean.setCronExpression(cronExpression);
		factoryBean.setMisfireInstruction(misFireInstruction);
		try {
			factoryBean.afterPropertiesSet();
		} catch (ParseException e) {
		}
		return factoryBean.getObject();
	}

	/**
	 * Create simple trigger.
	 *
	 * @param triggerName        Trigger name.
	 * @param startTime          Trigger start time.
	 * @param repeatTime         Job repeat period mills
	 * @param misFireInstruction Misfire instruction (what to do in case of misfire
	 *                           happens).
	 * @return {@link SimpleTrigger}
	 */
	public SimpleTrigger createSimpleTrigger(String triggerName, Date startTime, Long repeatTime,
			int misFireInstruction) {
		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
		factoryBean.setName(triggerName);
		factoryBean.setStartTime(startTime);
		factoryBean.setRepeatInterval(repeatTime);
		factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		factoryBean.setMisfireInstruction(misFireInstruction);
		factoryBean.afterPropertiesSet();
		return factoryBean.getObject();
	}
}