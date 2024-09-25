import java.util.Scanner;

public class CaesarCipher {
    // keep track of index
    public static final String alpha = "abcdefghijklmnopqrstuvwxyz";

    public static String encrypt(String message, int shiftKey) {
        // lowercase the message
        message = message.toLowerCase();
        // initialize ciphertext with empty string
        String cipherText = "";
        for (int ii = 0; ii < message.length(); ii++) {
            // find character position in message
            int charPosition = alpha.indexOf(message.charAt(ii));
            // add shift
            int keyVal = (shiftKey + charPosition) % 26;
            // replace the character with the shifted character
            char replaceVal = alpha.charAt(keyVal);
            // add replacement character to ciphertext
            cipherText += replaceVal;
        }
        return cipherText;
    }

    public static String decrypt(String cipherText, int shiftKey) {
        cipherText = cipherText.toLowerCase();
        String message = "";
        for (int ii = 0; ii < cipherText.length(); ii++) {
            int charPosition = alpha.indexOf(cipherText.charAt(ii));
            int keyVal = (charPosition - shiftKey) % 26;
            if (keyVal < 0) {
                keyVal = alpha.length() + keyVal;
            }
            char replaceVal = alpha.charAt(keyVal);
            message += replaceVal;
        }
        return message;
    }

    public static void main(String[] args) {
        // scan for user input
        Scanner sc = new Scanner(System.in);

        // initialize message string
        String message;

        // initialize key shift
        int key;

        // get message to encrypt from user input
        System.out.print("Enter the String for Encryption: ");
        message = sc.nextLine();  // Use nextLine() to capture the entire line of input

        // get key shift value from user input
        System.out.print("\nEnter Shift Key: ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer for the Shift Key.");
            sc.next();  // Clear the invalid input
        }
        key = sc.nextInt();

        String encrypted = encrypt(message, key);

        // print encrypted message
        System.out.println("\nEncrypted message: " + encrypted);
        System.out.println("\nDecrypted message: " + decrypt(encrypted, key));

        // close scanner
        sc.close();
    }

}
