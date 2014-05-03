package de.jstage.recommender.cf.mahout;

import com.google.common.base.Preconditions;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.Cache;
import org.apache.mahout.cf.taste.impl.common.RefreshHelper;
import org.apache.mahout.cf.taste.impl.common.Retriever;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Rescorer;
import org.apache.mahout.common.LongPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CachingItemRecommender implements ItemBasedRecommender {

	private static final Logger log = LoggerFactory.getLogger(CachingItemRecommender.class);

	private final ItemBasedRecommender recommender;
	private final int[] maxHowMany;
	private final Retriever<Long, Recommendations> recommendationsRetriever;
	private final Cache<Long, Recommendations> recommendationCache;
	private final Cache<LongPair, Float> estimatedPrefCache;
	private final RefreshHelper refreshHelper;
	private IDRescorer currentRescorer;

	public CachingItemRecommender(ItemBasedRecommender recommender) throws TasteException {
		Preconditions.checkArgument(recommender != null, "recommender is null");
		this.recommender = recommender;
		maxHowMany = new int[]{1};
		// Use "num users" as an upper limit on cache size. Rough guess.
		int numUsers = recommender.getDataModel().getNumUsers();
		recommendationsRetriever = new RecommendationRetriever();
		recommendationCache = new Cache<>(recommendationsRetriever, numUsers);
		estimatedPrefCache = new Cache<>(new EstimatedPrefRetriever(), numUsers);
		refreshHelper = new RefreshHelper(() -> {
			clear();
			return null;
		});
		refreshHelper.addDependency(recommender);
	}

	private void setCurrentRescorer(IDRescorer rescorer) {
		if (rescorer == null) {
			if (currentRescorer != null) {
				currentRescorer = null;
				clear();
			}
		} else {
			if (!rescorer.equals(currentRescorer)) {
				currentRescorer = rescorer;
				clear();
			}
		}
	}

	@Override
	public List<RecommendedItem> recommend(long userID, int howMany) throws TasteException {
		return recommend(userID, howMany, null);
	}

	@Override
	public List<RecommendedItem> recommend(long userID, int howMany, IDRescorer rescorer) throws TasteException {
		Preconditions.checkArgument(howMany >= 1, "howMany must be at least 1");
		synchronized (maxHowMany) {
			if (howMany > maxHowMany[0]) {
				maxHowMany[0] = howMany;
			}
		}

		// Special case, avoid caching an anonymous user
		if (userID == PlusAnonymousUserDataModel.TEMP_USER_ID) {
			return recommendationsRetriever.get(PlusAnonymousUserDataModel.TEMP_USER_ID).getItems();
		}

		setCurrentRescorer(rescorer);

		Recommendations recommendations = recommendationCache.get(userID);
		if (recommendations.getItems().size() < howMany && !recommendations.isNoMoreRecommendableItems()) {
			clear(userID);
			recommendations = recommendationCache.get(userID);
			if (recommendations.getItems().size() < howMany) {
				recommendations.setNoMoreRecommendableItems(true);
			}
		}

		List<RecommendedItem> recommendedItems = recommendations.getItems();
		return recommendedItems.size() > howMany ? recommendedItems.subList(0, howMany) : recommendedItems;
	}

	@Override
	public float estimatePreference(long userID, long itemID) throws TasteException {
		return estimatedPrefCache.get(new LongPair(userID, itemID));
	}

	@Override
	public void setPreference(long userID, long itemID, float value) throws TasteException {
		recommender.setPreference(userID, itemID, value);
		clear(userID);
	}

	@Override
	public void removePreference(long userID, long itemID) throws TasteException {
		recommender.removePreference(userID, itemID);
		clear(userID);
	}

	@Override
	public DataModel getDataModel() {
		return recommender.getDataModel();
	}

	@Override
	public void refresh(Collection<Refreshable> alreadyRefreshed) {
		refreshHelper.refresh(alreadyRefreshed);
	}

	/**
	 * <p>
	 * Clears cached recommendations for the given user.
	 * </p>
	 *
	 * @param userID clear cached data associated with this user ID
	 */
	public void clear(final long userID) {
		log.debug("Clearing recommendations for user ID '{}'", userID);
		recommendationCache.remove(userID);
		estimatedPrefCache.removeKeysMatching(userItemPair -> userItemPair.getFirst() == userID);
	}

	/**
	 * <p>
	 * Clears all cached recommendations.
	 * </p>
	 */
	public void clear() {
		log.debug("Clearing all recommendations...");
		recommendationCache.clear();
		estimatedPrefCache.clear();
	}

	@Override
	public String toString() {
		return "CachingRecommender[recommender:" + recommender + ']';
	}

	@Override
	public List<RecommendedItem> mostSimilarItems(long itemID, int howMany) throws TasteException {
		return mostSimilarItems(itemID, howMany);
	}

	@Override
	public List<RecommendedItem> mostSimilarItems(long itemID, int howMany, Rescorer<LongPair> rescorer) throws TasteException {
		return recommender.mostSimilarItems(itemID, howMany, rescorer);
	}

	@Override
	public List<RecommendedItem> mostSimilarItems(long[] itemIDs, int howMany) throws TasteException {
		return recommender.mostSimilarItems(itemIDs, howMany);
	}

	@Override
	public List<RecommendedItem> mostSimilarItems(long[] itemIDs, int howMany, Rescorer<LongPair> rescorer) throws TasteException {
		return recommender.mostSimilarItems(itemIDs, howMany, rescorer);
	}

	@Override
	public List<RecommendedItem> mostSimilarItems(long[] itemIDs, int howMany, boolean excludeItemIfNotSimilarToAll) throws TasteException {
		return recommender.mostSimilarItems(itemIDs, howMany, excludeItemIfNotSimilarToAll);
	}

	@Override
	public List<RecommendedItem> mostSimilarItems(long[] itemIDs, int howMany, Rescorer<LongPair> rescorer, boolean excludeItemIfNotSimilarToAll) throws TasteException {
		return recommender.mostSimilarItems(itemIDs, howMany, rescorer, excludeItemIfNotSimilarToAll);
	}

	@Override
	public List<RecommendedItem> recommendedBecause(long userID, long itemID, int howMany) throws TasteException {
		return recommender.recommendedBecause(userID, itemID, howMany);
	}

	private final class EstimatedPrefRetriever implements Retriever<LongPair, Float> {
		@Override
		public Float get(LongPair key) throws TasteException {
			long userID = key.getFirst();
			long itemID = key.getSecond();
			log.debug("Retrieving estimated preference for user ID '{}' and item ID '{}'", userID, itemID);
			return recommender.estimatePreference(userID, itemID);
		}
	}

	private final class RecommendationRetriever implements Retriever<Long, Recommendations> {
		@Override
		public Recommendations get(Long key) throws TasteException {
			log.debug("Retrieving new recommendations for user ID '{}'", key);
			int howMany = maxHowMany[0];
			IDRescorer rescorer = currentRescorer;
			List<RecommendedItem> recommendations =
					rescorer == null ? recommender.recommend(key, howMany) : recommender.recommend(key, howMany, rescorer);
			return new Recommendations(Collections.unmodifiableList(recommendations));
		}
	}

	private static final class Recommendations {

		private final List<RecommendedItem> items;
		private boolean noMoreRecommendableItems;

		private Recommendations(List<RecommendedItem> items) {
			this.items = items;
		}

		List<RecommendedItem> getItems() {
			return items;
		}

		boolean isNoMoreRecommendableItems() {
			return noMoreRecommendableItems;
		}

		void setNoMoreRecommendableItems(boolean noMoreRecommendableItems) {
			this.noMoreRecommendableItems = noMoreRecommendableItems;
		}
	}
}
