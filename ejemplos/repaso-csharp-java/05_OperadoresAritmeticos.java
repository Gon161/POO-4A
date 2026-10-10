import java.util.Scanner;

class OperadoresAritmeticos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresa el primer número (a):");
        int a = scanner.nextInt();

        System.out.println("Ingresa el segundo número (b):");
        int b = scanner.nextInt();

        System.out.println("Suma: " + (a + b));
        System.out.println("Resta: " + (a - b));
        System.out.println("Multiplicación: " + (a * b));
        System.out.println("División entera: " + (a / b));
        System.out.println("División exacta: " + (a / (double) b));
        System.out.println("Módulo: " + (a % b));
        System.out.println("Potencia: " + Math.pow(a, b));
    }
}
