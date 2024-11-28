class AccessControlList:
    def __init__(self):
        # Define roles and their permissions
        self.roles = {
            "Admin": {"read": True, "write": True, "delete": True},
            "User": {"read": True, "write": True, "delete": False},
            "Guest": {"read": True, "write": False, "delete": False},
        }
        # Store users and their assigned roles
        self.users = {}

    def add_user(self, username, role):
        if role in self.roles:
            self.users[username] = role
            print(f"User '{username}' added with role '{role}'")
        else:
            print(f"Error: Role '{role}' does not exist")

    def remove_user(self, username):
        if username in self.users:
            del self.users[username]
            print(f"User '{username}' removed")
        else:
            print(f"Error: User '{username}' does not exist")

    def check_permission(self, username, permission):
        role = self.users.get(username)
        if role:
            # Get the role's permissions and check if permission is granted
            allowed = self.roles[role].get(permission, False)
            print(f"Permission '{permission}' for user '{username}': {allowed}")
            return allowed
        else:
            print(f"Error: User '{username}' does not exist")
            return False

    def list_users(self):
        print("Current Users and Roles:")
        for user, role in self.users.items():
            print(f"{user}: {role}")

# Example usage of the Access Control List
acl = AccessControlList()

# Add users
acl.add_user("Jason", "Admin")
acl.add_user("Hank", "User")
acl.add_user("Caleb", "Guest")

# List users
acl.list_users()

# Check permissions
acl.check_permission("Jason", "read")
acl.check_permission("Hank", "write")
acl.check_permission("Caleb", "delete")

# Remove a user
acl.remove_user("Caleb")
acl.list_users()
