package com.mybatis.app.controller.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mybatis.app.instance.service.impl.SchedulerServiceMvcImpl;
import com.mybatis.app.model.SchedulerJob;

@Controller
@RequestMapping("/scheduler")
public class SchedulerMvcController {

	@Autowired
	private SchedulerServiceMvcImpl service;

	@RequestMapping(value = "/job-create", method = RequestMethod.GET)
	public String createJob(Model model) {
		SchedulerJob schedulerJob = new SchedulerJob();
		model.addAttribute("schedulerJob", schedulerJob);
		return "scheduler/scheduler-create";
	}

	@RequestMapping(value = "/createprocess", method = RequestMethod.POST)
	public String createProcess(@ModelAttribute SchedulerJob schedulerJob) {
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

	@RequestMapping(value = "/job-update/{jobName}")
	public String updateJob(Model model, @PathVariable("jobName") String jobName) {

		SchedulerJob schedulerJob = new SchedulerJob();
		schedulerJob.setJobName(jobName);
		model.addAttribute("schedulerJob", schedulerJob);
		return "scheduler/scheduler-update";
	}

	@RequestMapping(value = "/updateprocess", method = RequestMethod.POST)
	public String updateProcess(@ModelAttribute SchedulerJob schedulerJob) {

		if (service.isJobWithNamePresent(schedulerJob.getJobName(), schedulerJob.getGroupName())) {
			boolean status = service.updateOneTimeJob(schedulerJob.getJobName(), schedulerJob.getScheduleTime());
			if (status) {
				return "redirect:/scheduler/job-list";
			}
		} else {
			boolean status = service.updateCronJob(schedulerJob.getJobName(), schedulerJob.getScheduleTime(),
					schedulerJob.getCronExpression());
			if (status) {
				return "redirect:/scheduler/job-list";
			}
		}
		return "redirect:/scheduler/job-list";
	}
}
