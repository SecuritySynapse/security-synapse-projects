use std::io::{stdin, stdout, Write};
use std::collections::VecDeque;

fn main() {
    let message = "Common sense is not so common.";
    let key = 6;
    let mut input = String::new();

    println!("Enter 'e' for encrypt or 'd' for decrypt: ");
    stdout().flush().unwrap();

    stdin().read_line(&mut input).expect("Failed to read line");

    let choice = input.trim().to_lowercase();

    if choice == "e" {
        println!("You chose encryption.");
        let ciphertext = encrypt_message(key, message);
        println!("Encrypted message: {}|", ciphertext);
    } else {
        println!("You chose decryption.");
        let ciphertext = decrypt_message(key, message);
        println!("Decrypted message: {}|", ciphertext);
    }
}

fn encrypt_message(key: usize, message: &str) -> String {
    let mut ciphertext: VecDeque<char> = VecDeque::with_capacity(message.len());

    for col in 0..key {
        let mut current_index = col;
        while current_index < message.len() {
            ciphertext.push_back(message.chars().nth(current_index).unwrap());
            current_index += key;
        }
    }

    ciphertext.into_iter().collect() 
}

fn decrypt_message(key: usize, message: &str) -> String {
    // Calculate number of columns and rows
    let num_of_columns = (f64::ceil(message.len() as f64 / key as f64)) as usize;
    let num_of_rows = key;

    // Calculate number of shaded boxes
    let num_of_shaded_boxes = (num_of_columns * num_of_rows) - message.len();

    // Initialize plaintext vector
    let mut plaintext: Vec<String> = vec![String::new(); num_of_columns]; 

    // Pointers for column and row
    let mut column = 0;
    let mut row = 0;

    for symbol in message.chars() {
        plaintext[column].push(symbol);
        column += 1;

        // Move to next column or row
        if column == num_of_columns || (column == num_of_columns - 1 && row >= num_of_rows - num_of_shaded_boxes) {
            column = 0;
            row += 1;
        }
    }

    plaintext.join("")
}