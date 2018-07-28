package netdb.courses.softwarestudio.xmlparser;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * Represents a single XML node.
 * Stores the tag name and attribute name-value mapping.
 *
 */
class XmlNode {
	
	/**
	 * Tag name of the <tt>XmlNode</tt>
	 */
	String tagName;
	
	/**
	 * The content in the <tt>XmlNode</tt> in String format, will be used by XmlParser to
	 * construct the XmlTree
	 */
	String content;
	
	/**
	 * Store the children nodes of the current <tt>XmlNode</tt>
	 */
	ArrayList<XmlNode> children = new ArrayList<XmlNode>();
	
	/**
	 * Store the attribute name to value mappings
	 */
	Map<String, String> attributes = new TreeMap<String, String>();

	/**
	 * Construct a <tt>XmlNode</tt> with the specified tagName
	 * @param tagName	the tag name of this <tt>XmlNode</tt>
	 */
	public XmlNode(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * Set the content of this <tt>XmlNode</tt> as the specified content
	 * @param content	the specified content string
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Add a child to this <tt>XmlNode</tt>
	 * @param name	the tag name of the child node
	 * @return the added child node
	 */
	public XmlNode addChild(String name) {
		XmlNode child = new XmlNode(name);
		children.add(child);
		return child;
	}

	/**
	 * Add an attribute to this <tt>XmlNode</tt>
	 * @param name	name of the attribute
	 * @param value	value of the attribute
	 */
	public void addAttribute(String name, String value) {
		attributes.put(name, value);
	}
	
	/**
	 * Returns the formatted string represents the subtree.
	 */
	public String toString(){
		// Start from level = 0
		return toString(0);
	}
	
	/**
	 * Traverse the tree in a depth-first approach, extract the tag name and attributes
	 * of each <tt>XmlNode</tt>, and construct the formatted string.
	 * 
	 * @param level	the current level in the traversal process
	 * @return		the formatted string
	 */
	public String toString(int level){
		
		StringBuilder r = new StringBuilder(100);
		for(int i=0;i<level;i++)
			r.append('\t');
		r.append('<'+this.tagName);
		
		int i;
		
			
			for(Map.Entry<String, String> entry :attributes.entrySet()){
				r.append(" ");
				r.append(entry.getKey());
				r.append("=");
				r.append(entry.getValue());
			}
		
		/**
		 * TODO
		 * Go through the attributes map and append the attributes here
		 */
		r.append('>');
		//ArrayList<XmlNode> thisChild = new ArrayList<XmlNode>();
		if(children.size()!=0){
			r.append('\n');
			
			
			//System.out.println("yaa");
			int j;
			for(j=0;j<children.size();j++){
			//System.out.println("ybb");
				String temp;
				r.append(children.get(j).toString(level+1));
				r.append("\n");
				
			}
		
			/**
			 * TODO
			 * 
			 * Try to use a depth-first approach to traverse through the entire <tt>XmlTree</tt>,
			 * extract the tag name, and values of each child <tt>XmlNode</tt>
			 * 
			 * You might want to use the level parameter to help you format your string
			 * e.g. the number of indents of each line
			 */
		} else
			r.append(this.content);
		if(children.size()!=0)
		for(int k=0;k<level;k++)r.append("\t");
		
		r.append("</"+this.tagName+">");
		return r.toString();
	}
}