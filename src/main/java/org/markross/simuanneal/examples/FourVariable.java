package org.markross.simuanneal.examples;

import java.util.List;
import java.util.ArrayList;
import org.markross.simuanneal.SAInterface;

/*
Problem: You are making 4 products.  The specs for each are below:

Product A
- 3 hours of labor
- 2 chips
- 1 ore
profit: $90 per unit sold

Product B
- 5 hours of labor
- 4 chips
profit: $130 per unit sold

Product C
- 1 hours of labor
- 10 chips
- 2 ore
profit: $70 per unit sold

Product D
- 2 hours of labor
- 2 chips
- 4 ore
profit: $80 per unit sold

Restraints:
- 60 hours of labor
- 100 chips available
- 150 ore available

Goal: Maximize your profits.
*/
public class FourVariable {

  List<Integer> numbersOfEachProduct = new ArrayList<Integer>();

  public class ShopFunctions<Integer> implements SAInterface<Integer> {
    public double costCalculation(List<Integer> variables) {
      int productA = variables.get(0);
      int productB = variables.get(1);
      int productC = variables.get(2);
      int productD = variables.get(3);

      double profit =
        (productA * 90) + (productB * 130) + (productC * 70) + (productD * 80);

      return profit;
    }
    public void mutate(List<Integer> variables) {

    }
  }


  public static void main(String[] args) {
    System.out.println("Hello, World!");
  }



}
