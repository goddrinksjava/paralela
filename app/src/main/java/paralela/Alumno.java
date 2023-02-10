package paralela;

import java.util.concurrent.ThreadLocalRandom;

public class Alumno extends Thread {
  public static final Integer PESO_MIN = 2;
  public static final Integer PESO_MAX = 5;

  private Cesto cesto;
  private String nombre;

  public Alumno(Cesto cesto, String nombre) {
    this.cesto = cesto;
    this.nombre = nombre;
  }

  @Override
  public void run() {
    for (int i = 0; i < 5; i++) {
      Integer peso = ThreadLocalRandom.current().nextInt(PESO_MIN, PESO_MAX + 1);
      try {
        cesto.tirar(peso, nombre);
      } catch (InterruptedException e) {
        break;
      }
    }
  }
}
