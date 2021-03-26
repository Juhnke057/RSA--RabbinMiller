package pack;

import java.math.BigInteger;
import java.util.Random;

public class RabinMillerTest {
	private static final BigInteger zero = BigInteger.ZERO; //ref
	private static final BigInteger one = BigInteger.ONE; //ref
	private static final BigInteger two = new BigInteger("2"); //ref
	private static final BigInteger three = new BigInteger("3"); //ref

	private static BigInteger RandomBigInt(BigInteger bottom, BigInteger top) { //takes in 2 BigInteger
		Random rndom = new Random(); //generate random nbr
		BigInteger res; 
		do {
			res = new BigInteger(top.bitLength(), rndom); //makes res a random BigInteger as big as top
		} while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0); //Make sure it is within bounds
		return res;
	}
	
	public static boolean isPrime(BigInteger n, int iteration) {//gives true of false if prime or not
		if (n.compareTo(one) == 0) //Gives false if equal to 1
			return false;
		if (n.compareTo(three) < 0) // Gives true if smaller than 3
			return true;
		int s = 0;
		BigInteger d = n.subtract(one); // n - 1
		while (d.mod(two).equals(one)) { //while (n-1) % 2 = 0
			s++;
			d = d.divide(two); // (n-1) / 2
		}
		for (int c = 0; c < iteration; c++) { // iteration == nbr times
			BigInteger a = RandomBigInt(two, n.subtract(one)); //Generates random number in the span of [2, n-1]
			BigInteger x = a.modPow(d, n); //x = (a^n-1) % n
			if (x.equals(one) || x.equals(n.subtract(one))) //x == 1 OR x == (n - 1)
				continue; //Go Again
			int t = 0;
			for (; t < s; t++) {
				x = x.modPow(two, n); //x = x^2 % n
				if (x.equals(one)) //x == 1
					return false; //Not A Prime
				if (x.equals(n.subtract(one))) //x == n-1, exit for-loop
					break;
			}
			if (t == s) // None of the steps made x equal n-1.
				return false; //not prime
		}
		return true; //Its a prime
	}

	

	
}

