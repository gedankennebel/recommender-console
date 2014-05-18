package de.jstage.recommender.cf.enums;

public enum EvaluatorType {

	AVERAGE_ABSOLUTE_DIFFERENCE("Average absolute difference Evaluator"),
	ROOT_MEAN_SQUARE("Root mean square Evaluator");

	private final String displayName;

	EvaluatorType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
