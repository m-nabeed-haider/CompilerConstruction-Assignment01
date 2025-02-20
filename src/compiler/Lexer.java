package compiler;

import java.util.*;

public class Lexer {
    private String input;
    private int pos = 0;
    private int line = 1;
    private ErrorHandler errorHandler;

    public Lexer(String input, ErrorHandler errorHandler) {
        this.input = input;
        this.errorHandler = errorHandler;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (pos < input.length()) {
            char current = input.charAt(pos);
            if (Character.isWhitespace(current)) {
                if (current == '\n') line++;
                pos++;
            } else if (current == '/') {
                handleComment(tokens);
            } else if (Character.isLetter(current)) {
                tokens.add(readIdentifier());
            } else if (Character.isDigit(current)) {
                tokens.add(readNumber());
            } else {
                tokens.add(readSymbol());
            }
        }
        return tokens;
    }

    private void handleComment(List<Token> tokens) {
        if (pos + 1 >= input.length()) {
            tokens.add(readSymbol());
            return;
        }

        char next = input.charAt(pos + 1);
        if (next == '/') {
            pos += 2;
            while (pos < input.length() && input.charAt(pos) != '\n') pos++;
        } else if (next == '*') {
            pos += 2;
            int startLine = line;
            boolean commentClosed = false;

            while (pos < input.length()) {
                if (input.charAt(pos) == '\n') line++;
                if (input.charAt(pos) == '*' && pos + 1 < input.length() && input.charAt(pos + 1) == '/') {
                    pos += 2;
                    commentClosed = true;
                    break;
                }
                pos++;
            }

            if (!commentClosed) {
                errorHandler.reportError(startLine, "Unterminated multi-line comment");
            }
        } else {
            tokens.add(readSymbol());
        }
    }

    private Token readIdentifier() {
        StringBuilder sb = new StringBuilder();
        char firstChar = input.charAt(pos);
        if (Character.isLetter(firstChar) && Character.isLowerCase(firstChar)) {
            sb.append(firstChar);
            pos++;
            while (pos < input.length() && (Character.isLetterOrDigit(input.charAt(pos)))) {
                char nextChar = input.charAt(pos);
                if (Character.isLetter(nextChar) && !Character.isLowerCase(nextChar)) {
                    errorHandler.reportError(line, "Invalid identifier: uppercase letters are not allowed");
                    break;
                }
                sb.append(nextChar);
                pos++;
            }
        } else {
            errorHandler.reportError(line, "Invalid identifier: must start with a lowercase letter");
            pos++;
        }
        return new Token("IDENTIFIER", sb.toString(), line);
    }

    private Token readNumber() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.')) {
            sb.append(input.charAt(pos++));
        }
        return new Token("NUMBER", sb.toString(), line);
    }

    private Token readSymbol() {
        char current = input.charAt(pos++);
        return new Token("SYMBOL", String.valueOf(current), line);
    }
}

