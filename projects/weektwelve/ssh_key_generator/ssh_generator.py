import paramiko # type: ignore
import io

# Generate SSH key pair
# 2048 bit length is the minimum recommended length for RSA keys
# had some issues with getting 4096 to work, it sometimes didn't function on my terminal but I'm not sure why
key = paramiko.RSAKey.generate(4096)

# Save private key to an in-memory file
private_key_io = io.StringIO()
# Save the private key to the in-memory file
key.write_private_key(private_key_io)
# Get the private key from the in-memory file
private_key = private_key_io.getvalue()

# Print the private key
print("Private SSH key:")
print(private_key)

# Save public key to an in-memory file
public_key = f"{key.get_name()} {key.get_base64()}"
print("\nPublic SSH key:")
# Print the public key
print(public_key)
