
package Step1;


public class ModifiedRC4 {
    
     public static void ModifiedRC4Crypt(StringBuffer inp, StringBuffer key) {
        int Sbox1[] = new int[256];
        int Sbox2[] = new int [256];
        int i = 0, j = 0, t = 0, x = 0;
        char temp = 0, k;

        //KSA - Start
        for (i = 0; i < 256; i++) {
            Sbox1[i] = i;
        }

        j = 0;
        if (key.length() > 0) {
            for (i = 0; i < 256; i++) {
                if (j == key.length()) {
                    j = 0;
                }
                Sbox2[i] = key.charAt(j++);                
            }
        }

        j = 0;
        
        for (i = 0; i < 256; i++) {
            j = (j + Sbox1[i] + Sbox2[i]) % 256;
            temp = (char) Sbox1[i];
            Sbox1[i] = Sbox1[j];
            Sbox1[j] = temp;
        }
        //KSA - End

        //PRGA - Start
        i = j = 0;
        for (x = 0; x < inp.length(); x++) {
            //Increment i
            i = (i + 1) % 256;
            //Increment j
            j = (j + Sbox1[i]) % 256;

            //Scramble SBox1 #1 further so the encryption routine will repeat itself at great intervals
            temp = (char) Sbox1[i];
            Sbox1[i] = Sbox1[j];
            Sbox1[j] = temp;

            //Get ready to create pseudo random byte for the encryption key
            t = (Sbox1[i] + Sbox1[j]) % 256;

            //Get the key
            k = (char) Sbox1[t];

            //XOR with the data and encryption is done!
            //Tharindu(Phase-1) - Start
            //inp.setCharAt(x, (char) (inp.charAt(x) ^ k));
            inp.setCharAt(x, (char) (inp.charAt(x) ^ k ^ j));
            //Tharindu(Phase-1) - End
            //PRGA - End
        }
    }
    
}
