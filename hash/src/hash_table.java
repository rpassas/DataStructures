
/**
 * Hashtable to hold keys and values as tuples in nodes (each idx holds a node).
 */
public class hash_table {
  private int n;
  private node Table[];


  /**
   * Constructor for a hash table.
   * @param n is the size of the table
   */
  public hash_table(int n) {
    this.n = n;
    this.Table = new node[n];
  }


  /**
   * hash function
   * @param k is a key
   * @return integer for placement in hashtable
   */
  public int h(String k) {
    int hash = 3;
    int a;
    char c;
    for (int i = 0; i < k.length(); i++) {
      c = k.charAt(i);
      a = Character.getNumericValue(c);
      hash = (hash*7 + a)%n;
    }
    if(hash < 0){
      hash = 0;
    }
    return hash;
  }

  /**
   * insert key into table (if the key exists, increase the count).
   * @param k that is the key to be added to the hash table
   */
  public void insert(String k, int v) {
    int idx = h(k);
    if(Table[idx] == null){
      LL list = new LL();
      list.addNode(k);
      Table[idx] = list.getHead();
      return;
    } else {
      node n = Table[idx];
      while(n != null) {
        if(n.getWord().equals(k)){
          n.increaseCount();
          return;
        }
        n = n.getNext();
      }
    }
    // add node as head to LL
    node new_node = new node(k, 1);
    new_node.updateNext(Table[idx]);
    Table[idx].updatePrev(new_node);
    Table[idx] = new_node;
  }

  /**
   * delete a word from the hash table.
   * @param k (word) used to find the word to be deleted
   */
  public void delete(String k){
    node n = find(k);
    int idx = h(k);
    if(n.getCount() <= 0){
      System.out.println(k + " not in table");
      n = null;
      return;
    }
    if(n.getPrev() == null && n.getNext() == null){
      System.out.println("successful deletion of " + k);
      Table[idx] = null;
      n = null;
      return;
    }
    if(n.getPrev() == null && n.getNext() != null){
      Table[idx] = n.getNext();
      n.updatePrev(null);
      n = null;
      System.out.println("successful deletion of " + k);
      return;
    }
    if(n.getPrev() != null && n.getNext() == null){
      n.getPrev().updateNext(null);
      n = null;
      System.out.println("successful deletion of " + k);
      return;
    }
    if(n.getCount() > 0) {
      n.getPrev().updateNext(n.getNext());
      n.getNext().updatePrev(n.getPrev());
      n = null;
      System.out.println("successful deletion of " + k);
      return;
    }
    System.out.println("unsuccessful deletion of " + k);
  }

  /**
   * Finds the key in the table and increments count.
   * @param k string key where count needs to be incremented
   * @Throws NullPointerException when key is not in the table
   */
  public void increase(String k){
    node n = find(k);
    if(n.getCount() > 0) {
      n.increaseCount();
    } else{
      throw new NullPointerException("Given key cannot be increased - does not exist in table.");
    }
  }

  /**
   * Find the target node containing the key and value
   * @param k string key to be found
   * @return node (either target or dummy)
   */
  public node find(String k){
    int idx = h(k);
    node n = Table[idx];
    if(n != null && n.getWord().equals(k)){
      return n;
    }
    while(n.getNext() != null) {
      if(n != null && n.getWord() == k){
        return n;
      }
      if(n.getNext().getWord().equals(k)){
        return n.getNext();
      }
      n = n.getNext();
    }
    node dumb_node = new node("key does not exist", 0);
    return dumb_node;
  }

  /**
   * Prints all of the keys in the hash table.
   */
  public void list_all_keys(){
    int i;
    for(i=0;i<n;i++){
      if(Table[i] != null){
        node n = Table[i];
        while(n != null) {
          System.out.println(n.getWord());
          n = n.getNext();
          }
        }
      }
    }

  /**
   * Gets size of the Table.
   */
  public int getN(){
    return n;
  }

  /**
   * Gets Table.
   */
  public node[] getT(){
    return Table;
  }
}



