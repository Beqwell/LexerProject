import java.util.*;
import java.io.*;

public class Start {
    public static void main(String[] args) {
        // Store full content of input file
        StringBuilder text = new StringBuilder();

        try {
            // Read content of input.php line by line
            BufferedReader r = new BufferedReader(new FileReader("input.php"));
            String line;
            while ((line = r.readLine()) != null) {
                text.append(line).append("\n");
            }
            r.close();
        } catch (IOException e) {
            // Handle read error
            System.out.println("Error reading input.php: " + e.getMessage());
            return;
        }

        // Run lexer and get list of tokens
        List<TokenData> tokens = WordCutter.cutWords(text.toString());

        // Print each token to console
        for (TokenData token : tokens) {
            System.out.println(token);
        }
    }
}
