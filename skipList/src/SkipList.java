public class SkipList {
  private Node head;
  private int lim;
  private Node tail = null;
  private Node buffer = null;
  private static int MAX = 999999;
  private int levels = 0;
  private int count = 0;

  /**
   * Constructor for a skip list.
   */
  public SkipList(){
    // large lim ensure head and tail comparisons will be always <
    this.lim = MAX;
    // buffer is the upper most node (to help end loops going through levels)
    // assumes positive inputs
    this.buffer = new Node(0);
    this.buffer.right = buffer.up = buffer;
    this.tail = new Node(MAX);
    this.tail.right = tail;
    this.head = new Node(MAX, tail, buffer);
  }

  /**
   * Inserts element while only promoting every other (not randomized).
   * @Param d is the integer to be insert in the proper position
   */
  public void insert(int d){
    buffer.data = d;
    Node current = head;
    while (current != buffer) {
      while (current.data < d) {
        current = current.right;
      }
      //skip and insert
      if (current.up.right.right.data < current.data){
        current.right = new Node(current.data, current.right, current.up.right.right);
        current.data = current.up.right.data;
      } else {
        current = current.up;
      }
    }
    // point new head to tail, incrementing height if need be
    // using the same tail saves memory
    if (head.right != tail) {
      head = new Node(lim, tail, head);
      levels++;
    }
    count++;

  }

  /**
   * Finds element in list.
   * @param d is data for a node in the list
   * @throws IllegalStateException when d is not in the list
   */
  public int search(int d){
    Node current = head.up;
    while(current != buffer){
      if(current.data == d){
        return current.data;
      } else if(d < current.right.data){
        current = current.up;
      } else {
        // d >= right
        current = current.right;
      }
    }
    // d is not in the list
    throw new IllegalStateException("Given key not in list.");
  }

  /**
   * Deletes element in list (similar to search).
   */
  public void delete(int d){
    boolean deleted = false;
    Node current = head;
    while(current.up != buffer){
      if(current.up.data > d){
        current = current.up;
      }

      if(current.up.data < d){
        current = current.up;
        Node tmp = current;
        while(tmp.data != MAX){
          if(tmp.right.data == d){
            tmp.right = tmp.right.right;
            deleted = true;
            break;
          }
          tmp= tmp.right;
        }
      }

      if(current.up.data == d){
        deleted = true;
        if(current.up.right.data == MAX){
          current.up = current.up.up;
          current = current.up;
          levels--;
        } else {
          Node tmp = current.up.up;
          current.up = current.up.right;
          current = tmp;
        }
      }
    }
    // we are in the top (complete) list
    Node tmp = current;
    while(tmp.data != MAX){
      if(tmp.right.data > d){
        break;
      }
      if(tmp.right.data == d){
        deleted = true;
        tmp.right = tmp.right.right;
        break;
      }
      tmp= tmp.right;
    }
    if(deleted){
      count--;
    }
    if(count < (levels)*(levels)){
      head.up=head.up.up;
      levels--;
    }
  }

  /**
   * Prints towards the right.
   * @param current the current node
   */
  public void printerHelper(Node current){
    while(current != null & current.data < current.right.data){
      System.out.print(current.data + " ");
      current = current.right;
    }

  }

  /**
   * Prints the skip list in its full structure.
   */
  public void printSkipList(){
    Node current = head;
    System.out.print("\nskip list: \n");
    while(current.up != buffer) {
      current = current.up;
      System.out.print("\n");
      printerHelper(current);
    }
  }

}


