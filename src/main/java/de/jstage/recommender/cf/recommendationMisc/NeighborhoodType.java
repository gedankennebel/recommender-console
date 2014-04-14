package de.jstage.recommender.cf.recommendationMisc;

public enum NeighborhoodType {

	NEAREST_N("Fixed-size neighborhood"),
	THRESHOLD("Threshold-based neighborhood");

	private final String displayName;

	NeighborhoodType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
