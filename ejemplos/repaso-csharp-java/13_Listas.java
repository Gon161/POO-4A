import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Listas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> nombres = new ArrayList<>();

        System.out.println("Ingresa nombres uno por uno. Escribe 'fin' para terminar:");
        String entrada;
        do {
            entrada = scanner.nextLine();
            if (!entrada.equalsIgnoreCase("fin")) {
                nombres.add(entrada);
            }
        } while (!entrada.equalsIgnoreCase("fin"));

        System.out.println("size: " + nombres.size());
        for (String nombre : nombres) {
            System.out.println(nombre);
        }

        // Lista de listas: equivalente dinámico a una matriz
        List<List<Integer>> matriz = new ArrayList<>();
        matriz.add(new ArrayList<>(List.of(1, 2, 3)));
        matriz.add(new ArrayList<>(List.of(4, 5)));

        System.out.println("matriz.get(0).get(2) = " + matriz.get(0).get(2));
        System.out.println("matriz.get(1).size() = " + matriz.get(1).size());
    }
}
