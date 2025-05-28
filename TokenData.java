public class TokenData {
    TokenKind kind;      // type of the token 
    String content;      // actual text of the token

    // Constructor
    public TokenData(TokenKind kind, String content) {
        this.kind = kind;
        this.content = content;
    }

    // How the token will be printed
    public String toString() {
        if (kind == TokenKind.ID ||
            kind == TokenKind.VAR ||
            kind == TokenKind.INT ||
            kind == TokenKind.STRING ||
            kind == TokenKind.ERROR) {
            return kind + "(" + content + ")";
        } else {
            return kind.toString();
        }
    }
}