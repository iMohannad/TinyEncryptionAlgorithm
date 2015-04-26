
public class testCBC {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] key = {10,12,13,14};
		TEA tea = new TEA(key);
		tea.printKeys();
		int[] img = new int[2];
		int left,right;
		int IV[] = {120,165};
		
		int test [] = {2,6,7,8,9,8};
		int cipher[] = new int[2];
		int storeCipher[] = new int[test.length];
		
		boolean firstTime = true;
		for(int i=0;i<test.length;i=i+2){
			img[0] = test[i];
			img[1] = test[i+1];
			if(firstTime){
				cipher = tea.encryptCBC(img, IV);
				firstTime = false;
			}else{
				cipher = tea.encryptCBC(img, cipher);
			}	
		
			storeCipher[i] = cipher[0];
			storeCipher[i+1] = cipher[1];
		}
		
		System.out.println("The cipher are >");
		for(int i=0;i<storeCipher.length;i++)
			System.out.print(storeCipher[i] + ", ");
		
		
		
		
		
		System.out.println("\n------------ Decypting Ciphers --------------");
		/*left = 7;
		right = 8;
		//int left = Integer.parseInt(cipherText.substring(0,4));
		//int right = Integer.parseInt(cipherText.substring(4,8));
		left = left ^ -343104403;
		right = right ^ 24265516;
		
		System.out.println("left after> " + left + ", right after> " + right);
		
		int DELTA = 0x9e3779b9;
		int sum = 0;
		for(int i=0; i<32;i++){
			sum += DELTA;
			left += ((right << 4) + key[0]) ^ (right+sum) ^ ((right >> 5) + key[1]);
			right += ((left << 4) + key[2]) ^ (left+sum) ^ ((left >> 5) + key[3]);
		}
		
		System.out.println("left > " + left + ", right > " + right);
		
		
		sum = DELTA << 5;		//initialize the sum variable

		for(int i=0; i<32;i++){
			right -= ((left << 4) + key[2]) ^ (left+sum) ^ ((left >> 5) + key[3]);
			left -= ((right << 4) + key[0]) ^ (right+sum) ^ ((right >> 5) + key[1]);
			sum -= DELTA;
		}
		
		System.out.println("left after de> " + left + "Right after de> " + right);
		left = left ^ -343104403;
		right = right ^ 24265516;
		System.out.println("left > " + left + "Right> " + right);
		
		*/firstTime = true;
		int [] plain = new int[2];
		int [] storePlain = new int[test.length];
		int [] copyCipher = new int[2];
		for(int i=0;i<test.length;i=i+2){
			
			img[0] = storeCipher[i];
			img[1] = storeCipher[i+1];
			System.out.println("first TIme > " + firstTime);
			if(firstTime){
				plain = tea.decryptCBC(img, IV);
				firstTime = false;
			}else{
				System.out.println("img >>>>>> " + img[0] +", "+ img[1]);
				System.out.println("copy cipher >>>>>" + copyCipher[0] + ", " + copyCipher[1]);
				plain = tea.decryptCBC(img, copyCipher);
			}
				
			copyCipher[0] = img[0];
			copyCipher[1] = img[1];

			storePlain[i] = plain[0];
			storePlain[i+1] = plain[1];
		}	
			
		System.out.println("The numbers are >");
		for(int i=0;i<storeCipher.length;i++)
			System.out.print(storePlain[i] + ", ");

	}

}
