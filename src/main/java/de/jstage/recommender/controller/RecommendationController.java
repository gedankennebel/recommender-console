package de.jstage.recommender.controller;

import de.jstage.recommender.service.RecommenderService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RequestMapping("/recommend")
@RestController
public class RecommendationController {

	@Inject
	RecommenderService recommenderService;

	@RequestMapping(method = RequestMethod.GET)
	public List<RecommendedItem> recommendItems(@RequestParam long userId, @RequestParam int howMany) throws TasteException {
		return recommenderService.getItemBasedRecommendations(userId, howMany);
	}
}
