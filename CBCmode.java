import java.util.*;

public class CBCmode extends TEA{


	public CBCmode(){
		this.key = null;
	}

	public CBCmode(int[] keyAdd){
		this.key = new int[4];
		key[0] = keyAdd[0];
		key[1] = keyAdd[1];
		key[2] = keyAdd[2];
		key[3] = keyAdd[3];

	}

	/*
	 * CBC MODE ENCRYPTION
	 * This methods take a block of 64 bit size and the previous encrypted block of size
	 * 64 bit, too. Then, it apply CBC mode algorithms on it. 
	 * and the encryption is done using TEA Algorithm
	 * 
	 * @param: - plainText[2] Block of size 64 bit, 
	 * 		   - previous[2] previously encrypted block of size 64 bit
	 * 
	 * Output: block[2]: encrypted block of size 64 bit
	 */
	public int[] encrypt(int[] plainText, int[] previous){
		//Check if the user defined the key
		if(key == null){
			System.out.println("Key is not defined!");
			System.exit(0);
		}
		
		/* XOR the block with the previously encrypted block */
		int left = plainText[0] ^ previous[0];
		int right = plainText[1] ^ previous[1];
		
		sum = 0;

		for(int i=0; i<32;i++){
			sum += DELTA;
			left += ((right << 4) + key[0]) ^ (right+sum) ^ ((right >> 5) + key[1]);
			right += ((left << 4) + key[2]) ^ (left+sum) ^ ((left >> 5) + key[3]);

		}
		
		int block[] = new int[2];
		block[0] = left;
		block[1] = right;

		return block;

	}

	/*
	 * CBC MODE DECRYPTION
	 * This methods take an encrypted block of 64 bit size and the previous encrypted block of size
	 * 64 bit, too. Then, it apply CBC mode algorithms on it. 
	 * and the decryption is done using TEA Algorithm
	 * 
	 * @param: - cipherText[2] Block of size 64 bit, 
	 * 		   - previous[2] previously encrypted block of size 64 bit
	 * 
	 * Output: block[2]: decrypted block of size 64 bit
	 */
	public int[] decrypt(int[] cipherText, int previous[]){
		if(key == null){
			System.out.println("Key is not defined!");
			System.exit(0);
		}
		
		/* Diving the block into left and right sub blocks */
		int left = cipherText[0];
		int right = cipherText[1];

		sum = DELTA << 5;		//initialize the sum variable

		for(int i=0; i<32;i++){
			right -= ((left << 4) + key[2]) ^ (left+sum) ^ ((left >> 5) + key[3]);
			left -= ((right << 4) + key[0]) ^ (right+sum) ^ ((right >> 5) + key[1]);
			sum -= DELTA;
		}
		
		/*XOR the result of TEA Algorithm with the previous block */
		int block[] = new int[2];
		block[0] = left ^ previous[0];
		block[1] = right ^ previous[1];
		
		return block;

	}
	

}