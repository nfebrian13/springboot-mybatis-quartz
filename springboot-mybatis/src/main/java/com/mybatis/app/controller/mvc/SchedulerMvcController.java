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

	@RequestMapping(value = "/createprocess", method = RequestMethod.POST)
	public String createProcess(@ModelAttribute SchedulerJob schedulerJob) {
		System.out.println("masuk sini");
		System.out.println(schedulerJob.getJobName());
		return "redirect:/scheduler/list";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listJob(Model model) {
		List<SchedulerJob> listJob = service.schedulerJobMvcList();
		model.addAttribute("listJobs", listJob);
		return "scheduler/list_scheduler";
	}

}
