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
 * An {@link Analyzer} implementation specialized for the definition objects
 * having the "title" and "description" fields.
 */
public class DefAnalyzer extends Analyzer {

	@Override
	public TokenStream tokenStream(Reader input) {
		//Decorator pattern
		Tokenizer tokenizer = new LetterTokenizer(input);
		TokenFilter lowerCaseFilter = new LowerCaseFilter(tokenizer);
		TokenFilter stopFilter = new StopFilter(lowerCaseFilter);
		TokenFilter stemFilter = new StemFilter(stopFilter);
		
		return stemFilter;
	}

}
