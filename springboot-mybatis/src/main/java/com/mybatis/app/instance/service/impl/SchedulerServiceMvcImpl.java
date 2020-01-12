package com.mybatis.app.instance.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.mybatis.app.instance.job.conf.JobScheduleCreator;
import com.mybatis.app.instance.service.SchedulerMvcService;
import com.mybatis.app.model.SchedulerJob;

@Service
public class SchedulerServiceMvcImpl implements SchedulerMvcService {

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private JobScheduleCreator scheduleCreator;

	@Override
	public boolean createJob(SchedulerJob schedulerJob) {

		try {
			Trigger trigger;
			Scheduler scheduler = schedulerFactoryBean.getScheduler();

			JobDetail jobDetail = JobBuilder
					.newJob((Class<? extends QuartzJobBean>) Class.forName(schedulerJob.getJobClass()))
					.withIdentity(schedulerJob.getJobName(), schedulerJob.getGroupName()).build();

			if (schedulerJob.isCronJob()) {

				trigger = scheduleCreator.createCronTrigger(schedulerJob.getJobName(), new Date(),
						schedulerJob.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
			} else {

				trigger = scheduleCreator.createSimpleTrigger(schedulerJob.getJobName(), new Date(),
						schedulerJob.getRepeatTime(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
			}

			scheduler.scheduleJob(jobDetail, trigger);
			return true;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<SchedulerJob> schedulerJobMvcList() {

		List<SchedulerJob> list = new ArrayList<>();

		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();

			for (String groupName : scheduler.getJobGroupNames()) {
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

					String jobName = jobKey.getName();
					String jobGroup = jobKey.getGroup();

					// get job's trigger
					List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
					Date scheduleTime = triggers.get(0).getStartTime();
					Date nextFireTime = triggers.get(0).getNextFireTime();
					Date lastFiredTime = triggers.get(0).getPreviousFireTime();

					SchedulerJob jobObj = new SchedulerJob();
					jobObj.setJobName(jobName);
					jobObj.setGroupName(jobGroup);
					jobObj.setScheduleTime(scheduleTime);
					jobObj.setLastFiredTime(lastFiredTime);
					jobObj.setNextFireTime(nextFireTime);
					String jobState = getJobState(jobName, jobGroup);
					jobObj.setJobState(jobState);
					list.add(jobObj);
				}
			}

		} catch (SchedulerException e) {
			System.out.println("SchedulerException while fetching all jobs. error message :" + e.getMessage());
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public String getJobState(String jobName, String groupKey) {

		try {
			JobKey jobKey = new JobKey(jobName, groupKey);

			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);

			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
			if (triggers != null && triggers.size() > 0) {
				for (Trigger trigger : triggers) {
					TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());

					if (TriggerState.PAUSED.equals(triggerState)) {
						return "PAUSED";
					} else if (TriggerState.BLOCKED.equals(triggerState)) {
						return "BLOCKED";
					} else if (TriggerState.COMPLETE.equals(triggerState)) {
						return "COMPLETE";
					} else if (TriggerState.ERROR.equals(triggerState)) {
						return "ERROR";
					} else if (TriggerState.NONE.equals(triggerState)) {
						return "NONE";
					} else if (TriggerState.NORMAL.equals(triggerState)) {
						return "SCHEDULED";
					}
				}
			}

		} catch (SchedulerException e) {
			System.out.println("SchedulerException while checking job with name and group exist:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updateJob(SchedulerJob jobInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isJobWithNamePresent(String jobName, String groupKey) {
		// TODO Auto-generated method stub
		try {
//			String groupKey = "SampleGroup";
			JobKey jobKey = new JobKey(jobName, groupKey);
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			if (scheduler.checkExists(jobKey)) {
				return true;
			}
		} catch (SchedulerException e) {
			System.out.println("SchedulerException while checking job with name and group exist:" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateOneTimeJob(String jobName, Date date) {
		// TODO Auto-generated method stub
		System.out.println("Request received for updating one time job.");

		String jobKey = jobName;

		try {
			Trigger newTrigger = scheduleCreator.createSingleTrigger(jobKey, date,
					SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

			Date dt = schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobKey), newTrigger);

			return true;
		} catch (Exception e) {
			System.out.println("SchedulerException while updating one time job with key :" + jobKey + " message :"
					+ e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateCronJob(String jobName, Date date, String cronExpression) {
		// TODO Auto-generated method stub
		System.out.println("Request received for updating cron job.");

		String jobKey = jobName;

		System.out.println("Parameters received for updating cron job : jobKey :" + jobKey + ", date: " + date);

		try {
			Trigger newTrigger = scheduleCreator.createCronTrigger(jobKey, date, cronExpression,
					SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

			Date dt = schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobKey), newTrigger);

			return true;

		} catch (Exception e) {
			System.out.println(
					"SchedulerException while updating cron job with key :" + jobKey + " message :" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isJobRunning(String jobName, String groupName) {
		System.out.println("Request received to check if job is running");

		String jobKey = jobName;
		String groupKey = groupName;

		System.out.println("Parameters received for checking job is running now : jobKey :" + jobKey);

		try {

			List<JobExecutionContext> currentJobs = schedulerFactoryBean.getScheduler().getCurrentlyExecutingJobs();
			if (currentJobs != null) {
				for (JobExecutionContext jobCtx : currentJobs) {
					String jobNameDB = jobCtx.getJobDetail().getKey().getName();
					String groupNameDB = jobCtx.getJobDetail().getKey().getGroup();
					if (jobKey.equalsIgnoreCase(jobNameDB) && groupKey.equalsIgnoreCase(groupNameDB)) {
						return true;
					}
				}
			}
		} catch (SchedulerException e) {
			System.out.println("SchedulerException while checking job with key :" + jobKey
					+ " is running. error message :" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public boolean deleteJob(String jobName, String groupName) {
		System.out.println("Request received for deleting job.");

		String jobKey = jobName;
		String groupKey = groupName;

		JobKey jkey = new JobKey(jobKey, groupKey);
		System.out.println("Parameters received for deleting job : jobKey :" + jobKey);

		try {
			boolean status = schedulerFactoryBean.getScheduler().deleteJob(jkey);
			System.out.println("Job with jobKey :" + jobKey + " deleted with status :" + status);
			return status;
		} catch (SchedulerException e) {
			System.out.println(
					"SchedulerException while deleting job with key :" + jobKey + " message :" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean stopJob(String jobName, String groupName) {
		System.out.println("JobServiceImpl.stopJob()");
		try {
			String jobKey = jobName;
			String groupKey = groupName;

			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobKey jkey = new JobKey(jobKey, groupKey);

			return scheduler.interrupt(jkey);

		} catch (SchedulerException e) {
			System.out.println("SchedulerException while stopping job. error message :" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean pauseJob(String jobName, String groupName) {
		System.out.println("Request received for pausing job.");

		String jobKey = jobName;
		String groupKey = groupName;
		JobKey jkey = new JobKey(jobKey, groupKey);
		System.out.println("Parameters received for pausing job : jobKey :" + jobKey + ", groupKey :" + groupKey);

		try {
			schedulerFactoryBean.getScheduler().pauseJob(jkey);
			System.out.println("Job with jobKey :" + jobKey + " paused succesfully.");
			return true;
		} catch (SchedulerException e) {
			System.out.println(
					"SchedulerException while pausing job with key :" + jobName + " message :" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean resumeJob(String jobName, String groupName) {
		System.out.println("Request received for resuming job.");

		String jobKey = jobName;
		String groupKey = groupName;

		JobKey jKey = new JobKey(jobKey, groupKey); 
		System.out.println("Parameters received for resuming job : jobKey :"+jobKey);
		try {
			schedulerFactoryBean.getScheduler().resumeJob(jKey);
			System.out.println("Job with jobKey :"+jobKey+ " resumed succesfully.");
			return true;
		} catch (SchedulerException e) {
			System.out.println("SchedulerException while resuming job with key :"+jobKey+ " message :"+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean startJobNow(String jobName, String groupName) {
		System.out.println("Request received for starting job now.");

		String jobKey = jobName;
		String groupKey = groupName;

		JobKey jKey = new JobKey(jobKey, groupKey); 
		System.out.println("Parameters received for starting job now : jobKey :"+jobKey);
		try {
			schedulerFactoryBean.getScheduler().triggerJob(jKey);
			System.out.println("Job with jobKey :"+jobKey+ " started now succesfully.");
			return true;
		} catch (SchedulerException e) {
			System.out.println("SchedulerException while starting job now with key :"+jobKey+ " message :"+e.getMessage());
			e.printStackTrace();
			return false;
		}		
	}
}
