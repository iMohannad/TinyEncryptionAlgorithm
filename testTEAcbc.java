
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;
import java.io.*;

public class testCBCmode{

	
	public static void main(String[] args) throws IOException{
		Random rand = new Random();
		int[] key = {10,12,13,14};				//instantiating a key
		TEA tea = new TEA(key);					//instantiating a TEA class
		
		int[] img = new int[2];
		
		int IV[] = {rand.nextInt(),rand.nextInt()};		//generating a random IV
		
		/* Change the path if you install the image on different path */ 
		FileInputStream imgIn = new FileInputStream("C:\\Users\\sayed\\Desktop\\workspace\\Security\\src\\image\\img.bmp");
		FileOutputStream imgOut = new FileOutputStream("C:\\Users\\sayed\\Desktop\\workspace\\Security\\src\\image\\resultCBC.bmp");
		
		DataInputStream dataIn = new DataInputStream(imgIn);
		DataOutputStream dataOut = new DataOutputStream(imgOut);
		
		
		/* Skipping the first 10 blocks
		 * each block is 64 bit. Thus, ReadInt() is applied twice
		 * because ReadInt() return 32 bits
		 */
		for(int i=0;i<10;i++){
			if(dataIn.available() > 0){
				img[0] = dataIn.readInt();
				img[1] = dataIn.readInt();
				dataOut.writeInt(img[0]);
				dataOut.writeInt(img[1]);
			}
		}
		
		
		boolean firstTime = true;		//to know when to apply IV or the previous encrypted block
		int cipher[] = new int[2];
		boolean check = true;			//to catch where the reading from the file is stopped
		while(dataIn.available() > 0){
			try{
				img[0] = dataIn.readInt();
				check = true;
				img[1] = dataIn.readInt();
				if(firstTime){		//if true, the block is passed with IV to be encrypted by TEA algorithm
					cipher = tea.encryptCBC(img, IV);
					firstTime = false;		//set firstTime to false sense IV is only encrypted in the first block
				}
				else
					cipher = tea.encryptCBC(img, cipher);		//pass the block with the previous encrypted block
				
				dataOut.writeInt(cipher[0]);
				dataOut.writeInt(cipher[1]);
				check = false;
			}catch(EOFException e){				//excetion is thrown if the file ends and dataIn.readInt() is executed 
				if(!check){						//if false, it means last block were not encrypted
					dataOut.writeInt(img[0]);
					dataOut.writeInt(img[1]);
				}else							//if true, it means only last half a block is not encrypted
					dataOut.writeInt(img[0]);
			}
			
		}
		dataIn.close();
		dataOut.close();
		
		/*~~~~~~~~~~~~~~~~~~~~~~~Decrypting the Image ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
		DataInputStream dataIn1 = new DataInputStream(new FileInputStream("C:\\Users\\sayed\\Desktop\\workspace\\Security\\src\\image\\resultCBC.bmp"));
		DataOutputStream dataOut1 = new DataOutputStream(new FileOutputStream("C:\\Users\\sayed\\Desktop\\workspace\\Security\\src\\image\\unencryptcbc.bmp"));
		
		for(int i=0;i<10;i++){
			if(dataIn1.available() > 0){
				img[0] = dataIn1.readInt();
				img[1] = dataIn1.readInt();
				dataOut1.writeInt(img[0]);
				dataOut1.writeInt(img[1]);
			}
		}
		
		int[] copyCipher = new int[2];
		firstTime = true;
		int plain[] = new int[2];
		check = true;
		
		while(dataIn1.available() > 0){
			try{
				img[0] = dataIn1.readInt();
				check = true;
				img[1] = dataIn1.readInt();
				
				if(firstTime){							//if true, the first block is passed with IV to be decrytped
					plain = tea.decryptCBC(img,IV);
					firstTime = false;					//set first time to false
				}else									//if false, the block is passed with the previously encrypted block
					plain = tea.decryptCBC(img,copyCipher);		
				
				dataOut1.writeInt(plain[0]);
				dataOut1.writeInt(plain[1]);
				
				copyCipher[0] = img[0];				//Save the previously encryted block in copyCipher to use it
				copyCipher[1] = img[1];
				
				check = false;
			}catch(EOFException e){
				if(!check){
					dataOut1.writeInt(img[0]);
					dataOut1.writeInt(img[1]);
				}else
					dataOut1.writeInt(img[0]);;
			}
			
		}
		dataIn1.close();
		dataOut1.close();
		
		imgOut.close();
		imgIn.close();

	}
}