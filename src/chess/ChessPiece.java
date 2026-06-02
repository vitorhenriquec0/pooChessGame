package chess;

import boardgame.Board;
import boardgame.Piece;

public abstract class ChessPiece extends Piece {

    private int moveCount;

    public ChessPiece(Board board, Color color, PieceType type) {
        super(board, color, type);
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition
            .fromPosition(position);
    }
}