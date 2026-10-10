import java.util.Scanner;

class Casting {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresa un número decimal (ej. 3.9):");
        double pi = scanner.nextDouble();
        int entero = (int) pi; // trunca, no redondea
        System.out.println("entero: " + entero);

        scanner.nextLine(); // limpia el salto de línea pendiente
        System.out.println("Ingresa un número como texto (ej. 25):");
        String texto = scanner.nextLine();
        int numero = Integer.parseInt(texto);
        System.out.println("numero: " + numero);

        try {
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            System.out.println("Conversión fallida: " + e.getMessage());
        }

        String textoDesdeNumero = String.valueOf(numero);
        System.out.println("textoDesdeNumero: " + textoDesdeNumero);
    }
}
