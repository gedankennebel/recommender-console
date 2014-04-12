package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.utils.SpliteratorUtil;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserSuggestionService {

	private static final int MAX_SUGGESTIONS = 10;

	@Inject
	private DataModel dataModel;

	public List<String> suggestUsers(final String query) throws TasteException {
		return StreamSupport.stream(getSpliterator(), true)
				.filter(userId -> String.valueOf(userId).contains(query))
				.sorted()
				.limit(MAX_SUGGESTIONS)
				.map(Object::toString)
				.collect(Collectors.toList());
	}

	private Spliterator<Long> getSpliterator() throws TasteException {
		return SpliteratorUtil.getSpliterator(dataModel.getUserIDs());
	}
}
