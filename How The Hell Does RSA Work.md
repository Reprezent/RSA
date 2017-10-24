## RSA, How does it work?
### KeyGen
First we create two primes with a given bit length *n* such that the tuple *(N, p, q)* where *N* is the multiplication of both *p* and *q*, thus generating a coprime. The order of this group is then *Φ(N) := (p - 1)(q - 1)*.
Afterwards an *e* is chosen such that `e > 1 && gcd(e, Φ(N)) == 1`. Then a *d* must be computed such that `d = [inverse(e) % *Φ(N)*]`. The resulting key is then the tuple *(N, e, d)*. The public key is *(N, e)* and the private key is *(N, d)*. This works because *d* is the inverse of *e* so when a message m is encrypted with *m*<sup>*e*</sup> mod *N* => c, the decryption is then computed c<sup>e</sup> = (*m*<sup>e</sup>)<sup>d</sup> = m<sup>ed</sup> = m<sup>ee<sup>-1</sup></sup> = m mod N.

### Encryption
Encryption is done by taking the message 
### Decryption
