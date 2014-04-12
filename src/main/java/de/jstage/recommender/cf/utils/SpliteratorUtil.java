package de.jstage.recommender.cf.utils;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorUtil {

	public static <T> Spliterator<T> getSpliterator(Iterator<T> iterator) {
		return Spliterators.spliteratorUnknownSize(iterator, 0);
	}
}
