import pyotp
import qrcode

secret = pyotp.random_base32() # make random base 32 secret
totp = pyotp.TOTP(secret) # Create time based one time password
uri = totp.provisioning_uri(name="alden@allegheny.gov", issuer_name="BingoBosco")

qr = qrcode.make(uri)
print("QR code displayed. Scan it with your authenticator app.")
qr.show()

while True:
    try:
        user_totp = input("Enter the TOTP: ")
        if totp.verify(user_totp):
            print("Login successful!")
        else:
            print("Invalid TOTP. Please try again.")
    except KeyboardInterrupt:
        print("\nExiting...")
        break
