package paralela;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

public class App {
  public static void main(String[] args) {
    try {
      searchFiles(new File("/home/goddr/Pictures"), ".jpg");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void searchFiles(File file, String pattern) throws IOException {
    if (file.isDirectory()) {
      for (File f : file.listFiles()) {
        searchFiles(f, pattern);
      }
      return;
    }

    if (file.getName().contains(pattern)) {
      BasicFileAttributes bfa = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
      String creationTime = bfa.creationTime().toString();

      System.out.println("Nombre: " + file.getName());
      System.out.println("Ruta: " + file.getAbsolutePath());
      System.out.println("Fecha de creación: " + creationTime);
      System.out.println("Tamaño: " + file.length() + " bytes");
      System.out.println();
    }
  }
}
