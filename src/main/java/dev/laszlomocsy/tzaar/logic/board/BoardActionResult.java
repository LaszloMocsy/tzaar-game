package dev.laszlomocsy.tzaar.logic.board;

public enum BoardActionResult {
    SUCCESS, // The action was successful
    BOARD_STATUS_INVALID, // The board status is invalid for the action
    FIGURE_NULL, // The figure to place is null
    FIGURE_LOCATION_OCCUPIED, // The location to place the figure is already occupied
    LOCATIONS_ARE_EQUAL, // The locations to move the figure are equal
    LOCATIONS_HAS_NO_SAME_AXIS, // The locations to move the figure are not on the same axis
    LOCATION_A_EMPTY, // No figure at location A
    LOCATION_B_EMPTY, // No figure at location B
    COLOR_INVALID, // The color of the figure is invalid
    INVALID_MOVE // The move is invalid
}
