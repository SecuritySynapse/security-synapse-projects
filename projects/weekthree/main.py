from cryptography.fernet import Fernet
import os


def encrypt():
    plain_text = input("What do you want to Encrypt:")
    key = Fernet.generate_key()
    with open("key.txt", "w") as key_file:
        key_file.write(key.decode())
    with open("encrypted_data.txt", "w") as file:
        encrypted_text = Fernet(key).encrypt(bytes(plain_text.encode()))
        print("Encrypted data: " + encrypted_text.decode())
        file.write(encrypted_text.decode())


def decrypt():
    if os.path.exists("encrypted_data.txt"):
        file = open("encrypted_data.txt", "r")
        if os.path.exists("key.txt"):
            key_file = open("key.txt", "r")
            key = key_file.read()
            encrypted_text = file.read()
            print(
                "Decrypted data: "
                + Fernet(key.encode()).decrypt(encrypted_text.encode()).decode()
            )
        else:
            print("key.txt does not exist")
    else:
        print("encrypted_data.txt does not exist")


def main():
    answer = input("Do you want to (E)encrypt or (D) decrypt: ")
    if answer == "E":
        encrypt()
    elif answer == "D":
        decrypt()
    else:
        print("Invalid command")
        main()


if __name__ == "__main__":
    main()
