package de.jstage.recommender.cf.recommender;

public enum RecommendationType {
	ITEM_BASED("Item-based Recommender"),
	USER_BASED("User-based Recommender");

	private final String displayName;

	RecommendationType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
