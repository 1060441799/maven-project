package com.newframe.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GreetingController {

	private SimpMessagingTemplate template;

	@Autowired
	public GreetingController(SimpMessagingTemplate template) {
		this.template = template;
	}

	@RequestMapping(path = "/greetings")
	public void greeting(String message) throws Exception {
		System.out.println("aaaaaaaaaaaaaaaaaaa");
		String test = "[" + System.currentTimeMillis() + "]:" + message;
		this.template.convertAndSend("/topic/greetings", test);
	}

}
