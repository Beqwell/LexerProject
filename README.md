# PHP Lexer in Java

Simple manual lexer for a subset of PHP, implemented in Java using regular expressions and conditionals.

### Features

- Detects keywords, variables, identifiers, strings, numbers, operators
- Skips comments (`//` and `/* ... */`)
- Reports invalid tokens
- Outputs tokens in a format like: `ID(name)`, `INT(5)`, `STRING("text")`, `ERROR(@)`

### Files

- `input.php` — input file with PHP code
- `Start.java` — main file to run
- `WordCutter.java` — lexer logic
- `TokenData.java` / `TokenKind.java` — token structure

### How to compile

```bash
javac *.java
```

### How to run

```bash
java Start
```

Output will show token list based on `input.php`.

No external libraries or tools required.
