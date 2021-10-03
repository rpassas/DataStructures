#ifndef MYGRAPH_H
#define MYGRAPH_H

#include "my_dll.h"
#include <stdlib.h>
#include <assert.h>
// Create a graph data structure
typedef struct graph{
    int numNodes;
    int numEdges;
    dll_t* nodes;     //an array of nodes storing all of our nodes.
}graph_t;

typedef struct graph_node{
    int data;
    dll_t* inNeighbors;
    dll_t* outNeighbors;
}graph_node_t;

// Creates a graph
// Returns a pointer to a newly created graph.
// The graph should be initialized with data on the heap.
// The graph fields should also be initialized to default values.
// Returns NULL if we cannot allocate memory.
graph_t* create_graph(){
    // Modify the body of this function as needed.
    graph_t* myGraph= (graph_t*)malloc(sizeof(graph_t));
    myGraph->nodes = create_dll();
    myGraph->numEdges = 0;
    myGraph->numNodes = 0;
    return myGraph;
}

// Returns the node pointer if the node exists.
// Returns NULL if the node doesn't exist or the graph is NULL
graph_node_t* find_node( graph_t * g, int value){
    if (g == NULL) {   
        return NULL;
    }
    node_t* current_n = g->nodes->head;
    while (current_n != NULL) {
        graph_node_t* g_node = (graph_node_t*)current_n->data;
        if (g_node->data == value) {
            return g_node;
        }
        current_n = current_n->next;
    }
    return NULL;
}

// Creates a graph node
// Note: This relies on your dll implementation.
graph_node_t * create_graph_node(int value){
    graph_node_t * graph_node = (graph_node_t*)malloc(sizeof(graph_node_t));
    
    if ( graph_node == NULL ) return NULL;
    
    graph_node->data = value;
    graph_node->inNeighbors = create_dll();
    graph_node->outNeighbors = create_dll();
    
    return graph_node;
}

// Returns 1 on success
// Returns 0 on failure ( or if the node already exists )
// Returns -1 if the graph is NULL.
int graph_add_node(graph_t* g, int value){
    if ( g == NULL ) return -1;
    
    if (find_node(g, value) != NULL) return 0;
    
    graph_node_t * newNode = create_graph_node(value);
    if ( newNode == NULL ) return 0;
    
    assert(g->nodes);
    dll_push_back(g->nodes, newNode);
    g->numNodes++;
    
    return 1;
}


// Returns 1 on success
// Returns 0 on failure ( or if the source or destination nodes don't exist )
// Returns -1 if the graph is NULL.
int contains_edge( graph_t * g, int source, int destination){
    if (g==NULL) {
        return -1;
    }
    // nodes don't exist
    if (find_node(g, source) == NULL || find_node(g, destination) == NULL) {
        return 0;
    }
    // outNeighbors is null/empty
    if (find_node(g, source)->outNeighbors->head == NULL 
            || find_node(g, source) == NULL) {
        return 0;
    }
    int count = 0;
    graph_node_t* source_n = find_node(g, source);
    dll_t* s_dll = source_n->outNeighbors;
    int i;
    for (i=0;i<dll_size(s_dll);i++) {
        graph_node_t* ns = (graph_node_t*)dll_get(s_dll, i);
        if (ns->data == destination) {
            count++;
        }
    }
    graph_node_t* dest_n = find_node(g, destination);
    dll_t* d_dll = dest_n->inNeighbors;
    int j;
    for (j=0;j<dll_size(d_dll);j++) {
        graph_node_t* nd = (graph_node_t*)dll_get(d_dll, j);
        if (nd->data == source) {
            count++;
        }
    }
    if (count == 2) {
        return 1;
    } 
    if (count == 1) {
        printf("error - partial data for edge\n");
        return 0;
    }

    
    return 0;
}


// Returns dll_t* of all the in neighbors of this node.
// Returns NULL if thte node doesn't exist or if the graph is NULL.
dll_t* getInNeighbors( graph_t * g, int value ){
    if (g==NULL) {
        return NULL;
    }
    if (find_node(g, value) == NULL) {
        return NULL;
    }
    
    return find_node(g, value)->inNeighbors;
}


// Returns the number of in neighbors of this node.
// Returns -1 if the graph is NULL or the node doesn't exist.
int getNumInNeighbors( graph_t * g, int value){
    if (g==NULL) {
        return -1;
    }
    if (find_node(g, value) == NULL) {
        return -1;
    }
    
    return find_node(g, value)->inNeighbors->count;
}


