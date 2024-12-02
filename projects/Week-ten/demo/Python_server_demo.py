import socket
import select


def server_program():
    host = "localhost"
    port = 5000
    connection_count = 0
    message_count = 0
    message_limit = 3

    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((host, port))
    server_socket.listen(5)
    print("Server listening on", host, port)
    sockets_list = [server_socket]

    try:
        while True:
            # Use select to wait for any socket to be ready for reading
            read_sockets, _, _ = select.select(sockets_list, [], [])

            for notified_socket in read_sockets:
                if notified_socket == server_socket:
                    # accepts new connection
                    client_socket, client_address = server_socket.accept()
                    sockets_list.append(client_socket)  # Add to list of sockets
                    connection_count += 1
                    print(
                        f"New connection from {client_address}. Active connections: {connection_count}"
                    )
                else:
                    # receive messages
                    message = notified_socket.recv(1024).decode()
                    if message:
                        # keeps track of the amount of messages
                        message_count += 1
                        print(f"Received message: {message}")
                        print(f"Total messages received: {message_count}")

                        # Check if message limit is reached
                        if message_count >= message_limit:
                            print(
                                "DDOS or brute force attack detected. Closing server."
                            )
                            return  # Exit the server loop and close connections
                    else:
                        # Client disconnected
                        sockets_list.remove(notified_socket)
                        notified_socket.close()
                        connection_count -= 1
                        print(
                            f"Connection closed. Active connections: {connection_count}"
                        )

    except KeyboardInterrupt:
        print("Server is shutting down.")
    finally:
        for sock in sockets_list:
            sock.close()


server_program()
