
public class Tuple {
  private int key;
  private int degree;

  public Tuple(int key, int degree) {
    this.key = key;
    this.degree = degree;
  }

  public int getKey(){
    return this.key;
  }

  public int getDegree(){
    return this.degree;
  }

  public void setDegree(int d){
    this.degree = d;
  }

  public void updateKey(int k){
    this.key = k;
  }
}
