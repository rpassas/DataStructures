import java.io.File;
import java.util.Scanner;

public class RBTreeBuilder {
  public static void main(String[] args) throws Exception {
    //scanner for building from file
    File file = new File("keys.txt");
    Scanner sc = new Scanner(file);
    RBTree rb = new RBTree();
    while(sc.hasNextInt()){
      rb.insert(sc.nextInt());
    }

    Scanner scan = new Scanner(System.in);
    char c;
    do{
      System.out.println("\nOperations\n");
      System.out.println("1. insert");
      System.out.println("2. sort");
      System.out.println("3. search");
      System.out.println("4. min");
      System.out.println("5. max");
      System.out.println("6. predecessor");
      System.out.println("7. successor");
      System.out.println("8. left rotate");
      System.out.println("9. right rotate");
      System.out.println("10. print");

      int choice = scan.nextInt();
      switch (choice){
        case 1 :
          System.out.println("Enter key to insert");
          rb.insert(scan.nextInt());
          break;
        case 2 :
          rb.inOrderWalk();
          break;
        case 3 :
          System.out.println("Enter key to search for");
          int k = rb.search(scan.nextInt()).getKey();
          System.out.println(k);
          break;
        case 4 :
          int min = rb.minimum().getKey();
          System.out.println("min: " + min);
          break;
        case 5 :
          int max = rb.maximum().getKey();
          System.out.println("max: "+ max);
          break;
        case 6 :
          System.out.println("Enter key to find predecessor for");
          int pre = rb.predecessor(rb.search(scan.nextInt())).getKey();
          System.out.println(pre);
          break;
        case 7 :
          System.out.println("Enter key to find successor for");
          int suc = rb.successor(rb.search(scan.nextInt())).getKey();
          System.out.println(suc);
          break;
        case 8 :
          System.out.println("Enter key to rotate left");
          rb.leftRotate(rb.search(scan.nextInt()));
          break;
        case 9 :
          System.out.println("Enter key to rotate right");
          rb.leftRotate(rb.search(scan.nextInt()));
          break;
        case 10 :
          System.out.println("Tree:");
          rb.printTree(rb.getRoot());
          break;
        default :
          System.out.println("Wrong Entry \n ");
          break;
      }
      System.out.println("height: " + rb.maxDepth(rb.getRoot()));
      System.out.println("\ncontinue? (Type y or n) \n");
      c = scan.next().charAt(0);

    } while (c == 'Y'|| c == 'y');
  }
}

