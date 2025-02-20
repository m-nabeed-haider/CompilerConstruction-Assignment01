package compiler;

import java.util.*;

public class State {
    public int id;
    public Map<Character, Set<State>> transitions;

    public State(int id) {
        this.id = id;
        this.transitions = new HashMap<>();
    }

    public void addTransition(char input, State nextState) {
        transitions.computeIfAbsent(input, k -> new HashSet<>()).add(nextState);
    }

    public Set<State> getNextStates(char input) {
        return transitions.getOrDefault(input, new HashSet<>());
    }
}
