package compiler;

public class SymbolEntry {
    public String name;
    public String type;
    public boolean isConstant;
    public int memoryAddress;

    public SymbolEntry(String name, String type, boolean isConstant, int memoryAddress) {
        this.name = name;
        this.type = type;
        this.isConstant = isConstant;
        this.memoryAddress = memoryAddress;
    }

    @Override
    public String toString() {
        return String.format("Symbol(%s, %s, %s, %d)", name, type, isConstant ? "const" : "var", memoryAddress);
    }
}