package netdb.courses.softwarestudio.recommender;

import java.io.Reader;

import netdb.courses.softwarestudio.recommender.analysis.Analyzer;
import netdb.courses.softwarestudio.recommender.analysis.LetterTokenizer;
import netdb.courses.softwarestudio.recommender.analysis.LowerCaseFilter;
import netdb.courses.softwarestudio.recommender.analysis.StemFilter;
import netdb.courses.softwarestudio.recommender.analysis.StopFilter;
import netdb.courses.softwarestudio.recommender.analysis.TokenFilter;
import netdb.courses.softwarestudio.recommender.analysis.TokenStream;
import netdb.courses.softwarestudio.recommender.analysis.Tokenizer;

/**
 * An analyzer of query strings. Note the current implementation does not
 * support advanced query syntax such as "...", *, ?, or ~, etc.
 */
public class TermQueryAnalyzer extends Analyzer {

	@Override
	public TokenStream tokenStream(Reader input) {
        Tokenizer tokenizer = new LetterTokenizer(input);
        TokenFilter lowerCaseFilter = new LowerCaseFilter(tokenizer);
        TokenFilter stopFilter = new StopFilter(lowerCaseFilter);
        TokenFilter stemFilter = new StemFilter(stopFilter);
        return stemFilter;
	}

	
	
	
}
