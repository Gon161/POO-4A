# Unidad 2 — Matrices y Métodos

Continuación del repaso de C# a Java ([`repaso-csharp-java.md`](repaso-csharp-java.md), Unidad 1). Esta unidad profundiza en dos temas que ya se tocaron de forma básica ahí — arreglos y métodos — pero enfocados específicamente en **matrices** (arreglos de más de una dimensión) y en el **paso de parámetros**, que es la base para entender la POO de las unidades 3 y 4.

---

## Índice

1. [Matrices](#21-matrices)
   - 2.1.1. [Declaración e inicialización](#211-declaración-e-inicialización)
   - 2.1.2. [Recorrido con bucles anidados](#212-recorrido-con-bucles-anidados)
2. [Métodos](#22-métodos)
   - 2.2.1. [Parámetros y valor de retorno](#221-parámetros-y-valor-de-retorno)
   - 2.2.2. [Paso de parámetros por valor y por referencia](#222-paso-de-parámetros-por-valor-y-por-referencia)

---

## 2.1. Matrices

**Qué es:** una matriz es un arreglo de **dos o más dimensiones** — en la práctica, la forma más común es la de dos dimensiones (filas y columnas), que modela directamente cosas como una cuadrícula, un tablero o una tabla de datos.

**Por qué existe:** un arreglo normal (unidimensional, visto en la Unidad 1) solo modela una secuencia lineal de valores. Muchos problemas reales son inherentemente bidimensionales (o de más dimensiones): la posición de una ficha en un tablero necesita fila **y** columna, no un solo índice. La matriz extiende la idea del arreglo para cubrir ese caso sin tener que simular manualmente las dos dimensiones con aritmética sobre un arreglo de una sola dimensión.

### 2.1.1. Declaración e inicialización

| Concepto | C# | Java |
|---|---|---|
| Arreglo rectangular (matriz fija) | `int[,] matriz = new int[3,3];` | **No existe** como tipo nativo |
| Arreglo de arreglos (*jagged*) | `int[][] jagged = new int[3][];` | `int[][] matriz = new int[3][3];` (así es como Java simula una matriz) |
| Acceso a un elemento | `matriz[1,2]` | `matriz[1][2]` |
| Número de filas | `matriz.GetLength(0)` | `matriz.length` |
| Número de columnas | `matriz.GetLength(1)` | `matriz[0].length` (columnas de la fila 0) |
| Filas de distinto tamaño | Solo con `int[][]` (*jagged*), no con `int[,]` | Siempre es posible, ya que todo es arreglo de arreglos |

> **Diferencia clave:** C# tiene dos tipos distintos: arreglos **rectangulares** (`int[,]`, un solo bloque de memoria, todas las filas del mismo tamaño) y arreglos ***jagged*** (`int[][]`, un arreglo de arreglos, cada fila puede tener su propio tamaño). **Java solo tiene el segundo tipo** — `int[][]` siempre es un arreglo de arreglos, aunque se inicialice con un tamaño fijo como si fuera rectangular.

**Cómo funciona por dentro — cómo se ve esto en memoria:**

```
C# int[,] (rectangular) — UN solo bloque contiguo:
┌───┬───┬───┬───┬───┬───┬───┬───┬───┐
│ 1 │ 2 │ 3 │ 4 │ 5 │ 6 │ 7 │ 8 │ 9 │   matriz[1,2] = base + (1*3 + 2) = posición 5 → valor 6
└───┴───┴───┴───┴───┴───┴───┴───┴───┘

Java int[][] (jagged, aunque sea "rectangular" en apariencia) — un arreglo de REFERENCIAS:
matriz ──▶ [ ref0, ref1, ref2 ]
             │     │     │
             ▼     ▼     ▼
           [1,2,3][4,5,6][7,8,9]   ← tres arreglos independientes, no necesariamente contiguos
```

Esta diferencia tiene dos consecuencias prácticas: (1) el arreglo rectangular de C# usa **menos memoria**, porque no necesita un objeto arreglo independiente por cada fila (solo un encabezado para todo el bloque); (2) en Java, como cada fila es un objeto separado, es completamente válido que las filas tengan tamaños distintos — la "irregularidad" (*jagged*) no es una característica especial, es simplemente la consecuencia natural de que `int[][]` siempre funciona así.

**Declaración e inicialización — ejemplos:**

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
```

```csharp
// C# — arreglo jagged (filas de distinto tamaño)
int[][] jagged = new int[3][];
jagged[0] = new int[] { 1 };
jagged[1] = new int[] { 1, 2, 3 };
jagged[2] = new int[] { 1, 2 };
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
```

```java
// Java — filas de distinto tamaño (siempre es posible, es la naturaleza de int[][])
int[][] jagged = new int[3][];
jagged[0] = new int[] { 1 };
jagged[1] = new int[] { 1, 2, 3 };
jagged[2] = new int[] { 1, 2 };
```

**Errores y malentendidos típicos:**

- **Confundir `int[,]` (C#) con `int[][]`:** no son intercambiables — el primero es un bloque rectangular, el segundo un arreglo de arreglos, y la sintaxis de acceso es distinta (`matriz[1,2]` vs `matriz[1][2]`).
- **Asumir que `int[][]` en Java garantiza filas del mismo tamaño:** nada lo garantiza — si solo se inicializan algunas filas, las demás quedan en `null` hasta que se les asigne explícitamente un arreglo.

> También existen arreglos de **3 o más dimensiones**: `int[,,] cubo = new int[2,2,2];` en C#, o `int[][][] cubo = new int[2][2][2];` en Java. Se usan poco fuera de contextos matemáticos o gráficos, pero la lógica de acceso (`cubo[x,y,z]` vs `cubo[x][y][z]`) es la misma que con dos dimensiones.

### 2.1.2. Recorrido con bucles anidados

**Por qué un bucle anidado:** recorrer una matriz de dos dimensiones requiere **dos** índices (fila y columna) que varían de forma coordinada — un bucle externo que avanza fila por fila, y un bucle interno que, para cada fila, avanza columna por columna. Esta estructura de "bucle dentro de otro bucle" es exactamente lo que se necesita para visitar cada combinación de fila y columna exactamente una vez.

**Cómo funciona por dentro — orden de recorrido:** por cada iteración del bucle **externo** (una fila fija), el bucle **interno** se ejecuta completo (todas las columnas de esa fila) antes de que el externo avance a la siguiente fila. Esto se llama recorrido **por filas** (*row-major*) — es el orden natural dado que, como se vio en 2.1.1, cada fila ya es un bloque contiguo (en C# dentro del bloque rectangular; en Java como arreglo independiente), así que recorrer una fila completa antes de saltar a la siguiente aprovecha mejor la localidad de memoria.

```csharp
// C#
for (int fila = 0; fila < matriz.GetLength(0); fila++)
{
    for (int col = 0; col < matriz.GetLength(1); col++)
    {
        Console.Write(matriz[fila, col] + " ");
    }
    Console.WriteLine();
}
```

```java
// Java
for (int fila = 0; fila < matriz.length; fila++) {
    for (int col = 0; col < matriz[fila].length; col++) {
        System.out.print(matriz[fila][col] + " ");
    }
    System.out.println();
}
```

> **Ojo con usar `matriz.length` o `matriz.GetLength(1)` (número fijo de columnas) para el límite del bucle interno cuando el arreglo es *jagged*.** Si las filas tienen tamaños distintos (como en el ejemplo `jagged` de 2.1.1), el límite correcto del bucle interno es `jagged[fila].length` (la longitud de **esa** fila específica), no un número fijo — de lo contrario se produce `IndexOutOfBoundsException`/`IndexOutOfRangeException` en las filas más cortas.

**Trace paso a paso — recorriendo el arreglo *jagged* `{ {1}, {1,2,3}, {1,2} }`:**

| Iteración externa (`fila`) | Longitud de esa fila | Iteraciones internas (`col`) | Qué se imprime |
|---|---|---|---|
| `fila = 0` | 1 | `col = 0` | `1` |
| `fila = 1` | 3 | `col = 0, 1, 2` | `1 2 3` |
| `fila = 2` | 2 | `col = 0, 1` | `1 2` |

**Errores y malentendidos típicos:**

- **Usar el mismo nombre de variable para ambos índices** (`for (int i...) for (int i...)`): el bucle interno "tapa" (ensombrece) la `i` del externo dentro de su propio ámbito — es un error de compilación en Java/C# si se declara con el mismo nombre en el mismo ámbito anidado, así que conviene nombrar los índices de forma distinta (`fila`/`col`) desde el principio para evitar confusión.
- **Invertir el orden de los índices sin darse cuenta** (`matriz[col, fila]` en vez de `matriz[fila, col]`): en una matriz cuadrada (mismo número de filas y columnas) este error no lanza ninguna excepción — simplemente da resultados incorrectos de forma silenciosa, porque ambos índices están dentro del rango válido.

**Cuándo usar bucles anidados vs un solo bucle:** usa bucles anidados siempre que el problema tenga una estructura genuinamente bidimensional (tableros, cuadrículas, tablas); si solo necesitas visitar cada elemento sin importar su posición exacta (fila/columna), a veces es más simple aplanar la lógica con un solo contador que recorra todo linealmente, aunque esto es menos común y menos legible para matrices reales.

---

## 2.2. Métodos

### 2.2.1. Parámetros y valor de retorno

**Qué es:** un método es un bloque de código con nombre propio, que puede recibir datos de entrada (**parámetros**) y devolver un resultado (**valor de retorno**), diseñado para ejecutarse cada vez que se le "llama" desde otra parte del programa.

**Por qué existen:** sin métodos, cualquier operación que se repita en varios lugares del programa tendría que copiarse y pegarse cada vez — si luego hay que corregir un error en esa lógica, habría que corregirlo en cada copia. Los métodos son la herramienta básica contra la duplicación de código (principio DRY, *Don't Repeat Yourself*) y permiten dividir un problema grande en partes más pequeñas y manejables.

**Cómo funciona por dentro — la pila de llamadas:** cada vez que se llama a un método, el programa reserva un nuevo bloque en la **pila de llamadas** (*call stack*) para esa llamada específica: ahí se guardan los parámetros, las variables locales del método, y la dirección a la que hay que regresar cuando el método termine. Cuando el método hace `return` (o llega a su `}` final), ese bloque se destruye automáticamente y la ejecución regresa al punto exacto donde se hizo la llamada. Si un método se llama a sí mismo indefinidamente sin una condición de salida (recursión sin caso base), la pila crece sin límite hasta agotar la memoria reservada para ella, lo que produce `StackOverflowError` (Java) o `StackOverflowException` (C#).

| Concepto | C# | Java |
|---|---|---|
| Declaración | `tipoRetorno NombreMetodo(parametros)` | `tipoRetorno nombreMetodo(parametros)` |
| Convención de nombres | PascalCase (`CalcularTotal`) | camelCase (`calcularTotal`) |
| Método sin retorno | `void` | `void` |
| Sobrecarga de métodos | Sí | Sí (se profundiza en la unidad 4, sección 4.2.1) |
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

**Errores y malentendidos típicos:**

- **Olvidar el `return` en un método que no es `void`:** el compilador rechaza cualquier camino de ejecución dentro del método que pueda terminar sin devolver un valor del tipo declarado — a diferencia de otros errores silenciosos, este es siempre un error de **compilación**.
- **Declarar `void` y luego intentar usar el resultado:** `int x = miMetodoVoid();` no compila — un método `void` no produce ningún valor que se pueda asignar.
- **Sobrecargar métodos de forma ambigua:** declarar `sumar(int a, double b)` y `sumar(double a, int b)` puede hacer que una llamada como `sumar(1, 2)` sea ambigua para el compilador si ambos parámetros podrían convertirse implícitamente — produce un error de compilación, no una elección "inteligente" de cuál usar.

**Cuándo usar sobrecarga vs parámetros por defecto:** en C#, si varias variantes de un método solo difieren en que algunos parámetros son opcionales con un valor típico, usa parámetros por defecto (`int b = 0`) por ser más compacto; en Java, al no existir esa característica, la sobrecarga de métodos (declarar varias versiones con distinta cantidad de parámetros) es la única alternativa.

### 2.2.2. Paso de parámetros por valor y por referencia

> **El matiz más importante: cómo se pasan los parámetros.** Tanto C# como Java pasan los parámetros **por valor** por defecto — pero "por valor" significa algo distinto según el tipo de dato:
> - Si el parámetro es un **primitivo** (`int`, `double`, etc.), se copia el **valor exacto**. Cualquier modificación dentro del método es sobre esa copia y **no afecta** a la variable original del que llamó al método.
> - Si el parámetro es un **objeto** (un arreglo, una lista, una instancia de una clase), lo que se copia es la **referencia** (la "dirección" al objeto), no el objeto completo. Esto significa que si el método modifica el *contenido* del objeto (por ejemplo, `arreglo[0] = 99`), ese cambio **sí** se refleja afuera, porque ambas variables (la original y el parámetro) apuntan al mismo objeto en memoria. Pero si el método **reasigna** el parámetro a un objeto distinto (`arreglo = new int[10]`), esa reasignación solo afecta a la variable local del método — la variable original sigue apuntando al objeto de siempre.
>
> C# además permite pasar parámetros explícitamente **por referencia real** con `ref`, `out` e `in`, de forma que hasta los primitivos puedan modificarse dentro del método y que ese cambio se refleje en la variable original. **Java no tiene ningún equivalente a `ref`/`out`** — es una de las diferencias de diseño más comentadas entre ambos lenguajes.

**Trace paso a paso — por qué modificar un `int` dentro de un método no afecta al original:**

| Paso | Código | ¿Qué pasa en memoria? |
|---|---|---|
| 1 | `int x = 5; incrementar(x);` | Se copia el valor `5` al parámetro del método |
| 2 | dentro de `incrementar`: `a = a + 1;` | Se modifica la **copia** (`a`), ahora vale `6` |
| 3 | el método termina, su bloque en la pila se destruye | La copia se pierde |
| 4 | de vuelta en el llamador: `x` sigue valiendo `5` | La variable original nunca se tocó |

**Trace paso a paso — por qué modificar el contenido de un arreglo recibido como parámetro SÍ se refleja afuera:**

| Paso | Código | ¿Qué pasa en memoria? |
|---|---|---|
| 1 | `int[] numeros = {1,2,3}; modificar(numeros);` | Se copia la **referencia** al parámetro — ambas variables apuntan al mismo arreglo en el heap |
| 2 | dentro de `modificar`: `arr[0] = 99;` | Se modifica el **contenido** del arreglo compartido |
| 3 | el método termina | La copia de la referencia se pierde, pero el arreglo en el heap ya fue modificado |
| 4 | de vuelta en el llamador: `numeros[0]` ahora es `99` | El cambio es visible porque ambas variables apuntaban al mismo objeto |

```csharp
// C# — ref permite que incluso un primitivo se modifique "por referencia"
static void Incrementar(ref int valor)
{
    valor++;
}

static void Main()
{
    int x = 5;
    Incrementar(ref x);
    Console.WriteLine(x); // 6 -- SÍ cambió, porque se pasó por referencia explícita
}
```

```java
// Java no tiene ref/out: esto NO es posible con un int directamente.
// La única forma de "simular" el efecto es envolviendo el valor en un objeto
// (por ejemplo, un arreglo de un solo elemento, o una clase contenedora),
// ya que los objetos sí comparten la misma referencia.
static void incrementar(int[] contenedor) {
    contenedor[0]++;
}

public static void main(String[] args) {
    int[] x = { 5 };
    incrementar(x);
    System.out.println(x[0]); // 6 -- funciona porque x[0] vive dentro de un objeto compartido
}
```

**Errores y malentendidos típicos:**

- **Esperar que modificar un parámetro primitivo cambie la variable original:** como se ve en el primer trace, esto simplemente no ocurre sin `ref`/`out` en C#, y en Java no hay forma de lograrlo directamente con primitivos.
- **Confundir la mutabilidad del objeto con el paso por referencia real:** poder modificar el *contenido* de un arreglo recibido como parámetro no significa que Java o C# estén pasando "por referencia" en el sentido de poder reasignar la variable original — son dos cosas distintas, como muestran los dos traces de arriba.
- **Pensar que reasignar un parámetro-objeto dentro del método cambia la variable original:** `arreglo = new int[10];` dentro de un método solo cambia a qué apunta el **parámetro local** — la variable que se usó para llamar al método sigue apuntando al objeto original.

**Cuándo usar `ref`/`out` en C# (sin equivalente en Java):** `ref` cuando el método necesita modificar un valor existente que el llamador ya inicializó; `out` cuando el método necesita "devolver" más de un valor sin empaquetarlos en un objeto o arreglo (el patrón típico es `int.TryParse(texto, out valor)`, visto en la Unidad 1, sección de Casting). En Java, la alternativa habitual es devolver un objeto que agrupe varios valores, o modificar el contenido de un arreglo/colección pasado como parámetro.

---

*Universidad Politécnica de Tecamac — Ingeniería en Software*
