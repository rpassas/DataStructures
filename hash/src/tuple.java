
public class tuple {
  private final String word;
  private int count;

  public tuple(String word, int count) {
    this.word = word;
    this.count = count;
  }

  public String getString(){
    return this.word;
  }

  public int getInt(){
    return this.count;
  }

  public void increaseCount(){
    this.count++;
  }
}
