import java.util.Scanner;

class OperadoresAsignacion {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresa un valor inicial para 'contador':");
        int contador = scanner.nextInt();

        contador++;
        contador += 5;
        contador -= 2;
        contador *= 3;
        System.out.println("contador: " + contador);

        scanner.nextLine(); // limpia el salto de línea pendiente
        System.out.println("Ingresa un nombre (o deja vacío y presiona Enter):");
        String entrada = scanner.nextLine();
        if (entrada.isEmpty()) {
            entrada = null;
        }
        String nombre = (entrada != null) ? entrada : "Invitado";
        System.out.println("nombre: " + nombre);
    }
}
