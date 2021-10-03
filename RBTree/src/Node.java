public class Node {
  private final boolean RED = false;
  private final boolean BLACK = true;
  private boolean color;
  private Node left;
  private Node right;
  private Node parent;
  private int key;

  public Node(int key){
    //initially red
    this.key = key;
    this.color = RED;
    this.left = null;
    this.right = null;
  }

  public int getKey(){
    return key;
  }

  public void setKey(int key){
    this.key = key;
  }

  public boolean getColor(){
    return color;
  }

  public void setColor(boolean color){
    this.color = color;
  }

  public Node getRight(){
    return right;
  }

  public void setRight(Node right){
    this.right = right;
  }

  public Node getLeft(){
    return left;
  }

  public void setLeft(Node left){
    this.left = left;
  }

  public Node getParent(){
    return parent;
  }

  public void setParent(Node parent){
    this.parent = parent;
  }






  public String printNode() {
    return "RBTreeNode{" +
        ",key=" + key +
        ", color=" + color +
        '}';
  }
}

