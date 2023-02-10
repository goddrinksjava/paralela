package paralela;

public class App {
  public static void main(String[] args) {
    int[] array = new int[100];

    new RandomFillTask(array).invoke();
    printM(array);

    System.out.println();

    new PrimeTask(array).invoke();
    printM(array);
  }

  public static void printM(int[] array) {
    for (int i = 0; i < array.length; i += 10) {
      for (int j = 0; j < Math.min(10, array.length - i); j++) {
        int v = array[i + j];
        if (v < 10) {
          System.out.printf(" %d ", array[i + j]);
        } else {
          System.out.printf("%d ", array[i + j]);
        }
      }
      System.out.println();
    }
  }
}
