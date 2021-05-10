package tw.yukina.dcdos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

//		new Thread(() -> {
//			System.out.println("12345");
//		}).start();

//		SumThread sum = new SumThread();
//		Thread thread = new Thread(sum);
//		thread.start();
//		System.out.println("Flag 1");
//
//		try {
//			TimeUnit.SECONDS.sleep(5);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		synchronized(sum) {
//			sum.notify();
//		}
//
//		int result = sum.sum;
//		System.out.println("Count result is:" + result);
	}
}

class SumThread implements Runnable {
	int sum = 0;
	@Override
	public void run() {
		synchronized(this) {
			for (int i = 0 ; i <= 100 ; i++) {
				sum = sum + i;
				System.out.println(sum);
			}
			try {
				wait();
				System.out.println("after wait");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
