# Ejemplos — Repaso de Java

Un archivo `.java` ejecutable por cada tema de [`repaso-csharp-java.md`](../../repaso-csharp-java.md).

## Cómo ejecutar

- **Desde IntelliJ IDEA o NetBeans (recomendado):** abre el archivo `.java` y ejecútalo con el botón de Run — el IDE ya trae su propio JDK, no hace falta instalar ni configurar nada.
- **Desde la terminal (cmd):** guía detallada paso a paso (qué hacer si `javac` no se reconoce, etc.) en [`COMO-EJECUTAR.md`](COMO-EJECUTAR.md). Resumen rápido:

```bash
javac 03_TiposPrimitivos.java
java TiposPrimitivos
```

> Las clases son **no públicas**, por lo que el nombre del archivo no tiene que coincidir con el nombre de la clase — así se conserva la numeración (`03_TiposPrimitivos.java` → `class TiposPrimitivos`).

## Temas

| # | Tema | Archivo |
|---|---|---|
| 1 | Entrada y salida por consola | [`01_Consola.java`](01_Consola.java) |
| 2 | Comentarios | [`02_Comentarios.java`](02_Comentarios.java) |
| 3 | Tipos de datos primitivos | [`03_TiposPrimitivos.java`](03_TiposPrimitivos.java) |
| 4 | Variables y constantes | [`04_VariablesConstantes.java`](04_VariablesConstantes.java) |
| 5 | Operadores aritméticos | [`05_OperadoresAritmeticos.java`](05_OperadoresAritmeticos.java) |
| 6 | Operadores relacionales | [`06_OperadoresRelacionales.java`](06_OperadoresRelacionales.java) |
| 7 | Operadores lógicos | [`07_OperadoresLogicos.java`](07_OperadoresLogicos.java) |
| 8 | Operadores de asignación e incremento | [`08_OperadoresAsignacion.java`](08_OperadoresAsignacion.java) |
| 9 | Estructura condicional `if`/`else` | [`09_CondicionalIfElse.java`](09_CondicionalIfElse.java) |
| 10 | `switch` (incluye *fall-through*) | [`10_Switch.java`](10_Switch.java) |
| 11 | Estructuras iterativas | [`11_EstructurasIterativas.java`](11_EstructurasIterativas.java) |
| 12 | Arreglos (incluye multidimensionales) | [`12_Arreglos.java`](12_Arreglos.java) |
| 13 | Listas (colecciones dinámicas) | [`13_Listas.java`](13_Listas.java) |
| 14 | Métodos / Funciones | [`14_Metodos.java`](14_Metodos.java) |
| 15 | Conversión de tipos (Casting) | [`15_Casting.java`](15_Casting.java) |
