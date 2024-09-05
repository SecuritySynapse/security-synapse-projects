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
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int AES_KEY_SIZE = 256;
    private static final byte[] iv = new byte[16];  // Initialization vector (can be randomized)
    private SecretKey key;

    public AESCipher(String key) throws Exception {
        this.key = new SecretKeySpec(sha256(key), ALGORITHM);
    }

    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] decoded = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    private static byte[] sha256(String data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
    }

    public static void writeFile(String filePath, String data) throws IOException {
        Files.write(Paths.get(filePath), data.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) {
        try {
            String key = "mysecretpassword";  // The encryption key
            String filePath = "files/example_one.txt";  // File to encrypt
            AESCipher aesCipher = new AESCipher(key);  // Create AES object

            // Read original file content
            String plainText = readFile(filePath);
            System.out.println("Original Content: " + plainText);

            // Encrypt original content and write back to file
            String encryptedText = aesCipher.encrypt(plainText);
            System.out.println("Encrypted Original Content: " + encryptedText);
            writeFile(filePath, encryptedText);

            // Modify file content
            String modifiedContent = "This is modified content.";
            writeFile(filePath, modifiedContent);
            System.out.println("Modified Content: " + modifiedContent);

            // Encrypt modified content
            String encryptedModified = aesCipher.encrypt(modifiedContent);
            System.out.println("Encrypted Modified Content: " + encryptedModified);

            // Check if files are corrupted by comparing encrypted versions
            if (!encryptedText.equals(encryptedModified)) {
                System.out.println("File Status: Corrupted File");
            } else {
                System.out.println("File Status: Uncorrupted File");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
