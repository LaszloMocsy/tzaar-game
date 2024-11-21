package dev.laszlomocsy.tzaar.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.laszlomocsy.tzaar.logic.figure.Figure;

import java.util.List;

public class GameState {
    @JsonProperty("player1")
    public String player1;
    @JsonProperty("player2")
    public String player2;
    @JsonProperty("figures")
    public List<Figure> figures;

    public GameState() { /* Empty default constructor required for serialization */ }
}
