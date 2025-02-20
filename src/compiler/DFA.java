package compiler;

import java.util.*;

public class DFA {
    public Set<State> states;
    public State start;

    public DFA(Set<State> states, State start) {
        this.states = states;
        this.start = start;
    }

    public boolean accepts(String input) {
        State current = start;
        for (char c : input.toCharArray()) {
            Set<State> nextStates = current.getNextStates(c);
            if (nextStates.isEmpty()) return false;
            current = nextStates.iterator().next();
        }
        return current.id == 1; // Assuming state 1 is accepting
    }
}
