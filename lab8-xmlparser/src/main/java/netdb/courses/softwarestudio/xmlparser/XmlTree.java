package netdb.courses.softwarestudio.xmlparser;

/**
 * An XML tree structure. Stores the root node of the XML tree.
 *
 */
public class XmlTree {
	private XmlNode root;
	
	/**
	 * Constructs a <tt>XmlTree</tt> with a default root node named root
	 */
	public XmlTree(){
		this.root = new XmlNode("root");
	}
	
	/**
	 * Constructs a <tt>XmlTree</tt> with the specified root node
	 * @param root	the specified root node
	 */
	public XmlTree(XmlNode root){
		this.root = root;
	}

	/**
	 * Returns the root node of the tree
	 */
	public XmlNode getRoot() {
		return this.root;
	}
	
	/**
	 * Returns the formatted String that represents the <tt>XmlTree</tt>, including every 
	 * <tt>XmlNode</tt> in the tree. i.e. from the root node to leaf node
	 * 
	 * Example:
	 *  <pre><note id="154"><body>Hello</body></note></pre>
	 * 
	 * Output: 
	 *  <pre>
	 *  	<note id="154">	
	 * 			<body>Hello</body>
	 * 		</note>
	 * </pre>
	 */
	
	@Override
	public String toString() {
		return root.toString();
	}
}
