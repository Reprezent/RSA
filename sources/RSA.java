// Alex Riedel, J.T. Liso, Sean Whalen
// COSC 583 Fall 2017
// Programming Assignment 2

import java.math.BigInteger;
import java.security.SecureRandom;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class RSA {
	private BigInteger pubKey;
	private BigInteger secKey;
	private BigInteger N;
	private BigInteger order;
	private int numBits;

	private BigInteger e;
	private BigInteger d;


	RSA(int n){
		numBits = n;
		SecureRandom rand = new SecureRandom();

		//generate p and q
		BigInteger p = BigInteger.probablePrime(numBits/2, rand); //numBits or numBits/2 c???
		BigInteger q = BigInteger.probablePrime(numBits/2, rand);

		// N = p*q
		N = p.multiply(q);

		// order of N = (p-1)(q-1)
		order =  p.subtract(new BigInteger("1")).multiply(q.subtract(new BigInteger("1")));

		//calculate e that is coprime to order
		//need to figure out how to do this
		e = BigInteger.probablePrime(order.bitLength(), rand);

		//d is the modInverse of e with respect to N
		d = e.modInverse(N);
	}

	public void writepubKey(String path) throws java.io.IOException{ 
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		
		//first line contains number of bits in N
		writer.write(Integer.toString(numBits));
		writer.newLine();

		//second line contains N
		writer.write(N.toString());
		writer.newLine();

		//third line contains e
		writer.write(e.toString());
	}

	public void writesecKey(String path)throws java.io.IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		
		//first line contains number of bits in N
		writer.write(Integer.toString(numBits));
		writer.newLine();

		//second line contains N
		writer.write(N.toString());
		writer.newLine();

		//third line contains d
		writer.write(d.toString());
	}

}
