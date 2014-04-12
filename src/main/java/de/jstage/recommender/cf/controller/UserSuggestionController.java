package de.jstage.recommender.cf.controller;

import de.jstage.recommender.cf.service.UserSuggestionService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserSuggestionController {

	@Inject
	private UserSuggestionService userSuggestionService;

	@RequestMapping(value = "/suggest", method = RequestMethod.GET)
	public List<String> suggestUsers(@RequestParam String term) throws TasteException {
		return userSuggestionService.suggestUsers(term);
	}
}
