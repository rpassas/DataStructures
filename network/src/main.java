import java.util.Scanner;

public class main {

  public static void main(String[] args) throws Exception {

    Scanner scan = new Scanner(System.in);
    Network N = null;
    char character;
    do {
      System.out.println("\nOperations\n");
      System.out.println("1. build network");
      System.out.println("2. max flow");
      System.out.println("3. print matrices");

      int choice = scan.nextInt();
      switch (choice) {
        case 1:
          System.out.println("Making Network");
          Scanner sc = new Scanner(System.in);
          System.out.println("Enter number of vertices");
          int n = sc.nextInt();
          N = new Network(n);
          System.out.println("How many edges would you like to add?");
          int m = sc.nextInt();
          System.out.println("For edges give u, v and capacity between");
          System.out.println("Note: 0 must be 's' and highest number is 't'");
          for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int c = sc.nextInt();
            N.AddEdge(u, v, c);
          }
          break;
        case 2:
          if(N == null){
            System.out.println("Must build a network first");
          } else{
            int max = N.maxFlow();
            System.out.print("\nMax flow: ");
            System.out.println(max);
          }
          break;
        case 3:
          N.PrintNetwork();
          break;
      }
      System.out.println("\ncontinue? (Type y or n) \n");
      character = scan.next().charAt(0);

    } while (character == 'Y' || character == 'y');
  }
}