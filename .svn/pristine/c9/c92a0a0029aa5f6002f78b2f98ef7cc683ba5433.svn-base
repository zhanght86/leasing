/**
 * TODO 
 * @author LUYANFENG @ 2015年7月10日
 */
package test.comm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import com.pqsoft.weixinfw.utils.AbstractThreadJobBean;
import com.pqsoft.weixinfw.utils.PQsoftThreadUtils;

/**
 * TODO 
 * @author LUYANFENG @ 2015年7月10日
 *
 */
public class TestThread {

	
	@Test
	public void test1(){
		
		System.out.println("............1");
		ExecutorService execServ = Executors.newCachedThreadPool();
		execServ.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("..........do"+Thread.currentThread().getId());
			}
		});
		System.out.println("............2");
	}
	@Test
	public void test2(){
		
		ExecutorService execServ = Executors.newFixedThreadPool(5);
		execServ.execute(new Runnable() {
			@Override
			public void run() {
				calc(1, 1);
			}
		});
		execServ.execute(new Runnable() {
			@Override
			public void run() {
				calc(2, 2);
			}
		});
	}
	
	private void calc(int a, int b){
		synchronized(this.getClass()){
			a += 1;
			System.out.println("11="+Thread.currentThread().getId());
		}
		System.out.println(a+b);
	};
	
	
	@Test
	public void test4(){
		List<Future> futures = new ArrayList<Future>();
		ExecutorService execServ = Executors.newFixedThreadPool(10);
		Future<?> submit2 = execServ.submit(new Thread() {
			@Override
			public void run() {
				try {
					while(true){
						System.out.println("--------testtesttets--------");
						System.out.println(111);
						Thread.sleep(100);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		futures.add(submit2);
		try {
//			Thread.sleep(5000);
			for(Future f : futures){
				System.out.println(f.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	/**
	 * XXX 为何main方法中可以，在junit中就不行呢？
	 * @param args
	 * @author LUYANFENG @ 2015年8月11日
	 */
	public static void main(String[] args) {
		PQsoftThreadUtils newInstance = PQsoftThreadUtils.newInstance(4);
		newInstance.addNewThreadJob(new AbstractThreadJobBean() {
			@Override
			public void autoRun() {
				System.out.println("1");
			}
			@Override
			public long sleep() {
				return 2000;
			}
			/* (non-Javadoc)
			 * @see com.pqsoft.weixinfw.utils.AbstractThreadJobBean#getJobName()
			 */
			@Override
			public String getJobName() {
				return "test1";
			}
		});
		
		newInstance.addNewThreadJob(new AbstractThreadJobBean() {
			@Override
			public void autoRun() {
				System.out.println("2");
			}

			@Override
			public long sleep() {
				return 2000;
			}
			
		});
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		newInstance.shutdown("test1");
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}
	
	//--------------------------------------------------------------------------------------
	@Test
	public void testInt1(){
		A a = new A(1);
		System.out.println(a);
		A a2 = new A(2);
		System.out.println(a);
		System.out.println(a2);
	}
}

class A{
	private static int a = 1;

	public A(int a) {
		A.a = a;
	}
	@Override
	public String toString() {
		return a+"";
	}
	
}
