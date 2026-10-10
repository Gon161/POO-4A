import java.util.Scanner;

class VariablesConstantes {
    static final String VERSION = "1.0";
    static final double PI = 3.1416;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresa un valor inicial para 'total':");
        var total = scanner.nextInt(); // inferido como int
        total += 10;

        System.out.println("total: " + total);
        System.out.println("PI: " + PI);
        System.out.println("VERSION: " + VERSION);
    }
}
