## RSA, How does it work?
### KeyGen
First we create two primes with a given bit length *n* such that the tuple *(N, p, q)* where *N* is the multiplication of both *p* and *q*, thus generating a coprime. The order of this group is then *Φ(N) := (p - 1)(q - 1)*.
Afterwards an *e* is chosen such that `e > 1 && gcd(e, Φ(N)) == 1`. Then a *d* must be computed such that `d = [inverse(e) % *Φ(N)*]`. The resulting key is then the tuple *(N, e, d)*. The public key is *(N, e)* and the private key is *(N, d)*. This works because *d* is the inverse of *e* so when a message m is encrypted with *m*<sup>*e*</sup> mod *N* => c, the decryption is then computed c<sup>e</sup> = (*m*<sup>e</sup>)<sup>d</sup> = m<sup>ed</sup> = m<sup>ee<sup>-1</sup></sup> = m mod N.

### Padding (PKCS #1 v1.5)
Padding starts with two bytes, of 0x00 and 0x02 to denote the padding scheme used. Then r random bytes are computed where the lenght of r is (k - D - 3), 3 for padding, D is the length of the message in bytes, k is the length of N in bytes. Each message will be of length N/2 - 3 bytes. r must be N/2 of length. R must also not have any zeros since the serparator used is simply the null byte. 

### Encryption
Encryption is done by taking the message and separating it into (*N*/2 - 3) byte chunks. Computing a random *r* value of the size of *N/2* and then appending the message to it. Afterwards the modular exponentiation is computed on the resulting message with the public key *e*. That is then the cipher text. 

### Decryption
To Decrpyt you just take the private key *d* and do the modular exponentation up this, remove the padding by finding the first zero bytes after the first two version indicatior bytes and removing that chunk. THe resultant plaintext message is left. 
