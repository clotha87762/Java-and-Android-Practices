package netdb.courses.softwarestudio.recommender;

import java.io.Reader;

import netdb.courses.softwarestudio.recommender.analysis.TokenStream;
import netdb.courses.softwarestudio.recommender.document.Document;
import netdb.courses.softwarestudio.recommender.document.Field;
import netdb.courses.softwarestudio.recommender.document.FileField;
import netdb.courses.softwarestudio.recommender.document.RamField;

/**
 * An app that uses the {@link DefAnalyzer} to extract to tokens from a 
 * definition document.
 * 
 * <p>The output of this app may be too long to be fully shown in the console.
 * Increase the console buffer or turn of the line limitation before running
 * this app. For example, in Eclipse you can do this by unchecking the "Limit
 * console output" in Window | Preferences | Run/Debug | Console.</p>
 */
public class DefAnalyzerApp {
	
	public static void main(String[] args) throws Exception {
		Document doc = createDefDocument();
		
		Reader input = doc.getField("description").readerValue();
		TokenStream stream = new DefAnalyzer().tokenStream(input);
		while(stream.incrementToken()) {
			System.out.print(stream.getToken().getTerm() + ", ");
		}
		stream.close();
	}
	
	public static Document createDefDocument() {
		Document doc = new Document();
		Field titleFld = new RamField("title", "Apache Lucene");
		doc.add(titleFld);
		Field descriptionFld = new FileField("description",
				"def-apache_lucene-description.txt");
		doc.add(descriptionFld);
		return doc;
	}
	
}
