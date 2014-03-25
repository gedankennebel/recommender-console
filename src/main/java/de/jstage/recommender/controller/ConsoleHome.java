package de.jstage.recommender.controller;

import de.jstage.recommender.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

@Controller
@RequestMapping("/")
public class ConsoleHome {

	@Inject
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String home(ModelMap model) {
		return "hello";
	}
}
