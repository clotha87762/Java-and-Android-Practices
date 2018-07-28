package netdb.courses.softwarestudio.xmlparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Parse the XML file into an XML tree.
 * 
 *
 */
public class XmlParser {
	private String content;
	private int readCursor = 0;

	/**
	 * Construct a <tt>XmlParser</tt> with the specified XML file URL
	 * @param xmlfile	the URL of the XML file
	 */
	public XmlParser(URL xmlfile) {
		try {

			content = readXmlFile(xmlfile);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Read the content of the XML file on the specified URL,
	 * and returns the content with a String 
	 * @param xmlfile	the URL of the specified XML file
	 * @return the content of the XML file
	 * @throws IOException
	 */
	public String readXmlFile(URL xmlfile) throws IOException {
		BufferedReader xmlReader = new BufferedReader(new InputStreamReader(
				xmlfile.openStream()));
		StringBuilder tmp = new StringBuilder("");
		String str;
		while ((str = xmlReader.readLine()) != null) {
			tmp.append(str);
		}
		return tmp.toString();
	}
	

	/**
	 * Parse the content of the XML file into an XML tree, and returns the root node of 
	 * the XML tree
	 * @return the root node of the XML tree parsed from the XML file
	 * @throws Exception
	 */
	public XmlNode xmlToTree() throws Exception {
		XmlNode root = new XmlNode("root");
		root.setContent(content);
		xmlToTree(root);
		return root.children.get(0);
	}
	
	/**
	 * Parse the content of the current <tt>XmlNode</tt>
	 * Recursively parse the XML file and construct the XML tree.
	 * @param now	the XML node currently parsing
	 * @return the parsed XML node
	 * @throws Exception
	 */
	public XmlNode xmlToTree(XmlNode now) throws Exception {
		int tag_start, end_tag_start, content_start = readCursor;

		while (readCursor < content.length() - 1) {

			/**
			 *  set cursor to next '<'
			 */
			setCursorToNextLt(); 

			/**
			 * if '</...'
			 */
			if (content.charAt(readCursor + 1) == '/') {

				if (content_start < readCursor)
					now.setContent(content.substring(content_start + 1,
							readCursor));

				end_tag_start = readCursor + 1;
				setCursorToNextRt();
				String endTag = content
						.substring(end_tag_start + 1, readCursor);

				/**
				 * check the end tag
				 */
				if (now.tagName.equals(endTag)) {
					return now;
				} else {
					throw new Exception("Bad formed XML: mismatching end tag!");
				}
				
			/**
			 * if first line
			 */
			} else if (content.charAt(readCursor + 1) == '?') {
				setCursorToNextRt();
				continue;
				
			/**
			 *  if comment
			 */
			} else if (content.charAt(readCursor + 1) == '!') {
				readCursor = content.indexOf("-->", readCursor) + 3;
				continue;
			}

			/**
			 *  if '< ... >'
			 */
			tag_start = readCursor;
			setCursorToNextRt();
			String tags = content.substring(tag_start + 1, readCursor);
			XmlNode child = addTag(tags, now);

			xmlToTree(child);
		}

		return now;
	}

	/**
	 * Add a child node to the <tt>XmlNode</tt> now with the given tag string
	 * @param tags	the full tag string of the child node
	 * @param now	the current node
	 * @return the added child node with given tag
	 */
	public XmlNode addTag(String tags, XmlNode now) {
		String[] atts = tags.split(" ");
		/*for(int i=0;i<atts.length;i++)
		System.out.print(atts[i]+"/");
		
		System.out.println("");
		System.out.println("");*/
		/**
		 * Add a node as the child of the current XmlNode with the tag name
		 */
		XmlNode child = now.addChild(atts[0]);
		
		/**
		 * TODO 
		 * 
		 * You need to implement the remaining part of the method
		 * to add the attributes of the tag to the <tt>XmlNode</tt>
		 * 
		 * You might want to use the split method in String class to split "="
		 * See the Java API document for more information.
		 * 
		 */
		
		for(int i=1;i<atts.length;i++){
			String[] temp = atts[i].split("=");
			String attribute = temp[0];
			String value = temp[1];
			child.addAttribute(attribute, value);
		}
		
		
		return child;
	}

	/**
	 * Set the readerCursor position to the next less than sign (<) 
	 */
	public void setCursorToNextLt() {
		while (content.charAt(readCursor) != '<') {
			readCursor++;
		}
	}

	/**
	 * Set the readerCursor position to the next greater than sign (>)
	 */
	public void setCursorToNextRt() {
		while (content.charAt(readCursor) != '>') {
			readCursor++;
		}
	}

	/**
	 * @return the content of the current <tt>XmlNode</tt>
	 */
	public String getContent() {
		return this.content;
	}

}
