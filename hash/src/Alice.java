import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;

/**
Let's hash Alice In Wonderland
 */
  public class Alice {
  public static void main(String[] args) throws Exception {
    //punctuation to be excluded
    String punc = "[  ,:\\n)*/\\s!;\"?(._]+";

    Scanner sc = new Scanner(System.in);
    hash_table T = new hash_table(100);
    String k;
    char c;
    do {
      System.out.println("\nOperations\n");
      System.out.println("1. create hash table");
      System.out.println("2. write output of hash table");
      System.out.println("3. insert");
      System.out.println("4. delete");
      System.out.println("5. find");
      System.out.println("6. list all keys");

      int choice = 7;
      try {
        choice = sc.nextInt();
      } catch(Exception e) {
        choice = 7;
      }
      switch (choice) {
        case 1:
          System.out.println("Enter text file to hash");
          File file = new File(sc.next());
          Scanner fsc = new Scanner(file).useDelimiter(punc);
          while (fsc.hasNext()) {
            k = fsc.next();
            T.insert(k, 1);
          }
          System.out.println("Done hashing");
          break;
        case 2:
          System.out.println("Enter output file name");
          try {
            FileWriter myWriter = new FileWriter(sc.next());
            int i;
            String v;
            for (i = 0; i < T.getN(); i++) {
              if (T.getT()[i] != null) {
                node n = T.getT()[i];
                while (n != null) {
                  v = String.valueOf(n.getCount());
                  myWriter.write("k: " + n.getWord() + ", v: " + v + "\n");
                  n = n.getNext();
                }
              }
            }
            myWriter.close();
            System.out.println("Done writing.");
          } catch (IOException e) {
            System.out.println("IO error!");
            e.printStackTrace();
          }
          break;
        case 3:
          System.out.println("Enter key to be inserted");
          T.insert(sc.next(), 1);
          break;
        case 4:
          System.out.println("Enter key to be deleted");
          T.delete(sc.next());
          break;
        case 5:
          System.out.println("Enter key to be found");
          node n = T.find(sc.next());
          System.out.println("key: " + n.getWord() + ", count: " + n.getCount());
          break;
        case 6:
          System.out.println("Listing all keys:");
          T.list_all_keys();
          break;
        case 7:
          System.out.println("Wrong Entry \n ");
          break;
        default:
          System.out.println("Wrong Entry \n ");
          break;
      }
      System.out.println("\ncontinue? (Type y or n) \n");
      c = sc.next().charAt(0);
    } while (c != 'n');
  }
}



