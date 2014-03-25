package de.jstage.recommender.service;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class RecommenderService {

	@Inject
	ItemBasedRecommender itemBasedRecommender;

	public List<RecommendedItem> getItemBasedRecommendations(long userId, int howMany) throws TasteException {
		return itemBasedRecommender.recommend(userId, howMany);
	}
}
