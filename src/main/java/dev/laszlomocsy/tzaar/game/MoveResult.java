package dev.laszlomocsy.tzaar.game;

public enum MoveResult {
    SUCCESS,
    INVALID_MOVE,
    NO_FIGURE_TO_MOVE,
    NO_FIGURE_TO_CAPTURE_OR_STACK,
    NO_SAME_AXIS,
    NOT_THE_CLOSEST_FIGURE,
    GAME_NOT_IN_PROGRESS
}
