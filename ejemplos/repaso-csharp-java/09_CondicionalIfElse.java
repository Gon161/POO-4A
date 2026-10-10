import java.util.Scanner;

class CondicionalIfElse {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresa tu edad:");
        int edad = scanner.nextInt();

        if (edad >= 18) {
            System.out.println("Mayor de edad");
        } else if (edad >= 13) {
            System.out.println("Adolescente");
        } else {
            System.out.println("Niño");
        }
    }
}
