package test;

public class B extends A{
	public String a;
	public int b;
	
	public void fun1(){
		super.fun1();
		System.out.println("fun1___B");
	}
	
	public void fun2(){
		System.out.println("fun2___B");
	}
	
	public void fun3(){
		System.out.println("fun3___B");
	}
}
