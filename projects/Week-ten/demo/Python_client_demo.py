import socket
import string
import random


def generate_random_string(length: int = 10) -> str:
    """Generates a random string of specified length."""
    chars = string.ascii_letters
    random_chars = [random.choice(chars) for _ in range(length)]
    return "".join(random_chars)


def client_program():
    count = 0
    while count < 5:
        host = "localhost"  # as both code is running on same pc
        port = 5000  # socket server port number

        client_socket = socket.socket()  # instantiate
        client_socket.connect((host, port))  # connect to the server

        message = generate_random_string()

        client_socket.send(message.encode())  # send message
        data = client_socket.recv(1024).decode()  # receive response

        print("Received from server: " + data)  # show in terminal
        count += 1
    # client_socket.close()


print("connecting")
client_program()
