package netdb.courses.softwarestudio.recommender.analysis;

import java.util.HashSet;
import java.util.Set;

import netdb.courses.softwarestudio.recommender.util.CharArraySet;

public class BadStopFilter extends TokenFilter {

	private final Set<String> stopSet;

	public BadStopFilter(TokenStream input, String... stopWords) {
		super(input);
		this.stopSet = makeStopSet(stopWords == null || stopWords.length == 0 ? 
				getDefaultStopWords() : stopWords);
	}

	protected Set<String> makeStopSet(String... stopWords) {
		Set<String> set = new HashSet<String>(stopWords.length);
		for(String w : stopWords) {
			if(w != null) set.add(w);
		}
		return set;
	}
	
	protected String[] getDefaultStopWords() {
		return new String[] { "a", "an", "and", "are", "as", "at", "be", "but",
				"by", "for", "if", "in", "into", "is", "it", "no", "not", "of",
				"on", "or", "s", "such", "that", "the", "their", "then",
				"there", "these", "they", "this", "to", "was", "will", "with" };
	}

	/**
	 * Returns the next input Token whose term is not a stop word.
	 * 
	 * <p>FIXME Current implementation causes excessive creation of String 
	 * objects. Avoid this using {@link CharArraySet}.</P>
	 */
	@Override
	public final boolean incrementToken() throws Exception {
		while(input.incrementToken()) {
			String term = token.getTerm();
			if(!stopSet.contains(term))
				return true;
			// else, the loop continues
		}
		// end of stream
		return false;
	}
}
