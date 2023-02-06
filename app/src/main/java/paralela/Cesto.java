package paralela;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cesto {
  private Integer pesoBasura = 0;
  private Integer cantidadBasura = 0;
  private Lock lock = new ReentrantLock();
  private Condition full = lock.newCondition();
  private Condition empty = lock.newCondition();

  public void tirar(Integer basura) throws InterruptedException {
    lock.lock();

    try {
      while (cantidadBasura >= 10 || pesoBasura >= 20) {
        System.out.println("El cesto está lleno");
        full.signalAll();
        empty.await();
        System.out.println("El intendente vació el cesto");
      }

      cantidadBasura++;
      pesoBasura += basura;
      System.out.printf("El cesto tiene %d basuras y un peso de %d\n", cantidadBasura, pesoBasura);
    } finally {
      lock.unlock();
    }
  }

  public void vaciar() throws InterruptedException {
    lock.lock();

    try {
      while (cantidadBasura < 10 && pesoBasura < 20) {
        full.await();
      }

      cantidadBasura = 0;
      pesoBasura = 0;
      empty.signalAll();

    } finally {
      lock.unlock();
    }
  }
}
