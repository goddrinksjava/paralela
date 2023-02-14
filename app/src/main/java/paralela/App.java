package paralela;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Stream;

public class App {
  public static void main(String[] args) {
    try {
      long start, end;

      File f = new File("D:/");
      String pattern = ".dll";

      start = System.nanoTime();
      FileFinder.find(f, pattern);
      end = System.nanoTime();
      System.out.println("\n\nTiempo transcurrido (secuencial): " + (end - start) / 1_000_000d + "ms\n\n");

      start = System.nanoTime();
      new ParallelFileFinder(f, pattern).invoke();
      end = System.nanoTime();
      System.out.println("\n\nTiempo transcurrido (paralela): " + (end - start) / 1_000_000d + "ms\n\n");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class ParallelFileFinder extends RecursiveAction {
  private File file;
  private String pattern;

  ParallelFileFinder(File file, String pattern) {
    this.file = file;
    this.pattern = pattern;
  }

  @Override
  protected void compute() {
    if (file.isDirectory()) {
      File[] files = file.listFiles();

      if (files == null) {
        return;
      }

      invokeAll(Stream
          .of(files)
          .map(f -> new ParallelFileFinder(f, pattern))
          .toList());
      return;
    }

    if (file.getName().contains(pattern)) {
      BasicFileAttributes bfa;
      try {
        bfa = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        String creationTime = bfa.creationTime().toString();

        System.out.println(
            "Nombre: " + file.getName() + '\n' +
                "Ruta: " + file.getAbsolutePath() + '\n' +
                "Fecha de creación: " + creationTime + '\n' +
                "Tamaño: " + file.length() + " bytes" + '\n');
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}

class FileFinder {
  public static void find(File file, String pattern) throws IOException {
    if (file.isDirectory()) {

      File[] files = file.listFiles();

      if (files == null) {
        return;
      }

      for (File f : files) {
        find(f, pattern);
      }
      return;
    }

    if (file.getName().contains(pattern)) {
      BasicFileAttributes bfa = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
      String creationTime = bfa.creationTime().toString();

      System.out.println(
          "Nombre: " + file.getName() + '\n' +
              "Ruta: " + file.getAbsolutePath() + '\n' +
              "Fecha de creación: " + creationTime + '\n' +
              "Tamaño: " + file.length() + " bytes" + '\n');
    }
  }
}
