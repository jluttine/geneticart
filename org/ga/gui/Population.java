package org.ga.gui;
import org.ga.algorithm.*;

// TURHA LUOKKA???
//*
public class Population {
  private Function[] m_functions;
  public Population(int size) {
    this.m_functions = new Function[size];
    for (int ind = 0; ind < size; ind++)
      this.m_functions[ind] = new Function(3,8);
  }

  // Returns function.
  Function get_function(int index) {
    return this.m_functions[index];
  }
  
  // Generates new functions by crossbreeding.
  void crossbreed() {
  	
  }
  
  // Generates totally new functions.
  void generate() {
  	
  }
  
  // Mutates existing functions.
  void mutate() {
    for (int ind = 0; ind < this.m_functions.length; ind++) {
      this.m_functions[ind].mutate();
    }
  }
}
//*/