// Returns dll_t* of all the out neighbors of this node.
// Returns NULL if thte node doesn't exist or if the graph is NULL.
dll_t* getOutNeighbors( graph_t * g, int value ){
    if (g==NULL) {
        return NULL;
    }
    if (find_node(g, value) == NULL) {
        return NULL;
    } 
    return find_node(g, value)->outNeighbors;
}


// Returns the number of out neighbors of this node.
// Returns -1 if the graph is NULL or the node doesn't exist.
int getNumOutNeighbors( graph_t * g, int value){
    if (g==NULL) {
        return -1;
    }
    if (find_node(g, value) == NULL) {
        return -1;
    }
    
    return find_node(g, value)->outNeighbors->count;
}


// Returns 1 on success
// Returns 0 on failure ( or if the source or destination nodes don't exist, or the edge doesn't exists )
// Returns -1 if the graph is NULL.
int graph_remove_edge(graph_t * g, int source, int destination){
    //The function removes an edge from source to destination but not the other way.
    //Make sure you remove destination from the out neighbors of source.
    //Make sure you remove source from the in neighbors of destination.
    if (g==NULL) {
        return -1;
    }
    int got_out = 0;
    int got_in = 0;
    if (contains_edge(g, source, destination)) {
        graph_node_t* s_node = find_node(g, source);
        graph_node_t* d_node = find_node(g, destination);
        dll_t* outNeighbors = getOutNeighbors(g, source);
        dll_t* inNeighbors = getInNeighbors(g, destination);
        int size_out = dll_size(s_node->outNeighbors);
        int size_in = dll_size(d_node->inNeighbors);
        if (size_out == 0 || size_in == 0) {
            return 0;
        } else {    
            //remove outgoing edge
            int i;
            for (i=0;i<size_out;i++) {
                graph_node_t* out_n = (graph_node_t*)dll_get(outNeighbors, i);
                if (out_n->data == destination) {
                    dll_remove(s_node->outNeighbors, i);
                    got_out = 1;
                    break;
                }
            }
            //remove inbound node
            int j;
            for (j=0;j<size_in;j++) {
                graph_node_t* in_n = (graph_node_t*)dll_get(d_node->inNeighbors, j);
                if (in_n->data == source) {
                    dll_remove(d_node->inNeighbors, j);
                    got_in = 1;
                    break;
                }
            }
        }
    } else {
        printf("couldnt remove edge\n");
        return 0;
    }
    if (got_in == 0 || got_out == 0) {
        return 0;
    }
    g->numEdges--;
    return 1;
}


// Returns the number of nodes in the graph
// Returns -1 if the graph is NULL.
int graph_num_nodes(graph_t* g){
    if (g == NULL) {
        return -1;
    }
    return g->numNodes;
}


// Returns 1 on success
// Returns 0 on failure ( or if the node doesn't exist )
// Returns -1 if the graph is NULL.
int graph_remove_node(graph_t* g, int value){
    // The function removes the node from the graph along with any edges associated with it.
    // That is, this node would have to be removed from all the in and out neighbor's lists that countain it.
    if (g==NULL) {
        return -1;
    }
    // node does not exist
    if (find_node(g, value) == NULL) {
        return 0;
    }
    if (graph_num_nodes(g) == 0) {
        return 0;
    }
    // remove the edges pointing away from node
    dll_t* out = getOutNeighbors(g, value);
    int i;
    int got_out = 0;
    if (out->head == NULL) {
        got_out = 1;
    } else {
        // for each out neighbor
        for (i=0;i<dll_size(out);i++) {
            // get the out node
            graph_node_t* n_out = (graph_node_t*)dll_get(out, i);
            // remove their in neighbor that is the source
            int j;
            for (j=0;j<dll_size(n_out->inNeighbors);j++) {
                graph_node_t* check_in = (graph_node_t*)dll_get(n_out->inNeighbors, j);
                if (check_in->data == value) {
                    dll_remove(n_out->inNeighbors, j);
                    got_out = 1;
                    g->numEdges--;
                    break;
                }
            }
        }
    }

    //remove the edges pointing to node
    dll_t* in = getInNeighbors(g, value);
    int k;
    int got_in = 0;
    if (in->head == NULL) {
        got_in = 1;
    } else {
       // for each in neighbor
        for (k=0;k<dll_size(in);k++) {
            // get the in node
            graph_node_t* n_in = (graph_node_t*)dll_get(in, k);
            // remove their out neighbor that is the target
            int l;
            for (l=0;l<dll_size(n_in->outNeighbors);l++) {
                graph_node_t* check_out = (graph_node_t*)dll_get(n_in->outNeighbors, l);
                if (check_out->data == value) {
                    dll_remove(n_in->outNeighbors, l);
                    got_in = 1;
                    g->numEdges--;
                    break;
                }
            }
        }
    }
 
    if (got_in == 0 || got_out == 0) {
        return 0;
    }
    // free the node
    graph_node_t* node = find_node(g, value);
    free_dll(node->inNeighbors);
    free_dll(node->outNeighbors);
    dll_t* nodes = g->nodes;
    // remove the node from list in graph
    int m;
    for (m = 0; m<nodes->count; m++){
        graph_node_t* node_g = (graph_node_t*)dll_get(nodes, m);
        if (node_g->data == value) {
            dll_remove(nodes, m);
        }
    } 
    free(node);
    g->numNodes--;
    return 1;
}

