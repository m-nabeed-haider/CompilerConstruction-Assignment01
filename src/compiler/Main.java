package compiler;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
    	String input = "var x = 42;\n" +
                "var var1 = 10;\n" +
                "var abc123 = 3.14;\n" +
                "var X = 5; // Invalid identifier (uppercase)\n" +
                "var 1var = 7; // Invalid identifier (starts with a digit)\n" +
                "var var@ = 9; // Invalid identifier (contains invalid character)\n";

        ErrorHandler errorHandler = new ErrorHandler();
        Lexer lexer = new Lexer(input, errorHandler);
        List<Token> tokens = lexer.tokenize();

        SymbolTable symbolTable = new SymbolTable();
        symbolTable.addEntry("x", "int", false);
        symbolTable.addEntry("var1", "decimal", false);
        symbolTable.addEntry("abc123", "int", false);  // Invalid identifier
        symbolTable.addEntry("X", "int", false);
        symbolTable.addEntry("1var", "int", false);
        symbolTable.addEntry("var@", "int", false);

        // Report additional errors
        for (Token token : tokens) {
            if (token.type.equals("IDENTIFIER")) {
            	  if (!token.value.matches("[a-z][a-z0-9]*")) {
                      errorHandler.reportError(token.line, "Invalid identifier '" + token.value + "' (must start with a lowercase letter and can only contain lowercase letters and digits)");
                  }
            } else if (token.type.equals("SYMBOL")) {
                if (!token.value.matches("[+\\-*/%=;]")) {
                    errorHandler.reportError(token.line, "Invalid symbol '" + token.value + "'");
                }
            }
        }

        String outputFile = "output.txt";
        writeOutputToFile(outputFile, tokens, symbolTable, errorHandler);
        displayFileContents(outputFile);
    }

    private static void writeOutputToFile(String fileName, List<Token> tokens, SymbolTable symbolTable, ErrorHandler errorHandler) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Tokens:\n");
            for (Token token : tokens) {
                writer.write(token + "\n");
            }

            writer.write("\nSymbol Table:\n");
            writer.write(symbolTable.lookup("x") + "\n");
            writer.write(symbolTable.lookup("var1") + "\n");
            writer.write(symbolTable.lookup("abc123") + "\n");
            writer.write(symbolTable.lookup("w") + "\n");
            writer.write(symbolTable.lookup("X") + "\n");
            writer.write(symbolTable.lookup("1var") + "\n");
            writer.write(symbolTable.lookup("var@") + "\n");
            writer.write("\nErrors:\n");
            for (String error : errorHandler.getErrors()) {
                writer.write(error + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void displayFileContents(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println("\nContents of " + fileName + ":");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}