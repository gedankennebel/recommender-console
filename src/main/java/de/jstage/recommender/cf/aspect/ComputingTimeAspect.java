package de.jstage.recommender.cf.aspect;

import de.jstage.recommender.cf.model.RecommendationResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ComputingTimeAspect {

	@Around("execution(* de.jstage.recommender.cf.controller.RecommendationController.recommendItems*(..))")
	public Object measureTimeWithOverhead(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.nanoTime();
		Object proceed = joinPoint.proceed();
		double calculationTime = (System.nanoTime() - start);
		if (proceed instanceof RecommendationResponse)
			((RecommendationResponse) proceed).setCalculationTime(calculationTime / 1_000_000);
		return proceed;
	}
}
