package com.mybatis.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mybatis.app.dto.ApiResult;
import com.mybatis.app.instance.service.impl.SchedulerServiceImpl;
import com.mybatis.app.model.SchedulerJobInfo;
import com.mybatis.app.util.ResponseResult;

@RestController
@RequestMapping("/job")
public class SchedulerJobController {

	@Autowired
	private SchedulerServiceImpl service;

	/*
	 * Create Job Scheduler
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ApiResult jobInfoCreate(@RequestBody SchedulerJobInfo jobInfo) {

		ApiResult response = new ApiResult();
		String result = ResponseResult.MANDATORY_FIELD;

		response.setData(service.scheduleNewJob(jobInfo));
		response.setStatusCode(200);

		return response;

	}
}
