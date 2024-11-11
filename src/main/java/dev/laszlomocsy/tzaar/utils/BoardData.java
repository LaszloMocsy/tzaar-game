package dev.laszlomocsy.tzaar.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.laszlomocsy.tzaar.logic.board.Board;
import dev.laszlomocsy.tzaar.logic.figure.Figure;

import java.util.List;

public class BoardData {
    @JsonProperty("players")
    public BoardDataPlayers players;
    @JsonProperty("figures")
    public List<Figure> figures;
    
    public BoardData() {}
    
    public class BoardDataPlayers {
        public String whitePlayer;
        public String blackPlayer;
        
        public BoardDataPlayers() {}
    }
    
    public static BoardData fromBoard(Board board, String whitePlayer, String blackPlayer) {
        BoardData data = new BoardData();
        data.players = data.new BoardDataPlayers();
        data.players.whitePlayer = whitePlayer;
        data.players.blackPlayer = blackPlayer;
        data.figures = board.getFigures();
        return data;
    }
    
    public static Board toBoard(BoardData data) {
        Board board = Board.InitEmpty();
        for(Figure figure : data.figures) {
            board.placeFigure(figure);
        }
        board.startGame();
        return board;
    }
}
