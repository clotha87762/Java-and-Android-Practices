package netdb.courses.softwarestudio.recommender;

import netdb.courses.softwarestudio.recommender.document.Document;
import netdb.courses.softwarestudio.recommender.document.Field;
import netdb.courses.softwarestudio.recommender.document.RamField;
import netdb.courses.softwarestudio.recommender.index.IndexReader;
import netdb.courses.softwarestudio.recommender.index.IndexWriter;
import netdb.courses.softwarestudio.recommender.index.TrieIndexReader;
import netdb.courses.softwarestudio.recommender.index.TrieIndexWriter;
import netdb.courses.softwarestudio.recommender.search.IndexSearcher;
import netdb.courses.softwarestudio.recommender.search.Query;
import netdb.courses.softwarestudio.recommender.search.ScoreDoc;
import netdb.courses.softwarestudio.recommender.search.TermQuery;
import netdb.courses.softwarestudio.recommender.search.TopDocs;
import netdb.courses.softwarestudio.recommender.store.Directory;
import netdb.courses.softwarestudio.recommender.store.RamDirectory;

public class DefSearchApp {

	public static void main(String[] args) throws Exception {
		String queryString = "Java Sun Inc";

		Directory dir = createIndex();
		IndexReader reader = new TrieIndexReader(dir);
		IndexSearcher searcher = new IndexSearcher(reader);

		System.out.println("Search for the query string \"" + queryString
				+ "\" :");

		Query query = new TermQuery(queryString);
		TopDocs tops = searcher.search(query, 10);
		System.out.println("Total hits: " + tops.totalHits());
		System.out.println("ScoreDocs: ");
		for (ScoreDoc sd : tops.scoreDocs()) {
			System.out
					.println("Num: " + sd.docNum() + ", score: " + sd.score());
		}

		searcher.close();
	}

	public static Directory createIndex() throws Exception {
		Directory dir = new RamDirectory();
		IndexWriter writer = new TrieIndexWriter(dir, new DefAnalyzer());
		// add doc 0
		writer.addDocument(createDefDocument("Apache Maven",
				"A Java-based project management software."));
		// add doc 1
		writer.addDocument(createDefDocument("Java",
				"A programming language created by Sun Inc."));
		// add doc 2
		writer.addDocument(createDefDocument(
				"Software Studio",
				"A course that teaches practical Java, OOP, web developement, and web intelligence."));
		// add doc 3
		writer.addDocument(createDefDocument(
				"World Wide Web",
				"Known as WWW or Web. A system of interlinked hypertext documents accessed via the Internet."));
		// add doc 4
		writer.addDocument(createDefDocument("The Java",
				"An programming language created by the Sun Inc."));
		writer.close();
		return dir;
	}

	public static Document createDefDocument(String title, String description) {
		Document doc = new Document();
		// boosts the heading field
		Field f1 = new RamField("title", true, 2.0, title);
		doc.add(f1);
		Field f2 = new RamField("description", description);
		doc.add(f2);
		return doc;
	}

}