// Returns 1 on success
// Returns 0 on failure ( or if the source or destination nodes don't exist, or the edge already exists )
// Returns -1 if the graph is NULL.
int graph_add_edge(graph_t * g, int source, int destination){
    // The function adds an edge from source to destination but not the other way.
    // Make sure you are not adding the same edge multiple times.
    // Make sure you modify the in and out neighbors appropriatelly. destination will be an out neighbor of source.
    // Source will be an in neighbor of destination.
    if (g==NULL) {
        return -1;
    }
    // edge already exists or nodes don't exist
    if (contains_edge(g, source, destination)) {
        return 0;
    }
    // edge does not exist and must be added
    // to outgoing source
    dll_t* out = getOutNeighbors(g, source);
    graph_node_t* d_node = find_node(g, destination);
    dll_push_front(out, d_node);
    // and incoming destination
    dll_t* in = getInNeighbors(g, destination);
    graph_node_t* s_node = find_node(g, source);
    dll_push_front(in, s_node);
    g->numEdges++;
    return 1;
}


// Returns the number of edges in the graph,
// Returns -1 on if the graph is NULL
int graph_num_edges(graph_t* g){
    if (g == NULL) {
        return -1;
    }
    return g->numEdges;
}

// returns 1 if node is in the dll
// returns 0 if not
// reutrns -1 if dll is null
int dll_contains(dll_t* dll, graph_node_t* node) {
    if (dll == NULL) {
        return -1;
    }
    int i;
    for (i=0; i<dll_size(dll);i++) {
        graph_node_t* current_n = (graph_node_t*)dll_get(dll, i);
        if (current_n == node) {
            return 1;
        }
    }
    return 0;
}


// returns 1 if we can reach the destination from source
// returns 0 if it is not reachable
// returns -1 if the graph is NULL (using BFS)
int is_reachable(graph_t * g, int source, int dest){
    if (g == NULL) {
        return -1;
    }

    
    dll_t* visited = create_dll();
    dll_t* working_list = create_dll();
    graph_node_t* start = find_node(g, source);
    
    dll_push_back(working_list, start);
    while (dll_size(working_list) != 0) {
        graph_node_t* current_n = (graph_node_t*)dll_pop_front(working_list);
        if (!dll_contains(visited, current_n)) {
        if (current_n->data == dest) {
            free_dll(visited);
            free_dll(working_list);
            return 1;
        }
            dll_push_back(visited, current_n);
            dll_t* out_n = current_n->outNeighbors;
            int out_n_size = dll_size(out_n);
            int i;
            for (i = 0; i<out_n_size; i++) {
                graph_node_t* neighbor = (graph_node_t*)dll_get(out_n, i);
                dll_push_back(working_list, neighbor);
            }
        }
    }
    free_dll(visited);
    free_dll(working_list);
    return 0;
    
}

// returns the last item of a dll
void* dll_peek_back(dll_t* dll) {
    int last = dll_size(dll) - 1;
    return dll_get(dll, last);
}


// returns 1 if there is a cycle in the graph
// returns 0 if no cycles exist in the graph
// returns -1 if the graph is NULL 
// You may use either BFS or DFS to complete this task.
int has_cycle(graph_t * g){
   // for each node in g
   // if inNeighbor of currernt node is reachable by current node - return true
    int size = dll_size(g->nodes);
    int i;
    for (i=0;i<size;i++) {
        graph_node_t* g_node = (graph_node_t*)dll_get(g->nodes, i);
        dll_t* inNeighbors = g_node->inNeighbors;
        int j;
        for (j=0;j<dll_size(inNeighbors);j++) {
            graph_node_t* n_in = (graph_node_t*)dll_get(inNeighbors, j);
            if (is_reachable(g, g_node->data, n_in->data)) {
                return 1;
            }
        }
    }
    return 0;
}



