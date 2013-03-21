
package org.ga.algorithm;
import java.util.*;
import java.io.*;

public class NodeTree {
  public static final int CROSSBREED_SIMPLE = 1;
  public static final int CROSSBREED_SEED = 2;
  public static final int CROSSBREED_DOMINANT = 3;
  private Node m_tree;
  private static Random random = new Random();
  
  private NodeTree(Node tree) {
    this.m_tree = tree;
  }
  
  public NodeTree(int min_level, int max_level) {
    this.m_tree = create_tree(1, min_level, max_level);
  }
  
  public double evaluate(double x, double y) {
    return this.m_tree.evaluate(x, y);
  }
  
  public NodeTree get_copy() {
    return new NodeTree(this.m_tree.get_copy());
  }
  
  public static NodeTree clone(NodeTree[] clonables) {
    if (clonables == null) {
      System.err.println("NodeTree.clone: No sources available");
      return null;
    }
    int index = random_type(clonables.length, -1);
    return clonables[index].get_copy();
  }
  
  public static NodeTree crossbreed(NodeTree[] parents, int type) {
    if (parents == null ){
      System.err.println("NodeTree.crossbreed: No parents available");
      return null;
    }
    switch (type) {
    case CROSSBREED_SIMPLE:
      return NodeTree.crossbreed_simple(parents);
    case CROSSBREED_SEED:
      return NodeTree.crossbreed_jaakko(parents);
    case CROSSBREED_DOMINANT:
      return NodeTree.crossbreed_janne(parents);
    default:
      System.err.println("NodeTree.crossbreed: invalid crossbreed type: " + type);
      return null;
    }
    // You can select the desired breeding method by commenting the others out.
    //return NodeTree.crossbreed_simple(parents);
    /*
    NodeTree child_tree;
    if (parents.length == 1){
      return child_tree = parents[0].get_copy();
    }
    else {  
      int parent1 = random_type(parents.length, -1);
      int parent2 = random_type(parents.length, parent1);
      child_tree = new NodeTree(new NodeAdd(parents[parent1].m_tree.get_copy(), parents[parent2].m_tree.get_copy()));
      return child_tree;
    }
    //*/
  }
  private static NodeTree crossbreed_janne(NodeTree[] parents){
    if (parents.length == 1){
      return new NodeTree(NodeTree.create_node(parents[0].m_tree.get_copy(), (Class)null));
    }
    else {
      int parent1 = random_type(parents.length, -1);
      int parent2 = random_type(parents.length, parent1);
      int child1 = random_type(parents[parent1].m_tree.get_child_count(), -1);
      Node child = parents[parent2].m_tree.get_copy();
      Node parent = parents[parent1].m_tree.get_copy();
      parent.set_child(child1, child);
      return new NodeTree(parent);
    }
  }
  private static NodeTree crossbreed_simple(NodeTree[] parents) {
    if (parents.length == 1){
      return new NodeTree(NodeTree.create_node(parents[0].m_tree.get_copy(), (Class)null));
    }
    else {  
      int parent1 = random_type(parents.length, -1);
      int parent2 = random_type(parents.length, parent1);
      return new NodeTree(NodeTree.create_node(parents[parent1].m_tree.get_copy(), parents[parent2].m_tree.get_copy(), (Class)null));
    }
  }
 
  private static NodeTree crossbreed_jaakko(NodeTree[] parents) {
    int parent1 = random_type(parents.length, -1);
    Node seed1 = parents[parent1].m_tree.get_copy();
    seed1 = NodeTree.prune_children(seed1, seed1.get_tree_size() / 2, 0);
    if (parents.length == 1){
      return new NodeTree(NodeTree.create_node(seed1, (Class)null));
    }
    else {  
      int parent2 = random_type(parents.length, parent1);
      Node seed2 = parents[parent2].m_tree.get_copy();
      seed2 = NodeTree.prune_children(seed2, seed2.get_tree_size() / 2, 0);
      return new NodeTree(NodeTree.create_node(seed1, seed2, (Class)null));
    }
  }
  
  private static double factorial(double value, double low_limit) {
    if (value <= low_limit)
      return 1;
    else
      return value * factorial(value - 1, low_limit);
  }

