package de.jstage.recommender.cf.aop;

import de.jstage.recommender.cf.domain.RecommendationResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ComputingTimeAspect {

	private static final Logger log = LoggerFactory.getLogger(ComputingTimeAspect.class);

	public static double dataModelCreationTime;

	// Overhead: switch cases for selecting the desired recommendation and similarity type and
	// time of creating a Recommender instance if not already created and held in map.
	@Around("execution(* de.jstage.recommender.cf.controller.RecommendationController.recommendItems*(..))")
	public Object measureRecommendationComputationTimeWithOverhead(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.nanoTime();
		Object proceed = joinPoint.proceed();
		long end = System.nanoTime();
		if (proceed instanceof RecommendationResponse) {
			double calculationTime = getCalculationTimeInMilliseconds(start, end);
			((RecommendationResponse) proceed).setCalculationTime(calculationTime);
			log.info("Recommendations created in " + calculationTime + "ms");
		}
		return proceed;
	}

	@Around("execution(* de.jstage.recommender.cf.controller.RecommendationController.recommendedBecause*(..))")
	public Object measureRecommendedBecause(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.nanoTime();
		Object proceed = joinPoint.proceed();
		long end = System.nanoTime();
		log.info("Computation of recommendedBecause took " + getCalculationTimeInMilliseconds(start, end) + "ms");
		return proceed;
	}

//	@Around("execution(public * de.jstage.recommender.cf.service.AbstractCfRecommendationService.refresh(..))")
//	public Object measureExplicitRefresh(ProceedingJoinPoint joinPoint) throws Throwable {
//		long start = System.nanoTime();
//		Object proceed = joinPoint.proceed();
//		long end = System.nanoTime();
//		log.info("Explicit refresh took : " + getCalculationTimeInMilliseconds(start, end) + "ms");
//		return proceed;
//	}

//	@Around("execution(public * de.jstage.recommender.cf.service.AbstractCfRecommendationService.autoRefresh())")
//	public Object measurePeriodicallyRefresh(ProceedingJoinPoint joinPoint) throws Throwable {
//		long start = System.nanoTime();
//		Object proceed = joinPoint.proceed();
//		long end = System.nanoTime();
//		log.info("Full periodical refresh took " + getCalculationTimeInMilliseconds(start, end) + "ms");
//		return proceed;
//	}

	@Around("execution(public * de.jstage.recommender.cf.config.MahoutDataModelConfig.*())")
	public Object measureDataModelCreationTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.nanoTime();
		Object proceed = joinPoint.proceed();
		long end = System.nanoTime();
		dataModelCreationTime = getCalculationTimeInSeconds(start, end);
		log.info("DataModel created in " + dataModelCreationTime + " seconds");
		return proceed;
	}

	public static double getCalculationTimeInMilliseconds(long start, long end) {
		double calculationTime = (end - start);
		return (calculationTime / 1_000_000);
	}

	public static double getCalculationTimeInSeconds(long start, long end) {
		double calculationTime = (end - start);
		return (calculationTime / 1_000_000_000);
	}
}
