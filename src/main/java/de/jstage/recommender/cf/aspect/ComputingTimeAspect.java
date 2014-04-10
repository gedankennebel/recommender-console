package de.jstage.recommender.cf.aspect;

import de.jstage.recommender.cf.model.RecommendationResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ComputingTimeAspect {

	public static double dataModelCreationTime;

	// Overhead: switch cases for selecting the desired recommendation and similarity type and
	// time of creating a Recommender instance if not already created and held in map.
	@Around("execution(* de.jstage.recommender.cf.controller.RecommendationController.recommendItems*(..))")
	public Object measureRecommendationComputationTimeWithOverhead(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.nanoTime();
		Object proceed = joinPoint.proceed();
		long end = System.nanoTime();
		if (proceed instanceof RecommendationResponse)
			((RecommendationResponse) proceed).setCalculationTime(getCalculationTimeInMilliseconds(start, end));
		return proceed;
	}

	@Around("execution(* de.jstage.recommender.cf.controller.RecommendationController.recommendedBecause*(..))")
	public Object measureRecommendedBecause(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.nanoTime();
		Object proceed = joinPoint.proceed();
		long end = System.nanoTime();
		System.out.println("Computation of recommendedBecause took " + getCalculationTimeInMilliseconds(start, end) + "ms");
		return proceed;
	}

	@Around("execution(* de.jstage.recommender.cf.config.MahoutDataModelConfig.fileDataModel*())")
	public Object measureDataModelCreationTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.nanoTime();
		Object proceed = joinPoint.proceed();
		long end = System.nanoTime();
		dataModelCreationTime = getCalculationTimeInMilliseconds(start, end);
		return proceed;
	}

	private double getCalculationTimeInMilliseconds(long start, long end) {
		double calculationTime = (end - start);
		return (calculationTime / 1_000_000);
	}
}
