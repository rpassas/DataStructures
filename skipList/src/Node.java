public class Node {
  int data;
  Node right;
  Node up;

  /**
   * Constructors for nodes in the skip list (may point to null or to nodes).
   */
  public Node(int data){
    this.data = data;
    this.right = null;
    this.up = null;
  }

  public Node(int data, Node rt, Node up){
    this.data = data;
    this.right = rt;
    this.up = up;
  }
}
