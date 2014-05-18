package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.aop.ComputingTimeAspect;
import de.jstage.recommender.cf.domain.ConsoleMetaData;
import de.jstage.recommender.cf.domain.User;
import de.jstage.recommender.cf.enums.RecommendationType;
import de.jstage.recommender.cf.utils.SpliteratorUtil;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ConsoleMetaDataService {

	@Inject
	private DataModel dataModel;

	private ConsoleMetaData consoleMetaData;

	public ConsoleMetaData getConsoleMetaData() {
		return consoleMetaData;
	}

	@PostConstruct
	public void createConsoleMetaData() throws TasteException {
		consoleMetaData = new ConsoleMetaData(getRecommendationTypes(), getMax100Users(),
				getNumberOfUsers(), getNuberOfItem(), ComputingTimeAspect.dataModelCreationTime);
	}

	private List<RecommendationType> getRecommendationTypes() {
		return Arrays.asList(RecommendationType.values());
	}

	private List<User> getMax100Users() throws TasteException {
		final int MAX_USERS = 100;
		return StreamSupport.stream(getSpliterator(), true)
				.limit(MAX_USERS)
				.map(User::new)
				.collect(Collectors.toList());
	}

	private Spliterator<Long> getSpliterator() throws TasteException {
		return SpliteratorUtil.getSpliterator(dataModel.getUserIDs());
	}

	private int getNumberOfUsers() throws TasteException {
		return dataModel.getNumUsers();
	}

	private int getNuberOfItem() throws TasteException {
		return dataModel.getNumItems();
	}
}
