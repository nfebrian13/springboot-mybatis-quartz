package com.mybatis.app.instance.service;

import java.util.List;

import com.mybatis.app.model.SchedulerJob;

public interface SchedulerMvcService {

    List<SchedulerJob> schedulerJobMvcList();
    String getJobState(String jobName, String groupKey);

}
