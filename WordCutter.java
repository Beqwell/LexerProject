import java.util.*;
import java.util.regex.*;

public class WordCutter {
    // Token patterns
    private static final Pattern ID = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");
    private static final Pattern VAR = Pattern.compile("[$][a-zA-Z_][a-zA-Z0-9_]*");
    private static final Pattern INT = Pattern.compile("[0-9]+");
    private static final Pattern STR = Pattern.compile("\"(\\\\.|[^\"\\\\])*\"");
    private static final Pattern DOUBLE_OP = Pattern.compile("(==|!=|<=|>=|:=|<>)");
    private static final Pattern SINGLE_OP = Pattern.compile("[=+\\-*/.<>&|]");
    private static final Pattern SYM = Pattern.compile("[(){};:]");
    // private static final Pattern LINE_COMMENT = Pattern.compile("//.*");
    // private static final Pattern BLOCK_COMMENT_START = Pattern.compile("/\\*");
    // private static final Pattern BLOCK_COMMENT_END = Pattern.compile("\\*/");

    // Main lexer function
    public static List<TokenData> cutWords(String code) {
        List<TokenData> out = new ArrayList<>();
        int i = 0;

        while (i < code.length()) {
            // Skip whitespace
            if (Character.isWhitespace(code.charAt(i))) {
                i++;
                continue;
            }

            // Skip line comment
            if (code.startsWith("//", i)) {
                int end = code.indexOf('\n', i);
                if (end == -1) break;
                i = end + 1;
                continue;
            }

            // Skip block comment
            if (code.startsWith("/*", i)) {
                int end = code.indexOf("*/", i + 2);
                if (end == -1) break;
                i = end + 2;
                continue;
            }

            String part = code.substring(i);
            Matcher m;

            // Match string literal
            m = STR.matcher(part);
            if (m.lookingAt()) {
                String raw = m.group();
                String cleaned = raw
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\");
                out.add(new TokenData(TokenKind.STRING, cleaned));
                i += m.end();
                continue;
            }

            // Match PHP variable
            m = VAR.matcher(part);
            if (m.lookingAt()) {
                out.add(new TokenData(TokenKind.VAR, m.group()));
                i += m.end();
                continue;
            }

            // Match keyword or ID
            m = ID.matcher(part);
            if (m.lookingAt()) {
                String word = m.group();
                switch (word) {
                    case "function" -> out.add(new TokenData(TokenKind.FUNCTION, word));
                    case "return" -> out.add(new TokenData(TokenKind.RETURN, word));
                    case "echo" -> out.add(new TokenData(TokenKind.ECHO, word));
                    case "if" -> out.add(new TokenData(TokenKind.IF, word));
                    case "else" -> out.add(new TokenData(TokenKind.ELSE, word));
                    case "while" -> out.add(new TokenData(TokenKind.WHILE, word));
                    case "true" -> out.add(new TokenData(TokenKind.TRUE, word));
                    case "false" -> out.add(new TokenData(TokenKind.FALSE, word));
                    case "let" -> out.add(new TokenData(TokenKind.LET, word));
                    case "in" -> out.add(new TokenData(TokenKind.IN, word));
                    case "end" -> out.add(new TokenData(TokenKind.END, word));
                    case "do" -> out.add(new TokenData(TokenKind.DO, word));
                    case "for" -> out.add(new TokenData(TokenKind.FOR, word));
                    case "to" -> out.add(new TokenData(TokenKind.TO, word));
                    default -> out.add(new TokenData(TokenKind.ID, word));
                }
                i += m.end();
                continue;
            }

            // Match number
            m = INT.matcher(part);
            if (m.lookingAt()) {
                out.add(new TokenData(TokenKind.INT, m.group()));
                i += m.end();
                continue;
            }

            // Match two-char operators
            m = DOUBLE_OP.matcher(part);
            if (m.lookingAt()) {
                String op = m.group();
                switch (op) {
                    case "==" -> out.add(new TokenData(TokenKind.EQ, op));
                    case "!=" -> out.add(new TokenData(TokenKind.NEQ, op));
                    case "<=" -> out.add(new TokenData(TokenKind.LE, op));
                    case ">=" -> out.add(new TokenData(TokenKind.GE, op));
                    case ":=" -> out.add(new TokenData(TokenKind.ASSIGN, op));
                    case "<>" -> out.add(new TokenData(TokenKind.NEQ, op));
                }
                i += m.end();
                continue;
            }

            // Match single-char operators
            m = SINGLE_OP.matcher(part);
            if (m.lookingAt()) {
                String op = m.group();
                switch (op) {
                    case "+" -> out.add(new TokenData(TokenKind.PLUS, op));
                    case "-" -> out.add(new TokenData(TokenKind.MINUS, op));
                    case "*" -> out.add(new TokenData(TokenKind.TIMES, op));
                    case "/" -> out.add(new TokenData(TokenKind.DIVIDE, op));
                    case "." -> out.add(new TokenData(TokenKind.DOT, op));
                    case "=" -> out.add(new TokenData(TokenKind.EQ, op));
                    case "<" -> out.add(new TokenData(TokenKind.LT, op));
                    case ">" -> out.add(new TokenData(TokenKind.GT, op));
                }
                i += m.end();
                continue;
            }

            // Match symbols like ;, (,), etc.
            m = SYM.matcher(part);
            if (m.lookingAt()) {
                String s = m.group();
                switch (s) {
                    case ";" -> out.add(new TokenData(TokenKind.SEMICOLON, s));
                    case ":" -> out.add(new TokenData(TokenKind.COLON, s));
                    case "(" -> out.add(new TokenData(TokenKind.LPAREN, s));
                    case ")" -> out.add(new TokenData(TokenKind.RPAREN, s));
                    case "{" -> out.add(new TokenData(TokenKind.LBRACE, s));
                    case "}" -> out.add(new TokenData(TokenKind.RBRACE, s));
                }
                i += m.end();
                continue;
            }

            // If no match treat as error
            out.add(new TokenData(TokenKind.ERROR, String.valueOf(code.charAt(i))));
            i++;
        }

        return out;
    }
}
