public interface UsersList {
    void addUser(User u);
    User getUserById(Integer id);
    User getUserByIndex(Integer index);
    int getNumberOfUsers();
}
