# Unidad 3 — Programación Orientada a Objetos: Fundamentos

Las dos unidades anteriores cubrieron sintaxis básica — tipos de datos, operadores, estructuras de control, arreglos y métodos —, todo pensado como una secuencia de instrucciones que el programa ejecuta paso a paso. Esta unidad da el salto al paradigma que define a Java: la **Programación Orientada a Objetos (POO)**. A partir de aquí, el código ya no se piensa como "una receta de pasos", sino como **un conjunto de objetos que colaboran entre sí**, cada uno responsable de su propio estado y de su propio comportamiento. Todo el contenido de esta unidad usa Java.

---

## Índice

1. [Clases y objetos](#31-clases-y-objetos)
2. [Atributos y métodos](#32-atributos-y-métodos)
3. [Encapsulamiento](#33-encapsulamiento)
   - 3.3.1. [Modificadores de acceso: `public`, `private` y `protected`](#331-modificadores-de-acceso-public-private-y-protected)
   - 3.3.2. [Métodos *get* y *set*](#332-métodos-get-y-set)
4. [Constructores](#34-constructores)
5. [Diagramas de clase UML](#35-diagramas-de-clase-uml)
6. [Buenas prácticas en POO](#36-buenas-prácticas-en-poo)

---

## 3.1. Clases y objetos

**Qué es:** una **clase** es una plantilla o molde que define qué datos (atributos) y qué comportamiento (métodos) va a tener algo. Un **objeto** es una instancia concreta construida a partir de esa plantilla, con su propia copia independiente de los datos.

**Por qué existe:** la POO nace para modelar entidades del mundo real (un `Estudiante`, una `CuentaBancaria`, un `Producto`) agrupando en un solo lugar los datos relacionados **junto con** el comportamiento que opera sobre ellos. En programación procedural (lo que viste en las unidades 1 y 2), los datos y las funciones viven separados — una función `calcularPromedio` y un arreglo de calificaciones sueltos, sin relación explícita en el código más allá de que la función recibe el arreglo como parámetro. La clase convierte esa relación implícita en algo explícito y verificado por el compilador.

**Cómo funciona por dentro:** cuando escribes `class Estudiante { ... }`, el compilador **no reserva memoria todavía** — solo registra la "forma" que tendrá cada objeto de ese tipo (qué atributos, en qué orden, qué métodos). La memoria real se reserva cuando se ejecuta `new Estudiante()`: en ese momento, la JVM reserva espacio en el **heap** (la región de memoria para objetos de vida larga) para una instancia nueva, inicializa sus atributos con valores por defecto, y devuelve una **referencia** (la dirección de memoria) a ese bloque. La variable que recibe esa referencia no contiene el objeto en sí — contiene un puntero hacia él.

```
          LA CLASE (el molde — vive una sola vez en el programa)
          ┌────────────────────────────┐
          │ class Estudiante {          │
          │   String nombre;            │
          │   int edad;                 │
          │   void saludar() {...}      │
          │ }                            │
          └────────────────────────────┘
                      │
         new Estudiante()      new Estudiante()
                      │                     │
                      ▼                     ▼
         OBJETO #1 (heap)         OBJETO #2 (heap)
         nombre: "Ana"            nombre: "Luis"
         edad: 20                 edad: 22

  Estudiante e1 = ...;   ──▶ apunta al OBJETO #1
  Estudiante e2 = e1;    ──▶ apunta TAMBIÉN al OBJETO #1 (no es una copia)
```

> **Ojo con pensar que `e2 = e1` crea una copia independiente.** Como se ve en el diagrama, ambas variables terminan apuntando al **mismo** objeto. Si modificas `e2.nombre`, `e1.nombre` también "cambia" — en realidad nunca hubo dos objetos, solo dos nombres para el mismo. Esto es consistente con lo que ya viste en la sección de paso de parámetros por valor y referencia de la unidad 2: una variable de tipo objeto siempre guarda una referencia, nunca el objeto completo.

**Errores y malentendidos típicos:**

- **Confundir la clase con un objeto:** la clase es el molde, no se puede "usar" directamente como si tuviera datos propios (salvo miembros `static`, que pertenecen a la clase y no a ningún objeto en particular). Hay que instanciarla con `new` para obtener algo con lo que trabajar.
- **Olvidar `new` al crear un objeto:** `Estudiante e;` solo declara una variable que puede *apuntar* a un `Estudiante`, pero no crea ninguno — su valor es `null` hasta que se le asigne un objeto real con `new`.
- **Modificar un objeto pensando que es independiente, cuando en realidad es una referencia compartida:** la fuente más común de bugs confusos en código recién escrito con POO.

```java
class Estudiante {
    String nombre;
    int edad;

    void saludar() {
        System.out.println("Hola, soy " + nombre + " y tengo " + edad + " años");
    }
}

public class Main {
    public static void main(String[] args) {
        Estudiante e1 = new Estudiante();
        e1.nombre = "Ana";
        e1.edad = 20;
        e1.saludar(); // Hola, soy Ana y tengo 20 años

        Estudiante e2 = e1;   // e2 NO es una copia, apunta al mismo objeto
        e2.nombre = "Luis";
        e1.saludar(); // Hola, soy Luis y tengo 20 años -- e1 "cambió" también
    }
}
```

---

## 3.2. Atributos y métodos

**Qué es:** los **atributos** son variables declaradas dentro de una clase que representan el **estado** de cada objeto; los **métodos** son funciones declaradas dentro de una clase que representan el **comportamiento**, generalmente operando sobre esos atributos.

**Por qué existe la distinción entre atributo de instancia y atributo `static`:** un atributo normal (de instancia) tiene una copia **independiente por cada objeto** — cada `Estudiante` tiene su propio `nombre` y su propia `edad`. Un atributo `static`, en cambio, pertenece a la **clase misma** y es compartido por todas las instancias — útil para datos que no tiene sentido duplicar por objeto, como un contador de cuántos objetos se han creado en total.

**Cómo funciona por dentro:** cada objeto reserva, dentro de su propio bloque en el heap, espacio para sus atributos de instancia. Los atributos `static`, en cambio, viven en un área de memoria aparte asociada a la **clase**, que existe desde que la clase se carga en la JVM — incluso antes de crear ningún objeto, y sigue existiendo aunque se destruyan todos los objetos.

**Trace paso a paso — qué pasa con `contador` al crear tres objetos:**

| Paso | Código | `contador` (compartido, de la clase) | Atributos del objeto creado |
|---|---|---|---|
| 1 | `new Estudiante("Ana", 20)` | `0 → 1` | `nombre="Ana", edad=20` (propios de ese objeto) |
| 2 | `new Estudiante("Luis", 22)` | `1 → 2` | `nombre="Luis", edad=22` (propios de ese objeto) |
| 3 | `new Estudiante("Eva", 19)` | `2 → 3` | `nombre="Eva", edad=19` (propios de ese objeto) |

Nótese que `contador` termina en `3` sin importar cuántos objetos distintos lo modificaron — es **un solo valor**, no uno por objeto.

> **Ojo con el *shadowing* de parámetros:** si un constructor o método recibe un parámetro con el **mismo nombre** que un atributo (`Estudiante(String nombre)`), dentro de ese bloque el nombre `nombre` se refiere al **parámetro**, no al atributo — "tapa" (ensombrece) al atributo. Para referirte explícitamente al atributo de instancia en ese caso, se usa `this.nombre`, donde `this` significa "el objeto actual sobre el que se está ejecutando este método".

**Errores y malentendidos típicos:**

- **Modificar un atributo `static` pensando que es independiente por objeto:** si `contador` fuera modificado directamente con `e1.contador = 100;`, ese cambio se reflejaría para **todos** los objetos de esa clase, porque no hay "cien copias" — hay una sola.
- **Olvidar `this` cuando el parámetro ensombrece al atributo:** escribir `nombre = nombre;` dentro de un constructor con parámetro `nombre` no hace nada útil — asigna el parámetro a sí mismo. Lo correcto es `this.nombre = nombre;`.

```java
class Estudiante {
    String nombre;
    int edad;
    static int contador = 0; // compartido por todos los objetos de esta clase

    Estudiante(String nombre, int edad) {
        this.nombre = nombre; // this.nombre = atributo; nombre = parámetro
        this.edad = edad;
        contador++;
    }

    void cumplirAnios() {
        edad++; // método que modifica el estado de ESTE objeto
    }
}
```

---

## 3.3. Encapsulamiento

**Qué es:** el **encapsulamiento** es el principio de **ocultar los detalles internos** (los atributos) de un objeto, exponiendo solo una interfaz controlada (métodos públicos) para interactuar con él.

**Por qué existe:** sin encapsulamiento, cualquier parte del programa podría modificar directamente los atributos de un objeto sin ninguna validación, dejándolo en un estado inconsistente — por ejemplo, asignar `estudiante.edad = -5;` sin que nada lo impida. Al forzar el acceso a través de métodos, la clase misma controla qué cambios son válidos y cuáles no, en un solo lugar.

**Cómo funciona por dentro:** el encapsulamiento es una restricción **exclusivamente a nivel de compilador** — no existe ninguna "protección en memoria" especial para un atributo `private`. En tiempo de ejecución, un atributo `private` ocupa memoria exactamente igual que uno `public`; la diferencia es que el compilador rechaza (con un error de compilación) cualquier código fuera de la clase que intente acceder directamente a él. Es la misma idea de "restricción solo en tiempo de compilación" que ya se vio con `const` en la unidad 1.

### 3.3.1. Modificadores de acceso: `public`, `private` y `protected`

| Modificador | Accesible desde | Uso típico |
|---|---|---|
| `public` | Cualquier clase, de cualquier paquete | Métodos que forman la interfaz pública de la clase (lo que otros programadores deben poder usar) |
| `private` | Solo desde dentro de la misma clase | Atributos (casi siempre) y métodos auxiliares internos que no le incumben a nadie más |
| `protected` | La misma clase, sus **subclases** (unidad 4) y clases del mismo paquete | Miembros que las clases hijas necesitan usar o sobreescribir, pero que no deben ser parte de la interfaz pública general |
| *(sin modificador)* | Solo desde el mismo paquete (*package-private*) | El nivel por defecto si no escribes ninguno — menos común, útil para colaboración interna entre clases del mismo paquete |

> **Ojo con usar `protected` pensando que es equivalente a `private`:** `protected` es notablemente más permisivo — cualquier clase del mismo paquete (no solo las subclases) puede acceder, lo cual sorprende a quien espera que solo las clases hijas tengan ese privilegio.

**Árbol de decisión — ¿qué modificador usar?**

```
¿Quién necesita acceder a este atributo o método?
        │
        ├─ Solo esta clase ─────────────────▶ private
        │
        ├─ Esta clase y sus futuras subclases ▶ protected
        │
        └─ Cualquier clase del programa ─────▶ public
```

**Errores y malentendidos típicos:**

- **Declarar todos los atributos `public` "para que sea más fácil":** rompe el encapsulamiento por completo — cualquier parte del código puede corromper el estado del objeto sin pasar por ninguna validación. Es el antipatrón más común al empezar POO.
- **Hacer `public` lo que debería ser un detalle interno:** una vez que algo es `public`, otras clases pueden depender de ello — cambiarlo después puede romper código externo. Por defecto, declara todo `private` y solo "sube" la visibilidad cuando de verdad es necesario.

### 3.3.2. Métodos *get* y *set*

**Qué es:** un *getter* (`get...`) devuelve el valor de un atributo privado; un *setter* (`set...`) permite modificarlo, idealmente **validando** el nuevo valor antes de asignarlo.

**Por qué existen:** permiten exponer el valor de un atributo privado de forma controlada. La ventaja real está en el *setter*: puede rechazar o ajustar valores inválidos, algo imposible si el atributo fuera `public` directo.

```java
class Estudiante {
    private int edad;

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad < 0 || edad > 120) {
            throw new IllegalArgumentException("Edad fuera de rango válido");
        }
        this.edad = edad;
    }
}
```

> **Ojo con el "falso encapsulamiento":** escribir un *getter* y un *setter* para **todos** los atributos sin ninguna validación real (`setEdad(int edad) { this.edad = edad; }`, sin ningún `if`) es exactamente equivalente a hacer el atributo `public` directamente — solo con más líneas de código. El encapsulamiento vale la pena cuando el *setter* realmente impone una regla que de otra forma podría violarse.

**Matiz:** C# tiene una sintaxis nativa llamada *properties* (`public int Edad { get; set; }`) que simplifica este patrón visualmente. **Java no tiene esa sintaxis** — siempre son métodos explícitos, por convención `getNombreAtributo()`/`setNombreAtributo(valor)`, y para booleanos se prefiere el prefijo `is`/`has` (`isActivo()`) en vez de `getActivo()`.

---

## 3.4. Constructores

**Qué es:** un **constructor** es un método especial, con el mismo nombre que la clase y sin tipo de retorno, que se ejecuta automáticamente al crear un objeto con `new`, encargado de inicializar su estado.

**Por qué existe:** sin constructor, un objeto recién creado quedaría con los valores por defecto de cada tipo (`0`, `null`, `false`) — en la mayoría de los casos eso deja al objeto en un estado sin sentido (un `Estudiante` sin nombre asignado). El constructor garantiza que todo objeto nazca ya en un estado consistente y utilizable.

**Cómo funciona por dentro:** `new Estudiante(args)` ejecuta dos pasos en secuencia: (1) la JVM reserva memoria en el heap e inicializa **todos** los atributos con sus valores por defecto (esto ocurre siempre, antes de que el constructor haga nada); (2) se ejecuta el **cuerpo** del constructor, que típicamente sobrescribe esos valores por defecto con los que recibe. Si no escribes ningún constructor propio, Java genera automáticamente uno vacío sin parámetros (el *constructor por defecto*). **En cuanto escribes al menos un constructor propio, Java deja de generar ese constructor automático** — si todavía necesitas poder crear el objeto sin argumentos, tienes que escribirlo explícitamente.

> **Ojo con este error clásico:** si defines `Estudiante(String nombre, int edad) { ... }` y en otra parte del código intentas `new Estudiante();` sin argumentos, el programa **no compila**, porque el constructor vacío que existía implícitamente ya no existe — fue reemplazado por el tuyo.

Una clase puede tener **varios constructores** (sobrecarga de constructores, formalizada en la sección 4.2.1), y uno puede llamar a otro con `this(...)` para evitar duplicar lógica de inicialización:

```java
class Estudiante {
    String nombre;
    int edad;

    Estudiante(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    Estudiante(String nombre) {
        this(nombre, 18); // llama al otro constructor con una edad por defecto
    }
}
```

**Errores y malentendidos típicos:**

- **Olvidar que definir un constructor propio elimina el constructor vacío implícito** (ya explicado arriba).
- **Duplicar lógica de inicialización en varios constructores** en vez de usar `this(...)` para reutilizarla — si luego hay que agregar una validación, hay que recordar copiarla en cada constructor por separado.

---

## 3.5. Diagramas de clase UML

**Qué es:** UML (*Unified Modeling Language*) es una notación gráfica estándar para representar visualmente las clases de un programa — sus atributos, sus métodos y las relaciones entre ellas — sin necesidad de leer el código fuente.

**Por qué existe:** permite diseñar y comunicar la estructura de un sistema **antes** (o en paralelo) de escribir código. Un diagrama de clases muestra de un vistazo relaciones entre muchas clases que serían lentas de reconstruir leyendo archivo por archivo.

**Cómo se lee un diagrama de clase:**

```
┌──────────────────────────────┐
│          Estudiante            │   ← nombre de la clase
├──────────────────────────────┤
│ - nombre: String                │   ← atributos ( - = private )
│ - edad: int                      │
├──────────────────────────────┤
│ + Estudiante(nombre, edad)       │   ← constructor
│ + getNombre(): String            │   ← métodos ( + = public )
│ + setEdad(edad: int): void        │
└──────────────────────────────┘
```

Los símbolos antes de cada atributo o método corresponden **directamente** a los modificadores de acceso vistos en la sección 3.3.1:

| Símbolo | Modificador |
|---|---|
| `+` | `public` |
| `-` | `private` |
| `#` | `protected` |

La notación del atributo/método sigue el patrón `nombre: Tipo` (atributos) o `nombre(parametros): TipoDeRetorno` (métodos) — es un formato independiente del lenguaje de programación, pensado para que cualquiera que conozca UML pueda leerlo sin saber Java específicamente.

> **Ojo con confundir el signo `-` de un atributo privado con un número negativo:** en UML, el `-` siempre va **antes** del nombre del atributo o método para indicar visibilidad — no tiene relación con el valor que ese atributo pueda tener.

**Errores y malentendidos típicos:**

- **No actualizar el diagrama cuando el código cambia:** un diagrama desincronizado con el código real es peor que no tener diagrama, porque transmite información falsa a quien lo consulta.
- **Incluir en el diagrama detalles de implementación interna** (variables locales dentro de un método, por ejemplo) — UML de clases muestra la **interfaz** de la clase (lo que es visible estructuralmente), no cada línea de su lógica.

---

## 3.6. Buenas prácticas en POO

| Práctica | Por qué importa |
|---|---|
| **Alta cohesión:** cada clase debe tener una sola responsabilidad clara | Una clase que hace demasiadas cosas no relacionadas es difícil de entender, de probar, y cualquier cambio en una de sus responsabilidades arriesga romper las demás |
| **Bajo acoplamiento:** una clase debe depender lo menos posible de los detalles internos de otra | Si una clase accede directamente a los atributos internos de otra (rompiendo su encapsulamiento), un cambio interno en esa segunda clase rompe silenciosamente a la primera |
| **Encapsular por defecto:** atributos `private`, exponer solo lo necesario mediante métodos | Reduce la superficie de código que puede dejar al objeto en un estado inválido (sección 3.3) |
| **Validar en los *setters*, no confiar en quien llama** | El objeto es responsable de mantenerse en un estado válido — no se puede confiar en que cada parte del programa que lo use recuerde validar antes de asignar |
| **Nombrar con las convenciones del lenguaje:** clases en `PascalCase` (`Estudiante`), métodos y variables en `camelCase` (`calcularPromedio`), constantes en `MAYUSCULAS_CON_GUION_BAJO` | Un código que sigue las convenciones esperadas es más fácil de leer para cualquier programador Java, sin necesidad de explicación adicional |
| **Preferir composición sobre herencia cuando la relación no es claramente "es-un"** | Se profundiza en la unidad 4 — usar herencia de forma incorrecta genera jerarquías de clases confusas y difíciles de mantener |

> **Ojo con la sobre-ingeniería:** no toda pieza de código necesita convertirse en una clase con getters, setters y una jerarquía elaborada desde el primer momento. POO es una herramienta para modelar complejidad real — aplicarla donde no hace falta añade complejidad en vez de reducirla.

---

*Universidad Politécnica de Tecamac — Ingeniería en Software*
