package chess.pieces;

import boardgame.*;
import chess.*;

public class King extends Piece {

    public King(Board board, Color color) {
        super(board, color, PieceType.KING);
    }

    @Override
    public boolean[][] possibleMoves() {

        boolean[][] mat =
                new boolean[getBoard().getRows()][getBoard().getColumns()];

        int[][] moves = {
                {-1,-1},
                {-1,0},
                {-1,1},
                {0,-1},
                {0,1},
                {1,-1},
                {1,0},
                {1,1}
        };

        for (int[] m : moves) {

            Position p =
                    new Position(
                            position.getRow()+m[0],
                            position.getColumn()+m[1]);

            if (getBoard().positionExists(p)) {

                if (!getBoard().hasPiece(p)
                        || isThereOpponentPiece(p)) {

                    mat[p.getRow()][p.getColumn()] = true;
                }
            }
        }

        return mat;
    }
}
