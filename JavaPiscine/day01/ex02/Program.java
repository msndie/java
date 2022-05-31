public class Program {
    public static void main(String[] args) {
        UsersArrayList list = new UsersArrayList();
        for (int i = 0; i < 10; i++) {
            list.addUser(new User("john", i));
        }

        System.out.println("getNumberOfUsers - " + list.getNumberOfUsers());
        System.out.println("getUserById - " + list.getUserById(9).getId());
        System.out.println("getUserByIndex 5 - " + list.getUserByIndex(5).getId());
        System.out.println(list.getUserById(11));
    }
}
