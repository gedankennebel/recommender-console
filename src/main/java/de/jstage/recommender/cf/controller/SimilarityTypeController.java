package de.jstage.recommender.cf.controller;

import de.jstage.recommender.cf.recommender.RecommendationType;
import de.jstage.recommender.cf.recommender.SimilarityMetric;
import de.jstage.recommender.cf.service.SimilarityTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/similarityTyp")
public class SimilarityTypeController {

	@Inject
	private SimilarityTypeService similarityTypeService;

	@RequestMapping(method = RequestMethod.GET)
	public List<SimilarityMetric> getSimilarityList(@RequestParam RecommendationType recommendationType) {
		return similarityTypeService.getSimilarityMetrics(recommendationType);
	}
}
