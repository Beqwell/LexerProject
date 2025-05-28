public enum TokenKind {
    // Keywords
    FUNCTION,
    RETURN,
    ECHO,
    IF,
    ELSE,
    WHILE,
    TRUE,
    FALSE,

    // Types
    LET,
    IN,
    END,
    DO,
    FOR,
    TO,

    // Identifiers and literals
    ID,
    VAR,
    INT,
    STRING,

    // Operators
    ASSIGN,     // :=
    EQ,         // ==
    NEQ,        // != or <>
    LT,         // <
    LE,         // <=
    GT,         // >
    GE,         // >=
    PLUS,
    MINUS,
    TIMES,
    DIVIDE,
    DOT,

    // Symbols
    LPAREN,
    RPAREN,
    LBRACE,
    RBRACE,
    SEMICOLON,
    COLON,

    // Others
    ERROR
}
