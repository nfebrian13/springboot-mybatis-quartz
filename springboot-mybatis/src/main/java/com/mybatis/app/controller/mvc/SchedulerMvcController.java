package com.mybatis.app.controller.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mybatis.app.instance.service.impl.SchedulerServiceImpl;
import com.mybatis.app.model.SchedulerJob;

@Controller
public class SchedulerMvcController {

	@Autowired
	private SchedulerServiceImpl service;

	@GetMapping("/schedulerlist")
	public String listJob(Model model) {
		List<SchedulerJob> listJob = service.schedulerJobMvcList();
		System.out.println("scheduluerJob size: " + listJob.size());
		model.addAttribute("listJobs", listJob);
		return "scheduler/list_scheduler";
	}
}
