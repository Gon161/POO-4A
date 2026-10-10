import java.util.Scanner;

class OperadoresLogicos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresa tu edad:");
        int edad = scanner.nextInt();

        System.out.println("¿Tienes credencial? (true/false):");
        boolean tieneCredencial = scanner.nextBoolean();

        boolean puedeEntrar = (edad >= 18) && (tieneCredencial == true);
        boolean esMenorOInvalido = (edad < 18) || (!tieneCredencial);

        System.out.println("puedeEntrar: " + puedeEntrar);
        System.out.println("esMenorOInvalido: " + esMenorOInvalido);
    }
}
