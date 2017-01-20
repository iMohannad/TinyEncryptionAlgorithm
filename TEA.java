import java.util.*;

public class TEA{

	protected static int DELTA = 0x9e3779b9;		//Delta for 
	protected static int ROUNDS = 32;
	protected int sum;
		

	protected int[] key; 			//key used to encrypt or decrypt
	

	/* Constructor: 
	   @param: String keyAdd to assign the symmetric key, 
	   which will be used in encryption and decryption
	*/

	public TEA(){
		key = null;
	}

	public TEA(int[] keyAdd){
		key = new int[4];
		key[0] = keyAdd[0];
		key[1] = keyAdd[1];
		key[2] = keyAdd[2];
		key[3] = keyAdd[3];

	}
	
	
	/*
	 * To add or change a Key 
	 */
	public void addKey(int[] key){
		if(key.length < 4)
			System.out.println("Key is less than 128 bits");
		else if(key.length > 4)
			System.out.println("Key is more than 128 bits");
		else
			this.key = key;			
	}

	/* print the key added to TEA
	 * if there's no key added, a status message will be generated */
	public void printKeys(){
		if(key == null){
			System.out.println("There's no key");
		}
		System.out.println("Keys are\n");
		for(int i=0;i<4;i++){
			System.out.println(key[i]);
		}
	}
	
	// /*
	//  * TEA ENCRYPTION ALGORITHMS
	//  * @param: plainText[] of size 2 >> This is the block size
	//  * Output: block[2] result of encrypting plainText[2]
	//  */
	// public int[] encrypt(int[] plainText){
	// 	//Check if the user defined the key
	// 	if(key == null){
	// 		System.out.println("Key is not defined!");
	// 		System.exit(0);
	// 	}
		
	// 	/* Diving the block into left and right sub blocks */
	// 	int left = plainText[0];	
	// 	int right = plainText[1];
		
	// 	sum = 0;		//initialize the sum variable

	// 	for(int i=0; i<32;i++){
	// 		sum += DELTA;
	// 		left += ((right << 4) + key[0]) ^ (right+sum) ^ ((right >> 5) + key[1]);
	// 		right += ((left << 4) + key[2]) ^ (left+sum) ^ ((left >> 5) + key[3]);

	// 	}
		
	// 	int block[] = new int[2];
	// 	block[0] = left;
	// 	block[1] = right;

	// 	return block;

	// }

	
	// /*
	//  * TEA DECRYPTION ALGORITHMS
	//  * @param: cipherText[] of size 2 >> This is the block size to be decrypted
	//  * Output: block[2] result of decrypting cipherText[2]
	//  */
	// public int[] decrypt(int[] cipherText){
	// 	if(key == null){
	// 		System.out.println("Key is not defined!");
	// 		System.exit(0);
	// 	}

	// 	/* Diving the block into left and right sub blocks */
	// 	int left = cipherText[0];
	// 	int right = cipherText[1];

	// 	sum = DELTA << 5;		//initialize the sum variable

	// 	for(int i=0; i<32;i++){
	// 		right -= ((left << 4) + key[2]) ^ (left+sum) ^ ((left >> 5) + key[3]);
	// 		left -= ((right << 4) + key[0]) ^ (right+sum) ^ ((right >> 5) + key[1]);
	// 		sum -= DELTA;
	// 	}
		
	// 	int block[] = new int[2];
	// 	block[0] = left;
	// 	block[1] = right;

	// 	return block;

	// }
	
	
	//  * CBC MODE ENCRYPTION
	//  * This methods take a block of 64 bit size and the previous encrypted block of size
	//  * 64 bit, too. Then, it apply CBC mode algorithms on it. 
	//  * and the encryption is done using TEA Algorithm
	//  * 
	//  * @param: - plainText[2] Block of size 64 bit, 
	//  * 		   - previous[2] previously encrypted block of size 64 bit
	//  * 
	//  * Output: block[2]: encrypted block of size 64 bit
	 
	// public int[] encryptCBC(int[] plainText, int[] previous){
	// 	//Check if the user defined the key
	// 	if(key == null){
	// 		System.out.println("Key is not defined!");
	// 		System.exit(0);
	// 	}
		
	// 	/* XOR the block with the previously encrypted block */
	// 	int left = plainText[0] ^ previous[0];
	// 	int right = plainText[1] ^ previous[1];
		
	// 	sum = 0;

	// 	for(int i=0; i<32;i++){
	// 		sum += DELTA;
	// 		left += ((right << 4) + key[0]) ^ (right+sum) ^ ((right >> 5) + key[1]);
	// 		right += ((left << 4) + key[2]) ^ (left+sum) ^ ((left >> 5) + key[3]);

	// 	}
		
	// 	int block[] = new int[2];
	// 	block[0] = left;
	// 	block[1] = right;

	// 	return block;

	// }

	
	// /*
	//  * CBC MODE DECRYPTION
	//  * This methods take an encrypted block of 64 bit size and the previous encrypted block of size
	//  * 64 bit, too. Then, it apply CBC mode algorithms on it. 
	//  * and the decryption is done using TEA Algorithm
	//  * 
	//  * @param: - cipherText[2] Block of size 64 bit, 
	//  * 		   - previous[2] previously encrypted block of size 64 bit
	//  * 
	//  * Output: block[2]: decrypted block of size 64 bit
	//  */
	// public int[] decryptCBC(int[] cipherText, int previous[]){
	// 	if(key == null){
	// 		System.out.println("Key is not defined!");
	// 		System.exit(0);
	// 	}
		
	// 	/* Diving the block into left and right sub blocks */
	// 	int left = cipherText[0];
	// 	int right = cipherText[1];

	// 	sum = DELTA << 5;		//initialize the sum variable

	// 	for(int i=0; i<32;i++){
	// 		right -= ((left << 4) + key[2]) ^ (left+sum) ^ ((left >> 5) + key[3]);
	// 		left -= ((right << 4) + key[0]) ^ (right+sum) ^ ((right >> 5) + key[1]);
	// 		sum -= DELTA;
	// 	}
		
	// 	/*XOR the result of TEA Algorithm with the previous block */
	// 	int block[] = new int[2];
	// 	block[0] = left ^ previous[0];
	// 	block[1] = right ^ previous[1];
		
	// 	return block;

	// }
	


}