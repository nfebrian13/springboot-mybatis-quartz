package com.mybatis.app.instance.job;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
public class SampleCronJob extends QuartzJobBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleCronJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		System.out.println();
		System.out.println("--------------------------Start SampleCronJob-------------------------------------");
		System.out.println("");

		JobKey key = context.getJobDetail().getKey();
		System.out.println("Cron Job started with key .....");
		System.out.println("Name					:" + key.getName());
		System.out.println("Group 					:" + key.getGroup());
		System.out.println("Date 					:" + new Date());
		System.out.println("Thread Name 			:" + Thread.currentThread().getName());

		JobDataMap dataMap = context.getMergedJobDataMap();
		String myValue = dataMap.getString("myKey");

		System.out.println("Value					:" + myValue);
		System.out.println("Thread					: " + Thread.currentThread().getName() + " stopped.");
		System.out.println("--------------------------End SampleCronJob-------------------------------------");
		System.out.println();
	}

}
