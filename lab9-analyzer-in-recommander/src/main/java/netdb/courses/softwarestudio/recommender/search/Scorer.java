package netdb.courses.softwarestudio.recommender.search;

import java.util.List;

import netdb.courses.softwarestudio.recommender.index.IndexAccessException;
import netdb.courses.softwarestudio.recommender.index.IndexReader;


/**
 * Scores docs in an index based on docs' relevance to a {@link Query}. 
 * The purpose of this class is to separate the states of scoring (e.g., index 
 * reading) from query to to make query reusable.
 * 
 */
public abstract class Scorer {
	protected IndexReader reader;
	
	public Scorer(IndexReader reader) throws IndexAccessException {
		this.reader = reader;
	}
	
	/**
	 * Scores docs that hit the query.
	 * 
	 * @return hits ordered by their scores in descending order 
	 * @throws IndexAccessException
	 */
	public abstract List<ScoreDoc> score() throws IndexAccessException;
	
}
