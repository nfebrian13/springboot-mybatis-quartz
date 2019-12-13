package com.mybatis.app.controller.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mybatis.app.instance.service.impl.SchedulerServiceImpl;
import com.mybatis.app.model.SchedulerJob;

@Controller
@RequestMapping("/scheduler")
public class SchedulerMvcController {

	@Autowired
	private SchedulerServiceImpl service;

	@GetMapping("/create")
	public String create(Model model) {
		return "scheduler/create_scheduler";
	}

	@GetMapping("/list")
	public String listJob(Model model) {
		List<SchedulerJob> listJob = service.schedulerJobMvcList();
		System.out.println("scheduluerJob size: " + listJob.size());
		model.addAttribute("listJobs", listJob);
		return "scheduler/list_scheduler";
	}

}
