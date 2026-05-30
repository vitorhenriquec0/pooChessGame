package chess.pieces;

import boardgame.*;
import chess.*;

public class Queen extends Piece {

    public Queen(Board board, Color color) {
        super(board, color, PieceType.QUEEN);
    }

    @Override
    public boolean[][] possibleMoves() {

        boolean[][] mat =
                new boolean[getBoard().getRows()][getBoard().getColumns()];

        int[][] directions = {
                {-1,0},
                {1,0},
                {0,-1},
                {0,1},
                {-1,-1},
                {-1,1},
                {1,-1},
                {1,1}
        };

        for (int[] d : directions) {

            Position p =
                    new Position(
                            position.getRow()+d[0],
                            position.getColumn()+d[1]);

            while (getBoard().positionExists(p)
                    && !getBoard().hasPiece(p)) {

                mat[p.getRow()][p.getColumn()] = true;

                p = new Position(
                        p.getRow()+d[0],
                        p.getColumn()+d[1]);
            }

            if (getBoard().positionExists(p)
                    && isThereOpponentPiece(p)) {

                mat[p.getRow()][p.getColumn()] = true;
            }
        }

        return mat;
    }
}
