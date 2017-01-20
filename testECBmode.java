import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.*;

public class testECBmode{

	
	public static void main(String[] args) throws IOException{
		
		int[] key = {10,12,13,14};		//Instantiating a key
		ECBmode ecb = new ECBmode(key);			//instantiating a TEA class
		
		int[] img = new int[2];			//img Variable will contain the block to be encrypted
		int left,right;
		
		/* Change the path if you install the image on different path */ 
		FileInputStream imgIn = new FileInputStream("image\\Tux.bmp");
		FileOutputStream imgOut = new FileOutputStream("image\\ECBencrypt.bmp");
		
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
		
		
		int cipher[] = new int[2];
		boolean check = true;				//Check variable used to know where did the file end
		
		while(dataIn.available() > 0){
			try{
				img[0] = dataIn.readInt();		//left sub block 
				check = true;
				img[1] = dataIn.readInt();		//right sub block
				cipher = ecb.encrypt(img);		//Passing the block to TEA algorithm to encrypt it
				dataOut.writeInt(cipher[0]);	//writing back the encrypted block
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
		
		/*~~~~~~~~~~Decrypting the Image ~~~~~~~~~~~~~~~~~~*/
		DataInputStream dataIn1 = new DataInputStream(new FileInputStream("image\\ECBencrypt.bmp"));
		DataOutputStream dataOut1 = new DataOutputStream(new FileOutputStream("image\\ECBdecrypt.bmp"));
		
		for(int i=0;i<10;i++){
			if(dataIn1.available() > 0){
				img[0] = dataIn1.readInt();
				img[1] = dataIn1.readInt();
				dataOut1.writeInt(img[0]);
				dataOut1.writeInt(img[1]);
			}
		}
		
		int plain[] = new int[2];
		check = true;
		while(dataIn1.available() > 0){
			try{				
				img[0] = dataIn1.readInt();
				check = true;
				img[1] = dataIn1.readInt();
				plain = ecb.decrypt(img);
				dataOut1.writeInt(plain[0]);
				dataOut1.writeInt(plain[1]);
				check = false;
			}catch(EOFException e){
				System.out.println("Cheeck > " + check);
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