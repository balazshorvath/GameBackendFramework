package hu.sovaroq.framework.performance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class MethodInvocationSpeedTest {

	@Test
	public void testInvocation() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		TestClass test = new TestClass();

		
		System.out.println("Ivoking normally");
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			test.method();
		}
		System.out.println("Time spent: " + (System.currentTimeMillis() - start));
		
		
		
		Method m = TestClass.class.getMethod("method");
		
		System.out.println("Ivoking via reflection with 'pre-located' method");
		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			m.invoke(test);
		}
		System.out.println("Time spent: " + (System.currentTimeMillis() - start));
		
		System.out.println("Ivoking via reflection with 'on-time located' method");
		start = System.currentTimeMillis();
		Method m1;
		for (int i = 0; i < 100000; i++) {
			m1 = TestClass.class.getMethod("method");
			m1.invoke(test);
		}
		System.out.println("Time spent: " + (System.currentTimeMillis() - start));
		
	}
	
	class TestClass{
		public void method(){
			
		}
	}
}
