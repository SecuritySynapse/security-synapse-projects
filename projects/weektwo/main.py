"""AES Encryption Implementation and Anti-malware Tool."""
# https://stackoverflow.com/questions/12524994/encrypt-and-decrypt-using-pycrypto-aes-256
# source venv/bin/activate
# python main.py

import base64
import hashlib
import os
from Crypto import Random
from Crypto.Cipher import AES

class AESCipher(object):
    """Provides AES encryption and decryption."""

    def __init__(self, key):
        """Initializes the AESCipher with a given key."""
        self.bs = AES.block_size
        self.key = hashlib.sha256(key.encode()).digest()

    def encrypt(self, raw):
        """Encrypts a plaintext message."""
        raw = self._pad(raw)
        iv = Random.new().read(AES.block_size)
        # Uses Dynamic IV making it harder to predict what the charactors mean.
        cipher = AES.new(self.key, AES.MODE_CBC, iv)
        return base64.b64encode(iv + cipher.encrypt(raw.encode()))

    def decrypt(self, enc):
        """Decrypts a ciphertext message."""
        enc = base64.b64decode(enc)
        iv = enc[:AES.block_size]
        cipher = AES.new(self.key, AES.MODE_CBC, iv)
        return AESCipher._unpad(cipher.decrypt(enc[AES.block_size:])).decode('utf-8')

    def _pad(self, s):
        """Pads the plaintext to be a multiple of the block size."""
        return s + (self.bs - len(s) % self.bs) * chr(self.bs - len(s) % self.bs)

    @staticmethod
    def _unpad(s):
        """Removes the padding from the plaintext."""
        return s[:-ord(s[len(s)-1:])]

def read_file(file_path):
    """Reads the contents of a file."""
    with open(file_path, 'r') as file:
        return file.read()

def write_file(file_path, data):
    """Writes data to a file."""
    with open(file_path, 'w') as file:
        file.write(data)

def main():
    key = "mysecretpassword"  # The encryption key
    file_path = "files/example_one.txt"  # The file to encrypt
    cipher = AESCipher(key)  # Create a new AESCipher object

    # ANSI escape code for bold text
    bold_start = "\033[1m"
    bold_end = "\033[0m"
    print(f"\n{bold_start}🔐 Anti-Malware Tool 🔐{bold_end}\n")

    # Original File Content
    plaintext = read_file(file_path)
    print(f"{bold_start}Original Content:{bold_end} {plaintext}\n")

    # Encrypted Original File Content
    encrypted = cipher.encrypt(plaintext)
    print(f"{bold_start}Encrypted Original Content:{bold_end} {encrypted.decode('utf-8')}\n")
    # Write the encrypted contents back to the file
    write_file(file_path, encrypted.decode('utf-8'))

    # Modified File Content
    modified_content = "This is modified content."
    write_file(file_path, modified_content)
    print(f"{bold_start}Modified Content:{bold_end} {modified_content}\n")
    # Read the modified contents
    modified_content = read_file(file_path)

    # Decrypted Content
    decrypted_content = cipher.decrypt(encrypted)
    print(f"{bold_start}Decrypted Original Content:{bold_end} {decrypted_content}\n")
    # Check if the current file content matches the decrypted original content
    if modified_content != decrypted_content:
        print(f"{bold_start}File Status:{bold_end} Corrupted File\n")
    else:
        print(f"{bold_start}File Status:{bold_end} Uncorrupted File\n")

if __name__ == "__main__":
    main()