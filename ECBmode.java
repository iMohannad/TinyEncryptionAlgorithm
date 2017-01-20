import java.util.*;

public class ECBmode extends TEA{


	public ECBmode(){
		this.key = null;
	}

	public ECBmode(int[] keyAdd){
		this.key = new int[4];
		key[0] = keyAdd[0];
		key[1] = keyAdd[1];
		key[2] = keyAdd[2];
		key[3] = keyAdd[3];

	}

	/*
	 * TEA ENCRYPTION ALGORITHMS
	 * ECB Mode
	 * @param: plainText[] of size 2 >> This is the block size
	 * Output: block[2] result of encrypting plainText[2]
	 */
	public int[] encrypt(int[] plainText){
		//Check if the user defined the key
		if(key == null){
			System.out.println("Key is not defined!");
			System.exit(0);
		}
		
		/* Diving the block into left and right sub blocks */
		int left = plainText[0];	
		int right = plainText[1];
		
		sum = 0;		//initialize the sum variable

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
	 * TEA DECRYPTION ALGORITHMS
	 * ECB Mode
	 * @param: cipherText[] of size 2 >> This is the block size to be decrypted
	 * Output: block[2] result of decrypting cipherText[2]
	 */
	public int[] decrypt(int[] cipherText){
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
		
		int block[] = new int[2];
		block[0] = left;
		block[1] = right;

		return block;

	}
}