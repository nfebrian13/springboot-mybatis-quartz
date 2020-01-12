package com.mybatis.app.controller.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mybatis.app.instance.service.impl.SchedulerServiceMvcImpl;
import com.mybatis.app.model.SchedulerJob;

@Controller
@RequestMapping("/scheduler")
public class SchedulerMvcController {

	@Autowired
	private SchedulerServiceMvcImpl service;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		SchedulerJob schedulerJob = new SchedulerJob();
		model.addAttribute("schedulerJob", schedulerJob);
		return "scheduler/create_scheduler";
	}
	
	@RequestMapping(value = "/job-create", method = RequestMethod.GET)
	public String createJob(Model model) {
		SchedulerJob schedulerJob = new SchedulerJob();
		model.addAttribute("schedulerJob", schedulerJob);
		return "scheduler/scheduler-create";
	}

	@RequestMapping(value = "/createprocess", method = RequestMethod.POST)
	public String createProcess(@ModelAttribute SchedulerJob schedulerJob) {
		System.out.println(schedulerJob.getJobName());
		System.out.println(schedulerJob.getJobClass());
		System.out.println(schedulerJob.getGroupName());
		System.out.println(schedulerJob.getCronExpression());
		System.out.println(schedulerJob.getRepeatTime());
		System.out.println(schedulerJob.isCronJob());
		
		SchedulerJob obj = new SchedulerJob();
		obj.setJobName(schedulerJob.getJobName());
		obj.setJobClass(schedulerJob.getJobClass());
		obj.setGroupName(schedulerJob.getGroupName());
		obj.setCronExpression(schedulerJob.getCronExpression());
		obj.setRepeatTime(schedulerJob.getRepeatTime());
		obj.setCronJob(schedulerJob.isCronJob());

		boolean createJob = service.createJob(obj);
		System.out.println("createJob " + createJob);
		
		return "redirect:/scheduler/job-list";
	}

	@RequestMapping(value = "/job-list", method = RequestMethod.GET)
	public String JobList(Model model) {
		List<SchedulerJob> listJob = service.schedulerJobMvcList();
		model.addAttribute("listJobs", listJob);
		return "scheduler/scheduler-list";
	}

}
