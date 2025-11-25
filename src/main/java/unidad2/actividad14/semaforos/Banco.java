package unidad2.actividad14.semaforos;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Banco {

	int libres;
	ReentrantLock lock = new ReentrantLock();
	Condition lleno = lock.newCondition();
	
	public Banco(int plazas) {
		libres = plazas;
	}
	
	public boolean getPlaza() {
		try {
			if (!lock.tryLock(500, TimeUnit.MILLISECONDS))
				return false;
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName() + ": interrumpido en wait");
			Thread.currentThread().interrupt();
			return false;
		}
		try {
			while (libres == 0) {
				try {
					lleno.await();
				} catch (InterruptedException e) {
					System.out.println(Thread.currentThread().getName() + ": interrumpido en wait");
					Thread.currentThread().interrupt();
					return false;
				}
			}
			libres--;
			return true;
		} finally {
			lock.unlock();
		}
	}
	
	public synchronized void liberarPlaza() {
		lock.lock();
		try {
			libres++;
		} finally {
			lleno.signalAll();
			lock.unlock();
		}
	}
	
}
