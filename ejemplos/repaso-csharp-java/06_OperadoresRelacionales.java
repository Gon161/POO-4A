class OperadoresRelacionales {
    public static void main(String[] args) {
        int x = 10, y = 20;

        System.out.println("x == y: " + (x == y));
        System.out.println("x != y: " + (x != y));
        System.out.println("x > y: " + (x > y));
        System.out.println("x < y: " + (x < y));
        System.out.println("x >= y: " + (x >= y));
        System.out.println("x <= y: " + (x <= y));

        String a = "hola";
        String b = new String("hola");
        System.out.println("a == b (referencias): " + (a == b));        // false
        System.out.println("a.equals(b) (contenido): " + a.equals(b));  // true
    }
}
