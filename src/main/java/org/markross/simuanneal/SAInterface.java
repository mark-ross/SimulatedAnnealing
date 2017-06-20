package org.markross.simuanneal;

import java.util.List;

public interface SAInterface <T> {
  // function to determine the 'cost' of the system
  public double costCalculation(List<T> values);
  // mutation function needed to explore the space
  public void mutate(List<T> values);
}
