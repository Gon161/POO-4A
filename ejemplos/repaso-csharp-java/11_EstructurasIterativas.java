class EstructurasIterativas {
    public static void main(String[] args) {
        System.out.println("for:");
        for (int i = 0; i < 3; i++) {
            System.out.println(i);
        }

        System.out.println("while:");
        int j = 0;
        while (j < 3) {
            System.out.println(j);
            j++;
        }

        System.out.println("do-while:");
        int k = 0;
        do {
            System.out.println(k);
            k++;
        } while (k < 3);

        System.out.println("for-each:");
        int[] numeros = { 1, 2, 3 };
        for (var n : numeros) {
            System.out.println(n);
        }
    }
}
