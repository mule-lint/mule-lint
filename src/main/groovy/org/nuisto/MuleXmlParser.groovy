package org.nuisto

/**
 * NOTE I *think* the root Node should not change (immutable)
 */
class MuleXmlParser {

  String filePath
  NodeChecker nodeChecker
  Node root

  public MuleXmlParser(String filePath) {

    //TODO Is the "foundNamespaces" populated after the "parser.parse"?
    //I can't remember. I do remember it being funky how I dug into the code to figure it out
    Map<String, String> foundNamespaces = new Hashtable<String, String>()
    def parser = new PeakNamespacesXmlParser(foundNamespaces)

    this.filePath = filePath

    root = parser.parse(filePath)

    nodeChecker = new NodeChecker(foundNamespaces)
  }

  public boolean isMuleFile() {
    nodeChecker.isRoot(root)
  }

  public void forEachMuleNode(Closure closure) {
    root.children().each { it ->
      // There are times when the Node could be a text node and in that case, we get a String
      if (it instanceof Node) closure(new MuleXmlNode(it, nodeChecker))
    }
  }
}