// helps print path to destination
void* dfs_helper(graph_t* g, graph_node_t* dest, dll_t* a_stack, dll_t* visited) {
    graph_node_t* current_n = (graph_node_t*)dll_peek_back(a_stack);
    if (current_n == dest) {
        return a_stack;
    }
    dll_push_back(visited, current_n);
    int i;
    for (i=0;i<dll_size(current_n->outNeighbors);i++) {
        dll_t* out_neigh = getOutNeighbors(g, current_n->data);
        graph_node_t* neighbor = (graph_node_t*)dll_get(out_neigh, i);
        if (dll_contains(visited, neighbor) == 0) {
        dll_push_back(a_stack, neighbor);
        }
        dll_t* result = (dll_t*)dfs_helper(g, dest, a_stack, visited);
        if (result != NULL) {
            return result;
        }
    }
    dll_pop_back(a_stack);
    return NULL;
}

// prints any path from source to destination if there 
// exists a path from the source to the destination.
// Note: Consider using one of the other functions to help you
//       determine if a path exists first
// (Choose either BFS or DFS, typically DFS is much simpler)
//
// Returns 1 if there is a path from source to destination
// Returns 0 if there is not a path from a source to destination
// Returns -1 if the graph is NULL
int print_path(graph_t * g, int source, int dest){
    if (g == NULL) {
        return -1;
    }
    
    if (is_reachable(g, source, dest) != 1) {
        printf("path not possible - destination not reachable\n");
        return 0;
    }
    dll_t* a_stack = create_dll();
    dll_t* visited = create_dll();
    graph_node_t* s_node = find_node(g, source);
    dll_push_back(a_stack, s_node);
    graph_node_t* d_node = find_node(g, dest);
    dll_t* printer =  (dll_t*)dfs_helper(g, d_node, a_stack, visited);
    // for each node in stack print
    printf("PATH %d -> %d\n", source, dest);
    int i;
    for (i = 0; i<dll_size(printer); i++) {
        graph_node_t* n = (graph_node_t*)dll_get(printer, i);
        printf("%d\n", n->data);
    }
    free_dll(a_stack);
    free_dll(visited);
    return 1;

}


// Free graph
// Removes a graph and ALL of its elements from memory.
// This should be called before the program terminates.
// Make sure you free all the dll's too.
void free_graph(graph_t* g){
    if (g == NULL) {   
        return;
    }
    if (graph_num_nodes(g) == 0) {
        free_dll(g->nodes);
        free(g);
        return;
    }
    node_t* current_n = g->nodes->head;
    while (current_n != NULL) {
        // free graph nodes' neighbors in/out 
        graph_node_t* g_node = (graph_node_t*)current_n->data;
        free_dll(g_node->outNeighbors);
        free_dll(g_node->inNeighbors);
        // free graph node
        free(g_node);
        current_n = current_n->next;
    }
     // free graph nodes from graph
    free_dll(g->nodes);
    free(g);
    return;
}


// print a graph
void print_g(graph_t* g) {
    if (g == NULL) {
        printf("graph is null");
        return;
    }
    if (graph_num_nodes(g) == 0) {
    printf("graph is empty\n");
        return;
    }
    printf("GRAPH: \n");
    dll_t* g_dll = g->nodes;
    int i;
    for (i=0;i<dll_size(g_dll);i++) {
        graph_node_t* n = (graph_node_t*)dll_get(g_dll, i);
        int size_in = dll_size(n->inNeighbors);
        int size_out = dll_size(n->outNeighbors);
        printf("\nNODE %d", n->data);
        printf("\nin: %d nodes\n", size_in);
        // ins
        int j;
        for (j=0;j<size_in;j++) {
            graph_node_t* in_n = (graph_node_t*)dll_get(n->inNeighbors, j);
            printf("%d ", in_n->data);
        }
        printf("\nout: %d nodes\n", size_out);
        // ins
        int k;
        for (k=0;k<size_out;k++) {
            graph_node_t* out_n = (graph_node_t*)dll_get(n->outNeighbors, k);
            printf("%d ", out_n->data);
        }
    }
    printf("\nnum nodes: %d\n", graph_num_nodes(g));
    printf("num edges: %d\n\n", graph_num_edges(g));
    return;
}

#endif
