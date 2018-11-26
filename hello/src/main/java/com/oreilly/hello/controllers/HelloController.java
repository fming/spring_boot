package com.oreilly.hello.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	//@RequestMapping(value="/hello", method= RequestMethod.GET)
	
	@GetMapping("/hello")
	public String sayHello(@RequestParam(value = "name", 
					defaultValue="World") String name, Model model) {
		model.addAttribute("user", name);
		return "hello";
	}

}
