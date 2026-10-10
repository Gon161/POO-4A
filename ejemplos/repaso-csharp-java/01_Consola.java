import java.util.Scanner;

class Consola {
    public static void main(String[] args) {
        System.out.println("Hola Mundo");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresa tu nombre:");
        String nombre = scanner.nextLine();
        System.out.println("Hola, " + nombre);

        // Java no tiene Console.ReadKey() ni Console.Clear() nativos.
        // Para leer una sola tecla o limpiar pantalla se necesitan
        // librerías externas (JLine) o trucos con códigos ANSI.
        System.out.println("Presiona Enter para continuar...");
        scanner.nextLine();
    }
}
