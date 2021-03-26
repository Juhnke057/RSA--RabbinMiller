package pack;

import java.math.BigInteger;
import java.util.Random;
import java.util.ArrayList;

public class main {
	
	private static BigInteger p; //declare the integers and others
	private static BigInteger q;
	private static BigInteger one = new BigInteger("1");
	private static boolean qIsPrime = false;
	private static boolean pIsPrime = false;
	private static long time1;
	private static long time2;
	private static long totTime;
	private static RabinMillerTest rb = new RabinMillerTest();
	final static BigInteger zero = BigInteger.ZERO;
	private static final BigInteger two = new BigInteger("2");


	
	
	


	public static void main(String[] args) {
		
	
		time1 = System.currentTimeMillis(); // start timer
		p = primeGenerator(); //generate primes of desired size
		q = primeGenerator();




		
		pIsPrime = RabinMillerTest.isPrime(p, 20); //Do the rabinMillerTest iteration as desired
		qIsPrime = RabinMillerTest.isPrime(q, 20);
		
		System.out.println("P Is Prime : " + pIsPrime);  // print if they make it
		System.out.println("Q Is Prime : " + qIsPrime);
		
		System.out.println("Q = : " + q + "\n" +"P = : " + p ); //Print the primes
		
		RSA(p, q); //Run Euclidean Algorithm and Encryption
		
		
		time2 = System.currentTimeMillis(); //End timer
		totTime = time2 - time1; // calc. total time
		System.out.println("Total Time To Execute : " + totTime +" ms"); //Print time in ms (sec)
		
	}
		
		
		public static BigInteger primeGenerator() {
			
			
			Random rand1 = new Random(System.nanoTime()); //random with top as big number
			
			BigInteger prime = new BigInteger(512, rand1); //generate a bigInteger based on random with size 512
			
			while(!RabinMillerTest.isPrime(prime, 20)) { //do the RabinMillerTest
				rand1 = new Random(System.nanoTime());  //Do again if fail
				
				 prime = new BigInteger(512, rand1);
			}
			return prime; // return the prime
			
		}
		private static BigInteger EuclideanAlorithm(BigInteger d1, BigInteger d2) { //d1 = e, d2 = m = (p-1)(q-1)
			BigInteger t1 = one, t2 = zero, q, u1 = zero, v2 = one; //REF
			while (!d2.equals(zero)){ //d2 != 0
				q = d1.divide(d2);  //q = e / ((p-1)(q-1))
				d1 = d1.mod(d2); //d1 = e % (p-1)(q-1)
		    	t1 = t1.subtract(q.multiply(t2)); //t1 = t1 -(q*t2)
		    	u1 = u1.subtract(q.multiply(v2)); //u1 = u1 -(q*v2)
	    		q = d2.divide(d1);  //q = d2/d1
	    		d2 = d2.mod(d1); //d2 = d2 % d1
	    		t2 = t2.subtract(q.multiply(t1)); //t2 = t2 - (q*t1)  
	    		v2 = v2.subtract(q.multiply(u1)); //v2 = v2 - (*u1)
			}
			System.out.println("U: " + u1); //printing
			System.out.println("d: " + d1);
			System.out.println("InverseMod/V: " + t1);
			return t1; //return the inverse
		
		
	}
		
		
		public static void RSA(BigInteger d1, BigInteger d2) {
			BigInteger e = new BigInteger("2");
			e = e.pow(16).add(one);
			//BigInteger e = new BigInteger("21"); //for assignment 4
			//BigInteger m = new BigInteger("93");
			System.out.println("e: " + e); //e = (2^16) + 1
			
			
			BigInteger inverseMod = EuclideanAlorithm(e, d1.subtract(one).multiply(d2.subtract(one))); //Run the Euclidean Alogrithm 
			//BigInteger inverseMod = EuclideanAlorithm(e, m);
			
			
			//Encrypting
			BigInteger N = d1.multiply(d2); // N = d1 * d2
			Random rnd = new Random(); // gen. random
			//BigInteger s = new BigInteger("1");
			BigInteger s = new BigInteger(N.bitLength(), rnd); //Gen. on random
			while ((s.compareTo(N) >= 0) || s.compareTo(one) <= 0) { // check if s is ok
				s = new BigInteger(N.bitLength(), rnd);//if not, generate new
			}
			
			System.out.println("S: " + s);//printing
			BigInteger c = s.modPow(e, N);
			System.out.println("C: " + c);
			BigInteger z = c.modPow(inverseMod, N);
			System.out.println("Z: " + z);
			if(s.equals(z)) {
				if(!s.equals(c)) {
					System.out.println("Medelande har Krypterats och dekrypterats");
					System.out.println("100% fool proof");
				}
				
			}
			
		}
		
		

}
