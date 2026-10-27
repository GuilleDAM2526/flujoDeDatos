# Catálogo TXT del Pip-Boy

Ejemplo de consola para practicar lectura y escritura de archivos de texto. Cada categoría se guarda en su propio archivo:

- `datos/armas.txt`
- `datos/armaduras.txt`
- `datos/municion.txt`
- `datos/objetos.txt`

Cada línea contiene cuatro campos separados por `|`:

```text
id|nombre|campo1|campo2
```

Los nombres de los campos dependen de la categoría. Por ejemplo, en `armas.txt`, el cuarto campo es el ID de la munición relacionada. Los ID son únicos entre todas las categorías. `datos/siguienteId.txt` guarda el próximo ID para que no se reutilicen los que ya se asignaron, incluso después de borrar un registro.

El programa valida que los ID no se repitan y que las armas no apunten a munición inexistente. No permite borrar munición que todavía usa un arma.

## Ejecutar desde VS Code

1. Abre en VS Code la carpeta del proyecto que contiene `Ejercicios`.
2. Abre una terminal integrada en esa carpeta.
3. Comprueba que tienes instalado un JDK con `java -version` y `javac -version`.
4. Compila las clases:

   ```powershell
   javac -encoding UTF-8 -d out Ejercicios\Ficheros\TextoPlano\PipBoy\*.java
   ```

5. Ejecuta el programa:

   ```powershell
   java -cp out Ejercicios.Ficheros.TextoPlano.PipBoy.Main
   ```

Los archivos de datos se buscan desde la carpeta raíz del proyecto. Cada alta, edición o eliminación se guarda inmediatamente en el TXT de su categoría.
