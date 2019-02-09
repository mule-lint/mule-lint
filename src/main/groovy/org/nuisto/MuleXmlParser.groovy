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
    //"root.children()" gives us the "mule" node
    //We don't perform any checks on that node

    // There are times when the Node could be a text node and in that case, we get a String
    walkThroughEachNode(root, nodeChecker, closure)
  }

  public void walkThroughEachNode(Node node, NodeChecker nodeChecker, Closure closure) {
    closure(new MuleXmlNode(node, nodeChecker))

    node.children().each { it ->
      walkThroughEachNode(it, nodeChecker, closure)
    }
  }

  /**
   * At the time of writing, it was understood you could get either a
   * Node or String object from "root.children()". Instead of having
   * "if (node instanceof Node)" in places, that having the method
   * overridden would give us that information. So if we have a String 'node'
   * then there isn't anything to do.
   *
   * @param node
   * @param nodeChecker
   * @param closure
   */
  public void processEachNode(String node, NodeChecker nodeChecker, Closure closure) {
    //Ignore
  }
}
