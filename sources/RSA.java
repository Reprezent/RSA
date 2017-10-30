// Alex Riedel, J.T. Liso, Sean Whalen
// COSC 583 Fall 2017
// Programming Assignment 2


import java.nio.file.Paths;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

public class RSA {
	private BigInteger pubKey;
	private BigInteger secKey;
	private BigInteger N;
	private BigInteger order;
	private int numBits;

	private BigInteger e;
	private BigInteger d;

	//reads in key file and generates RSA class based on that
	RSA(String path, boolean enc) throws java.io.IOException{
		BufferedReader reader = new BufferedReader(new FileReader(path));

			
		String currLine;

		for(int i = 0; i < 3; i++){
			currLine = reader.readLine();
			
			//exiting if missing info
			if(currLine == null){
				System.err.printf("ERROR: invalid key file, line %d%n", i+1);
				System.exit(1);
			}

			switch(i){
				case 0: //first line num bits
					numBits = Integer.parseInt(currLine);
					break;
				case 1: //second line N
					N = new BigInteger(currLine);
					break;
				case 2: //third line e or d
					if(enc)
						e = new BigInteger(currLine);
					else
						d = new BigInteger(currLine);
				default: //should never get here, included for completeness
					break;
			}
		}

		reader.close();
	}

	//generates RSA with n bits for N
	RSA(int n){
		numBits = n;
		SecureRandom rand = new SecureRandom();

		//generate p and q
		BigInteger p = BigInteger.probablePrime(numBits/2, rand); 
		BigInteger q = BigInteger.probablePrime(numBits/2, rand);

		// N = p*q
		N = p.multiply(q);

		// order of N = (p-1)(q-1)
		order =  p.subtract(new BigInteger("1")).multiply(q.subtract(new BigInteger("1")));

		//calculate e that is coprime to order
		e = BigInteger.probablePrime(order.bitLength(), rand);

		//looping until the gcd between order and e is 1
		while(order.gcd(e).compareTo(new BigInteger("1")) != 0){
			e = e.add(new BigInteger("1"));

		}
		
		//making e mod the order of the group if e >= order
		if(order.compareTo(e) != 1)
			e = e.mod(order);


		//d is the modInverse of e with respect to order
		d = e.modInverse(order);
	}

	public void encrypt(String input, String output) throws java.io.IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		BufferedReader reader = new BufferedReader(new FileReader(input));

		String currLine = reader.readLine();

		//exiting on bad input
		if(currLine == null){
			System.err.println("ERROR: input file read");
			System.exit(1);
		}

		reader.close();

		BigInteger msg = new BigInteger(currLine);

		//encrypting...
		BigInteger encrypted = msg.modPow(e, N);

		//writing encrypted message
		writer.write(encrypted.toString());
		writer.newLine();
		writer.close();
	}

	public void decrypt(String input, String output) throws java.io.IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		BufferedReader reader = new BufferedReader(new FileReader(input));

		String currLine = reader.readLine();

		//exiting on bad input
		if(currLine == null){
			System.err.println("ERROR: input file read");
			System.exit(1);
		}

		reader.close();

		BigInteger msg = new BigInteger(currLine);

		//decrypting...
		BigInteger decrypted = msg.modPow(d, N);

		//writing decrypted message
		writer.write(decrypted.toString());
		writer.newLine();
		writer.close();
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
		writer.newLine();

		writer.close();
	}

	public void writesecKey(String path) throws java.io.IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));

		//first line contains number of bits in N
		writer.write(Integer.toString(numBits));
		writer.newLine();

		//second line contains N
		writer.write(N.toString());
		writer.newLine();

		//third line contains d
		writer.write(d.toString());
		writer.newLine();
		
		writer.close();
	}

}
