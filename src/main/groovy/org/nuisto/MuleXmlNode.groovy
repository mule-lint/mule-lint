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

  public boolean isMatch(String name) {
    nodeChecker.isMatch(node, name)
  }

  public boolean hasAttribute(String key) {
    node.attributes().containsKey(key)
  }

  public String getAttribute(String key) {
    node.attribute(key)
  }

  public List<MuleXmlNode>  getSiblings() {
    return node.parent().children().collect {
      new MuleXmlNode(it, nodeChecker)
    }
  }

  public MuleXmlNode getPriorSibling() {
    def siblings = node.parent().children()
    def thisNodeIndex = siblings.indexOf(node)

    Node foundPriorSibling = null

    try {
      foundPriorSibling = siblings[thisNodeIndex - 1]
    }
    catch (Exception) {
      //Ignore
    }

    return new MuleXmlNode(foundPriorSibling, nodeChecker)
  }

  public MuleXmlNode getFollowingSibling() {
    def siblings = node.parent().children()
    def thisNodeIndex = siblings.indexOf(node)

    Node foundFollowingSibling = null

    try {
      foundFollowingSibling = siblings[thisNodeIndex + 1]
    }
    catch (Exception) {
      //Ignore
    }

    return new MuleXmlNode(foundFollowingSibling, nodeChecker)
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
}
