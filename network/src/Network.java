
import java.util.ArrayList;

public class Network {
  int num_v;
  int[] excess;
  int[] height;
  int[][] capacity;
  int[][] flow;


  Network(int numv){

    int i;
    int j;
    num_v = numv;
    excess = new int[num_v];
    height = new int[num_v];
    capacity = new int[num_v][num_v];
    flow = new int[num_v][num_v];

    height[0] = num_v;

    for(i=0;i<num_v;i++){
      for(j=0;j<num_v;j++){
        flow[i][j]=0;
        capacity[i][j] = 0;
      }
    }
    for(j=0;j<num_v;j++){
      height[j] = 0;
      excess[j] = 0;
    }
    height[0] = num_v;


  }

  void AddEdge(int u, int v, int e){
    capacity[u][v] = e;
  }

  void initPreflow(){
    int i;
    for(i=0;i<num_v;i++){
      flow[0][i] = capacity[0][i];
      excess[i] = capacity[0][i];
      excess[0] = excess[0] - capacity[0][i];
    }
  }

  int findActiveVertex(){
    int i;
    int j;
    //go through all non-s v's to find excess
    for(i = 1;i<num_v-1;i++){
      if(excess[i]>0) {
        return i;
        // if there is a residual path and excess return it
        /*
        for(j=0;j<num_v;j++){
          if(capacity[i][j]!=0){
            if(capacity[i][j]!=flow[i][j]){
              return i;
            }
          }
        }
        */
      }
    }
    return -1;
  }

  void relabel(int u){
    int min_h = 2147483646;
    int i;
    //find min height of vertex with residual edge
    for(i=0;i<num_v;i++) {
      //forward edge
      if(height[i] >= height[u] && capacity[u][i] - flow[u][i] > 0){
        if(height[i] < min_h){
          min_h = height[i];
        }
        // residual back edge
      } else if(height[i] >= height[u] && flow[i][u] > 0){
        if(height[i] < min_h){
          min_h = height[i];
        }
      }

      /*
      if(capacity[u][i]!=0 && capacity[u][i] !=flow[u][i]){
        if(height[i] < min_h){
          min_h = height[i];
        }
      }
      */
    }
    height[u] = min_h + 1;


  }

  void push(int u, int v){
    //push as much flow from u to v as possible
    int cf;
    if(capacity[u][v] - flow[u][v] == 0){
      cf = flow[v][u];
    }else{
      cf = capacity[u][v] - flow[u][v];
    }
    int pushable = Math.min(cf, excess[u]);
    if(capacity[u][v] != 0){
      flow[u][v] = pushable + flow[u][v];
      //capacity[v][u] = flow[u][v] + capacity[v][u];
    } else{
      flow[v][u] = flow[v][u] - pushable;
      //capacity[u][v] = capacity[v][u] + flow[v][u];
    }
    excess[u] = excess[u] - pushable;
    excess[v] = excess[v] + pushable;

  }

  int maxFlow(){
    int i;
    this.initPreflow();
    boolean updated;
    while(true){
      //find excess flow until there is none
      int u = findActiveVertex();
      if(u == -1){
        return excess[excess.length-1];
      }
      updated = false;
      //vertex u is overflowing & there is a residual path w/ u.h = v.h + 1
      for(i=0;i<num_v;i++){
        //push toward sink
        if(capacity[u][i] - flow[u][i] > 0 && height[u] > height[i]){
          push(u,i);
          updated = true;
          //residual path back
        }else if(flow[i][u] > 0 && height[u] > height[i]){
          push(u,i);
          updated = true;
        }
      }
      if(updated){
        continue;
      }
      //vertex u is overflowing & for all v's with residual paths v.h >= u.h
      /*
      for(i=0;i<num_v;i++){
        if(){}
      }
      */
      relabel(u);
    }

    /*
    vertices[s].height = num_v;
    for(i=1;i<num_v;i++){
      if(capacity[s][i]!=0){
        flow[s][i]=capacity[s][i];
        vertices[i].excess+=flow[s][i];
        AddEdge(i, s, 0);
        flow[i][s]=-flow[s][i];
      }
    }
     */
  }

  void PrintNetwork(){
    int j;
    int i;
    System.out.println("number of vertices: " + num_v);
    System.out.println("height on each vertex: " );
    for(j = 0; j < num_v; j++){
      System.out.println(j + ": " + height[j] );
    }
    System.out.println("capacity: " );
    for(i = 0; i < num_v; i++) {
      System.out.print("\n");
      for (j = 0; j < num_v; j++) {
        System.out.print(capacity[i][j]+ " ");
      }
    }
    System.out.println("\nflow: " );
    for(i = 0; i < num_v; i++) {
      System.out.print("\n");
      for (j = 0; j < num_v; j++) {
        System.out.print(flow[i][j] + " ");
      }
    }

    /*
    System.out.println("\n\nu:" + u);
    System.out.print("v:" + v);

    for(j = 0; j < num_v; j++){
      int cap = capacity[u][j];
      int f = flow[u][j];
      System.out.println("\nheight "+ j + ": " + height[j]);
      System.out.println("excess "+ j + ": "+ excess[j]);
      System.out.print(" capacity " + u + ", " + j +": " + cap);
      System.out.print(" flow " + u + ", " + j +": " + f);
    }
    int j;
    for(j = 0; j < num_v; j++){
      int cap = capacity[u][j];
      int f = flow[u][j];
      System.out.println("\nheight "+ j + ": " + height[j]);
      System.out.println("excess "+ j + ": "+ excess[j]);
      System.out.print(" capacity " + u + ", " + j +": " + cap);
      System.out.print(" flow " + u + ", " + j +": " + f);
    }
    System.out.println("\n\nheight "+ u + ": " + height[u]);
    System.out.println("relabeled height "+ u + ": " + height[u] + "\n");
     */
  }

}
