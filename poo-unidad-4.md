# Unidad 4 — Programación Orientada a Objetos: Herencia y Polimorfismo

La unidad 3 estableció los pilares básicos de POO: clases, objetos y encapsulamiento. Esta unidad cubre cómo las clases se **relacionan** entre sí (herencia), cómo los objetos de distintos tipos relacionados pueden tratarse de forma uniforme (polimorfismo), los mecanismos de abstracción que lo hacen posible (clases abstractas e interfaces), y un complemento práctico indispensable: el manejo de errores con excepciones. Todo el contenido usa Java.

---

## Índice

1. [Herencia](#41-herencia)
   - 4.1.1. [Clases base y derivadas](#411-clases-base-y-derivadas)
2. [Polimorfismo](#42-polimorfismo)
   - 4.2.1. [Sobrecarga de métodos y constructores](#421-sobrecarga-de-métodos-y-constructores)
   - 4.2.2. [Sobreescritura de métodos](#422-sobreescritura-de-métodos)
3. [Clases abstractas](#43-clases-abstractas)
4. [Interfaces](#44-interfaces)
5. [Manejo básico de excepciones](#45-manejo-básico-de-excepciones)

---

## 4.1. Herencia

**Qué es:** la herencia es un mecanismo que permite que una clase (**subclase** o clase derivada) reutilice los atributos y métodos de otra clase (**superclase** o clase base), estableciendo una relación conceptual de tipo **"es-un"** (*is-a*).

**Por qué existe:** resuelve la duplicación de código entre clases que comparten comportamiento común pero tienen diferencias específicas. En vez de copiar y pegar los mismos atributos y métodos en `Perro` y `Gato`, ambas heredan de una clase común `Animal` que define lo compartido (`nombre`, `comer()`), y cada subclase agrega solamente lo que la distingue (`Perro.ladrar()`).

**Cómo funciona por dentro:** en Java, `extends` establece la relación en tiempo de compilación, pero tiene un efecto muy concreto en memoria: un objeto `Perro` en realidad contiene, en un solo bloque contiguo del heap, **el espacio de los atributos de `Animal` más los atributos propios de `Perro`**. No hay dos objetos separados "pegados" — es uno solo con todos los campos heredados y propios juntos. Cuando llamas a un método heredado que no fue sobrescrito, la JVM ejecuta el código definido en la superclase, pero operando sobre el estado del objeto subclase actual.

La palabra clave `super` se usa para dos cosas: llamar al **constructor** de la superclase (`super(args);`, debe ser la primera línea del constructor de la subclase) o llamar a la versión de un **método sobrescrito** definida en la superclase (`super.metodo()`, ver sección 4.2.2).

### 4.1.1. Clases base y derivadas

```
          ┌────────────┐
          │   Animal     │   ← clase base (superclase)
          ├────────────┤
          │ - nombre     │
          ├────────────┤
          │ + comer()    │
          └──────▲─────┘
                 │  herencia (extends)
         ┌───────┴────────┐
         │                │
   ┌────────────┐   ┌────────────┐
   │    Perro     │   │    Gato      │   ← clases derivadas (subclases)
   ├────────────┤   ├────────────┤
   │ + ladrar()   │   │ + maullar()  │
   └────────────┘   └────────────┘
```

```java
class Animal {
    protected String nombre;

    Animal(String nombre) {
        this.nombre = nombre;
    }

    void comer() {
        System.out.println(nombre + " está comiendo");
    }
}

class Perro extends Animal {
    Perro(String nombre) {
        super(nombre); // llama al constructor de Animal -- obligatorio como primera línea
    }

    void ladrar() {
        System.out.println(nombre + " dice: ¡Guau!"); // nombre se heredó de Animal
    }
}
```

**Trace — qué contiene un objeto `Perro` en memoria:**

| Origen del miembro | Miembro | ¿Visible desde `Perro`? |
|---|---|---|
| Heredado de `Animal` | `nombre` (atributo) | Sí (es `protected`, ver unidad 3.3.1) |
| Heredado de `Animal` | `comer()` (método) | Sí, se ejecuta sin cambios si no se sobrescribe |
| Propio de `Perro` | `ladrar()` (método) | Sí, solo existe en `Perro`, no en `Animal` ni en `Gato` |

**Errores y malentendidos típicos:**

- **Intentar heredar de más de una clase a la vez:** Java **no permite herencia múltiple de clases** (`class X extends A, B` no compila), a diferencia de C++. La solución para necesitar múltiples "contratos" es implementar varias **interfaces** (sección 4.4).
- **Olvidar llamar a `super(...)` cuando la superclase no tiene un constructor vacío:** si `Animal` solo define `Animal(String nombre)` (sin una versión sin parámetros), y `Perro` no llama explícitamente a `super(nombre)` en su constructor, el compilador **rechaza el código** — no puede asumir un constructor vacío que no existe.
- **Usar herencia para relaciones "tiene-un" en vez de "es-un":** un error de diseño común es heredar `Motor` dentro de `Auto` (`class Auto extends Motor`) cuando la relación real es que un `Auto` **tiene** un `Motor`, no que **es** un motor. La solución correcta ahí es **composición**: `Auto` guarda una referencia a un objeto `Motor` como atributo, en vez de heredar de él.

**Matiz:** toda clase en Java hereda implícitamente de `Object` aunque no se escriba `extends` explícitamente. Por eso todo objeto, sin excepción, ya tiene disponibles métodos como `toString()`, `equals()` y `hashCode()` desde el momento en que se crea.

---

## 4.2. Polimorfismo

**Qué es:** polimorfismo ("muchas formas") es la capacidad de tratar objetos de distintas clases relacionadas por herencia de manera **uniforme**, a través de su tipo común (la superclase), mientras cada uno ejecuta su propio comportamiento específico.

**Por qué existe:** permite escribir código genérico que funciona con cualquier subtipo sin necesidad de saber de antemano cuál es exactamente. Un método que recibe un parámetro de tipo `Animal` puede recibir indistintamente un `Perro` o un `Gato`, y cada uno "sabe" cómo comportarse según lo que realmente es, sin que el método que los recibe tenga que preguntar "¿qué tipo eres?" con una cadena de `if`.

**Cómo funciona por dentro — enlace dinámico (*dynamic dispatch*):** cuando se declara `Animal a = new Perro("Rex");` y luego se llama `a.hacerSonido()`, la JVM **no** decide en tiempo de compilación qué código ejecutar basándose en el tipo **declarado** de la variable (`Animal`). Decide en tiempo de **ejecución**, basándose en el tipo **real** del objeto al que `a` apunta (`Perro`). Esto se llama *enlace dinámico* o *late binding*, y se implementa internamente mediante una **tabla de métodos virtuales** (*vtable*): cada objeto, además de sus atributos, lleva asociada una referencia a la tabla de métodos de su clase real, y esa tabla es la que se consulta en el instante exacto de la llamada — no antes.

### 4.2.1. Sobrecarga de métodos y constructores

**Qué es:** la **sobrecarga** (*overloading*) consiste en declarar dos o más métodos (o constructores) con el **mismo nombre** pero con **distinta lista de parámetros** (diferente cantidad y/o distintos tipos), dentro de la misma clase.

**Por qué existe:** permite ofrecer varias formas de invocar una operación conceptualmente igual, adaptándose a distintos datos de entrada, sin inventar nombres artificiales distintos para cada variante (`sumar(int, int)`, `sumar(double, double)`, `sumar(int, int, int)`).

**Cómo funciona por dentro:** la sobrecarga se resuelve **completamente en tiempo de compilación** (*static binding*) — el compilador examina los tipos de los argumentos en el punto exacto de la llamada y decide, antes de que el programa se ejecute siquiera una vez, cuál de las versiones sobrecargadas aplica. Esto es **fundamentalmente distinto** al polimorfismo con sobreescritura (4.2.2), que se resuelve en tiempo de ejecución.

```java
class Calculadora {
    int sumar(int a, int b) {
        return a + b;
    }

    double sumar(double a, double b) {
        return a + b;
    }

    int sumar(int a, int b, int c) {
        return a + b + c;
    }
}
```

**Errores y malentendidos típicos:**

- **Sobrecargas ambiguas:** si existieran `sumar(int, long)` y `sumar(long, int)`, una llamada `sumar(5, 10)` (ambos `int`) podría convertirse implícitamente para calzar en cualquiera de las dos — el compilador no puede elegir y marca error de **ambigüedad**, en vez de adivinar.
- **Confundir sobrecarga (*overloading*) con sobreescritura (*overriding*):** a pesar de que suenan parecido en español, son conceptos completamente distintos — la sobrecarga ocurre **dentro de una misma clase** y se resuelve en compilación; la sobreescritura ocurre **entre superclase y subclase** y se resuelve en ejecución (siguiente sección).

### 4.2.2. Sobreescritura de métodos

**Qué es:** la **sobreescritura** (*overriding*) ocurre cuando una subclase redefine la implementación de un método que ya existe en su superclase, manteniendo **exactamente la misma firma** (mismo nombre, mismos parámetros, mismo tipo de retorno o un subtipo compatible).

**Por qué existe:** es el mecanismo que hace posible el polimorfismo real (ya descrito arriba). Sin sobreescritura, todas las subclases ejecutarían siempre el comportamiento genérico definido en la superclase, sin poder especializarlo.

**Cómo funciona por dentro:** ya se explicó arriba — se resuelve mediante la *vtable*, en tiempo de **ejecución**, según el tipo real del objeto al que apunta la variable, no su tipo declarado.

```java
class Animal {
    void hacerSonido() {
        System.out.println("El animal hace un sonido genérico");
    }
}

class Perro extends Animal {
    @Override
    void hacerSonido() {
        System.out.println("¡Guau!");
    }
}

class Gato extends Animal {
    @Override
    void hacerSonido() {
        System.out.println("¡Miau!");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal[] animales = { new Perro(), new Gato() };
        for (Animal a : animales) {
            a.hacerSonido(); // ¡Guau! luego ¡Miau! -- cada objeto ejecuta SU versión
        }
    }
}
```

**Comparación directa — sobrecarga vs sobreescritura:**

| Característica | Sobrecarga (*overloading*) | Sobreescritura (*overriding*) |
|---|---|---|
| Nombre del método | Igual | Igual |
| Parámetros | **Distintos** (cantidad o tipo) | **Idénticos** |
| Dónde ocurre | Dentro de la **misma clase** | Entre **superclase y subclase** |
| Cuándo se resuelve | Tiempo de **compilación** | Tiempo de **ejecución** |
| Requiere herencia | No | Sí |

**La anotación `@Override`:** no es obligatoria, pero es muy buena práctica — le pide al compilador que **verifique** que efectivamente se está sobreescribiendo un método existente en la superclase. Si hay un error de tipeo en el nombre o en los parámetros, el compilador avisa con un error en vez de crear silenciosamente un método nuevo, sin ninguna relación con el de la superclase (un bug muy difícil de detectar sin `@Override`).

**Errores y malentendidos típicos:**

- **Reducir la visibilidad del método al sobreescribirlo:** si en `Animal` el método es `public`, no se puede sobreescribir como `private` en `Perro` — violaría el contrato de que cualquier `Animal` puede llamar a ese método públicamente. El compilador lo rechaza.
- **Cometer un error de tipeo en el nombre sin usar `@Override`:** escribir `haserSonido()` en vez de `hacerSonido()` en la subclase, sin la anotación, compila sin ningún error — simplemente crea un método nuevo no relacionado, y el polimorfismo esperado nunca ocurre (sigue ejecutándose el método genérico de `Animal`). Con `@Override`, este mismo error se detecta de inmediato.

---

## 4.3. Clases abstractas

**Qué es:** una **clase abstracta** es una clase que **no se puede instanciar directamente** (no se puede hacer `new`) y que puede declarar **métodos abstractos** (sin cuerpo, solo la firma) que toda subclase concreta está **obligada** a implementar.

**Por qué existe:** resuelve el caso en que una superclase define comportamiento común parcial, pero una parte de ese comportamiento no tiene una implementación genérica razonable — solo cada subclase específica sabe cómo hacerlo. `Animal.comer()` puede tener una implementación genérica válida para todos, pero `Animal.hacerSonido()` no: cada subclase concreta **debe** definir la suya.

**Cómo funciona por dentro:** el compilador rechaza cualquier intento de `new Animal()` si `Animal` está declarada `abstract` — es una restricción puramente de compilación, igual que el encapsulamiento (unidad 3.3). Si una subclase no implementa **todos** los métodos abstractos heredados, esa subclase también debe declararse `abstract` (propagando la obligación hacia abajo), o el compilador la rechaza.

```java
abstract class Animal {
    protected String nombre;

    Animal(String nombre) {
        this.nombre = nombre;
    }

    void comer() { // método concreto: compartido tal cual por todas las subclases
        System.out.println(nombre + " está comiendo");
    }

    abstract void hacerSonido(); // sin cuerpo: cada subclase DEBE implementarlo
}

class Perro extends Animal {
    Perro(String nombre) {
        super(nombre);
    }

    @Override
    void hacerSonido() {
        System.out.println(nombre + " dice: ¡Guau!");
    }
}
```

> **Ojo:** `new Animal("Rex")` **no compila** — `Animal` es abstracta. Solo se puede instanciar `Perro` (o cualquier otra subclase concreta que implemente `hacerSonido()`), pero se puede seguir **declarando** una variable de tipo `Animal` que apunte a un `Perro`, exactamente como en los ejemplos de polimorfismo de la sección 4.2.

**Errores y malentendidos típicos:**

- **Olvidar implementar un método abstracto heredado:** si `Perro` no implementa `hacerSonido()`, el compilador exige que `Perro` también se declare `abstract` — no se puede "ignorar" un método abstracto heredado en una clase concreta.
- **Declarar una clase `abstract` sin ningún método abstracto:** es sintácticamente válido (simplemente impide instanciarla directamente), pero casi nunca tiene sentido práctico si no hay ningún método que las subclases deban implementar obligatoriamente.

**Cuándo usar clase abstracta (vs interfaz, sección 4.4):** cuando las clases relacionadas comparten **estado** real (atributos) y/o **comportamiento concreto** además del contrato — es decir, cuando además de obligar a implementar algo, hay código compartido que vale la pena centralizar.

---

## 4.4. Interfaces

**Qué es:** una **interfaz** es un contrato puro — una lista de métodos que cualquier clase que la implemente se compromete a proporcionar, tradicionalmente **sin ninguna implementación propia**.

**Por qué existe:** resuelve la limitación de que Java no permite herencia múltiple de **clases** (sección 4.1.1). Una clase **sí** puede implementar **varias interfaces a la vez** (`class Pajaro implements Volador, Nadador`), lo que permite que un objeto cumpla múltiples "roles" o "capacidades" independientes, sin la ambigüedad que tendría heredar estado de varias clases distintas a la vez (el llamado "problema del diamante", que Java evita precisamente prohibiendo la herencia múltiple de clases).

**Cómo funciona por dentro:** una interfaz **no tiene estado propio** — no puede declarar atributos de instancia (cualquier campo que declare es implícitamente `public static final`, es decir, una constante compartida, no un atributo por objeto). Es puramente un conjunto de firmas de métodos que el compilador usa para **verificar en tiempo de compilación** que cualquier clase que declare `implements EsaInterfaz` realmente proporcione cada uno de esos métodos. En tiempo de ejecución, el enlace dinámico funciona exactamente igual que con clases (la misma *vtable* descrita en 4.2), permitiendo polimorfismo también a través del tipo de la interfaz.

```java
interface Volador {
    void volar();
}

interface Nadador {
    void nadar();
}

class Pato implements Volador, Nadador { // una clase, VARIAS interfaces
    @Override
    public void volar() {
        System.out.println("El pato vuela bajo");
    }

    @Override
    public void nadar() {
        System.out.println("El pato nada en el lago");
    }
}
```

**Comparación directa — clase abstracta vs interfaz:**

| Característica | Clase abstracta | Interfaz |
|---|---|---|
| Atributos de instancia (estado) | Sí puede tener | No (solo constantes implícitas) |
| Métodos concretos (con cuerpo) | Sí, además de los abstractos | Solo métodos `default` (Java 8+, ver matiz abajo) |
| Cuántas puede "heredar" una clase | Solo **una** (`extends`) | **Varias** a la vez (`implements`) |
| Tipo de relación que expresa | "Es-un" fuerte, con estado compartido | "Puede-hacer" — un contrato de capacidad |

> **Matiz (Java 8+):** las interfaces pueden declarar métodos `default` con cuerpo propio (una implementación de respaldo que las clases implementadoras pueden usar tal cual o sobreescribir). Esto difumina un poco la distinción clásica "interfaz = sin implementación", pero la diferencia de **estado** (atributos) sigue siendo absoluta: una interfaz nunca puede tener atributos de instancia, tenga o no métodos `default`.

**Árbol de decisión — ¿clase abstracta o interfaz?**

```
¿Las clases que lo usarán comparten ESTADO (atributos) real en común?
        │
        ├─ Sí ──────────────────────────────▶ clase abstracta
        │
        └─ No, solo necesito garantizar que todas
           tengan cierto método, sin importar su jerarquía
                │
                └─ ¿La clase ya necesita heredar de otra cosa,
                   o cumplir varios contratos independientes?
                        │
                        └─ Sí (o simplemente no hay estado que compartir) ─▶ interfaz
```

**Errores y malentendidos típicos:**

- **Intentar declarar un atributo de instancia en una interfaz:** cualquier campo en una interfaz es automáticamente constante (`public static final`) — no se puede usar para guardar estado distinto por cada objeto que la implemente.
- **Olvidar implementar algún método de la interfaz:** si `Pato` no implementara `nadar()`, el compilador lo rechaza — igual que con los métodos abstractos de una clase abstracta.
- **Elegir clase abstracta cuando en realidad se necesitan varios contratos independientes:** si `Pato` también necesitara comportarse como `Nadador` y `Volador` a la vez, no podría lograrse con clases abstractas (solo se puede heredar de una), pero sí con interfaces.

---

## 4.5. Manejo básico de excepciones

**Qué es:** una **excepción** es un objeto que representa un error o una condición anómala ocurrida durante la ejecución, que interrumpe el flujo normal del programa y "sube" buscando un bloque que sepa manejarla.

**Por qué existe:** sin excepciones, cada método tendría que devolver manualmente códigos de error que quien lo llama debe revisar explícitamente cada vez — algo fácil de olvidar y que mezcla la lógica normal con la lógica de manejo de errores en el mismo nivel. Las excepciones separan el "camino feliz" del código de manejo de errores, y **garantizan** que un error no manejado detenga el programa de forma visible (con un *stack trace*), en vez de continuar silenciosamente con datos corruptos.

**Cómo funciona por dentro — desenrollado de la pila (*stack unwinding*):** cuando ocurre un `throw` (lanzado explícitamente por el código, o por la JVM al detectar algo como una división entre cero), la ejecución normal se detiene de inmediato. La JVM empieza a **"subir" por la pila de llamadas** (la misma pila de la unidad 2, sección "Métodos") buscando, en cada método que está en curso, un bloque `try/catch` capaz de manejar ese tipo específico de excepción. Si la excepción llega hasta `main` sin que ningún `catch` la haya atrapado, el programa termina abruptamente mostrando el *stack trace* completo.

**Trace — una excepción atravesando tres llamadas anidadas:**

| Paso | Método en ejecución | ¿Qué pasa? |
|---|---|---|
| 1 | `main()` llama a `procesar()` | `procesar()` entra en la pila |
| 2 | `procesar()` llama a `dividir(10, 0)` | `dividir()` entra en la pila |
| 3 | `dividir()` ejecuta `10 / 0` | la JVM lanza `ArithmeticException` |
| 4 | la JVM revisa si `dividir()` tiene un `try/catch` para esa excepción | No tiene → `dividir()` sale de la pila sin terminar normalmente |
| 5 | la JVM revisa si `procesar()` tiene un `try/catch` | Si sí lo tiene, ahí se detiene la búsqueda y se ejecuta ese `catch` |

```java
class Main {
    static int dividir(int a, int b) {
        return a / b; // puede lanzar ArithmeticException si b == 0
    }

    static void procesar() {
        try {
            int resultado = dividir(10, 0);
            System.out.println("Resultado: " + resultado);
        } catch (ArithmeticException e) {
            System.out.println("Error: no se puede dividir entre cero");
        } finally {
            System.out.println("Esto se ejecuta siempre, haya error o no");
        }
    }

    public static void main(String[] args) {
        procesar();
        System.out.println("El programa sigue corriendo normalmente");
    }
}
```

| Elemento | Qué hace |
|---|---|
| `try` | Bloque donde puede ocurrir el error |
| `catch` | Bloque que maneja un tipo específico de excepción |
| `finally` | Bloque que se ejecuta **siempre**, haya o no excepción — ideal para liberar recursos |
| `throw` | Lanza una excepción manualmente desde el código |
| `throws` | En la firma de un método, declara que puede lanzar cierta excepción (excepciones *checked*) |

> **Matiz — excepciones *checked* vs *unchecked*:** Java distingue dos familias. Las *checked* (como `IOException`) **obligan** a manejarlas o declararlas con `throws` — el compilador rechaza el código si no lo haces. Las *unchecked* (subclases de `RuntimeException`, como `ArithmeticException` o `NullPointerException`) **no obligan** a nada — pueden ocurrir en cualquier línea sin que el compilador lo exija, por lo que suelen representar errores de programación más que condiciones esperables del entorno.

**Errores y malentendidos típicos:**

- **Capturar `Exception` genérico para "tapar" cualquier error:** `catch (Exception e) { }` atrapa absolutamente cualquier tipo de excepción, ocultando errores que probablemente deberían manejarse de forma distinta (o no deberían ocultarse en absoluto).
- **Dejar un bloque `catch` vacío:** silenciar el error sin hacer nada (ni registrarlo, ni mostrarlo) es el peor antipatrón de manejo de excepciones — el programa sigue corriendo como si nada hubiera pasado, pero con un error real sin resolver.
- **Olvidar que `finally` se ejecuta incluso si hay un `return` dentro del `try` o del `catch`:** el `finally` es la única garantía real de "esto se ejecuta sin importar qué pase" — por eso es el lugar correcto para cerrar recursos (archivos, conexiones) manualmente abiertos.

---

*Universidad Politécnica de Tecamac — Ingeniería en Software*
