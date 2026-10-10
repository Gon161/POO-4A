import java.util.Scanner;

class EstructurasIterativas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Hasta qué número quieres contar?");
        int limite = scanner.nextInt();

        System.out.println("for:");
        for (int i = 0; i < limite; i++) {
            System.out.println(i);
        }

        System.out.println("while:");
        int j = 0;
        while (j < limite) {
            System.out.println(j);
            j++;
        }

        System.out.println("do-while:");
        int k = 0;
        do {
            System.out.println(k);
            k++;
        } while (k < limite);

        System.out.println("for-each:");
        int[] numeros = { 1, 2, 3 };
        for (var n : numeros) {
            System.out.println(n);
        }
    }
}
