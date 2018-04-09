package Step1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileChunkAndEncrypt implements Runnable {

    private int chunkSize;
    private static StringBuffer key;

    public FileChunkAndEncrypt(int chunkSize)
    {
        this.chunkSize = chunkSize;
    }

    @Override
    public void run() {
        try {
            // File chunking in parallel goes here....
            readFragmentEncrypt("E:/testChunk.txt", chunkSize);
        } catch (IOException ex) {
            Logger.getLogger(FileChunkAndEncrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void readFragmentEncrypt ( String SourceFileName, int CHUNK_SIZE ) throws IOException
    {
            //log ("File Is Reading "+ SourceFileName );
            File willBeRead = new File ( SourceFileName );
            int FILE_SIZE   = (int) willBeRead.length();
            ArrayList<String> nameList = new ArrayList<String> ();

            //System.out.println("Total File Size: " + FILE_SIZE);

            int NUMBER_OF_CHUNKS = 0;
            byte[] temporary = null;

            SecureRandom random = new SecureRandom();
            key                 = new StringBuffer(new BigInteger(640, random).toString(32));

            try {
                    InputStream inStream = null;
                    int totalBytesRead = 0;

            try {
                    inStream = new BufferedInputStream ( new FileInputStream( willBeRead ));

            while ( totalBytesRead < FILE_SIZE )
            {
                String PART_NAME ="data"+NUMBER_OF_CHUNKS+".txt";
                int bytesRemaining = FILE_SIZE-totalBytesRead;
                if ( bytesRemaining < CHUNK_SIZE ) // Remaining Data Part is Smaller Than CHUNK_SIZE
                // CHUNK_SIZE is assigned to remain volume
                {
                    CHUNK_SIZE = bytesRemaining;
                    //System.out.println("CHUNK_SIZE: "+CHUNK_SIZE);
                }

                temporary = new byte[CHUNK_SIZE]; //Temporary Byte Array
                int bytesRead = inStream.read(temporary, 0, CHUNK_SIZE);

                if ( bytesRead > 0) // If bytes read is not empty
                {
                    totalBytesRead += bytesRead;
                    NUMBER_OF_CHUNKS++;
                }
                if (bytesRead != FILE_SIZE )
                {
                    StringBuffer input = new StringBuffer(temporary.toString());
                    RC4.RC4Crypt(input, key);
                    write(input.toString().getBytes(), "E://Temp/Parallel/Encrypted/"+PART_NAME);
                    nameList.add("E://Temp/Parallel/Encrypted/"+PART_NAME);
                    //mergeParts("E://Temp/Parallel/Encrypted/"+PART_NAME, "E://Temp/Parallel/Encrypted//Final.txt");
                }
                
                //System.out.println("Total Bytes Read: " + totalBytesRead);

                if (totalBytesRead==0)
                    break;                
            }

            }
            finally {
                    inStream.close();
            }
            }
            catch (FileNotFoundException ex)
            {
            }
            catch (IOException ex)
            {
            }
            }

     static void write(byte[] DataByteArray, String DestinationFileName){
       try {
         OutputStream output = null;
         try {
           output = new BufferedOutputStream(new FileOutputStream(DestinationFileName));
           output.write( DataByteArray );
           //System.out.println("Writing Process Was Performed");
         }
         finally {
           output.close();
         }
       }
       catch(FileNotFoundException ex){
       }
       catch(IOException ex){
       }
    }

    public void mergeParts ( ArrayList<String> nameList, String DESTINATION_PATH )
    {
            File[] file = new File[nameList.size()];
            byte AllFilesContent[] = null;

            int TOTAL_SIZE = 0;
            int FILE_NUMBER = nameList.size();
            int FILE_LENGTH = 0;
            int CURRENT_LENGTH=0;

            for ( int i=0; i<FILE_NUMBER; i++)
            {
                    file[i] = new File (nameList.get(i));
                    TOTAL_SIZE+=file[i].length();
            }

            try {
                    AllFilesContent= new byte[TOTAL_SIZE]; // Length of All Files, Total Size
                    InputStream inStream = null;

            for ( int j=0; j<FILE_NUMBER; j++)
            {
                    inStream = new BufferedInputStream ( new FileInputStream( file[j] ));
                    FILE_LENGTH = (int) file[j].length();
                    inStream.read(AllFilesContent, CURRENT_LENGTH, FILE_LENGTH);
                    CURRENT_LENGTH+=FILE_LENGTH;
                    inStream.close();
            }

            }
            catch (FileNotFoundException e)
            {
                    System.out.println("File not found " + e);
            }
            catch (IOException ioe)
            {
                    System.out.println("Exception while reading the file " + ioe);
            }
            finally
            {
                    write (AllFilesContent,DESTINATION_PATH);
            }
            System.out.println("Merge was executed successfully.!");

            }
}
