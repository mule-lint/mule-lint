package org.nuisto

class MuleXmlNode {
  Node node
  NodeChecker nodeChecker

  public MuleXmlNode(Node node, NodeChecker nodeChecker) {
    this.node = node
    this.nodeChecker = nodeChecker
  }

  public MuleXmlNode(String node) {

  }

  public String getName() {
    node.name()
  }

  /**
   * This is meant to check for a flow or sub-flow node
   * @return
   */
  public boolean isFlowesque() {
    isMatch('flow') || isMatch('sub-flow')
  }

  public boolean isMatch(String name) {
    nodeChecker.isMatch(node, name)
  }

  public boolean hasAttribute(String key) {
    node.attributes().any {
      nodeChecker.isAttributeMatch(it.key, key)
    }
  }

  public String getAttribute(String key) {
    node.attribute(key)
  }

  public List<MuleXmlNode> getSiblings() {
    return node.parent().children().collect {
      new MuleXmlNode(it, nodeChecker)
    }
  }

  public MuleXmlNode getPriorSibling() {
    getSiblingWithOffset(-1)
  }

  public MuleXmlNode getFollowingSibling() {
    getSiblingWithOffset(1)
  }

  private MuleXmlNode getSiblingWithOffset(int index) {
    def siblings = node.parent().children()
    def thisNodeIndex = siblings.indexOf(node)

    try {
      // Use .get() instead of [] to force an exception instead of a null value
      // TODO Should I use the Null Object Pattern here?
      // If we use the Null Object Pattern - I think it would solely be restricted to within this class
      Node foundSibling = siblings.get(thisNodeIndex + index)

      return new MuleXmlNode(foundSibling, nodeChecker)
    }
    catch (Exception) {
      //Ignore
    }

    return null
  }


  //TODO Maybe instead have a way to check its parent
  //boolean hasParent(String parentName)
  public MuleXmlNode getParent() {
    new MuleXmlNode(node.parent(), nodeChecker)
  }

  public int getLineNumber() {
    //The _msaLineNumber is set in the "PeakNamespacesXmlParser" class
    String lineNumber = node.@_msaLineNumber
    lineNumber == null ? -1 : lineNumber.toInteger()
  }

  String toString() {
    return "lineNumber: ${lineNumber}, name: ${name}, flowesque: ${isFlowesque()}"
  }
}
