class Switch {
    public static void main(String[] args) {
        int dia = 1;

        switch (dia) {
            case 1:
                System.out.println("Lunes");
                break;
            case 2:
                System.out.println("Martes");
                break;
            default:
                System.out.println("Otro día");
        }

        String nombreDia = switch (dia) {
            case 1 -> "Lunes";
            case 2 -> "Martes";
            default -> "Otro día";
        };
        System.out.println("Switch expresión: " + nombreDia);

        // fall-through: al omitir el break de case 1, cae a case 2
        switch (dia) {
            case 1:
                System.out.println("Es lunes");
                // sin break: cae intencionalmente al siguiente case
            case 2:
                System.out.println("Esto también se imprime por el fall-through");
                break;
        }
    }
}
