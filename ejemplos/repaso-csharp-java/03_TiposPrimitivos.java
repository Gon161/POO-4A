import java.util.Scanner;

class TiposPrimitivos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresa tu edad (int):");
        int edad = scanner.nextInt();

        System.out.println("Ingresa tu promedio (double):");
        double promedio = scanner.nextDouble();

        scanner.nextLine(); // limpia el salto de línea pendiente antes de leer texto
        System.out.println("Ingresa tu nombre (String):");
        String nombre = scanner.nextLine();

        boolean esActivo = true;
        char letra = 'A';
        byte pequenio = 127;
        short corto = 32000;
        long grande = 10000000000L;
        float flotante = 3.14f;
        // No hay 'decimal'; para precisión exacta se usa BigDecimal

        System.out.println("int: " + edad);
        System.out.println("double: " + promedio);
        System.out.println("boolean: " + esActivo);
        System.out.println("char: " + letra);
        System.out.println("String: " + nombre);
        System.out.println("byte: " + pequenio);
        System.out.println("short: " + corto);
        System.out.println("long: " + grande);
        System.out.println("float: " + flotante);
    }
}
