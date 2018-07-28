package netdb.courses.softwarestudio.recommender.analysis;

/**
 * Normalizes token text to lower case.
 */
public final class BadLowerCaseFilter extends TokenFilter {

	public BadLowerCaseFilter(TokenStream input) {
		super(input);
	}
	
	/**
	 * FIXME Current implementation causes excessive creation of String objects.
	 * Avoided this using char[].
	 */
	@Override
	public final boolean incrementToken() throws Exception {
		if(input.incrementToken()) {
			String lowerTerm = token.getTerm().toLowerCase();
			token.setTermBuffer(lowerTerm.toCharArray(), 0, lowerTerm.length());
			return true;
		} else
			return false;
	}
}
