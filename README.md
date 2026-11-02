# Kata 2 — Data input and output with streams

Java project for Kata 2 of Ingeniería de Software 2 (ULPGC). It reads a TSV dataset of pirate ships from an `InputStream`, turns each row into a `Ship`, computes how many ships belong to each port, and writes the summary to an `OutputStream`.

## Purpose of the assignment

Practice working with byte streams (`InputStream` / `OutputStream`) and keep the data logic independent of where the data comes from and where the result goes. The same loading and summarizing code works with different sources (resource, file, URL) and different destinations (console, file).

## How to build and run

Requires JDK 26. Open the project in IntelliJ IDEA (it imports Maven from `pom.xml`) and run `Main.java`.

By default, `Main` reads `src/main/resources/ships.tsv` and prints the summary to the console:

```
Nassau: 4
Port Royal: 3
Tortuga: 3
```

To change the source or destination, change only the stream passed in `Main`:

```java
// Source: file
new StreamShipReader(new FileInputStream("data/malformed.tsv"), new TsvShipDeserializer());
// Source: URL
new StreamShipReader(URI.create("https://raw.githubusercontent.com/Quesnay84/kata2/master/src/main/resources/ships.tsv").toURL().openStream(), new TsvShipDeserializer());
// Destination: file
new OutputStreamWriter(new FileOutputStream("summary.txt"), new TextSummarySerializer());
```

## Dependencies and JDK version

- JDK 26 (`Reader.readAllLines()` is used)
- Maven
- No external libraries

## Project structure

```
src/main/java/software/ulpgc/katas/
 Main.java                      → composition root: chooses source and destination and runs the flow
 model/
  Ship.java                     → record: name, port, cannons
  ShipSummary.java              → ships per port, sorted (TreeMap) and immutable
 io/
  ShipLoadException.java        → clear error for missing resources or invalid rows
  ports/
   ShipReader.java              → List<Ship> load()
   ShipDeserializer.java        → line → Ship
   ShipWriter.java              → write(ShipSummary)
   SummarySerializer.java       → ShipSummary → String
  adapters/
   StreamShipReader.java        → InputStream → UTF-8 text → lines → ships (skips the header)
   TsvShipDeserializer.java     → splits by tab, validates 3 columns and numeric cannons
   TextSummarySerializer.java   → "Port: count" per line
   OutputStreamWriter.java      → writes the serialized summary to any OutputStream
src/main/resources/ships.tsv    → valid dataset (10 ships)
data/malformed.tsv              → dataset with an invalid row, used to test errors
```

### Design decisions

- **The model knows nothing about I/O.** `ShipSummary` only receives a `List<Ship>`; it never opens files or URLs.
- **Ports and adapters.** Interfaces describe what is done, and adapters describe how. Each extension point maps to one class:
    - a new source needs only another `InputStream`;
    - a new input format needs another `ShipDeserializer`;
    - a new output format (for example JSON) needs another `SummarySerializer`;
    - a new destination needs only another `OutputStream`.
- **The format belongs to the input adapter.** The separator (`\t`), the header and row validation all live in `TsvShipDeserializer` and `StreamShipReader`.
- **Errors are clear, and null is never returned.** Invalid data throws `ShipLoadException` with the line number. It is caught once, in `Main`.
- **Deterministic output.** A `TreeMap` keeps the summary the same even if the rows of the file are reordered.

## Repetitions

The kata was done three times, keeping the same structure (loader, entity, transformation, writer) and changing the source and destination:

| Try | Source → Destination | Main change |
|-----|----------------------|-------------|
| TRY1 | File → console | First version with loops; interfaces and `StreamShipReader` |
| TRY2 | URL → file | Rewritten with `java.util.stream`; serializer/deserializer classes and custom exception |
| TRY3 | Resource → console | Ports and adapters packages; reader and writer depend on serializer/deserializer interfaces |

Work was done on `develop`, with commits prefixed `TRY1`, `TRY2`, and `TRY3`, and then merged into `master`. Full detail is in `git log`.

## Evidence

| Case | Result |
|------|--------|
| Valid resource / file / URL | `Nassau: 4`, `Port Royal: 3`, `Tortuga: 3` |
| Output redirected to a file | Same summary in `summary.txt`; nothing printed to console |
| Malformed row (`data/malformed.tsv`) | `Line 2 expected 3 columns but got: 2` |
| Non-numeric cannons | `Line N Cannon X is not a number` |
| Missing resource | `Resource not found: /ships.tsv` |
| Missing file | `data/x.tsv (The system cannot find the file specified)` |
| Rows reordered | Same summary (sorted by port) |

## How to clone and verify it builds outside the original folder

```bash
git clone https://github.com/Quesnay84/kata2.git kata2-verificacion
```

(or from IntelliJ: `File → New → Project from Version Control`)

## Explanatory video

[Watch the video](https://youtu.be/v512viWOc9Y)

The video shows:
- which `InputStream` goes in and which `OutputStream` receives the result;
- the dataset and how each row becomes a `Ship`;
- the same logic working with two sources;
- output redirected without changing the transformation;
- a controlled error;
- which class to change to add another source or destination.