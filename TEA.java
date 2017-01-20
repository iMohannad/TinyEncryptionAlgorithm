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
	
	
}