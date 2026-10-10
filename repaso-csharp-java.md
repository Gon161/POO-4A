# Repaso de Programación — C# vs Java

Repaso de fundamentos de programación antes de entrar a POO, comparando sintaxis y comportamiento entre **C#** y **Java**. Ambos lenguajes son fuertemente tipados, orientados a objetos y comparten mucha sintaxis heredada de C, pero tienen diferencias importantes que conviene tener claras desde el inicio.

---

## Índice

1. [Entrada y salida por consola](#1-entrada-y-salida-por-consola)
2. [Comentarios](#2-comentarios)
3. [Tipos de datos primitivos](#3-tipos-de-datos-primitivos)
4. [Variables y constantes](#4-variables-y-constantes)
5. [Operadores aritméticos](#5-operadores-aritméticos)
6. [Operadores relacionales](#6-operadores-relacionales)
7. [Operadores lógicos](#7-operadores-lógicos)
8. [Operadores de asignación e incremento](#8-operadores-de-asignación-e-incremento)
9. [Estructura condicional `if` / `else`](#9-estructura-condicional-if--else)
10. [`switch`](#10-switch)
11. [Estructuras iterativas](#11-estructuras-iterativas)
12. [Arreglos (Arrays)](#12-arreglos-arrays)
13. [Listas (Colecciones dinámicas)](#13-listas-colecciones-dinámicas)
14. [Métodos / Funciones](#14-métodos--funciones)
15. [Conversión de tipos (Casting)](#15-conversión-de-tipos-casting)

---

## 1. Entrada y salida por consola

Todo programador empieza igual: un "Hola Mundo" en la consola. Por eso arrancamos aquí antes de ver tipos, operadores o estructuras de control.

| Operación | C# | Java | Notas |
|---|---|---|---|
| Imprimir con salto de línea | `Console.WriteLine(valor);` | `System.out.println(valor);` | Igual |
| Imprimir sin salto de línea | `Console.Write(valor);` | `System.out.print(valor);` | Igual |
| Leer línea de texto | `Console.ReadLine();` | `new Scanner(System.in).nextLine();` | Igual |
| Leer número | `int.Parse(Console.ReadLine());` | `scanner.nextInt();` | Igual |
| Leer una sola tecla | `Console.ReadKey();` | **No existe** nativo en consola estándar | Java requiere librerías externas (p. ej. JLine) para leer tecla por tecla sin `Enter` |
| Limpiar la pantalla | `Console.Clear();` | **No existe** de forma portable | En Java se simula con `System.out.print("\033[H\033[2J")` (no funciona en todas las consolas) o imprimiendo saltos de línea |
| Cambiar título de la ventana | `Console.Title = "Mi App";` | **No existe** | Java no tiene control del título de la terminal |
| Color de texto/fondo | `Console.ForegroundColor` / `Console.BackgroundColor` | **No existe** nativo | Java requiere códigos ANSI manuales o librerías (JAnsi, etc.) |
| Posición del cursor | `Console.SetCursorPosition(x, y);` | **No existe** nativo | Requiere códigos ANSI o librerías externas |

```csharp
// C# — Hola Mundo
Console.WriteLine("Hola Mundo");
```

```java
// Java — Hola Mundo
System.out.println("Hola Mundo");
```

```csharp
Console.Title = "Demo Consola";
Console.WriteLine("Ingresa tu nombre:");
string nombre = Console.ReadLine();
Console.WriteLine($"Hola, {nombre}");

Console.ForegroundColor = ConsoleColor.Green;
Console.WriteLine("Presiona una tecla para continuar...");
Console.ResetColor();

Console.ReadKey();   // espera una sola tecla, sin necesidad de Enter
Console.Clear();     // limpia toda la pantalla
```

```java
Scanner scanner = new Scanner(System.in);
System.out.println("Ingresa tu nombre:");
String nombre = scanner.nextLine();
System.out.println("Hola, " + nombre);

// Java no tiene Console.ReadKey() ni Console.Clear() nativos.
// Para leer una sola tecla o limpiar pantalla se necesitan
// librerías externas (JLine) o trucos con códigos ANSI,
// que no son portables entre sistemas operativos.
System.out.println("Presiona Enter para continuar...");
scanner.nextLine();
```

> `Console.ReadKey()` y `Console.Clear()` son parte de la clase `System.Console` de .NET y no tienen equivalente directo en la biblioteca estándar de Java — ahí es donde más se nota que Java trata la consola como un flujo de texto simple (`System.in` / `System.out`), mientras que .NET expone una API más completa para controlar la terminal.

---

## 2. Comentarios

| Tipo | C# | Java |
|---|---|---|
| Línea | `// comentario` | `// comentario` |
| Bloque | `/* comentario */` | `/* comentario */` |
| Documentación | `/// <summary>...</summary>` (XML doc) | `/** ... */` (Javadoc) |

---

## 3. Tipos de datos primitivos

| Tipo | C# | Java | Notas |
|---|---|---|---|
| Entero corto | `byte` (8 bits, sin signo) | `byte` (8 bits, **con signo**) | En Java `byte` va de -128 a 127 |
| Entero corto | `short` (16 bits) | `short` (16 bits) | Iguales |
| Entero | `int` (32 bits) | `int` (32 bits) | Iguales |
| Entero largo | `long` (64 bits) | `long` (64 bits) | En Java se escribe `10L`, en C# `10L` también |
| Decimal flotante | `float` (32 bits) | `float` (32 bits) | Sufijo `f` en ambos: `3.14f` |
| Decimal doble | `double` (64 bits) | `double` (64 bits) | Iguales |
| Decimal preciso | `decimal` (128 bits) | **No existe** | Java usa `BigDecimal` (clase, no primitivo) |
| Booleano | `bool` | `boolean` | Nombre distinto |
| Carácter | `char` (UTF-16) | `char` (UTF-16) | Iguales |
| Cadena | `string` (alias de `String`) | `String` | En C# es un alias; en Java es una clase, no un primitivo |

```csharp
// C#
int edad = 20;
double promedio = 8.75;
bool esActivo = true;
char letra = 'A';
string nombre = "Nerial";
decimal precio = 199.99m; // sufijo m
```

```java
// Java
int edad = 20;
double promedio = 8.75;
boolean esActivo = true;
char letra = 'A';
String nombre = "Nerial";
// No hay 'decimal'; para precisión exacta se usa BigDecimal
```

---

## 4. Variables y constantes

| Concepto | C# | Java |
|---|---|---|
| Declaración | `tipo nombre = valor;` | `tipo nombre = valor;` |
| Inferencia de tipo | `var x = 10;` | `var x = 10;` (desde Java 10) |
| Constante | `const int MAX = 100;` | `final int MAX = 100;` |
| Constante de clase/estática | `static readonly` o `const` | `static final` |

```csharp
// C#
var total = 0;        // inferido como int
const double PI = 3.1416;
```

```java
// Java
var total = 0;             // inferido como int
final double PI = 3.1416;
```

---

## 5. Operadores aritméticos

| Operador | C# | Java | Notas |
|---|---|---|---|
| Suma | `+` | `+` | Igual |
| Resta | `-` | `-` | Igual |
| Multiplicación | `*` | `*` | Igual |
| División | `/` | `/` | División entera si ambos operandos son enteros, en **ambos** lenguajes |
| Módulo | `%` | `%` | Igual |
| Potencia | `Math.Pow(b, e)` | `Math.pow(b, e)` | Ninguno tiene operador `**` |

```csharp
int resultado = 7 / 2;       // 3 (división entera)
double exacto = 7.0 / 2;     // 3.5
double potencia = Math.Pow(2, 3); // 8
```

```java
int resultado = 7 / 2;       // 3 (división entera)
double exacto = 7.0 / 2;     // 3.5
double potencia = Math.pow(2, 3); // 8
```

---

## 6. Operadores relacionales

| Operador | C# | Java | Notas |
|---|---|---|---|
| Igual | `==` | `==` | **Cuidado con `String`**: en Java `==` compara referencias, no contenido |
| Distinto | `!=` | `!=` | Igual |
| Mayor / menor | `>` `<` | `>` `<` | Igual |
| Mayor o igual / menor o igual | `>=` `<=` | `>=` `<=` | Igual |

```csharp
string a = "hola";
string b = "hola";
bool iguales = a == b; // true (C# compara contenido para string)
```

```java
String a = "hola";
String b = new String("hola");
boolean iguales = a == b;       // false (compara referencias)
boolean igualesReal = a.equals(b); // true (compara contenido)
```

> En Java, para comparar el **contenido** de objetos (incluyendo `String`) siempre se usa `.equals()`, nunca `==`, salvo que quieras comparar referencias a propósito.

---

## 7. Operadores lógicos

| Operador | C# | Java | Notas |
|---|---|---|---|
| AND lógico (corto-circuito) | `&&` | `&&` | Igual |
| OR lógico (corto-circuito) | `\|\|` | `\|\|` | Igual |
| NOT lógico | `!` | `!` | Igual |

```csharp
bool resultado = (edad >= 18) && (tieneCredencial == true);
```

```java
boolean resultado = (edad >= 18) && (tieneCredencial == true);
```

---

## 8. Operadores de asignación e incremento

| Operador | C# | Java |
|---|---|---|
| Asignación | `=` | `=` |
| Asignación compuesta | `+= -= *= /= %=` | `+= -= *= /= %=` |
| Incremento / decremento | `++` `--` (prefijo y postfijo) | `++` `--` (prefijo y postfijo) |
| Null-coalescing | `??` y `??=` | **No existe** (se usa `Optional` o `if`) |

```csharp
int contador = 0;
contador++;
contador += 5;
string nombre = entrada ?? "Invitado"; // si entrada es null, usa "Invitado"
```

```java
int contador = 0;
contador++;
contador += 5;
String nombre = (entrada != null) ? entrada : "Invitado";
```

---

## 9. Estructura condicional `if` / `else`

Sintaxis **idéntica** en ambos lenguajes.

```csharp
// C#
if (edad >= 18)
{
    Console.WriteLine("Mayor de edad");
}
else if (edad >= 13)
{
    Console.WriteLine("Adolescente");
}
else
{
    Console.WriteLine("Niño");
}
```

```java
// Java
if (edad >= 18) {
    System.out.println("Mayor de edad");
} else if (edad >= 13) {
    System.out.println("Adolescente");
} else {
    System.out.println("Niño");
}
```

---

## 10. `switch`

| Característica | C# | Java |
|---|---|---|
| `switch` clásico | Sí, **no permite** *fall-through* implícito | Sí, **permite** *fall-through* si se omite `break` |
| `switch` expresión (moderno) | Sí, desde C# 8 (`=>`) | Sí, desde Java 14 (`->`) |
| Switch sobre `String` | Sí | Sí |
| Switch sobre patrones/tipos | Sí (*pattern matching* avanzado) | Sí (*pattern matching* desde Java 17+) |

```csharp
// C# clásico
switch (dia)
{
    case 1:
        Console.WriteLine("Lunes");
        break;
    default:
        Console.WriteLine("Otro día");
        break;
}

// C# moderno (expresión)
string nombreDia = dia switch
{
    1 => "Lunes",
    2 => "Martes",
    _ => "Otro día"
};
```

```java
// Java clásico
switch (dia) {
    case 1:
        System.out.println("Lunes");
        break;
    default:
        System.out.println("Otro día");
        break;
}

// Java moderno (expresión, Java 14+)
String nombreDia = switch (dia) {
    case 1 -> "Lunes";
    case 2 -> "Martes";
    default -> "Otro día";
};
```

### ¿Qué es el *fall-through*?

*Fall-through* ("caer a través") es cuando, al omitir `break` al final de un `case`, la ejecución **continúa automáticamente** hacia el siguiente `case`, sin volver a evaluar su condición — simplemente sigue ejecutando el código de abajo hacia abajo.

```java
// Java: fall-through intencional (o accidental si se olvida el break)
int dia = 1;
switch (dia) {
    case 1:
        System.out.println("Lunes");
        // sin break: cae al siguiente case
    case 2:
        System.out.println("Martes");
        break;
    default:
        System.out.println("Otro día");
}
// Salida con dia = 1:
// Lunes
// Martes
```

Esto es útil cuando varios casos deben compartir el mismo bloque de código (por ejemplo, agrupar días del fin de semana), pero es una fuente común de bugs cuando se olvida el `break` por accidente.

```java
// Uso válido e intencional: varios case comparten lógica
switch (dia) {
    case 6:
    case 7:
        System.out.println("Es fin de semana");
        break;
    default:
        System.out.println("Es día laboral");
}
```

**En C#, esto no existe por diseño.** El compilador obliga a que cada `case` con código termine en `break`, `return`, `throw` o `goto` — no se puede "caer" al siguiente caso sin indicarlo explícitamente. Si de verdad quieres ese comportamiento, se hace explícito con `goto case`:

```csharp
// C#: fall-through explícito con goto case
switch (dia)
{
    case 1:
        Console.WriteLine("Lunes");
        goto case 2; // forzado explícitamente, no es automático
    case 2:
        Console.WriteLine("Martes");
        break;
    default:
        Console.WriteLine("Otro día");
        break;
}
```

> Esta es una diferencia real de diseño: Java hereda el comportamiento de C/C++ (fall-through automático, hay que cortar con `break`), mientras que C# lo considera una fuente de errores y lo prohíbe salvo que se pida explícitamente con `goto case`.

---

## 11. Estructuras iterativas

| Estructura | C# | Java | Notas |
|---|---|---|---|
| `for` | `for (int i = 0; i < n; i++)` | `for (int i = 0; i < n; i++)` | Idéntico |
| `while` | `while (condición)` | `while (condición)` | Idéntico |
| `do-while` | `do { } while (condición);` | `do { } while (condición);` | Idéntico |
| Recorrido de colecciones | `foreach (var x in coleccion)` | `for (var x : coleccion)` | Palabra clave y sintaxis distintas |

```csharp
// C#
int[] numeros = { 1, 2, 3 };
foreach (var n in numeros)
{
    Console.WriteLine(n);
}
```

```java
// Java
int[] numeros = { 1, 2, 3 };
for (var n : numeros) {
    System.out.println(n);
}
```

---

## 12. Arreglos (Arrays)

| Concepto | C# | Java |
|---|---|---|
| Declaración | `int[] numeros = new int[5];` | `int[] numeros = new int[5];` |
| Inicialización literal | `int[] numeros = { 1, 2, 3 };` | `int[] numeros = { 1, 2, 3 };` |
| Longitud | `numeros.Length` (propiedad) | `numeros.length` (campo) |

```csharp
int[] numeros = { 10, 20, 30 };
Console.WriteLine(numeros.Length); // 3
```

```java
int[] numeros = { 10, 20, 30 };
System.out.println(numeros.length); // 3
```

### 12.1 Arreglos multidimensionales

| Concepto | C# | Java |
|---|---|---|
| Arreglo rectangular (matriz fija) | `int[,] matriz = new int[3,3];` | **No existe** como tipo nativo |
| Arreglo de arreglos (*jagged*) | `int[][] jagged = new int[3][];` | `int[][] matriz = new int[3][3];` (así es como Java simula una matriz) |
| Acceso a un elemento | `matriz[1,2]` | `matriz[1][2]` |
| Número de filas | `matriz.GetLength(0)` | `matriz.length` |
| Número de columnas | `matriz.GetLength(1)` | `matriz[0].length` (columnas de la fila 0) |
| Filas de distinto tamaño | Solo con `int[][]` (*jagged*), no con `int[,]` | Siempre es posible, ya que todo es arreglo de arreglos |

> **Diferencia clave:** C# tiene dos tipos distintos: arreglos **rectangulares** (`int[,]`, una sola bloque de memoria, todas las filas del mismo tamaño) y arreglos ***jagged*** (`int[][]`, un arreglo de arreglos, cada fila puede tener su propio tamaño). **Java solo tiene el segundo tipo** — `int[][]` siempre es un arreglo de arreglos, aunque se inicialice con un tamaño fijo como si fuera rectangular.

```csharp
// C# — arreglo rectangular (matriz fija, 3x3)
int[,] matriz = {
    { 1, 2, 3 },
    { 4, 5, 6 },
    { 7, 8, 9 }
};

Console.WriteLine(matriz[1, 2]); // 6
Console.WriteLine(matriz.GetLength(0)); // 3 filas
Console.WriteLine(matriz.GetLength(1)); // 3 columnas

for (int fila = 0; fila < matriz.GetLength(0); fila++)
{
    for (int col = 0; col < matriz.GetLength(1); col++)
    {
        Console.Write(matriz[fila, col] + " ");
    }
    Console.WriteLine();
}
```

```csharp
// C# — arreglo jagged (filas de distinto tamaño)
int[][] jagged = new int[3][];
jagged[0] = new int[] { 1 };
jagged[1] = new int[] { 1, 2, 3 };
jagged[2] = new int[] { 1, 2 };

for (int fila = 0; fila < jagged.Length; fila++)
{
    for (int col = 0; col < jagged[fila].Length; col++)
    {
        Console.Write(jagged[fila][col] + " ");
    }
    Console.WriteLine();
}
```

```java
// Java — "matriz" de tamaño fijo (en realidad es un arreglo de arreglos)
int[][] matriz = {
    { 1, 2, 3 },
    { 4, 5, 6 },
    { 7, 8, 9 }
};

System.out.println(matriz[1][2]);        // 6
System.out.println(matriz.length);       // 3 filas
System.out.println(matriz[0].length);    // 3 columnas (de la fila 0)

for (int fila = 0; fila < matriz.length; fila++) {
    for (int col = 0; col < matriz[fila].length; col++) {
        System.out.print(matriz[fila][col] + " ");
    }
    System.out.println();
}
```

```java
// Java — filas de distinto tamaño (siempre es posible, es la naturaleza de int[][])
int[][] jagged = new int[3][];
jagged[0] = new int[] { 1 };
jagged[1] = new int[] { 1, 2, 3 };
jagged[2] = new int[] { 1, 2 };

for (int fila = 0; fila < jagged.length; fila++) {
    for (int col = 0; col < jagged[fila].length; col++) {
        System.out.print(jagged[fila][col] + " ");
    }
    System.out.println();
}
```

> También existen arreglos de **3 o más dimensiones**: `int[,,] cubo = new int[2,2,2];` en C#, o `int[][][] cubo = new int[2][2][2];` en Java. Se usan poco fuera de contextos matemáticos o gráficos, pero la lógica de acceso (`cubo[x,y,z]` vs `cubo[x][y][z]`) es la misma que con dos dimensiones.

---

## 13. Listas (Colecciones dinámicas)

Un arreglo tiene tamaño fijo una vez creado. Cuando se necesita agregar o quitar elementos dinámicamente, se usa una **lista**.

| Concepto | C# | Java | Notas |
|---|---|---|---|
| Tipo genérico de lista | `List<T>` | `List<T>` (interfaz) + `ArrayList<T>` (implementación común) | En C# `List<T>` es una clase concreta; en Java `List` es una interfaz que casi siempre se implementa con `ArrayList` |
| Crear una lista | `List<int> numeros = new List<int>();` | `List<Integer> numeros = new ArrayList<>();` | Java no permite tipos primitivos como genéricos: se usa `Integer`, no `int` |
| Agregar elemento | `numeros.Add(10);` | `numeros.add(10);` | Mismo nombre, distinta convención de mayúsculas |
| Eliminar elemento (por valor) | `numeros.Remove(10);` | `numeros.remove(Integer.valueOf(10));` | En Java hay que tener cuidado: `remove(10)` elimina por **índice**, no por valor |
| Eliminar elemento (por índice) | `numeros.RemoveAt(0);` | `numeros.remove(0);` | |
| Acceder por índice | `numeros[0]` | `numeros.get(0)` | C# permite indexador `[]`; Java requiere `.get()` |
| Modificar por índice | `numeros[0] = 99;` | `numeros.set(0, 99);` | |
| Tamaño / cantidad | `numeros.Count` (propiedad) | `numeros.size()` (método) | |
| Verificar si contiene un valor | `numeros.Contains(10);` | `numeros.contains(10);` | |
| Recorrer | `foreach (var n in numeros)` | `for (var n : numeros)` | Igual que con arreglos |

```csharp
// C#
List<string> nombres = new List<string>();
nombres.Add("Ana");
nombres.Add("Luis");
nombres.Add("Carlos");

nombres.RemoveAt(1);           // elimina "Luis"
nombres[0] = "Ana María";      // modifica el primer elemento

Console.WriteLine(nombres.Count); // 2

foreach (var nombre in nombres)
{
    Console.WriteLine(nombre);
}
```

```java
// Java
List<String> nombres = new ArrayList<>();
nombres.add("Ana");
nombres.add("Luis");
nombres.add("Carlos");

nombres.remove(1);                 // elimina "Luis" (por índice)
nombres.set(0, "Ana María");       // modifica el primer elemento

System.out.println(nombres.size()); // 2

for (String nombre : nombres) {
    System.out.println(nombre);
}
```

### Lista de listas (equivalente dinámico a una matriz)

Cuando se necesita una "matriz" cuyo tamaño no se conoce de antemano, se usa una lista de listas en vez de un arreglo multidimensional.

```csharp
// C#
List<List<int>> matriz = new List<List<int>>();
matriz.Add(new List<int> { 1, 2, 3 });
matriz.Add(new List<int> { 4, 5 });

Console.WriteLine(matriz[0][2]); // 3
Console.WriteLine(matriz[1].Count); // 2
```

```java
// Java
List<List<Integer>> matriz = new ArrayList<>();
matriz.add(new ArrayList<>(List.of(1, 2, 3)));
matriz.add(new ArrayList<>(List.of(4, 5)));

System.out.println(matriz.get(0).get(2)); // 3
System.out.println(matriz.get(1).size());  // 2
```

---

## 14. Métodos / Funciones

| Concepto | C# | Java |
|---|---|---|
| Declaración | `tipoRetorno NombreMetodo(parametros)` | `tipoRetorno nombreMetodo(parametros)` |
| Convención de nombres | PascalCase (`CalcularTotal`) | camelCase (`calcularTotal`) |
| Método sin retorno | `void` | `void` |
| Sobrecarga de métodos | Sí | Sí |
| Parámetros por defecto | Sí (`int x = 0`) | **No existe** (se usa sobrecarga) |

```csharp
static int Sumar(int a, int b = 0)
{
    return a + b;
}
```

```java
static int sumar(int a, int b) {
    return a + b;
}
// Java no tiene valores por defecto: se necesita una sobrecarga
static int sumar(int a) {
    return sumar(a, 0);
}
```

---

## 15. Conversión de tipos (Casting)

| Operación | C# | Java |
|---|---|---|
| Casting explícito (numérico) | `(int)miDouble` | `(int) miDouble` |
| Texto → número | `int.Parse("10")` o `Convert.ToInt32("10")` | `Integer.parseInt("10")` |
| Número → texto | `numero.ToString()` | `String.valueOf(numero)` o `Integer.toString(numero)` |
| Conversión segura (sin excepción) | `int.TryParse(texto, out valor)` | `try/catch` con `NumberFormatException` |

```csharp
double pi = 3.9;
int entero = (int)pi; // 3 (trunca, no redondea)

string texto = "25";
int numero = int.Parse(texto);
```

```java
double pi = 3.9;
int entero = (int) pi; // 3 (trunca, no redondea)

String texto = "25";
int numero = Integer.parseInt(texto);
```

---

## Resumen de diferencias clave

- **Convenciones de nombres:** C# usa PascalCase para métodos y propiedades; Java usa camelCase.
- **`string` vs `String`:** en C# es un alias de tipo primitivo-like; en Java es una clase normal.
- **Comparación de cadenas:** `==` compara contenido en C#, pero compara referencias en Java (usar `.equals()`).
- **Parámetros por defecto:** existen en C#, no en Java (se resuelve con sobrecarga de métodos).
- **Precisión decimal exacta:** C# tiene `decimal` nativo; Java requiere `BigDecimal`.
- **Control de consola:** C# (`System.Console`) tiene una API rica (`ReadKey`, `Clear`, colores, cursor); Java solo ofrece flujos de texto simples (`System.in`/`System.out`).

*Universidad Politécnica de Tecamac — Ingeniería en Software*
