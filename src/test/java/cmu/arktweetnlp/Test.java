package cmu.arktweetnlp;

public class Test {

	public static void main(String[] args) {
		String hello = "Hello w    orld";
		String[] h = hello.split(" ");
		System.out.println(h.length);
		for (String t : h) System.out.println(t);
	}
}
