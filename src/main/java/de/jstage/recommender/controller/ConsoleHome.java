package de.jstage.recommender.controller;

import de.jstage.recommender.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@Controller
@RequestMapping("/")
public class ConsoleHome {

	@Inject
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("console");
		modelAndView.addObject("userList", userService.getAllUsers());
		return modelAndView;
	}
}
