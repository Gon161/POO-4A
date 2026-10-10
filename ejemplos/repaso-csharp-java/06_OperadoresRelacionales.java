import java.util.Scanner;

class OperadoresRelacionales {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresa el valor de x:");
        int x = scanner.nextInt();

        System.out.println("Ingresa el valor de y:");
        int y = scanner.nextInt();

        System.out.println("x == y: " + (x == y));
        System.out.println("x != y: " + (x != y));
        System.out.println("x > y: " + (x > y));
        System.out.println("x < y: " + (x < y));
        System.out.println("x >= y: " + (x >= y));
        System.out.println("x <= y: " + (x <= y));

        String a = "hola";
        String b = new String("hola");
        System.out.println("a == b (referencias): " + (a == b));        // false
        System.out.println("a.equals(b) (contenido): " + a.equals(b));  // true
    }
}
