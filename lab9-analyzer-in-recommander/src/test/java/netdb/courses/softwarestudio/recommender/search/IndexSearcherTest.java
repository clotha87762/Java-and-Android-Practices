package netdb.courses.softwarestudio.recommender.search;

import static org.junit.Assert.assertEquals;

import netdb.courses.softwarestudio.recommender.DefAnalyzer;
import netdb.courses.softwarestudio.recommender.document.Document;
import netdb.courses.softwarestudio.recommender.document.Field;
import netdb.courses.softwarestudio.recommender.document.RamField;
import netdb.courses.softwarestudio.recommender.index.IndexAccessException;
import netdb.courses.softwarestudio.recommender.index.IndexReader;
import netdb.courses.softwarestudio.recommender.index.IndexWriter;
import netdb.courses.softwarestudio.recommender.index.TrieIndexReader;
import netdb.courses.softwarestudio.recommender.index.TrieIndexWriter;
import netdb.courses.softwarestudio.recommender.search.IndexSearcher;
import netdb.courses.softwarestudio.recommender.search.MoreLikeThisQuery;
import netdb.courses.softwarestudio.recommender.search.Query;
import netdb.courses.softwarestudio.recommender.search.ScoreDoc;
import netdb.courses.softwarestudio.recommender.search.SingleFieldSortComparator;
import netdb.courses.softwarestudio.recommender.search.TopDocs;
import netdb.courses.softwarestudio.recommender.store.Directory;
import netdb.courses.softwarestudio.recommender.store.RamDirectory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for {@link IndexSearcher}.
 */
public class IndexSearcherTest {
	Directory dir;
	IndexReader reader;
	IndexSearcher searcher;
	
	@Before
	public void startup() throws Exception {
		Directory dir = new RamDirectory();
		IndexWriter writer = new TrieIndexWriter(dir, new DefAnalyzer());
		writer.addDocument(createWikiDocument("Java", 
				"The java book."));
		writer.addDocument(createWikiDocument("Software Studio", 
				"A java course."));
		writer.addDocument(createWikiDocument("Book", 
				"This is a book."));
		writer.close();
		
		reader = new TrieIndexReader(dir);
		searcher = new IndexSearcher(reader);
	}
	
	public static Document createWikiDocument(String firstHeading, 
			String bodyContent) {
		Document doc = new Document();
		// boosts the heading field
		Field f1 = new RamField("firstHeading", true, 2.0, firstHeading);
		doc.add(f1);
		Field f2 = new RamField("bodyContent", bodyContent);
		doc.add(f2);
		return doc;
	}
	
	@After
	public void teardown() throws IndexAccessException {
		searcher.close();
	}
	
	@Test
	public void testSeachByRelevance() throws IllegalStateException, 
			IndexAccessException {
		int refNum = 0;
		System.out.println("Finding recommendations for doc " + refNum 
				+ ", ordered by relevance...");
		Query query = new MoreLikeThisQuery(refNum);
		TopDocs tops = searcher.search(query, 10);
		System.out.println("Total hits: " + tops.totalHits());
		System.out.println("ScoreDocs: ");
		for(ScoreDoc sd : tops.scoreDocs()) {
			System.out.println("Num: " + sd.docNum() + ", score: " 
					+ sd.score());
		}
		assertEquals(tops.totalHits(), 2);
		assertEquals( 
				(1 / Math.sqrt(10)) * Math.log(3.0 / 2) * Math.log(3.0 / 2),
				tops.scoreDocs().get(0).score(), 1e-6);
		assertEquals( 
				0.3 * Math.log(3.0 / 2) * Math.log(3.0 / 2),
				tops.scoreDocs().get(1).score(),1e-6);
	}
	
	@Test
	public void testSeachByField() throws IllegalStateException, 
			IndexAccessException {
		int refNum = 0;
		System.out.println("Finding recommendations for doc " + refNum 
				+ ", ordered by field...");
		Query query = new MoreLikeThisQuery(refNum);
		String sortField = "firstHeading";
		TopDocs tops = searcher.search(query, 10,
				new SingleFieldSortComparator(reader, sortField, false));
		System.out.println("Total hits: " + tops.totalHits());
		System.out.println("ScoreDocs: ");
		for(ScoreDoc sd : tops.scoreDocs()) {
			System.out.println("Num: " + sd.docNum() + ", score: " 
					+ sd.score());
		}
		assertEquals(tops.totalHits(), 2);
		assertEquals( 
				0.3 * Math.log(3.0 / 2) * Math.log(3.0 / 2),
				tops.scoreDocs().get(0).score(), 1e-6);
		assertEquals( 
				(1 / Math.sqrt(10)) * Math.log(3.0 / 2) * Math.log(3.0 / 2),
				tops.scoreDocs().get(1).score(), 1e-6);
	}
	
}
