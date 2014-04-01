package de.jstage.recommender.cf.recommender;

public enum RecommendationType {
	ITEM_BASED("item-based"),
	USER_BASED("user-based");

	private final String displayName;

	RecommendationType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
