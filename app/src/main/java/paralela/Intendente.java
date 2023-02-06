package paralela;

public class Intendente extends Thread {
  private Cesto cesto;

  public Intendente(Cesto cesto) {
    this.cesto = cesto;
  }

  @Override
  public void run() {
    while (true) {
      try {
        cesto.vaciar();
      } catch (InterruptedException e) {
        break;
      }
    }
  }
}
