
public class BinomHeap {
  private Node head;
  private static int MAX = 2147483647;
  private static int MIN = -2147483648;

  public BinomHeap() {
    this.head = null;
  }

  public BinomHeap makeHeap(){
    return new BinomHeap();
  }

  public Node getHead(){
    return head;
  }

  public void setHead(Node n){
    head = n;
  }

  public void insert(Node n){
    BinomHeap h = makeHeap();
    n.setParent(null);
    n.setChild(null);
    n.setDegree(0);
    n.setSibling(null);
    h.setHead(n);
    BinomHeap newH = union(this, h);
    this.setHead(newH.getHead());
  }

  public Node min(){
    Node y = null;
    Node x = head;
    int min = MAX;
    while(x != null){
      if(x.getKey() < min){
        min = x.getKey();
        y = x;
      }
      x = x.getSibling();
    }
  return y;
  }

  public Node extractMin(){
    Node prev = null;
    Node prevMin = null;
    Node iterNode = this.head;
    Node min = this.head;
    if(head == null){
      return null;
    }
    //find min
    while(iterNode != null){
      if(iterNode.getKey() < min.getKey()){
        prevMin = prev;
        min = iterNode;
      }
      prev = iterNode;
      iterNode = iterNode.getSibling();
    }
    //remove min from root list
    if(prevMin == null && head == min){
      //min is the head
      Node tmp = head;
      this.setHead(this.head.getSibling());
      //head.setSibling(null);
    } else {
      prevMin.setSibling(min.getSibling());
    }
    //create new heap with reversed root list using min's children
    BinomHeap h = makeHeap();
    Node childIter = min.getChild();
    if(childIter != null) {
      Node childNext = min.getChild().getSibling();
      while (childNext != null) {
        childIter.setParent(null);
        childIter.setSibling(h.getHead());
        h.setHead(childIter);
        childIter = childNext;
        childNext = childNext.getSibling();
      }
      childIter.setParent(null);
      childIter.setSibling(h.getHead());
      h.setHead(childIter);
    }
    BinomHeap newH = union(this, h);
    this.setHead(newH.getHead());

    return min;
  }

  public BinomHeap union(BinomHeap a, BinomHeap b){
    BinomHeap h = makeHeap();
    h.setHead(binomHeapMerge(a,b));
    //h.printSiblings(h.getHead());
    if(h.getHead() == null){
      return h;
    }
    Node prevX = null;
    Node x = h.getHead();
    Node nextX = x.getSibling();
    while(nextX != null){
      if(x.getDegree() != nextX.getDegree() ||
          (nextX.getSibling() != null && nextX.getSibling().getDegree() == x.getDegree())){
        //case 1 and 2
        prevX = x;
        x = nextX;
      } else if(x.getKey() <= nextX.getKey()){
        //case 3
        x.setSibling(nextX.getSibling());
        h.binomLink(nextX, x);
      } else {
        if (prevX == null) {
          //case 4
          h.setHead(nextX);
        } else {
          //case 4
          prevX.setSibling(nextX);
        }
        h.binomLink(x, nextX);
        x = nextX;
      }
      nextX = x.getSibling();
    }
    return h;
  }

  public Node binomHeapMerge(BinomHeap a, BinomHeap b){
    Node h = null;
    Node tail = null;
    Node aRoot = a.getHead();
    Node bRoot = b.getHead();
    while(aRoot != null && bRoot != null){
      //new root list is empty
      if(tail == null){
        if(aRoot.getDegree() <= bRoot.getDegree()){
          tail = aRoot;
          aRoot = aRoot.getSibling();
          h = tail;
        } else {
          tail = bRoot;
          bRoot = bRoot.getSibling();
          h = tail;
        }
        //new root list not empty
      } else if(aRoot.getDegree() <= bRoot.getDegree()){
        tail.setSibling(aRoot);
        aRoot = aRoot.getSibling();
        tail = tail.getSibling();
      } else {
        tail.setSibling(bRoot);
        bRoot = bRoot.getSibling();
        tail = tail.getSibling();
      }
    }
    //one of the heap root lists is used up
    while(aRoot != null){
      if(tail == null) {
        h = aRoot;
        break;
      } else {
        tail.setSibling(aRoot);
        aRoot = aRoot.getSibling();
        tail = tail.getSibling();
      }
    }
    while(bRoot != null){
      if(tail == null) {
        h = bRoot;
        break;
      } else {
        tail.setSibling(bRoot);
        bRoot = bRoot.getSibling();
        tail = tail.getSibling();
      }
    }
    head = h;
    return head;
  }


  public void binomLink(Node x, Node y){
    x.setParent(y);
    x.setSibling(y.getChild());
    y.setChild(x);
    y.setDegree(y.getDegree()+1);
  }

  public void decreaseKey(Node n, int k){
    if(k > n.getKey()){
      System.out.println("error: given key is larger than that of the target");
      return;
    }
    n.setKey(k);
    Node y = n;
    Node z = y.getParent();
    while(z != null && y.getKey() < z.getKey()){
      //exchange z and y keys
      //int deg = z.getDegree();
      int key = z.getKey();
      z.setKey(y.getKey());
      //z.setDegree(y.getDegree());
      //y.setDegree(deg);
      y.setKey(key);
      //float up
      y = z;
      z = y.getParent();
    }
  }

  public void delete(Node n){
    decreaseKey(n, MIN);
    extractMin();
  }

  public void printChildren(Node n){
    while(n != null){
      System.out.print("key: " + n.getKey() + " degree: " + n.getDegree() + " ");
      if(n.getSibling() != null){
        printSiblings(n.getSibling());
      }
      n = n.getChild();
    }
  }

  public void printSiblings(Node n){
    while(n != null){
      if(n.getParent() == null) {
        System.out.println("\nkey: " + n.getKey() + " degree: " + n.getDegree() );
      } else{
        System.out.println("key: " + n.getKey() + " degree: " + n.getDegree());
      }
      if(n.getChild() != null){
        printChildren(n.getChild());
      }
      n = n.getSibling();
    }

  }

  public void printRoot(){
    Node n = head;
    while(n != null){
      System.out.println("key: " + n.getKey() + " degree: " + n.getDegree() + "\n");
      n = n.getSibling();
    }
  }

  public Node findDown(Node n, int k) {
    Node r = null;
    while (n != null) {
      if (n.getKey() == k) {
        return n;
      }
      if(n.getSibling() != null){
        r = findRight(n, k);
      }
      if(r != null){
        if(r.getKey() == k){
          return r;
        }
      }
      n = n.getChild();
    }
    return n;
  }

  public Node findRight(Node n, int k) {
    //go right
    while (n.getKey() >= k) {
      if (n.getKey() == k) {
        return n;
      }
      n = n.getSibling();
    }
    return n;
  }

  public Node find(int k){
    Node n = head;
    Node d = null;
    Node r = null;
    while(n != null) {
      //go right
      r = findRight(n, k);
      //go down
      if(r.getKey() != k) {
        d = findDown(r, k);
      } else {
        return r;
      }
      if(d != null) {
        if (d.getKey() == k) {
          return d;
        }
      }
      //go right 1
      n = r.getSibling();
      }
    return null;
  }
}
