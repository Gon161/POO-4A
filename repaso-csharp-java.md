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

**Qué es:** la **consola** (o terminal) es la interfaz de texto que conecta tu programa con el sistema operativo a través de dos canales estándar: la **entrada estándar** (`stdin`, lo que escribes en el teclado) y la **salida estándar** (`stdout`, lo que se imprime en pantalla). Cuando llamas a `System.out.println` o `Console.WriteLine`, no estás "dibujando texto": estás escribiendo bytes a un flujo (*stream*) que el sistema operativo redirige a la ventana de la terminal.

**Por qué existe:** antes de que existieran interfaces gráficas, la consola era la única forma de interactuar con un programa. Sigue existiendo porque es el mecanismo más simple y universal para depurar, automatizar tareas (scripts) y, en el contexto de aprender POO, para probar la lógica de un programa sin la complejidad adicional de una interfaz gráfica.

**Cómo funciona por dentro:** tanto `System.in` en Java como `Console.In` en C# son flujos (*streams*) **bufferizados**: el sistema operativo no entrega los caracteres uno por uno apenas los escribes, sino que los acumula en un búfer hasta que presionas `Enter` (salto de línea). Por eso `Scanner.nextLine()` espera hasta encontrar ese salto de línea — no lee tecla por tecla, lee el búfer completo de una vez. Esto explica por qué `Console.ReadKey()` (C#) es especial: desactiva temporalmente el buffering de línea del sistema operativo para capturar la tecla en el instante en que se presiona, algo que Java no ofrece de forma nativa precisamente porque su API de consola fue diseñada solo alrededor de flujos de texto bufferizados.

> **Ojo con mezclar `nextInt()` y `nextLine()`:** `Scanner.nextInt()` solo consume los dígitos del número, **no** el salto de línea (`\n`) que queda después de que presionas Enter. Si luego llamas a `nextLine()` esperando leer una nueva línea, en realidad lees esa cadena vacía que quedó pendiente en el búfer. Por eso en los ejemplos de este repositorio verás un `scanner.nextLine();` "de limpieza" justo después de leer un número con `nextInt()` o `nextDouble()`, antes de leer texto.

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

**El flujo de datos, visto como diagrama:**

```
  Teclado                 Programa                      Pantalla
 ┌────────┐   stdin     ┌───────────┐     stdout       ┌─────────┐
 │  "20"  │ ──────────▶ │  Scanner  │ ───────────────▶ │ "int: 20"│
 │ +Enter │  (búfer)    │ .nextInt()│  System.out      │          │
 └────────┘             └───────────┘                  └─────────┘
```

**Errores típicos:**

- **Olvidar que `nextInt()` no consume el `\n`:** ya explicado arriba — provoca que la siguiente lectura de texto parezca "saltarse" el valor.
- **Tipo de dato incorrecto para el método de lectura:** si el usuario escribe `"veinte"` y el programa llama a `scanner.nextInt()`, Java lanza `InputMismatchException` porque el texto no se puede interpretar como número; el equivalente en C# (`int.Parse(Console.ReadLine())`) lanza `FormatException`. Ninguno de los dos "adivina" el tipo: hay que validar antes de convertir (ver la sección 15, Casting).
- **Cerrar el `Scanner` dos veces o leer después de cerrarlo:** una vez que llamas a `scanner.close()`, cualquier lectura posterior lanza `IllegalStateException`. En los ejemplos de este repaso no cerramos el `Scanner` porque envuelve a `System.in`, que de todas formas se cierra junto con el programa.

**Cuándo usar cada operación:** usa `Write`/`print` cuando quieras construir una línea con varias piezas antes del salto de línea (por ejemplo, un prompt seguido de la respuesta en la misma línea); usa `WriteLine`/`println` para la mayoría de los casos, ya que deja la salida más legible al separar cada mensaje en su propia línea.

---

## 2. Comentarios

**Qué es:** un **comentario** es texto dentro del código fuente que el compilador ignora por completo — no genera ninguna instrucción, no ocupa espacio en el programa compilado y no afecta en nada la ejecución. Sirve únicamente para que un humano (tú mismo en el futuro, u otro programador) entienda el código.

**Por qué existe:** el código explica *qué* hace el programa, pero no siempre explica *por qué* se hizo de esa forma. Sin comentarios, decisiones importantes (una solución poco obvia, una restricción del negocio, un *workaround* a un bug externo) se pierden y quien lea el código después tiene que adivinar o repetir el mismo error.

