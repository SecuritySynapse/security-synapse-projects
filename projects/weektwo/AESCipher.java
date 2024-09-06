import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCipher {
    // Algorithm used for encryption (AES)
    private static final String ALGORITHM = "AES";
    
    // Transformation: AES with CBC mode and PKCS5 padding
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    
    // AES key size (256 bits)
    private static final int AES_KEY_SIZE = 256;
    
    // Initialization vector (IV) for CBC mode, set to 16 bytes (can be randomized)
    // Very Predictable hash code because it doesn't randomize each time it is generated.
    private static final byte[] iv = new byte[16];
    
    // Secret key used for AES encryption
    private SecretKey key;

    // Constructor takes a string as the encryption key, hashes it with SHA-256, and creates an AES key
    public AESCipher(String key) throws Exception {
        this.key = new SecretKeySpec(sha256(key), ALGORITHM);
    }

    // Encrypt a plain text string
    public String encrypt(String plainText) throws Exception {
        // Get a cipher instance with the specified transformation
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        
        // Set the IV for CBC mode
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        
        // Initialize the cipher in ENCRYPT_MODE using the secret key and IV
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        
        // Encrypt the plaintext and return the result encoded in Base64
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // Decrypt a Base64-encoded cipher text
    public String decrypt(String cipherText) throws Exception {
        // Get a cipher instance with the specified transformation
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        
        // Set the IV for CBC mode
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        
        // Initialize the cipher in DECRYPT_MODE using the secret key and IV
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        
        // Decode the Base64 encoded ciphertext and decrypt it
        byte[] decoded = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decoded);
        
        // Return the decrypted text as a UTF-8 string
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    // Generate a SHA-256 hash of the input string (used to create the AES key)
    private static byte[] sha256(String data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(data.getBytes(StandardCharsets.UTF_8));
    }

    // Read the contents of a file from the given file path
    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
    }

    // Write the given data to a file at the specified file path
    public static void writeFile(String filePath, String data) throws IOException {
        Files.write(Paths.get(filePath), data.getBytes(StandardCharsets.UTF_8));
    }

    // Main method for testing encryption and file handling
    public static void main(String[] args) {
        try {
            String key = "mysecretpassword";  // Define the encryption key
            String filePath = "python_anti_malware/files/example_one.txt";  // Path to the file to be encrypted
            AESCipher aesCipher = new AESCipher(key);  // Create a new AESCipher object

            // Read the original content of the file
            String plainText = readFile(filePath);
            System.out.println("Original Content: " + plainText);

            // Encrypt the original content and write it back to the file
            String encryptedText = aesCipher.encrypt(plainText);
            System.out.println("Encrypted Original Content: " + encryptedText);
            writeFile(filePath, encryptedText);

            // Modify the file content with new text
            String modifiedContent = "This is modified content.";
            writeFile(filePath, modifiedContent);
            System.out.println("Modified Content: " + modifiedContent);

            // Encrypt the modified content and print it
            String encryptedModified = aesCipher.encrypt(modifiedContent);
            System.out.println("Encrypted Modified Content: " + encryptedModified);

            // Check if the encrypted original and modified content are the same (corruption check)
            if (!encryptedText.equals(encryptedModified)) {
                System.out.println("File Status: Corrupted File");
            } else {
                System.out.println("File Status: Uncorrupted File");
            }
        } catch (Exception e) {
            e.printStackTrace();  // Handle any exceptions
        }
    }
}
