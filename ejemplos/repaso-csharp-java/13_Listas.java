import java.util.ArrayList;
import java.util.List;

class Listas {
    public static void main(String[] args) {
        List<String> nombres = new ArrayList<>();
        nombres.add("Ana");
        nombres.add("Luis");
        nombres.add("Carlos");

        nombres.remove(1); // elimina por índice, no por valor
        nombres.set(0, "Ana María");

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
