package de.jstage.recommender.cf.controller;

import de.jstage.recommender.cf.model.ConsoleMetaData;
import de.jstage.recommender.cf.recommendationMisc.AdditionalRecommendationSettings;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@Controller
@RequestMapping("/")
public class HomeController {

	@Inject
	private ConsoleMetaData consoleMetaData;

	@Inject
	private AdditionalRecommendationSettings settings;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home() throws TasteException {
		ModelAndView modelAndView = new ModelAndView("console");
		modelAndView.addObject("consoleMetaData", consoleMetaData);
		modelAndView.addObject("settings", settings);
		return modelAndView;
	}
}
