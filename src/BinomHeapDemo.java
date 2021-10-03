import java.util.Scanner;

public class BinomHeapDemo {
  public static void main(String[] args) throws Exception {

    BinomHeap i = null;
    BinomHeap h = new BinomHeap();
    Scanner scan = new Scanner(System.in);
    char c;
    do{
      System.out.println("\nOperations\n");
      System.out.println("1. make-heap");
      System.out.println("2. insert");
      System.out.println("3. minimum");
      System.out.println("4. extract-minimum");
      System.out.println("5. union");
      System.out.println("6. decrease-key");
      System.out.println("7. delete");
      System.out.println("8. find");
      System.out.println("9. print heap");
      System.out.println("10. print root");

      int choice = scan.nextInt();
      switch (choice){
        case 1 :
          System.out.println("Making new heap");
          i = h.makeHeap();
          break;
        case 2 :
          System.out.println("Enter key to insert");
          Node n = new Node(scan.nextInt(), 0);
          h.insert(n);
          System.out.println("\nkey inserted");
          break;
        case 3 :
          int min = h.min().getKey();
          System.out.println("min: " + min);
          break;
        case 4 :
          if(h.min() == null){
            System.out.println("heap is empty");
          } else {
            int emin = h.extractMin().getKey();
            System.out.println("extracted min: " + emin);
          }
          break;
        case 5 :
          if(i == null){
            i = new BinomHeap();
          }
          BinomHeap j = h.union(h, i);
          break;
        case 6 :
          System.out.println("Enter key to be decreased");
          int find = scan.nextInt();
          System.out.println("Enter new key");
          int newKey = scan.nextInt();
          if(h.find(find) == null){
            System.out.println("key not in heap");
          } else {
            h.decreaseKey(h.find(find), newKey);
            break;
          }
        case 7 :
          System.out.println("Enter key to delete");
          Node del = h.find(scan.nextInt());
          if(del == null){
            System.out.println("key not in heap");
          } else{
            h.delete(del);
          }
          break;
        case 8 :
          System.out.println("Enter key to find");
          Node found = h.find(scan.nextInt());
          if(found == null){
            System.out.println("key not in heap");
          } else{
            System.out.println("found: "+ found.getKey());
          }
          break;
        case 9 :
          System.out.println("Printing tree:");
          h.printSiblings(h.getHead());
          break;
        case 10 :
          System.out.println("Printing root list:");
          h.printRoot();
          break;
        default :
          System.out.println("Wrong Entry \n ");
          break;
      }
      System.out.println("\ncontinue? (Type y or n) \n");
      c = scan.next().charAt(0);

    } while (c == 'Y'|| c == 'y');
  }
}
