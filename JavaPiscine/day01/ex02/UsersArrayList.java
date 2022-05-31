public class UsersArrayList implements UsersList {

    private User[] arr;
    private Integer i;

    public UsersArrayList() {
        arr = new User[10];
        i = 0;
    }

    @Override
    public void addUser(User u) {
        User[] tmp;
        if (u == null) {
            return;
        }
        if (arr[arr.length - 1] != null) {
            tmp = new User[arr.length + (arr.length / 2)];
            for (int j = 0; j < arr.length; j++) {
                tmp[j] = arr[j];
            }
            arr = tmp;
        }
        arr[i++] = u;
    }

    @Override
    public User getUserById(Integer id) throws UserNotFoundException {
        for (int j = 0; j < i; j++) {
            if (arr[j].getId().equals(id)) {
                return arr[j];
            }
        }
        throw new UserNotFoundException("Wrong id");
    }

    @Override
    public User getUserByIndex(Integer index) {
        if (index < 0 || index >= i) {
            return null;
        }
        return arr[index];
    }

    @Override
    public int getNumberOfUsers() {
        return i;
    }
}
