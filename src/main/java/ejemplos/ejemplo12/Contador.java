package ejemplos.ejemplo12;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Contador {
	ReentrantLock lock = new ReentrantLock();
	private int n;

	public Contador(int n) {
		this.n = n;
	}

	public void inc() {
//		lock.lock();
//		try {
//			// dentro del bloque try se recoge la sección crítica
//			n++;
//		} finally {
//			lock.unlock();
//		}

		try {
			boolean isLockAcquired = lock.tryLock(1, TimeUnit.SECONDS);
			if (isLockAcquired) {
				try {
					n++;
				} finally {
					lock.unlock();
				}
			}
		} catch (InterruptedException e) {
		}

	}

	public int get() {
		return n;
	}
}
