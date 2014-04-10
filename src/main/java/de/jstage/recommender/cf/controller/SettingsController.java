package de.jstage.recommender.cf.controller;

import de.jstage.recommender.cf.recommendationMisc.AdditionalRecommendationSettings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;

@Controller
@RequestMapping("/settings")
public class SettingsController {

	@Inject
	private AdditionalRecommendationSettings settings;

	@RequestMapping(value = "/because", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void setNumberOfRecommendedBecause(@RequestParam int howManyBecause) {
		if (howManyBecause < 1)
			throw new IllegalArgumentException();
		settings.setNumberOfRecommendedBecause(howManyBecause);
	}
}