  private static Node prune_children(Node node, int to_prune, int prunable_elsewhere) {
    int child_count = node.get_child_count();
    // If no children, can't prune. Also pruning accuracy is +/-1.
    if (child_count == 0 || to_prune <= 0)
      return node;
    
    double pruning_prob;
    int prunable_here = node.get_tree_size() - 1;
    //for (int ind = 0; ind < node.get_child_count(); ind++)
    //  prunable_here += node.get_child(ind).get_tree_size() - 1;
    if (to_prune >= prunable_here + prunable_elsewhere) {
      // All the rest must be pruned.
      pruning_prob = 1;
    }
    else if (to_prune < prunable_here) {
      // This is too big tree to be pruned.
      pruning_prob = 0;
    }
    else {
      // Calculate probability that all the children should be pruned:
      // C( prun_here prun_here ) * C( prun_else prun-prun_here ) / C( prun_here+prun_else prun )
      // = C( prun_else prun-prun_here ) / C( prun_here+prun_else prun)
      // = ( prun_else! * prun! ) / ( (prun-prun_here)! * (prun_here+prun_else)! )
      double else_per_prune_else = factorial(prunable_elsewhere, to_prune - prunable_here);
      double prunable_per_prune = factorial(prunable_here + prunable_elsewhere, to_prune);
      pruning_prob = else_per_prune_else / prunable_per_prune;
    }
    
    if (random.nextDouble() <= pruning_prob) {
      // Prune this node.
//      System.out.println("Pruned with prob: " + pruning_prob);
      return get_random_leaf_node(node);
    }
    else {
      // Prune recursively.
//      System.out.println("Didn't prune with prob: " + pruning_prob);
      int pruned = 0;
      for (int ind = 0; ind < child_count; ind++) {
        int prunable_in_remaining_children = 0;
        int old_size = node.get_child(ind).get_tree_size();
        for (int jnd = ind + 1; jnd < child_count; jnd++) {
          prunable_in_remaining_children += node.get_child(jnd).get_tree_size() - 1;
        }
        node.set_child(ind,
                       prune_children(node.get_child(ind),
                                      to_prune - pruned,
                                      prunable_elsewhere + prunable_in_remaining_children));
        int new_size = node.get_child(ind).get_tree_size();
        pruned += old_size - new_size;
      }
      return node;
    }
  }
  
  public void mutate() {// Arpoo kummanlainen mutaatio
    if(random.nextDouble() < 0.8)

      this.m_tree = mutate_similar(this.m_tree);
    else
      this.m_tree = mutate_subtree(this.m_tree);
  }
  
  protected static Node mutate_similar(Node node) {
    int size = node.get_tree_size();
    int probability = random_type(size, -1);
    
    if (probability == 0) { //(1/(this.get_tree_size()+1)) {
      int count = node.get_child_count();
      switch (count) {
      case 0:
        return create_node(node.getClass());
      case 1:
        return create_node(node.get_child(0), node.getClass());
      case 2:
        return create_node(node.get_child(0), node.get_child(1), node.getClass());
      case 3:
        return create_node(node.get_child(0), node.get_child(1), node.get_child(2));
      default:
        return create_node(node.getClass());
      }
    }
    else {
      int ind = (int) (random.nextDouble() * node.get_child_count());
      node.set_child(ind, mutate_similar(node.get_child(ind)));
      return node;
    }
  }
  
  private static Node get_random_leaf_node(Node node) {
    int child_count = node.get_child_count();
    if (child_count == 0) {
      return node;
    }
    else {
      int[] leaf_counts = new int[child_count];
      int total_leafs = 0;
      for (int ind = 0; ind < child_count; ind++) {
        leaf_counts[ind] = node.get_child(ind).get_leaf_count();
        total_leafs += leaf_counts[ind];
      }
      
      int child = (int)(random.nextDouble() * total_leafs);
      int leaf_ind = 0;
      for (int ind = 0; ind < child_count; ind++) {
        leaf_ind += leaf_counts[ind];
        if (child < leaf_ind) {
          return get_random_leaf_node(node.get_child(ind));
        }
      }
    }
    System.err.println("NodeTree.get_random_leaf_node: should not end up here!");
    return null;
  }
  
  protected static Node mutate_subtree(Node node) {
    int child_count = node.get_child_count();
    if (child_count == 0) {
      return create_tree(1, 2, 2);
    }
    else {
      int[] leaf_counts = new int[child_count];
      int total_leafs = 0;
      for (int ind = 0; ind < child_count; ind++) {
        leaf_counts[ind] = node.get_child(ind).get_leaf_count();
        total_leafs += leaf_counts[ind];
      }
      
      int child = (int)(random.nextDouble() * total_leafs);
      int leaf_ind = 0;
      for (int ind = 0; ind < child_count; ind++) {
        leaf_ind += leaf_counts[ind];
        if (child < leaf_ind) {
          node.set_child(ind, mutate_subtree(node.get_child(ind)));
          break;
        }
      }
      return node;
    }
  }
  
  private static int random_type(int size, int invalid_index) {
    int new_size = size;
    if (invalid_index >= 0 && invalid_index < size)
      new_size--;
    int type = (int)(random.nextDouble() * new_size);
    
    // Fix because according to API nextDouble may return 1.0.
    if (type >= new_size)
      type = new_size - 1;
    
    if (invalid_index >= 0 && invalid_index < size) {
      if (type >= invalid_index)
        type++;
    }
    return type;
  }
  
