package Step1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;

public class TestSquentialRC4 {
    private static StringBuffer key;
    private static long startTime1;
    private static long endTime1;
    
    public static void main (String args[]) throws FileNotFoundException, IOException{
        
        String strLine      = null;
        String strNewLine   = null;
        StringBuffer strBufferMsg;     

        SecureRandom random = new SecureRandom();
        key                 = new StringBuffer(new BigInteger(640, random).toString(32));

        FileInputStream fstream = new FileInputStream("E://testChunk.txt");
        // Get the object of DataInputStream
        DataInputStream in  = new DataInputStream(fstream);
        BufferedReader br   = new BufferedReader(new InputStreamReader(in));


        //Read File Line By Line
        while ((strLine = br.readLine()) != null) {
             if (strNewLine == null) {
                strNewLine = strLine;
            }
             else {
                strNewLine = strNewLine + strLine;
            }
        }
        //Close the input stream
        in.close();
        br.close();
        String strMsg       = strNewLine;
        strBufferMsg        = new StringBuffer(strMsg);
        //Encryption Work
        startTime1 = System.nanoTime();
        RC4.RC4Crypt(strBufferMsg, key);
        endTime1 = System.nanoTime();
        //Calculate the elapsed time in milliseconds
        long timeElapsed1 = CalculateTimeDifference.GetTimeElapsed(startTime1, endTime1);
        System.out.println("Time Elapsed: " + timeElapsed1 + "ns");
        FileChunkAndEncrypt.write(strBufferMsg.toString().getBytes(), "E://Temp/Sequential/Encrypted.txt");
        //Decryption Work
        RC4.RC4Crypt(strBufferMsg, key);
        FileChunkAndEncrypt.write(strBufferMsg.toString().getBytes(), "E://Temp/Sequential/Recovered.txt");
}


}
