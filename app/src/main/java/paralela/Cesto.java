package paralela;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cesto {
  private Integer pesoBasura = 0;
  private Integer cantidadBasura = 0;
  private Lock lock = new ReentrantLock();
  private Condition full = lock.newCondition();
  private Condition empty = lock.newCondition();
  private AtomicBoolean fullSignaled = new AtomicBoolean(false);
  private AtomicBoolean emptySignaled = new AtomicBoolean(false);

  public void tirar(Integer basura, String nombre) throws InterruptedException {
    lock.lock();

    try {
      while (cantidadBasura >= 10 || pesoBasura >= 16) {

        if (fullSignaled.compareAndSet(false, true)) {
          System.out.println("El cesto está lleno");
          full.signalAll();
          empty.await();
          System.out.println("El intendente vació el cesto");
          fullSignaled.set(false);
        } else {
          empty.await();
        }
      }

      cantidadBasura++;
      pesoBasura += basura;
      System.out.printf("%s tiró basura con un peso de %d. El cesto tiene %d basuras y un peso de %d\n",
          nombre, basura,
          cantidadBasura,
          pesoBasura);
    } finally {
      lock.unlock();
    }
  }

  public void vaciar() throws InterruptedException {
    lock.lock();

    try {
      while (cantidadBasura < 9 && pesoBasura < 16) {
        full.await();
      }

      cantidadBasura = 0;
      pesoBasura = 0;

      if (emptySignaled.compareAndSet(false, true)) {
        empty.signalAll();
        emptySignaled.set(false);
      }

    } finally {
      lock.unlock();
    }
  }
}
