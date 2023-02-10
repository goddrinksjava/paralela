package paralela;

import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;

public class PrimeTask extends RecursiveAction {
  static final int THRESHOLD = 10;
  private int[] array;

  PrimeTask(int[] array) {
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
        if (isPrime(array[i])) {
          array[i] = 0;
        }
      }
    }

    private static boolean isPrime(int number) {
      return IntStream.rangeClosed(2, (int) (Math.sqrt(number)))
          .allMatch(n -> number % n != 0);
    }
  }
}
