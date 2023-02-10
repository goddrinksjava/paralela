package paralela;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class RandomFillTask extends RecursiveAction {
  static final int THRESHOLD = 10;
  private static final int MIN = 1;
  private static final int MAX = 100;
  private int[] array;

  RandomFillTask(int[] array) {
    this.array = array;
  }

  @Override
  protected void compute() {
    int n = (int) Math.ceil(array.length / THRESHOLD);
    invokeAll(IntStream
        .rangeClosed(0, n)
        .mapToObj(x -> new ComputeTask(x * THRESHOLD))
        .toList());
  }

  class ComputeTask extends RecursiveAction {
    private int offset;

    ComputeTask(int offset) {
      this.offset = offset;
    }

    @Override
    protected void compute() {
      int lim = Math.min(array.length, THRESHOLD + offset);
      for (int i = offset; i < lim; i++) {
        array[i] = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
      }
    }
  }
}
