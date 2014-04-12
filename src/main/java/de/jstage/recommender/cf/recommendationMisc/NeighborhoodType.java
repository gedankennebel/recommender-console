package de.jstage.recommender.cf.recommendationMisc;

public enum NeighborhoodType {

	NEAREST_N("nearest N"),
	THRESHOLD("threshold");

	private final String displayName;

	NeighborhoodType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
