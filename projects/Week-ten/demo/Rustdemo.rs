use std::io::{Read, Write};
use std::net::{TcpListener, TcpStream};
use std::sync::atomic::{AtomicUsize, Ordering};
use std::sync::Arc;

// Handles communication with any connected client
fn handle_client(mut stream: TcpStream) {
    // This is a buffer to read data from the client
    let mut buffer = [0; 1024];
    // This line reads data from the stream and stores it in the buffer.
    stream
        .read(&mut buffer)
        .expect("Failed to read from client");
    //Converts data in the buffer to a utf8 string
    let request = String::from_utf8_lossy(&buffer[..]);
    println!("Received request: {}", request);

    let response = "Hello, Client".as_bytes();
    stream.write(response).expect("failed to write response!");
}

// Entry point

fn main() {
    // Listens on port 8080
    let listener = TcpListener::bind("127.0.0.1:8080").expect("Failed to bind to address");
    println!("Server listening on 127.0.0.1:8080");

    // keeps track of live connections
    let connection_count = Arc::new(AtomicUsize::new(0));
    let max_connections = 5;
    // This handles the resutlt of listener.incoming
    for stream in listener.incoming() {
        match stream {
            //If connection is established the stream is executed
            Ok(stream) => {
                // spawns a new thread,
                // || a anonymous function that can capture and use variables from the surronding scope//
                std::thread::spawn(|| handle_client(stream));
                //
                let connection_count = Arc::clone(&connection_count);
                let current_count = connection_count.fetch_add(1, Ordering::SeqCst); // Increment counter
                println!("Current num of connections {}", current_count);
                if current_count >= max_connections {
                    println!("This appears to be a DDOS attack. Closing the server")
                }
            }
            Err(e) => {
                eprintln!("Failed to establish connection: {}", e);
                // Standard error message printout
            }
        }
    }
}

