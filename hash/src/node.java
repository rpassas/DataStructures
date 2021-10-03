

public class node {
  private tuple data;
  private node next;
  private node prev;

  node(String info, int count) {
    data = new tuple(info, count);
    next = null;
    prev = null;
  }

  public node getNext() {
    return this.next;
  }

  public node getPrev() {
    return this.prev;
  }

  public void updateNext(node n) {
    this.next = n;
  }

  public void updatePrev(node n) {
    this.prev = n;
  }

  public String getWord(){
    return data.getString();
  }

  public int getCount(){
    return data.getInt();
  }

  public void increaseCount() {
    this.data.increaseCount();
  }
}

