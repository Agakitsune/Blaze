package src.blaze.command.input;

import java.util.ArrayList;

public class Input {

    private final ArrayList<Token> tokens = new ArrayList<>();

    private Input() {

    }

    public static Input parse(String str) {
        Input input = new Input();
        Character string_quote;
        String tmp;
        int checkpoint = 0;
        int i = 0;

        while (str.charAt(i) == ' ')
            i++;

        checkpoint = i;

        while (i < str.length()) {

            if ((i < str.length()) && (str.charAt(i) == '"' || str.charAt(i) == '\'')) {

                string_quote = str.charAt(i);
                i++;

                while ((i < str.length()) && (str.charAt(i) != string_quote))
                    i++;

                if (i >= str.length())
                    throw new SyntaxException("Unexpected End of Input: String at " + checkpoint + " isn't closed, expected '" + string_quote + "'");

                input.tokens.add(new Token("String", str.substring(checkpoint, i + 1), checkpoint));
                i++;

            } else {

                while ((i < str.length()) && (str.charAt(i) != ' '))
                    i++;

                tmp = str.substring(checkpoint, i);

                try {

                    Double.valueOf(tmp);
                    input.tokens.add(new Token("Number", tmp, checkpoint));

                } catch (NumberFormatException e) {

                    input.tokens.add(new Token("Identifier", tmp, checkpoint));

                }
            }

            while ((i < str.length()) && (str.charAt(i) == ' '))
                i++;

            checkpoint = i;
        }
        return input;
    }

    public Token getToken(int index) {
        return tokens.get(index);
    }

    public int getTokensNumber() {
        return tokens.size();
    }
}
