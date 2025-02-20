package compiler;

import java.util.*;

public class SymbolTable {
    private Stack<Map<String, SymbolEntry>> scopes = new Stack<>();

    public SymbolTable() {
        enterScope();
    }

    public void enterScope() {
        scopes.push(new HashMap<>());
    }

    public void exitScope() {
        scopes.pop();
    }

    public void addEntry(String name, String type, boolean isConstant) {
        scopes.peek().put(name, new SymbolEntry(name, type, isConstant, scopes.peek().size()));
    }

    public SymbolEntry lookup(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name)) {
                return scopes.get(i).get(name);
            }
        }
        return null;
    }
}