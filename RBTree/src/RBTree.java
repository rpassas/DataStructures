/**
 * RBTree is a red-black tree.
 */
public class RBTree {
  private final boolean RED = false;
  private final boolean BLACK = true;
  private Node root;

  public Node getRoot(){
    return this.root;
  }

  public Node minimum(){
    return minimum(root);
  }

  public Node minimum(Node node){
    while(node.getLeft() != null){
      node = node.getLeft();
    }
    return node;
  }

  public Node maximum(){
    return maximum(root);
  }

  public Node maximum(Node node){
    while(node.getRight() != null){
      node = node.getRight();
    }
    return node;
  }

  public Node successor(Node node){
    if(node.getRight() != null){
      return minimum(node.getRight());
    }
    Node y = node.getParent();
    while(y != null && node == y.getRight()){
      node = y;
      y = y.getParent();
    }
    return y;
  }

  public Node predecessor(Node node){
    if(node.getLeft() != null){
      return maximum(node.getLeft());
    }
    Node y = node.getParent();
    while(y != null && node == y.getLeft()){
      node = y;
      y = y.getParent();
    }
    return y;
  }

  public void leftRotate(Node node) {
    Node y = node.getRight();
    Node parent = node.getParent();
    //base
    if (parent == null) {
      root = y;
      y.setParent(null);
    } else {
      if (parent.getLeft() != null && parent.getLeft() == node) {
        parent.setLeft(y);
      } else {
        parent.setRight(y);
      }
      y.setParent(parent);
    }
    node.setParent(y);
    node.setRight(y.getLeft());
    if (y.getLeft() != null) {
      y.getLeft().setParent(node);
    }
    y.setLeft(node);
  }

  public void rightRotate(Node node) {
    Node y = node.getLeft();
    Node parent = node.getParent();
    //base
    if (parent == null) {
      root = y;
      y.setParent(null);
    } else {
      if (parent.getLeft() != null && parent.getLeft() == node) {
        parent.setLeft(y);
      } else {
        parent.setRight(y);
      }
      y.setParent(parent);
    }
    node.setParent(y);
    node.setLeft(y.getRight());
    if (y.getRight() != null) {
      y.getRight().setParent(node);
    }
    y.setRight(node);
  }


  public void insert(int key) {
    Node node = new Node(key);
    // empty tree
    if (root == null) {
      root = node;
      node.setColor(BLACK);
      return;
    }
    // else try children
    Node parent = root;
    Node child = null;
    if (key > parent.getKey()) {
      child = parent.getRight();
    } else {
      child = parent.getLeft();
    }
    // go down the branches
    while (child != null) {
      parent = child;
      if (key > parent.getKey()) {
        child = parent.getRight();
      } else {
        child = parent.getLeft();
      }
    }
    if (key > parent.getKey()) {
      parent.setRight(node);
    } else {
      parent.setLeft(node);
    }
    node.setParent(parent);
    node.setColor(RED);
    insertFixUp(node);
  }

  private void insertFixUp(Node node) {
    while (node.getParent() != null && node.getParent().getColor() == RED) {
      if (node.getParent() == node.getParent().getParent().getLeft()) {
        Node y = node.getParent().getParent().getRight();
        if (y != null && y.getColor() == RED) {
          node.getParent().setColor(BLACK);
          y.setColor(BLACK);
          node.getParent().getParent().setColor(RED);
          node = node.getParent().getParent();
        } else {
          if (node == node.getParent().getRight()) {
            Node p = node.getParent();
            this.leftRotate(p);
            Node tmp = node;
            node = p;
            p = tmp;
          }
        }
        node.getParent().setColor(BLACK);
        node.getParent().getParent().setColor(RED);
        this.rightRotate(node.getParent().getParent());
      } else {
        // same but with right/left swapped
        Node y = node.getParent().getParent().getLeft();
        if (y != null && y.getColor() == RED) {
          node.getParent().setColor(BLACK);
          y.setColor(BLACK);
          node.getParent().getParent().setColor(RED);
          node = node.getParent().getParent();
        } else {
          if (node == node.getParent().getLeft()) {
            Node p = node.getParent();
            this.rightRotate(p);
            Node tmp = node;
            node = p;
            p = tmp;
          }
          node.getParent().setColor(BLACK);
          node.getParent().getParent().setColor(RED);
          this.leftRotate(node.getParent().getParent());
        }
        if(node == root){
          break;
        }
      }
    }
    root.setColor(BLACK);
  }

  public Node search(int key){
    return search(root, key);
  }

  public Node search(Node node, int key){
    if(node == null || node.getKey() == key){
      return node;
    }
    if(key < node.getKey()){
      return search(node.getLeft(), key);
    } else{
      return search(node.getRight(), key);
    }
  }
  public void inOrderWalk() {
    inOrderWalk(root);
  }

