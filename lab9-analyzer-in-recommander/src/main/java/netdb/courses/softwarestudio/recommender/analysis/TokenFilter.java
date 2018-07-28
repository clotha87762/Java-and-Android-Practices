package netdb.courses.softwarestudio.recommender.analysis;


/**
 * A {@link TokenStream} that filters/modifies tokens from another 
 * {@link TokenStream}. Works at token level.  
 */
public abstract class TokenFilter extends TokenStream {
	protected TokenStream input;
	
	protected TokenFilter(TokenStream input) {
		super();
		this.input = input;
	}
	
	@Override
	public void close() throws Exception {
		input.close();
	}
}
