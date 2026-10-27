# Ejemplos resueltos de lectura y escritura

En esta carpeta hay cinco ejemplos de texto plano. En `datos` están las entradas de muestra y cada programa crea sus resultados en `resultados`.

- `EjemploTexto1`: calcula la media de temperaturas y cuenta líneas incorrectas.
- `EjemploTexto2`: procesa notas separadas por `;` y obtiene aprobados y media.
- `EjemploTexto3`: filtra las líneas que contienen una palabra.
- `EjemploTexto4`: calcula qué productos hay que reponer.
- `EjemploTexto5`: combina y ordena números de dos archivos.

Los ejemplos XML están en `Ejercicios/Ficheros/XML_DOM/Combinado`. Leen XML con DOM y guardan sus resultados en una carpeta `resultados`, sin reemplazar los XML originales de `datos`.

Desde PowerShell, situado en la carpeta raíz del proyecto:

```powershell
javac -encoding UTF-8 -d out Ejercicios\Ficheros\TextoPlano\Combinado\EjemploTexto*.java Ejercicios\Ficheros\XML_DOM\Combinado\EjemploDOM*.java
java -cp out Ejercicios.Ficheros.TextoPlano.Combinado.EjemploTexto1
java -cp out Ejercicios.Ficheros.XML_DOM.Combinado.EjemploDOM1
```

Para estudiar otro ejemplo, cambia el nombre de la clase al ejecutarla. Las rutas de datos son relativas a la carpeta raíz del proyecto.