**Cómo funciona por dentro:** el comentario se elimina en la fase más temprana de la compilación, el **análisis léxico** (*lexing*), antes incluso de que el compilador entienda la gramática del lenguaje. Es literalmente como si el texto nunca hubiera existido para el resto del proceso de compilación — por eso un comentario nunca puede causar un error de sintaxis en el código que lo rodea. La única excepción es la documentación (`///` en C#, `/**...*/` en Java): estas sí son leídas por herramientas externas (no por el compilador normal) que generan documentación en HTML a partir de etiquetas como `<param>`/`<returns>` en C# o `@param`/`@return` en Java.

> **Ojo con los comentarios de bloque anidados:** ni C# ni Java permiten anidar `/* */` dentro de otro `/* */`. Si escribes `/* externo /* interno */ resto */`, el comentario se cierra en el **primer** `*/` que encuentra (después de "interno"), y `resto */` queda como código normal, lo que casi siempre provoca un error de compilación.

**Errores y malentendidos típicos:**

- **Comentar código en vez de borrarlo:** dejar bloques enteros de código "comentados por si acaso" ensucia el archivo y confunde — para eso existe el control de versiones (Git): si lo necesitas después, lo recuperas del historial, no del comentario.
- **Comentarios que repiten el código:** `int edad = 20; // asigna 20 a edad` no aporta nada — el código ya lo dice. Un buen comentario explica el *porqué*, no el *qué*.
- **Dejar comentarios desactualizados:** un comentario que describe un comportamiento que el código ya no tiene es peor que no tener comentario, porque engaña a quien lo lee.

**Cuándo usar cada tipo:** usa comentarios de línea (`//`) para aclaraciones puntuales de una sola línea; usa comentarios de bloque (`/* */`) para explicaciones más largas o para desactivar temporalmente varias líneas mientras depuras; usa comentarios de documentación (`///` o `/** */`) sobre clases y métodos **públicos**, porque esos son los que otros programadores van a consultar sin leer la implementación.

| Tipo | C# | Java |
|---|---|---|
| Línea | `// comentario` | `// comentario` |
| Bloque | `/* comentario */` | `/* comentario */` |
| Documentación | `/// <summary>...</summary>` (XML doc) | `/** ... */` (Javadoc) |

---

## 3. Tipos de datos primitivos

**Qué es:** un **tipo de dato primitivo** es un tipo básico que el lenguaje maneja directamente, sin la sobrecarga de ser un objeto — se almacena como una secuencia fija de bits en memoria, no como una referencia a algo en otro lugar.

**Por qué existe:** si cada número tuviera que ser un objeto completo (con su propio espacio reservado en el *heap*, metadatos del tipo, etc.), un programa que sume un millón de números sería enormemente más lento y consumiría mucha más memoria. Los primitivos existen por **eficiencia**: ocupan exactamente el espacio que necesitan y las operaciones sobre ellos (suma, comparación) se traducen directamente a instrucciones del procesador.

**Cómo funciona por dentro — representación en memoria:**

| Tipo | Representación interna | Ejemplo |
|---|---|---|
| Enteros (`byte`, `short`, `int`, `long`) | **Complemento a dos**: el bit más significativo indica el signo; permite que la suma y la resta funcionen igual para positivos y negativos sin lógica especial | `-1` en un `byte` de 8 bits es `11111111` |
| Flotantes (`float`, `double`) | **IEEE 754**: un bit de signo, varios bits de exponente y varios bits de mantisa (fracción) — por eso son una *aproximación*, no un valor exacto | `0.1` no tiene representación binaria exacta, igual que 1/3 no tiene representación decimal exacta |
| `char` | Un **code unit UTF-16** de 16 bits (no es "un byte" como en C) | `'A'` se almacena como el número `65` |
| `bool` / `boolean` | Normalmente 1 byte en memoria (ningún lenguaje garantiza "1 bit" por simplicidad de direccionamiento) | `true` / `false` |

> **Ojo con comparar `float`/`double` con `==`:** por la representación IEEE 754, operaciones matemáticamente iguales pueden dar resultados *casi* iguales pero no idénticos en bits. Por ejemplo, `0.1 + 0.2 == 0.3` da **`false`** tanto en C# como en Java, porque `0.1 + 0.2` en realidad produce `0.30000000000000004`. Para comparar decimales se usa una tolerancia (`Math.Abs(a - b) < 0.0001`), no `==` directo.

**Errores y malentendidos típicos:**

- **Overflow silencioso:** sumar `1` a un `byte` que ya vale `127` (su máximo) no lanza ningún error — simplemente "da la vuelta" y el resultado es `-128`. Esto ocurre en ambos lenguajes por defecto; C# ofrece el bloque `checked { }` para que lance una excepción en vez de desbordarse en silencio, algo que Java no tiene.
- **Confundir el tamaño de `byte`:** como señala la tabla, en Java `byte` es **con signo** (-128 a 127) y en C# también es con signo pero su versión *sin signo* (`sbyte` en C# equivale al `byte` con signo; el `byte` de C# por defecto es sin signo, 0 a 255). Es la inconsistencia de nombres más común entre ambos lenguajes.
- **Pensar que `string`/`String` es primitivo porque se comporta como tal:** ninguno de los dos lo es — ambos son **objetos** (clases). Se incluyen en esta tabla por comparación práctica, no porque compartan la naturaleza de los primitivos.

**Matices importantes:** en C#, cada tipo primitivo en realidad es un *alias* de una estructura (`int` = `System.Int32`, `double` = `System.Double`), que sí tiene métodos (`int.MaxValue`, `"25".Length`). Internamente sigue comportándose como un valor en pila (*stack*) mientras no se convierta a `object` (proceso llamado ***boxing***). En Java, los primitivos **no** tienen esa dualidad directa: para tratarlos como objetos (por ejemplo, para meterlos en una `List`, ver sección 13) se necesita su *clase envolvente* (`int` → `Integer`, `double` → `Double`), y esa conversión automática se llama *autoboxing*.

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

**Qué es:** una **variable** es un nombre simbólico que el programador le da a un espacio de memoria para poder leerlo y modificarlo sin tener que conocer su dirección real. Una **constante** es una variable cuyo valor se fija una sola vez y no puede cambiar después.

**Por qué existen:** sin variables, cada operación tendría que trabajar directamente con direcciones de memoria (como en ensamblador), lo que haría el código ilegible y propenso a errores. Las constantes existen para **documentar intención**: cuando ves `const double PI = 3.1416`, sabes de inmediato que ese valor nunca debería cambiar, y el compilador te lo garantiza — si alguien intenta reasignarlo, el programa ni siquiera compila.

**Cómo funciona por dentro:** una variable local (declarada dentro de un método) vive en la **pila de llamadas** (*stack*) durante el tiempo que el método se está ejecutando — en cuanto el método termina, ese espacio se libera automáticamente. `var` (en ambos lenguajes) **no es tipado dinámico**: el compilador deduce el tipo exacto a partir del valor asignado en esa misma línea, y a partir de ahí la variable queda fija a ese tipo para siempre — es exactamente lo mismo que escribir el tipo explícito, solo que el compilador lo escribe por ti. Por eso `var x = 10; x = "hola";` **no compila** en ninguno de los dos lenguajes: `x` ya quedó fijado como `int`.

> **Ojo con confundir `var` con tipado dinámico:** en lenguajes como JavaScript o Python, una variable puede cambiar de tipo en cualquier momento. En C# y Java, `var` solo le ahorra escritura al programador — la verificación de tipos sigue siendo **estática** (ocurre en tiempo de compilación, no de ejecución).

**Errores y malentendidos típicos:**

- **Usar `const` con un valor que no se conoce hasta tiempo de ejecución:** `const` en C# exige que el valor sea calculable por el compilador (un *literal* o una expresión de literales). `const int Resultado = ObtenerValor();` no compila, aunque `ObtenerValor()` siempre devuelva lo mismo.
- **Creer que `final` en Java hace inmutable al objeto:** `final List<String> lista = new ArrayList<>();` impide reasignar `lista` a otra lista distinta, pero **no impide** modificar su contenido (`lista.add("algo")` sigue funcionando). `final`/`const`/`readonly` protegen la *referencia*, no necesariamente lo que hay "adentro".
- ***Shadowing* accidental:** declarar una variable local con el mismo nombre que un campo de la clase oculta (ensombrece) al campo dentro de ese bloque, lo que puede hacer que una asignación no actualice lo que el programador esperaba.

**Cuándo usar cada alternativa:**

| Alternativa | Cuándo usarla |
|---|---|
| `var` | Cuando el tipo ya es obvio por el lado derecho de la asignación (`var lista = new List<int>();`) — mejora la legibilidad al evitar redundancia |
| Tipo explícito | Cuando el tipo no es evidente a simple vista, o cuando quieres que el tipo quede documentado explícitamente en la firma |
| `const` (C#) | Para valores verdaderamente fijos y conocidos en tiempo de compilación (constantes matemáticas, límites fijos) |
| `static readonly` (C#) / `static final` (Java) | Para valores que se calculan **una sola vez** (por ejemplo, al iniciar el programa) pero no se conocen hasta tiempo de ejecución |

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

**Qué es:** los operadores aritméticos realizan las operaciones matemáticas básicas (suma, resta, multiplicación, división, módulo) directamente sobre valores numéricos.

**Por qué existe cada uno:** son el equivalente directo de las operaciones del álgebra básica, pero hay dos detalles que **no** tienen equivalente en matemáticas tradicionales: la **división entera** y el **módulo** (`%`), que resuelven un problema muy concreto de programación — saber cuántas veces "cabe" un número en otro, y qué sobra. Por ejemplo, `%` es la base de "¿este número es par?" (`n % 2 == 0`) o "¿a qué posición de una tabla circular le toca?" (`indice % tamaño`).

**Cómo funciona por dentro:** cuando ambos operandos de `/` son enteros, el procesador ejecuta una instrucción de **división entera**, que descarta la parte decimal del resultado — no redondea, **trunca** hacia cero. Si al menos uno de los operandos es `double` o `float`, el compilador inserta una conversión implícita (*widening*) antes de dividir, y la instrucción que se ejecuta es de punto flotante, que sí conserva decimales.

**Trace paso a paso — por qué `7 / 2` da `3` y no `3.5`:**

| Paso | Expresión | Tipo de la operación | Resultado |
|---|---|---|---|
| 1 | `7 / 2` | ambos son `int` → división entera | `3` (se descarta el `.5`) |
| 2 | `7.0 / 2` | un operando es `double` → división decimal | `3.5` |
| 3 | `7 / (double) 2` | se fuerza la conversión antes de dividir → división decimal | `3.5` |

> **Ojo con dividir entre cero:** `5 / 0` con enteros lanza una excepción en ambos lenguajes (`DivideByZeroException` en C#, `ArithmeticException` en Java). Pero `5.0 / 0.0` (con `double`) **no lanza ninguna excepción** — da como resultado `Infinity` (o `-Infinity` si el numerador es negativo), y `0.0 / 0.0` da `NaN` ("Not a Number"). Es uno de los matices que más confunde: el mismo operador `/` se comporta completamente distinto según el tipo de dato.

**Errores y malentendidos típicos:**

- **Esperar que `/` redondee:** `7 / 2` no da `4` (redondeado) ni `3.5` — da `3` (truncado), porque ambos operandos son `int`.
- **Olvidar que `%` con negativos sigue el signo del dividendo:** `-7 % 2` da `-1` en ambos lenguajes (no `1`), porque el resultado del módulo conserva el signo del primer operando.
- **No usar `Math.Pow`/`Math.pow` y escribir `x^2`:** el operador `^` existe en ambos lenguajes, pero es un **OR exclusivo bit a bit** (XOR), no una potencia. `5 ^ 2` da `7`, no `25`.

**Cuándo usar cada alternativa:** usa `%` para detectar múltiplos, ciclos o repartir elementos de forma equitativa; usa `Math.Pow`/`Math.pow` para exponentes (recuerda que siempre devuelve `double`, así que si necesitas un `int` hay que convertir el resultado explícitamente, ver sección 15).

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

**Qué es:** los operadores relacionales comparan dos valores y devuelven siempre un `bool`/`boolean` (`true` o `false`) — nunca un número ni otro tipo.

**Por qué existe la distinción `==` vs `.equals()`:** cuando `==` compara **tipos primitivos** (`int`, `double`, `char`...), compara los valores directamente bit a bit — no hay ambigüedad posible. El problema aparece con **objetos** (como `String`): ahí `==` compara si ambas variables apuntan **a la misma dirección de memoria** (la misma instancia), no si "se ven iguales". `.equals()` existe precisamente para comparar *contenido* en vez de *identidad*.

**Cómo funciona por dentro:** cuando escribes un literal de texto como `"hola"`, tanto C# como Java usan un mecanismo llamado ***string interning***: todos los literales de texto idénticos en el programa se almacenan **una sola vez** en un área especial de memoria (el *string pool*), y las variables que los usan apuntan a esa misma instancia compartida. Por eso en C# `"hola" == "hola"` da `true` incluso comparando por referencia: ambos literales apuntan al mismo objeto interno. Pero en Java, `new String("hola")` **fuerza** la creación de un objeto nuevo fuera del *pool*, con su propia dirección de memoria — por eso `==` da `false` aunque el contenido sea idéntico.

> **Ojo con comparar `double`/`float` con `==` o `!=`:** por la imprecisión de la representación en punto flotante (ver sección 3), dos cálculos que *matemáticamente* deberían ser iguales pueden no serlo en bits. La forma correcta de comparar decimales es con una tolerancia: `Math.Abs(a - b) < 0.0001` en vez de `a == b`.

**Errores y malentendidos típicos:**

- **Usar `==` para comparar objetos en Java esperando que compare contenido:** esto incluye no solo `String`, sino cualquier clase propia — por defecto, `==` siempre compara referencias salvo que la clase sobreescriba `equals()` explícitamente (tema que se profundiza más adelante en POO).
- **Asumir que C# siempre compara contenido con `==`:** solo es así para `string` porque la clase `System.String` sobreescribe el operador `==` para comparar contenido. Para tus propias clases en C#, `==` también compara referencia por defecto, igual que en Java.
- **Encadenar comparaciones como en matemáticas:** `0 < x < 10` no funciona como "x está entre 0 y 10" — se evalúa de izquierda a derecha, `0 < x` da un `bool`, y luego ese `bool` se compararía contra `10`, lo cual ni siquiera compila en C#/Java (no se puede comparar `bool` con `int`). Hay que escribir `0 < x && x < 10` explícitamente (ver sección 7).

**Cuándo usar cada alternativa:** usa `==`/`!=` siempre para primitivos; para objetos, usa `==`/`!=` solo cuando *de verdad* quieras saber si son la misma instancia (por ejemplo, comprobar si una referencia es `null`), y usa `.equals()` (Java) o el `==` sobrecargado de una clase (C#) cuando quieras comparar contenido.

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

**Qué es:** los operadores lógicos combinan valores booleanos (`true`/`false`) para formar condiciones compuestas.

**Por qué existe el "corto-circuito":** `&&` y `||` tienen una propiedad fundamental llamada **evaluación de corto-circuito** (*short-circuit evaluation*): dejan de evaluar en cuanto el resultado final ya es seguro, sin necesidad de revisar el resto. Esto no es solo una optimización de rendimiento — es una herramienta para **evitar errores**. El patrón más común es comprobar que algo no sea nulo antes de usarlo, en la misma condición.

**Cómo funciona por dentro — trace paso a paso de `&&`:**

| Paso | Expresión evaluada | ¿Se evalúa? | Razón |
|---|---|---|---|
| 1 | `edad >= 18` | Sí | Siempre se evalúa el primer operando |
| 2a | si el paso 1 dio `false` | **No** se evalúa el segundo operando | Ya se sabe que el AND completo es `false`, sin importar el resto |
| 2b | si el paso 1 dio `true` | **Sí** se evalúa `tieneCredencial == true` | Solo así se sabe el resultado final |

Con `||` ocurre lo simétrico: si el primer operando ya es `true`, no se evalúa el segundo, porque el OR completo ya es `true` sin importar qué diga el resto.

> **Ojo con usar `&`/`|` en vez de `&&`/`||`:** `&` y `|` también existen como operadores lógicos en ambos lenguajes, pero **sin** corto-circuito: siempre evalúan ambos lados, sin importar el resultado del primero. El riesgo clásico es escribir algo como `obj != null & obj.getValor() > 0`: si `obj` es `null`, el lado derecho se evalúa igual y el programa lanza `NullPointerException`/`NullReferenceException`. Con `&&`, si `obj != null` da `false`, el lado derecho jamás se evalúa y el error no ocurre. Por esta razón, en código normal casi siempre se prefiere `&&`/`||` sobre `&`/`|` (estos últimos se reservan para operaciones a nivel de bits sobre enteros).

**Errores y malentendidos típicos:**

- **Pensar que "y"/"o" del lenguaje natural siempre se traduce igual:** en español decimos "mayor o igual a 18 y tiene credencial", lo que se traduce literal a `&&`, pero hay que tener cuidado con frases ambiguas como "mayor a 18 o menor a 13 y acompañado" — ahí el orden de evaluación de `&&` y `||` (el primero tiene mayor precedencia) puede no coincidir con lo que el español sugiere, y conviene usar paréntesis explícitos para no depender de la precedencia.
- **Doble negación innecesaria:** `!(!activo)` es simplemente `activo` — common code smell que dificulta la lectura.

**Cuándo usar cada alternativa:** usa `&&`/`||` casi siempre, por seguridad y por eficiencia (evita cálculos innecesarios). Reserva `&`/`|` para cuando de verdad necesites forzar que **ambos** lados se evalúen siempre (por ejemplo, si el lado derecho tiene un efecto secundario que debe ejecutarse sin importar el primero — un caso raro e intencional).

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

**Qué es:** la asignación (`=`) guarda un valor en una variable; los operadores compuestos (`+=`, `++`, etc.) son **azúcar sintáctico** — una forma corta de escribir una operación que modifica y reasigna la variable en un solo paso.

**Por qué existen:** `contador += 5` es exactamente equivalente a `contador = contador + 5`, pero más corto y, sobre todo, menos propenso a errores de copiar/pegar cuando el nombre de la variable es largo. `++`/`--` existen porque incrementar/decrementar en 1 es la operación más común dentro de bucles (sección 11), y merece su propia notación compacta.

**Cómo funciona por dentro — la diferencia entre pre y post incremento:** `++x` (prefijo) incrementa la variable **y luego** usa el nuevo valor en la expresión; `x++` (postfijo) usa el valor **actual** en la expresión y **después** incrementa la variable. Ambos modifican `x` de la misma forma — la diferencia está en qué valor queda disponible para el resto de la expresión en el momento exacto en que se evalúa.

**Trace paso a paso — `x = 5; y = x++ + ++x;`:**

| Paso | Operación | Valor usado en la suma | Valor de `x` después del paso |
|---|---|---|---|
| 1 | `x++` (postfijo) | se usa `5` (el valor *antes* de incrementar) | `x` pasa a `6` |
| 2 | `++x` (prefijo) | se usa `7` (se incrementa primero, *luego* se usa) | `x` pasa a `7` |
| 3 | `y = 5 + 7` | — | `y = 12`, `x = 7` |

> **Ojo con combinar varios incrementos de la misma variable en una sola expresión:** aunque el ejemplo anterior es válido y predecible en C# y Java (ambos definen un orden de evaluación estricto de izquierda a derecha), este tipo de código es difícil de leer y es una fuente común de errores al modificarlo después. La recomendación práctica es **no** combinar `x++` y `++x` sobre la misma variable en la misma línea — se gana claridad separándolos en pasos.

**Errores y malentendidos típicos:**

- **Confundir `=` con `==`:** `=` asigna, `==` compara. Escribir `if (activo = true)` en vez de `if (activo == true)` es un error clásico en C/C++, pero en C# y Java el compilador lo rechaza directamente si `activo` no es de tipo `bool`/`boolean`, porque una asignación devuelve el tipo de la variable asignada, no necesariamente un booleano utilizable en el `if`.
- **Olvidar que `??=`/`??` (C#) no tiene equivalente directo en Java:** como se ve en la tabla, Java necesita una comprobación explícita con el operador ternario.

**Cuándo usar cada alternativa:** usa los operadores compuestos (`+=`, `++`, etc.) siempre que la operación sea simplemente "tomar el valor actual y modificarlo" — comunican la intención más claro que escribir la operación completa.

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

**Qué es:** la estructura `if`/`else` permite que el programa ejecute un bloque de código **u otro**, dependiendo de si una condición booleana es verdadera o falsa.

**Por qué existe:** sin ramificación condicional, un programa solo podría ejecutar la misma secuencia de instrucciones siempre, sin poder reaccionar a los datos de entrada — sería incapaz de tomar decisiones. Es, junto con los bucles, uno de los dos bloques de control fundamentales de cualquier lenguaje imperativo.

**Cómo funciona por dentro:** a nivel de código máquina, un `if` se traduce en una **instrucción de salto condicional** (*conditional jump*): el procesador evalúa la condición, y según el resultado, "salta" o no a una dirección distinta de memoria donde continúa la ejecución. Un `if`/`else if`/`else` encadenado se traduce en una secuencia de comparaciones evaluadas **en orden**, de arriba hacia abajo — en cuanto una condición resulta verdadera, las siguientes ni siquiera se evalúan.

**Trace paso a paso — `edad = 15` en el ejemplo de abajo:**

| Paso | Condición evaluada | Resultado | ¿Qué pasa? |
|---|---|---|---|
| 1 | `edad >= 18` → `15 >= 18` | `false` | Pasa a revisar el `else if` |
| 2 | `edad >= 13` → `15 >= 13` | `true` | Ejecuta ese bloque ("Adolescente") y **se detiene** |
| 3 | — | — | El `else` final nunca se evalúa porque el paso 2 ya fue verdadero |

**Errores y malentendidos típicos:**

- **El "else colgante" (*dangling else*):** cuando hay `if` anidados sin llaves, un `else` siempre se asocia al `if` **más cercano** sin cerrar, no al que el programador cree visualmente por la indentación. Por eso siempre es buena práctica usar llaves `{ }` incluso para bloques de una sola línea.
- **Condiciones mutuamente no excluyentes mal ordenadas:** si en el ejemplo se hubiera escrito primero `edad >= 13` y después `edad >= 18`, un mayor de edad (por ejemplo, 20 años) entraría en la rama de "Adolescente", porque `20 >= 13` ya es verdadero y el `if` nunca llega a evaluar la condición más específica. **El orden de las condiciones importa** cuando no son mutuamente excluyentes.

**Cuándo usar `if`/`else` vs `switch`:** usa `if`/`else` cuando la condición involucra **rangos**, comparaciones (`>=`, `<`) o combinaciones de varias variables; usa `switch` (sección 10) cuando comparas **un solo valor** contra una lista de constantes conocidas — es más legible y, en varios lenguajes, más eficiente para ese caso específico.

> **El operador ternario `?:`** es una forma compacta de un `if`/`else` que **devuelve un valor** en vez de ejecutar bloques: `string resultado = edad >= 18 ? "Mayor" : "Menor";` — la sintaxis es idéntica en C# y Java. Úsalo solo para asignaciones simples; anidar varios ternarios (`a ? b : c ? d : e`) sacrifica legibilidad.

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

**Qué es:** `switch` compara **un único valor** contra una lista de casos posibles (constantes) y ejecuta el bloque que coincide, en vez de encadenar múltiples comparaciones `if`/`else if`.

**Por qué existe (y no basta con `if`/`else`):** más allá de la legibilidad, un `switch` sobre valores enteros o `enum` le da al compilador información que un `if`/`else if` encadenado no tiene: todos los casos posibles se conocen por adelantado. Esto permite que, en muchos casos, el compilador genere una **tabla de saltos** (*jump table*): una estructura que calcula directamente a qué bloque saltar a partir del valor, en tiempo **constante** (`O(1)`), en vez de comparar valor por valor de arriba hacia abajo como haría un `if`/`else if` (que en el peor caso es `O(n)`, con `n` = número de casos).

> **Ojo:** esto no es una garantía universal — el compilador solo puede construir una tabla de saltos eficiente cuando los valores de los `case` están suficientemente "juntos" (poca dispersión numérica) o cuando compila sobre un tipo con *hashing* eficiente (como `String`). Con pocos casos dispersos, el compilador puede generar simplemente una secuencia de comparaciones, igual que un `if`/`else if`. La ventaja real y constante de `switch` frente a `if`/`else if` es la **legibilidad**, no una garantía de rendimiento.

**Errores y malentendidos típicos (más allá del *fall-through*, cubierto abajo):**

- **Olvidar el `default`:** si ningún `case` coincide y no hay `default`, el `switch` simplemente no hace nada y continúa con el código siguiente — no lanza ningún error. Esto puede ocultar casos no manejados.
- **Valores de `case` duplicados:** escribir dos `case 1:` en el mismo `switch` es un **error de compilación** en ambos lenguajes, no un error silencioso — el compilador lo detecta porque sería ambiguo cuál ejecutar.
- **Usar variables como valor de `case`:** los valores de cada `case` deben ser constantes conocidas en tiempo de compilación (literales, `enum`, o `const`) — no se puede escribir `case variable:` con una variable normal.

**Cuándo usar `switch` vs `if`/`else`:** usa `switch` cuando compares **un solo valor** contra varias constantes conocidas (días de la semana, códigos de opción de un menú, un `enum` de estados); usa `if`/`else` cuando la condición involucra rangos, múltiples variables, o comparaciones que no son de igualdad exacta.

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

**Qué son:** las estructuras iterativas (bucles) repiten un bloque de código mientras se cumpla una condición, evitando escribir la misma instrucción muchas veces de forma manual.

**Por qué existen varias y no solo una:** aunque `for`, `while` y `do-while` son intercambiables en teoría (cualquiera puede reescribirse con los otros), cada uno comunica una **intención distinta** al lector: `for` dice "sé de antemano cuántas veces o bajo qué condición numérica se repite esto"; `while` dice "repito mientras algo externo siga siendo cierto, sin saber cuántas veces"; `do-while` dice "esto se ejecuta **al menos una vez**, sin importar la condición".

**Cómo funciona por dentro:** un `for (int i = 0; i < n; i++) { ... }` es, en esencia, azúcar sintáctico sobre un `while`: el compilador lo traduce internamente a algo equivalente a `int i = 0; while (i < n) { ...; i++; }`. La diferencia clave entre `while` y `do-while` está en **cuándo** se evalúa la condición: `while` la evalúa **antes** de la primera ejecución del bloque (por lo que puede ejecutarse cero veces), y `do-while` la evalúa **después** (por lo que el bloque se ejecuta garantizadamente al menos una vez, incluso si la condición ya era falsa desde el principio).

**Trace paso a paso — diferencia entre `while` y `do-while` cuando la condición ya es falsa desde el inicio:**

| Estructura | `int i = 10; condición: i < 5` | ¿Cuántas veces se ejecuta el cuerpo? |
|---|---|---|
| `while (i < 5) { ... }` | `10 < 5` es `false` desde el principio | **Cero veces** |
| `do { ... } while (i < 5);` | el cuerpo se ejecuta primero, la condición se revisa después | **Una vez** (y luego se detiene) |

**Errores y malentendidos típicos:**

- **Bucles infinitos por condición mal planteada:** olvidar actualizar la variable de control (por ejemplo, un `for` sin `i++`) deja la condición siempre verdadera.
- **Errores de "uno de más/uno de menos" (*off-by-one*):** usar `<=` en vez de `<` (o viceversa) en la condición de un `for` que recorre un arreglo es la causa más común de `IndexOutOfBoundsException`/`IndexOutOfRangeException` (ver sección 12).
- **Modificar una colección mientras se recorre con `foreach`/`for-each`:** ambos lenguajes recorren la colección mediante un *iterador* interno; si agregas o quitas elementos de la colección durante ese recorrido, Java lanza `ConcurrentModificationException` de inmediato, mientras que C# puede lanzar `InvalidOperationException` en el siguiente paso de la iteración — en ningún caso es seguro hacerlo.

**Cuándo usar cada alternativa:**

```
¿Sabes de antemano cuántas veces vas a repetir, o es un rango claro (0 a n)?
        │
        ├─ Sí ──────────────▶ usa for
        │
        └─ No, depende de una condición externa
                │
                ├─ ¿Debe ejecutarse al menos una vez sin importar la condición?
                │       │
                │       ├─ Sí ─▶ usa do-while
                │       └─ No ─▶ usa while
                │
                └─ ¿Solo quieres recorrer todos los elementos de una colección,
                    sin necesitar el índice?
                        │
                        └─ Sí ─▶ usa foreach / for-each
```

> **Matiz que casi nadie nota: `break`/`continue` con etiquetas.** Java permite etiquetar un bucle (`etiqueta: for (...) { for (...) { break etiqueta; } }`) para romper o continuar un bucle **externo** desde dentro de uno anidado, con una sola instrucción. **C# no tiene esta característica** — la forma de lograr el mismo efecto es con una variable `bool` de control que se revisa en el bucle externo, o usando `goto` (que existe en C# pero se considera mala práctica en la mayoría de los casos).

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

**Qué es:** un **arreglo** es un bloque **contiguo** de memoria que guarda varios valores del mismo tipo, accesibles mediante un índice numérico.

**Por qué existe:** si tuvieras que guardar 100 números usando variables sueltas (`n1`, `n2`, ..., `n100`), no podrías recorrerlos con un bucle ni acceder a "el número 47" sin escribir 100 nombres distintos. El arreglo resuelve esto dándole **un solo nombre** a todo el bloque, y usando aritmética simple para localizar cualquier elemento.

**Cómo funciona por dentro:** el arreglo reserva en memoria un bloque continuo de `tamaño × cantidadDeElementos` bytes. Para acceder a `numeros[i]`, el programa **no recorre** el arreglo buscando el elemento — calcula directamente la dirección de memoria con una fórmula: `dirección_base + (i × tamaño_de_cada_elemento)`. Por eso el acceso por índice es de complejidad **O(1)** (tiempo constante): acceder a `numeros[0]` o a `numeros[99]` toma exactamente el mismo tiempo. Esta es la razón de ser del arreglo y lo que lo distingue de otras estructuras de datos.

> **Ojo con el tamaño fijo:** una vez creado un arreglo con `new int[5]`, su tamaño **no puede cambiar**. No existe una operación de "agregar un elemento más" — si necesitas eso, necesitas una lista (sección 13), que internamente usa un arreglo pero lo reemplaza por uno más grande cuando se llena.

**Errores y malentendidos típicos:**

- **`IndexOutOfBoundsException` (Java) / `IndexOutOfRangeException` (C#):** acceder a `numeros[5]` en un arreglo de longitud 5 es un error, porque los índices válidos van de `0` a `longitud - 1` (en este caso, de `0` a `4`). Es el error de "uno de más" más común en programación.
- **Confundir longitud con último índice válido:** `numeros.Length`/`numeros.length` da la **cantidad** de elementos (5), no el último índice accesible (4) — por eso un bucle correcto usa `i < numeros.length`, nunca `i <= numeros.length`.
- **Pensar que un arreglo de objetos se inicializa "vacío":** `new String[5]` crea un arreglo de 5 posiciones, pero cada posición vale `null`, no una cadena vacía — intentar usar esos valores antes de asignarlos lanza `NullPointerException`/`NullReferenceException`. Los arreglos de primitivos sí se inicializan con un valor por defecto (`0` para numéricos, `false` para booleanos).

**Cuándo usar arreglo vs lista:** usa un arreglo cuando el tamaño es fijo y conocido de antemano, y el rendimiento es crítico (sin la sobrecarga de redimensionamiento de una lista); usa una lista (sección 13) cuando necesites agregar o quitar elementos dinámicamente.

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

**Cómo se ve esto en memoria:**

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

**Qué es:** una lista es una estructura de datos que **se comporta** como si tuviera tamaño variable, aunque por dentro sigue usando un arreglo de tamaño fijo — simplemente lo reemplaza automáticamente cuando se queda sin espacio.

**Por qué existe:** resuelve exactamente la limitación de los arreglos descrita en la sección 12 — no saber de antemano cuántos elementos vas a necesitar. Sin listas, tendrías que calcular el tamaño máximo posible por adelantado (desperdiciando memoria) o reconstruir manualmente un arreglo más grande cada vez que se llena.

**Cómo funciona por dentro:** tanto `List<T>` (C#) como `ArrayList` (Java, la implementación más común de la interfaz `List`) mantienen internamente un **arreglo** con una capacidad mayor a la cantidad real de elementos guardados. Cuando agregas un elemento y el arreglo interno ya está lleno, la lista crea un **arreglo nuevo más grande**, copia todos los elementos existentes y descarta el arreglo viejo. La proporción de crecimiento no es idéntica entre los dos: `List<T>` de C# **duplica** la capacidad (×2) cada vez que se llena, mientras que `ArrayList` de Java crece por un factor de **1.5×** aproximadamente (`nuevaCapacidad = capacidadVieja + capacidadVieja / 2`). En ambos casos, esto hace que `Add`/`add` sea rápido **casi siempre** (`O(1)`), salvo en esos momentos puntuales de redimensionamiento donde toma `O(n)` — en promedio, repartido entre todas las inserciones, sigue siendo `O(1)` ("`O(1)` amortizado").

**Complejidad de las operaciones más comunes (igual en ambos lenguajes, porque la estructura interna es la misma idea):**

| Operación | Complejidad | Por qué |
|---|---|---|
| Acceder por índice (`numeros[i]` / `numeros.get(i)`) | `O(1)` | Es aritmética directa sobre el arreglo interno, igual que en un arreglo normal |
| Agregar al final (`Add` / `add`) | `O(1)` amortizado | Casi siempre hay espacio; solo de vez en cuando se redimensiona |
| Insertar/eliminar en una posición intermedia | `O(n)` | Hay que desplazar todos los elementos posteriores una posición |
| Buscar un valor (`Contains` / `contains`) | `O(n)` | No hay atajo: hay que revisar elemento por elemento hasta encontrarlo |

> **El matiz más importante y menos obvio entre C# y Java: cómo manejan los genéricos con primitivos.** En C#, `List<int>` guarda enteros **sin convertirlos en objetos** — los genéricos en C# se compilan de forma especializada para cada tipo de valor (esto se llama *reification*), así que `List<int>` es tan eficiente en memoria como un arreglo de `int`. En Java, **no existe** `List<int>` — es un error de compilación. Tienes que usar `List<Integer>`, la clase envolvente, porque los genéricos en Java usan *type erasure* (en tiempo de ejecución, toda la información genérica se "borra" y todo se trata como `Object`) y `Object` no puede contener un primitivo directamente. Esto implica que cada entero en una `List<Integer>` de Java es en realidad un objeto independiente en el *heap* (con el *autoboxing* mencionado en la sección 3), lo cual consume más memoria y es más lento que el equivalente en C#.

**Errores y malentendidos típicos:**

- **`ConcurrentModificationException` al modificar una lista mientras se recorre con `for-each`** (ya mencionado en la sección 11) — para eliminar elementos durante un recorrido hay que usar un `Iterator` explícito (`iterator.remove()`) en vez de `for-each`.
- **Confundir `remove(10)` con eliminar el valor `10`:** como señala la tabla, en Java `list.remove(10)` con un `List<Integer>` elimina el elemento en el **índice** 10, no el valor 10 — para eliminar por valor hay que forzar la sobrecarga correcta con `list.remove(Integer.valueOf(10))`. Es una trampa común para quien viene de C#, donde `Remove(10)` siempre elimina por valor.
- **Comparar dos listas con `==`:** igual que con cualquier objeto (sección 6), `==` compara referencias. Para comparar si dos listas tienen el mismo contenido se usa `.Equals()`/`.equals()` (que en las implementaciones estándar de ambos lenguajes sí compara elemento por elemento).

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

**Qué es:** un método es un bloque de código con nombre propio, que puede recibir datos de entrada (parámetros) y devolver un resultado, diseñado para ejecutarse cada vez que se le "llama" desde otra parte del programa.

**Por qué existen:** sin métodos, cualquier operación que se repita en varios lugares del programa tendría que copiarse y pegarse cada vez — si luego hay que corregir un error en esa lógica, habría que corregirlo en cada copia. Los métodos son la herramienta básica contra la duplicación de código (principio DRY, *Don't Repeat Yourself*) y permiten dividir un problema grande en partes más pequeñas y manejables.

**Cómo funciona por dentro — la pila de llamadas:** cada vez que se llama a un método, el programa reserva un nuevo bloque en la **pila de llamadas** (*call stack*) para esa llamada específica: ahí se guardan los parámetros, las variables locales del método, y la dirección a la que hay que regresar cuando el método termine. Cuando el método hace `return` (o llega a su `}` final), ese bloque se destruye automáticamente y la ejecución regresa al punto exacto donde se hizo la llamada. Si un método se llama a sí mismo indefinidamente sin una condición de salida (recursión sin caso base), la pila crece sin límite hasta agotar la memoria reservada para ella, lo que produce `StackOverflowError` (Java) o `StackOverflowException` (C#).

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

**Errores y malentendidos típicos:**

- **Esperar que modificar un parámetro primitivo cambie la variable original:** como se ve en el trace anterior, esto simplemente no ocurre sin `ref`/`out` en C#, y en Java no hay forma de lograrlo directamente con primitivos.
- **Confundir la mutabilidad del objeto con el paso por referencia real:** poder modificar el *contenido* de un arreglo recibido como parámetro no significa que Java o C# estén pasando "por referencia" en el sentido de poder reasignar la variable original — son dos cosas distintas, como se explicó arriba.
- **Sobrecargar métodos de forma ambigua:** declarar `sumar(int a, double b)` y `sumar(double a, int b)` puede hacer que una llamada como `sumar(1, 2)` sea ambigua para el compilador si ambos parámetros podrían convertirse implícitamente — produce un error de compilación, no una elección "inteligente" de cuál usar.

**Cuándo usar sobrecarga vs parámetros por defecto:** en C#, si varias variantes de un método solo difieren en que algunos parámetros son opcionales con un valor típico, usa parámetros por defecto (`int b = 0`) por ser más compacto; en Java, al no existir esa característica, la sobrecarga de métodos (declarar varias versiones con distinta cantidad de parámetros) es la única alternativa.

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

**Qué es:** el *casting* es convertir un valor de un tipo de dato a otro — por ejemplo, de `double` a `int`, o de texto a número.

**Por qué existe la distinción entre conversión implícita y explícita:** no todas las conversiones son igual de "seguras". Convertir un `int` a `double` nunca pierde información (todo entero se puede representar como decimal exacto), así que el compilador la hace **automáticamente**, sin que el programador tenga que pedirlo (conversión *implícita* o *widening*). Pero convertir un `double` a `int` **sí** puede perder información (la parte decimal, y potencialmente dígitos enteros si el número es demasiado grande) — por eso el compilador **obliga** a escribir el *cast* explícitamente (`(int) miDouble`), como una forma de decir "sí, sé que esto puede perder datos, y lo acepto a propósito" (conversión *explícita* o *narrowing*).

**Cómo funciona por dentro:**

| Conversión | Tipo | Qué ocurre a nivel de bits | ¿Puede perder datos? |
|---|---|---|---|
| `int` → `double` (implícita) | *widening* | Se reinterpreta el patrón de bits a la representación IEEE 754 equivalente | No |
| `double` → `int` (explícita) | *narrowing* | Se **descarta la parte decimal** (trunca, no redondea) y, si el entero resultante no cabe en 32 bits, el resultado se vuelve indefinido/incorrecto | Sí |
| `long` → `int` (explícita) | *narrowing* | Se descartan los bits más significativos que no caben en 32 bits — si el valor era mayor al máximo de `int`, el resultado "da la vuelta" de forma silenciosa | Sí |
| texto → número (`Parse`/`parseInt`) | análisis de caracteres | El método recorre cada carácter del texto, verificando que sea un dígito válido, y construye el número acumulando dígito por dígito | Sí (si el texto no es válido) |

> **Ojo:** `(int) 3.9` da `3`, no `4` — el *cast* numérico **trunca** (descarta la parte decimal), nunca redondea. Para redondear hay que usar explícitamente `Math.Round()`/`Math.round()` antes de convertir.

**Errores y malentendidos típicos:**

- **Asumir que convertir texto a número nunca falla:** si el texto no tiene formato numérico válido (`"abc"`), tanto `Integer.parseInt` (Java) como `int.Parse` (C#) lanzan una excepción (`NumberFormatException` / `FormatException`) — no devuelven `0` ni ningún valor por defecto silenciosamente. Hay incluso una diferencia sutil entre ambos: `int.Parse(" 25 ")` en C# **sí funciona** (ignora espacios al inicio y al final por defecto), mientras que `Integer.parseInt(" 25 ")` en Java **lanza excepción** — Java no recorta espacios automáticamente, hay que hacerlo explícitamente con `.trim()` antes de convertir.
- **No validar la entrada del usuario antes de convertirla:** cualquier dato que venga de `Scanner`/`Console.ReadLine()` como texto (sección 1) es, en el fondo, texto no confiable — convertirlo sin manejar el posible error es una causa común de que un programa se "caiga" con una excepción no capturada.
- **Perder precisión sin darse cuenta en un cast descendente:** convertir un `long` muy grande a `int` no lanza ningún error — simplemente produce un número incorrecto (por *overflow* silencioso, igual que se vio en la sección 3), lo que puede ser más peligroso que una excepción porque el programa sigue corriendo con datos erróneos.

**Cuándo usar `Parse` vs `TryParse` (C#) / `try-catch` con `parseInt` (Java):**

```
¿El texto a convertir viene de una fuente que puede no ser válida
(entrada del usuario, un archivo externo, datos de red)?
        │
        ├─ Sí ──▶ usa TryParse (C#) o try-catch alrededor de parseInt (Java):
        │          permite manejar el caso inválido sin detener el programa
        │
        └─ No, el texto ya fue validado o es un literal fijo en el código
                │
                └─ usa Parse / parseInt directamente:
                   si falla, es síntoma de un error real en el programa,
                   no un caso esperado que haya que manejar con elegancia
```

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
- **Genéricos con primitivos:** `List<int>` es válido y eficiente en C# (genéricos reificados); Java no permite `List<int>` por *type erasure* y obliga a usar la clase envolvente `Integer` (con el costo de *autoboxing*).
- **Paso de parámetros:** ambos pasan primitivos por valor y objetos por referencia-de-la-referencia; pero solo C# permite forzar el paso por referencia real de un primitivo con `ref`/`out` — Java no tiene equivalente.

*Universidad Politécnica de Tecamac — Ingeniería en Software*
