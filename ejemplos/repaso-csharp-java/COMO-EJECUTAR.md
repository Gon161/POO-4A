# Cómo ejecutar estos programas desde la terminal

Guía paso a paso para compilar y correr los ejemplos `.java` de esta carpeta usando la terminal de Windows. Los comandos de las secciones 1 a 7 son para **cmd.exe**; si usas **PowerShell** (por ejemplo, la terminal integrada de VS Code), ve directo al [Apéndice B](#apéndice-b-usando-powershell-por-ejemplo-la-terminal-de-vs-code).

---

## 1. Verifica que tienes Java instalado

Abre `cmd` y escribe:

```cmd
javac -version
java -version
```

- Si ves algo como `javac 21.0.2` y `java 21.0.2`, ya puedes continuar al paso 2.
- Si ves `'javac' no se reconoce como un comando interno o externo...`, sigue el **Apéndice: Java no está en el PATH** al final de este documento.

---

## 2. Entra a la carpeta de los ejemplos

```cmd
cd "C:\Users\Gon161\Desktop\27-2\POO-4A\ejemplos\repaso-csharp-java"
```

---

## 3. Compila un archivo

La compilación convierte el `.java` (código fuente) en un `.class` (bytecode que la JVM puede ejecutar).

```cmd
javac 03_TiposPrimitivos.java
```

Esto genera un archivo `TiposPrimitivos.class` en la misma carpeta (el nombre del `.class` es el de la **clase**, no el del archivo — por eso no lleva el número).

> Si no aparece ningún error en la consola, la compilación fue exitosa. Si hay errores, te dirá la línea exacta del problema.

---

## 4. Ejecuta el programa compilado

```cmd
java TiposPrimitivos
```

Usas el nombre de la **clase** (sin `.class` y sin el número del archivo), no el nombre del archivo `.java`.

### Ejemplo completo (compilar + ejecutar)

```cmd
cd "C:\Users\Gon161\Desktop\27-2\POO-4A\ejemplos\repaso-csharp-java"
javac 03_TiposPrimitivos.java
java TiposPrimitivos
```

---

## 5. Tabla: archivo → comando para ejecutar

Compila cada archivo con `javac <archivo>.java` y ejecútalo con `java <Clase>`:

| Archivo | Comando para ejecutar |
|---|---|
| `01_Consola.java` | `java Consola` |
| `02_Comentarios.java` | `java Comentarios` |
| `03_TiposPrimitivos.java` | `java TiposPrimitivos` |
| `04_VariablesConstantes.java` | `java VariablesConstantes` |
| `05_OperadoresAritmeticos.java` | `java OperadoresAritmeticos` |
| `06_OperadoresRelacionales.java` | `java OperadoresRelacionales` |
| `07_OperadoresLogicos.java` | `java OperadoresLogicos` |
| `08_OperadoresAsignacion.java` | `java OperadoresAsignacion` |
| `09_CondicionalIfElse.java` | `java CondicionalIfElse` |
| `10_Switch.java` | `java Switch` |
| `11_EstructurasIterativas.java` | `java EstructurasIterativas` |
| `12_Arreglos.java` | `java Arreglos` |
| `13_Listas.java` | `java Listas` |
| `14_Metodos.java` | `java Metodos` |
| `15_Casting.java` | `java Casting` |

---

## 6. Compilar todos de una vez (opcional)

Para compilar **todos** los `.java` de la carpeta en un solo paso:

```cmd
javac *.java
```

Esto genera un `.class` por cada archivo. Luego ejecutas cualquiera con `java <Clase>` como en el paso 4.

---

## 7. Limpiar los archivos compilados

Los `.class` son archivos generados, no hace falta conservarlos. Para borrarlos todos:

```cmd
del *.class
```

---

## Apéndice A: Java no está en el PATH (cmd)

Si `javac -version` da error, significa que Windows no sabe dónde está el JDK instalado. Dos soluciones:

### Opción A — Agregar Java al PATH (recomendado, permanente)

1. Busca dónde está instalado el JDK, normalmente algo como:
   `C:\Program Files\Java\jdk-21\bin`
2. Presiona `Win + R`, escribe `sysdm.cpl` y da Enter.
3. Ve a la pestaña **Opciones avanzadas** → **Variables de entorno**.
4. En **Variables del sistema**, selecciona `Path` → **Editar** → **Nuevo**.
5. Pega la ruta de la carpeta `bin` del JDK (del paso 1).
6. Acepta todo y **abre una nueva ventana de cmd** (los cambios no aplican en la ventana ya abierta).
7. Verifica de nuevo: `javac -version`.

### Opción B — Usar la ruta completa sin tocar el PATH (rápido, temporal)

Si tienes IntelliJ IDEA instalado, ya trae un JDK empaquetado que puedes usar directamente sin configurar nada:

```cmd
"C:\Program Files\JetBrains\IntelliJ IDEA 2026.1\jbr\bin\javac.exe" 03_TiposPrimitivos.java
"C:\Program Files\JetBrains\IntelliJ IDEA 2026.1\jbr\bin\java.exe" TiposPrimitivos
```

Ajusta la ruta según la versión de IntelliJ instalada (`C:\Program Files\JetBrains\...\jbr\bin\`).

---

## Apéndice B: Usando PowerShell (por ejemplo, la terminal de VS Code)

PowerShell es la terminal que abre VS Code por defecto en Windows — es distinta de `cmd.exe`, así que los mismos comandos a veces fallan o hay que escribirlos distinto.

### El mismo error de "javac no se reconoce"

Si ves:

```
javac : El término 'javac' no se reconoce como nombre de un cmdlet, función, archivo de script o programa ejecutable.
```

Es el mismo problema del Apéndice A: no hay ningún JDK en el `PATH`. Las soluciones son equivalentes, pero la sintaxis de PowerShell cambia un poco:

**Opción 1 — Ruta completa con el operador de llamada `&` (rápido, solo esta sesión):**

En PowerShell, para ejecutar un programa cuya ruta tiene espacios y está entre comillas, hay que anteponer `&`:

```powershell
& "C:\Program Files\JetBrains\IntelliJ IDEA 2026.1\jbr\bin\javac.exe" .\01_Consola.java
& "C:\Program Files\JetBrains\IntelliJ IDEA 2026.1\jbr\bin\java.exe" Consola
```

**Opción 2 — Agregar la carpeta al PATH solo para esta ventana de terminal:**

```powershell
$env:PATH += ";C:\Program Files\JetBrains\IntelliJ IDEA 2026.1\jbr\bin"
javac .\01_Consola.java
java Consola
```

Esto solo dura mientras esa ventana de PowerShell esté abierta; si la cierras, se pierde y hay que volver a ejecutarlo.

**Opción 3 — Agregarlo al PATH de forma permanente:**

Los pasos son los mismos que en el **Apéndice A, Opción A** (`sysdm.cpl` → Variables de entorno → `Path`) — eso aplica para cmd y PowerShell por igual, porque el `PATH` es una configuración de Windows, no de la terminal.

> **Recomendación:** si vas a usar la terminal seguido (no solo el botón Run del IDE), lo más práctico a la larga es instalar un JDK normal (por ejemplo Eclipse Temurin u Oracle JDK) y agregarlo al PATH, en vez de depender de la ruta interna de IntelliJ — esa ruta cambia con cada versión del IDE.

### Diferencias de sintaxis cmd vs PowerShell que te puedes topar

| Acción | cmd.exe | PowerShell |
|---|---|---|
| Ejecutar un .exe con ruta completa entre comillas | `"ruta\al\programa.exe" args` | `& "ruta\al\programa.exe" args` |
| Borrar archivos `.class` | `del *.class` | `Remove-Item *.class` (`del` también funciona, es un alias) |
| Ver variable PATH actual | `echo %PATH%` | `$env:PATH` |
| Variable de entorno temporal | `set VAR=valor` | `$env:VAR = "valor"` |
