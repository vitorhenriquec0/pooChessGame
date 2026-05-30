package boardgame;

import chess.Color;
import chess.PieceType;

public abstract class Piece {

    protected Position position;

    private Color color;
    private PieceType type;
    private Board board;

    public Piece(Board board, Color color, PieceType type) {
        this.board = board;
        this.color = color;
        this.type = type;
        this.position = null;
    }

    public Color getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }

    public Board getBoard() {
        return board;
    }

    public Position getPosition() {
        return position;
    }

    // precisa ser public para o Board conseguir chamar
    public void setPosition(Position position) {
        this.position = position;
    }

    // usado pelas classes das peças
    protected boolean isThereOpponentPiece(Position position) {
        Piece p = board.piece(position);

        return p != null &&
               p.getColor() != this.color;
    }

    public abstract boolean[][] possibleMoves();

    public boolean possibleMove(Position position) {

        boolean[][] mat = possibleMoves();

        return mat[position.getRow()]
                  [position.getColumn()];
    }

    public boolean isThereAnyPossibleMove() {

        boolean[][] mat = possibleMoves();

        for (int i = 0; i < mat.length; i++) {

            for (int j = 0; j < mat[i].length; j++) {

                if (mat[i][j]) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String toString() {

        switch (type) {

            case KING:
                return "K";

            case QUEEN:
                return "Q";

            case ROOK:
                return "R";

            case BISHOP:
                return "B";

            case KNIGHT:
                return "N";

            case PAWN:
                return "P";

            default:
                return "?";
        }
    }
}