  private static Node create_node(Class unwanted) {
    int invalid_index = -1;
    if (unwanted == NodeConstant.class)
      invalid_index = 0;
    if (unwanted == NodeX.class)
      invalid_index = 1;
    if (unwanted == NodeY.class)
      invalid_index = 2;

    int type = random_type(3, invalid_index);
    
    switch (type) {
    case 0:
      return new NodeConstant();
    case 1:
      return new NodeX();
    case 2:
      return new NodeY();
    default:
      System.err.println("NodeTree.create_node0 invalid switch case.\n");
      return null;
    }
  }

  private static Node create_node(Node child1, Class unwanted) {
    int invalid_index = -1;
    if (unwanted == NodeSin.class)
      invalid_index = 0;
    if (unwanted == NodeCos.class)
      invalid_index = 1;
    if (unwanted == NodeAbs.class)
      invalid_index = 2;
    if (unwanted == NodePolar.class)
      invalid_index = 3;

    int type = random_type(4, invalid_index);

    switch (type) {
    case 0:
      return new NodeSin(child1);
    case 1:
      return new NodeCos(child1);
    case 2:
      return new NodeAbs(child1);
    case 3:
      return new NodePolar(child1);
    default:
      System.err.println("NodeTree.create_node1 invalid switch case.\n");
      return null;
    }
  }

  private static Node create_node(Node child1, Node child2, Class unwanted) {
    int invalid_index = -1;
    if (unwanted == NodeMultiply.class)
      invalid_index = 0;
    if (unwanted == NodeDivide.class)
      invalid_index = 1;
    if (unwanted == NodeAdd.class)
      invalid_index = 2;
    if (unwanted == NodeSubtract.class)
      invalid_index = 3;
    if (unwanted == NodeMax.class)
      invalid_index = 4;
    if (unwanted == NodeMin.class)
      invalid_index = 5;

    int type = random_type(6, invalid_index);
    
    switch (type) {
    case 0:
      return new NodeMultiply(child1, child2);
    case 1:
      return new NodeDivide(child1, child2);
    case 2:
      return new NodeAdd(child1, child2);
    case 3:
      return new NodeSubtract(child1, child2);
    case 4:
      return new NodeMax(child1, child2);
    case 5:
      return new NodeMin(child1, child2);
    default:
      System.err.println("NodeTree.create_node2 invalid switch case.\n");
      return null;
    }
  }

  private static Node create_node(Node child1, Node child2, Node child3) {
   return new NodeDistort(child1, child2, child3);
  }

  private static Node create_tree(int level, int min_level, int max_level) {
    int type;
    
    if (level >= max_level)
      type = (int)(random.nextDouble() * 3);
    else if (level < min_level)
      type = (int)(random.nextDouble() * 11) + 3;
    else 
      type = (int)(random.nextDouble() * 14);
    
    switch (type) {
    case 0:
      return new NodeX();
    case 1:
      return new NodeY();
    case 2:
      return new NodeConstant();
    case 3:
      return new NodeSin(create_tree(level+1, min_level, max_level));
    case 4:
      return new NodeCos(create_tree(level+1, min_level, max_level));
    case 5:
      return new NodeAbs(create_tree(level+1, min_level, max_level));
    case 6:
      return new NodePolar(create_tree(level+1, min_level, max_level));
    case 7:
      return new NodeMultiply(create_tree(level+1, min_level, max_level),
                              create_tree(level+1, min_level, max_level));
    case 8:
      return new NodeDivide(create_tree(level+1, min_level, max_level),
                            create_tree(level+1, min_level, max_level));
    case 9:
      return new NodeAdd(create_tree(level+1, min_level, max_level),
                         create_tree(level+1, min_level, max_level));
    case 10:
      return new NodeSubtract(create_tree(level+1, min_level, max_level),
                              create_tree(level+1, min_level, max_level));
    case 11:
      return new NodeMax(create_tree(level+1, min_level, max_level),
                         create_tree(level+1, min_level, max_level));
    case 12:
      return new NodeMin(create_tree(level+1, min_level, max_level),
                         create_tree(level+1, min_level, max_level));
    case 13:
      return new NodeDistort(create_tree(level+1, min_level, max_level),
                             create_tree(level+1, min_level, max_level),
                             create_tree(level+1, min_level, max_level));
    default:
      return new NodeConstant();
    }
  }
  
  public void save_tree(File file) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      writer.write("digraph G {\n");//size=\"4,4\"\n");
      this.m_tree.write_tree(writer, "A");
      writer.write("}\n");
      writer.close();
    }
    catch (FileNotFoundException excep) {
      // What to do?
      System.err.println("NodeTree:save_tree: file not found.");
    }
    catch (IOException excep) {
      // What to do?
      System.err.println("NodeTree:save_tree: io exception.");
    }
  }
}
