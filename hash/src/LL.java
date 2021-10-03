public class LL {
  private node head;

  public LL(){
    this.head = null;
  }

  /**
   * addNode adds node with k as new head.
   * @param k
   */
  public void addNode(String k) {
    node new_node = new node(k, 1);
    new_node.updateNext(this.head);
    this.head = new_node;
  }

  public node getHead(){
    return this.head;
  }

  public boolean inLL(String k){
    boolean out = false;
    node n = this.head;
    while(n != null) {
      if(n.getWord() == k){
        out = true;
        return true;
      }
      n = n.getNext();
    }
    return out;
  }

}

