package chess.pieces;

import boardgame.*;
import chess.*;

public class Pawn extends Piece {

    public Pawn(Board board, Color color) {
        super(board, color, PieceType.PAWN);
    }

    @Override
    public boolean[][] possibleMoves() {

        boolean[][] mat =
                new boolean[getBoard().getRows()][getBoard().getColumns()];

        if (getColor() == Color.WHITE) {

            Position p =
                    new Position(
                            position.getRow()-1,
                            position.getColumn());

            if (getBoard().positionExists(p)
                    && !getBoard().hasPiece(p)) {

                mat[p.getRow()][p.getColumn()] = true;
            }

            if (position.getRow() == 6) {

                Position p1 =
                        new Position(
                                position.getRow()-1,
                                position.getColumn());

                Position p2 =
                        new Position(
                                position.getRow()-2,
                                position.getColumn());

                if (getBoard().positionExists(p2)
                        && !getBoard().hasPiece(p1)
                        && !getBoard().hasPiece(p2)) {

                    mat[p2.getRow()][p2.getColumn()] = true;
                }
            }

            Position left =
                    new Position(
                            position.getRow()-1,
                            position.getColumn()-1);

            Position right =
                    new Position(
                            position.getRow()-1,
                            position.getColumn()+1);

            if (getBoard().positionExists(left)
                    && isThereOpponentPiece(left)) {

                mat[left.getRow()][left.getColumn()] = true;
            }

            if (getBoard().positionExists(right)
                    && isThereOpponentPiece(right)) {

                mat[right.getRow()][right.getColumn()] = true;
            }
        }

        else {

            Position p =
                    new Position(
                            position.getRow()+1,
                            position.getColumn());

            if (getBoard().positionExists(p)
                    && !getBoard().hasPiece(p)) {

                mat[p.getRow()][p.getColumn()] = true;
            }

            if (position.getRow() == 1) {

                Position p1 =
                        new Position(
                                position.getRow()+1,
                                position.getColumn());

                Position p2 =
                        new Position(
                                position.getRow()+2,
                                position.getColumn());

                if (getBoard().positionExists(p2)
                        && !getBoard().hasPiece(p1)
                        && !getBoard().hasPiece(p2)) {

                    mat[p2.getRow()][p2.getColumn()] = true;
                }
            }

            Position left =
                    new Position(
                            position.getRow()+1,
                            position.getColumn()-1);

            Position right =
                    new Position(
                            position.getRow()+1,
                            position.getColumn()+1);

            if (getBoard().positionExists(left)
                    && isThereOpponentPiece(left)) {

                mat[left.getRow()][left.getColumn()] = true;
            }

            if (getBoard().positionExists(right)
                    && isThereOpponentPiece(right)) {

                mat[right.getRow()][right.getColumn()] = true;
            }
        }

        return mat;
    }
}
