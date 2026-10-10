import java.util.Scanner;

class Metodos {
    static int sumar(int a, int b) {
        return a + b;
    }

    // Java no tiene parámetros por defecto: se simula con sobrecarga
    static int sumar(int a) {
        return sumar(a, 0);
    }

    static void saludar(String nombre) {
        System.out.println("Hola, " + nombre);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresa el primer número:");
        int a = scanner.nextInt();

        System.out.println("Ingresa el segundo número:");
        int b = scanner.nextInt();

        System.out.println(sumar(a, b));
        System.out.println(sumar(a)); // usa la sobrecarga con un solo parámetro

        scanner.nextLine(); // limpia el salto de línea pendiente
        System.out.println("Ingresa tu nombre:");
        String nombre = scanner.nextLine();
        saludar(nombre);
    }
}
