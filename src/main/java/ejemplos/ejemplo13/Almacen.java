package ejemplos.ejemplo13;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Almacen {
	private Lock lock = new ReentrantLock();
	private Condition lleno = lock.newCondition();
	private Condition vacio = lock.newCondition();
	private String[] productos;
	private int stock = 0;

	public Almacen(int capacidad) {
		productos = new String[capacidad];
	}

	public void almacenar(String producto) {
		try {
			lock.lock();
			while (stock == productos.length)
				try {
					lleno.await();
				} catch (InterruptedException e) {
				}
			productos[stock++] = producto;
		} finally {
			vacio.signalAll();
			lock.unlock();
		}
	}

	public String retirar() {
		try {
			lock.lock();
			while (stock == 0)
				try {
					vacio.await();
				} catch (InterruptedException e) {
				}
			return productos[--stock];
		} finally {
			lleno.signalAll();
			lock.unlock();
		}
	}
}
