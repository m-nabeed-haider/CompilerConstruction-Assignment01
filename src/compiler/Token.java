package compiler;

public class Token {
    public String type;
    public String value;
    public int line;

    public Token(String type, String value, int line) {
        this.type = type;
        this.value = value;
        this.line = line;
    }

    @Override
    public String toString() {
        return String.format("Token(%s, %s, %d)", type, value, line);
    }
}