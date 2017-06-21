package org.markross.simuanneal;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing<T> {
  int iterationCounter = 0;
  List<T> data;
  SAInterface<T> dataInterface;
  double temperature;
  double decreaseRate;

  public static class SimulatedAnnealingBuilder<T> {
    private double temperature;
    private double decreaseRate;
    private SAInterface<T> dataInterface;
    private List<T> data;

    public SimulatedAnnealingBuilder<T> setTemperature(double temperature) {
      this.temperature = temperature;
      return this;
    }

    public SimulatedAnnealingBuilder<T> setDecreaseRate(double decreaseRate) {
      this.decreaseRate = decreaseRate;
      return this;
    }

    public SimulatedAnnealingBuilder<T> setDataInterface(SAInterface<T> dataInterface) {
      this.dataInterface = dataInterface;
      return this;
    }

    public SimulatedAnnealingBuilder<T> setData(List<T> data) {
      this.data = data;
      return this;
    }

    public SimulatedAnnealing<T> build() {
      return new SimulatedAnnealing<T>(data, dataInterface, temperature, decreaseRate);
    }

  }

  SimulatedAnnealing(List<T> inputData, SAInterface<T> inputInterface,
                     double startTemp, double decreaseRate) {
    this.data = inputData;
    this.dataInterface = inputInterface;
    this.temperature = startTemp;
    this.decreaseRate = decreaseRate;
  }

  public List<T> optimize() {
    // create a psuedo random generator for our seeding function
    Random random = new Random(System.currentTimeMillis());

    // keep track of the previous set's score
    double previousEnergy = 0;
    List<T> previous = null;
    // keep track of the 'best' score
    double bestEnergy = 0;
    List<T> best = null;
    // keep a record of the traveling data
    double currentEnergy = 0;
    List<T> current = data;

    // optimize!
    while(temperature > 0) {
      System.out.println("Iteration #" + (iterationCounter++) + " -- temperature: " + temperature);
      // calculate energy
      currentEnergy = dataInterface.costCalculation(current);

      // compare with previous
      if(currentEnergy > previousEnergy) {
        // store as previous energy
        previousEnergy = currentEnergy;
        previous = current;

        if(currentEnergy > bestEnergy) {
          bestEnergy = currentEnergy;
          best = current;
        }

      } else {
        double probability = Math.exp(-(currentEnergy-previousEnergy)/temperature);
        if( probability > random.nextDouble()) {
          previousEnergy = currentEnergy;
          previous = current;
        }
      }

      // mutate the data set
      dataInterface.mutate(current);

      // decrease the termperature
      temperature -= decreaseRate;
    }

    // after the termperature has cooled enough...
    return best;
  }

  public static void main(String[] args) {
    System.out.println("Starting Simulation");

    List<Integer> parameters = new ArrayList<Integer>();
    parameters.add(10);
    parameters.add(16);

    SAInterface<Integer> si = new SAInterface<Integer>() {
      public double costCalculation(List<Integer> values) {
        return (values.get(0) * 2) - values.get(1);
      }
      public void mutate(List<Integer> values) {
        Random rand = new Random(System.currentTimeMillis());
        values.set(0, values.get(0) + rand.nextInt(5));
        values.set(1, values.get(1) - rand.nextInt(3));
      }
    };

    SimulatedAnnealing<Integer> sa =
     new SimulatedAnnealing.SimulatedAnnealingBuilder<Integer>()
        .setData(parameters)
        .setDataInterface(si)
        .setTemperature(100)
        .setDecreaseRate(1)
      .build();

    parameters = sa.optimize();

    for(int i : parameters) {
      System.out.println(i);
    }

  }

}
