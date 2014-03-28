package de.jstage.recommender.cf.service;

import de.jstage.recommender.cf.model.ConsoleMetaData;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ConsoleService {

	@Inject
	ConsoleMetaData recommendationTypes;

}
