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
	private int numBits;

	private BigInteger e;
	private BigInteger d;


	RSA(int n){
		numBits = n;
		N = BigInteger.probablePrime(numBits, new SecureRandom());

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

		//third line contains e
		writer.write(d.toString());
	}

}
