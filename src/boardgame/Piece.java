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

    protected void setPosition(Position position) {
        this.position = position;
    }

    public abstract boolean[][] possibleMoves();

    // verifica se uma pos especifica é um movimento valido
    public boolean possibleMove(Position position) {
        // chama a matriz do metodo abstrato (pessoa 2 precisa implementar dps)
        boolean[][] mat = possibleMoves();

        // boolean para a linha e coluna especifica
        return mat[position.getRow()][position.getColumn()];
    }

    // se houver ao menos 1 movimento possivel
    public boolean isThereAnyPossibleMove() {
        boolean[][] mat = possibleMoves();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if (mat[i][j]) {
                    return true; // existe
                }
            }
        }
        return false; // nao existe
    }
}
