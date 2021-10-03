import java.util.Scanner;
public class SkipDemo {
  public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    SkipList skip = new SkipList();
    int i;
    for(i=0;i<1000;i++){
      skip.insert(i);
    }
    char c;
    do{
      System.out.println("\nOperations\n");
      System.out.println("1. insert");
      System.out.println("2. delete");
      System.out.println("3. search");

      int choice = sc.nextInt();
      switch (choice){
        case 1 :
          System.out.println("Enter integer to insert");
          skip.insert(sc.nextInt());
          break;
        case 2 :
          System.out.println("Enter integer to delete");
          skip.delete(sc.nextInt());
          break;
        case 3 :
          System.out.println("Enter integer to search for");
          System.out.println("Found: " + skip.search(sc.nextInt()));
          break;
        default :
          System.out.println("Wrong Entry \n ");
          break;
      }
      skip.printSkipList();
      System.out.println("\ncontinue? (Type y or n) \n");
      c = sc.next().charAt(0);

    } while (c == 'Y'|| c == 'y');
  }
}
