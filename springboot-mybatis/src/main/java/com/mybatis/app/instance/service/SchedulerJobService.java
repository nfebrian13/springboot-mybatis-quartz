package com.mybatis.app.instance.service;

import com.mybatis.app.model.SchedulerJobInfo;

public interface SchedulerJobService {

	void startAllSchedulers();
    boolean scheduleNewJob(SchedulerJobInfo jobInfo);
    void updateScheduleJob(SchedulerJobInfo jobInfo);
    boolean unScheduleJob(String jobName);
    boolean deleteJob(SchedulerJobInfo jobInfo);
    boolean pauseJob(SchedulerJobInfo jobInfo);
    boolean resumeJob(SchedulerJobInfo jobInfo);
    boolean startJobNow(SchedulerJobInfo jobInfo);

}
