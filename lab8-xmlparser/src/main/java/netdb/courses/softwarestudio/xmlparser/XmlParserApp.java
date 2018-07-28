package netdb.courses.softwarestudio.xmlparser;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * The main class. 
 * Fetch an XML file, parse it into an XML tree, and print the formatted version of 
 * the XML file content.
 *
 */
public class XmlParserApp {
	
	public static void main(String[] args) throws Exception{
		try {
			XmlParser xps = new XmlParser(new URL("http://shwu07.cs.nthu.edu.tw/catalog.xml"));
			XmlNode root = xps.xmlToTree();
			XmlTree xmlTree = new XmlTree(root);
			System.out.println(xmlTree);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			throw new Exception("haha");
		}

	}

}
