package de.jstage.recommender.cf.recommender;

public enum SimilarityMetric {
	PEARSON_CORRELATION("Pearson´s correlation coefficient", true, true),
	EUCLIDEAN_DISTANCE("Euclidean distance", true, true),
	TANIMOTO_COEFFICIENT("Jaccard/Tanimoto index", true, true),
	CITY_BLOCK("Manhattan metric", true, true),
	SPEARMAN_CORRELATION("Spearman's rank correlation coefficient", true, false),
	LOGLIKELIHOOD_SIMILARITY("Log-Likelihood function", true, true),
	UNCENTERED_COSINE_SIMILARITY("Uncentered cosine similarity", true, true);

	private final String displayName;
	private final boolean userBasedApproach;
	private final boolean itemBasedApproach;

	SimilarityMetric(String displayName, boolean userBasedApproach, boolean itemBasedApproach) {
		this.displayName = displayName;
		this.userBasedApproach = userBasedApproach;
		this.itemBasedApproach = itemBasedApproach;
	}

	public String getDisplayName() {
		return displayName;
	}

	public boolean isUserBasedApproach() {
		return userBasedApproach;
	}

	public boolean isItemBasedApproach() {
		return itemBasedApproach;
	}
}
