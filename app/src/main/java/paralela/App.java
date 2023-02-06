package paralela;

import java.util.List;

public class App {
  public static void main(String[] args) {
    Cesto cesto = new Cesto();

    List<Alumno> alumnos = List.of(
        new Alumno(cesto, "Juan"),
        new Alumno(cesto, "Miguel"),
        new Alumno(cesto, "Perla"),
        new Alumno(cesto, "Edson"),
        new Alumno(cesto, "Erick"));
    Intendente intendente = new Intendente(cesto);

    alumnos.forEach(alumno -> alumno.start());
    intendente.start();

    alumnos.forEach(alumno -> {
      try {
        alumno.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    intendente.interrupt();
  }
}
