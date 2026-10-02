class VariablesConstantes {
    static final String VERSION = "1.0";
    static final double PI = 3.1416;

    public static void main(String[] args) {
        var total = 0; // inferido como int
        total += 10;

        System.out.println("total: " + total);
        System.out.println("PI: " + PI);
        System.out.println("VERSION: " + VERSION);
    }
}
