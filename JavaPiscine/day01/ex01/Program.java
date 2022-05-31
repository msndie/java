public class Program {
    public static void main(String[] args) {
        User[] uArr = new User[5];

        for (int i = 0; i < 5; i++) {
            uArr[i] = new User("John", i);
            System.out.println(uArr[i].getId());
        }
    }
}
