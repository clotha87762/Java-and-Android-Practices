package netdb.courses.softwarestudio.recommender.analysis;

/**
 * Normalizes token text to lower case.
 */
public final class LowerCaseFilter extends TokenFilter {

	public LowerCaseFilter(TokenStream input) {
		super(input);
	}
	
	/**
	 * FIXME Current implementation causes excessive creation of String objects.
	 * Avoided this using char[].
	 */
	@Override
	public final boolean incrementToken() throws Exception {
		if(input.incrementToken()) {
			
			char[] termBuff = token.getTermBuffer();
			int termLength = token.getTermLength();
			
			for(int i=0; i < termLength; i++){
				termBuff[i] = Character.toLowerCase(termBuff[i]);
			}
			
			return true;
		} else
			return false;
	}
}
