/*
node has:
key, degree
parent, sibling, child
children > parents
prev degree < next degree
 */

public class Node {
  private Tuple data;
  private Node parent;
  private Node sibling;
  private Node child;

  Node(int key, int degree) {
    data = new Tuple(key, degree);
    child = null;
    sibling = null;
    parent = null;
  }

  public Node getChild() {
    return this.child;
  }

  public void setChild(Node n) {
    this.child = n;
  }

  public Node getSibling() {
    return this.sibling;
  }

  public void setSibling(Node n) {
    this.sibling = n;
  }

  public Node getParent() {
    return this.parent;
  }

  public void setParent(Node n) {
    this.parent = n;
  }

  public int getKey(){
    return data.getKey();
  }

  public void setKey(int k){
    data.updateKey(k);
  }

  public int getDegree(){
    return data.getDegree();
  }

  public void setDegree(int d){
    data.setDegree(d);
  }

}