  public void inOrderWalk(Node node) {
    if (node == null)
      return;
    inOrderWalk(node.getLeft());
    System.out.println(node.getKey());
    inOrderWalk(node.getRight());
  }

  static void printTreeHelper(Node node, int count){
    if (node == null)
      return;

    count += 10;
    // right child
    printTreeHelper(node.getRight(), count);

    System.out.print("\n");
    for (int i = 10; i < count; i++)
      System.out.print(" ");
      if(node.getColor()) {
        System.out.print(node.getKey() + " black " + "\n");
      } else {
        System.out.print(node.getKey() + " red " + "\n");
      }

    // left child
    printTreeHelper(node.getLeft(), count);
  }

  public void printTree(Node node){
    printTreeHelper(node, 0);
  }

  public int maxDepth(Node node) {
    if (node == null)
      return 0;
    else {
      int lDepth = maxDepth(node.getLeft());
      int rDepth = maxDepth(node.getRight());
      // max
      if (lDepth > rDepth)
        return (lDepth + 1);
      else {
        return (rDepth + 1);
      }
    }
  }









  /*

  private boolean isBlack(Node node) {
    if (node == null)
      return true;
    return node.getColor() == BLACK;
  }

  private boolean isRed(Node node) {
    if (node == null)
      return false;
    return node.getColor() == RED;
  }




  //EC

  public Node query(int key) {
    Node tmp = root;
    while (tmp != null) {
      if (tmp.getKey() == key)
        return tmp;
      else if (tmp.getKey() > key)
        tmp = tmp.getLeft();
      else
        tmp = tmp.getRight();
    }
    return null;
  }

  public void delete(int key) {
    delete(query(key));
  }

  private void delete(Node node) {
    if (node == null)
      return;
    if (node.getLeft() != null && node.getRight() != null) {
      Node replaceNode = node;
      Node tmp = node.getRight();
      while (tmp != null) {
        replaceNode = tmp;
        tmp = tmp.getLeft();
      }
      int t = replaceNode.getKey();
      replaceNode.setKey(node.getKey());
      node.setKey(t);
      delete(replaceNode);
      return;
    }
    Node replaceNode = null;
    if (node.getLeft() != null)
      replaceNode = node.getLeft();
    else
      replaceNode = node.getRight();

    Node parent = node.getParent();
    if (parent == null) {
      root = replaceNode;
      if (replaceNode != null)
        replaceNode.setParent(null);
    } else {
      if (replaceNode != null)
        replaceNode.setParent(parent);
      if (parent.getLeft() == node)
        parent.setLeft(replaceNode);
      else {
        parent.setRight(replaceNode);
      }
    }
    if (node.getColor() == BLACK)
      removeFix(parent, replaceNode);

  }

  // The extra color is in the node
  private void removeFix(Node parent, Node node) {
    while ((node == null || node.getColor() == BLACK) && node != root) {
      if (parent.getLeft() == node) {  // S is the left child of P, as in the previous analysis
        Node sibling = parent.getRight();
        if (sibling != null && sibling.getColor() == RED) {
          setRed(parent);
          setBlack(sibling);
          leftRotate(parent);
          sibling = parent.getRight();
        }
        if (sibling == null || (isBlack(sibling.getLeft()) && isBlack(sibling.getRight()))) {
          setRed(sibling);
          node = parent;
          parent = node.getParent();
          continue;
        }
        if (isRed(sibling.getLeft())) {
          setBlack(sibling.getLeft());
          setRed(sibling);
          rightRotate(sibling);
          sibling = sibling.getParent();
        }

        sibling.setColor(parent.getColor());
        setBlack(parent);
        setBlack(sibling.getRight());
        leftRotate(parent);
        node = root;
      } else {
        Node sibling = parent.getLeft();
        if (sibling != null && sibling.getColor() == RED) {
          setRed(parent);
          setBlack(sibling);
          rightRotate(parent);
          sibling = parent.getLeft();
        }
        if (sibling == null || (isBlack(sibling.getLeft()) && isBlack(sibling.getRight()))) {
          setRed(sibling);
          node = parent;
          parent = node.getParent();
          continue;
        }
        if (isRed(sibling.getRight())) {
          setBlack(sibling.getRight());
          setRed(sibling);
          leftRotate(sibling);
          sibling = sibling.getParent();
        }

        sibling.setColor(parent.getColor());
        setBlack(parent);
        setBlack(sibling.getLeft());
        rightRotate(parent);
        node = root;
      }
    }

    if (node != null)
      node.setColor(BLACK);
  }
 */
}

