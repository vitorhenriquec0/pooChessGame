package boardgame;

public class Board {
    private int rows = 8;
    private int columns = 8;
    private Piece[][] pieces;

    public Board() {
        pieces = new Piece[rows][columns];
    }

    public Piece piece(int row, int column) {
        if (!positionExists(row, column)) {
            throw new IllegalArgumentException("Erro: posicao invalida.");
        }
        return pieces[row][column];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece(Position position) {
        return piece(position.getRow(), position.getColumn());
    }

    public void placePiece(Piece piece, Position position) {
        if (hasPiece(position)) {
            throw new IllegalArgumentException("Erro; Ja existe uma peca na posicao " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.setPosition(position);
    }

    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new IllegalArgumentException("Erro: pos nao existe no tabuleiro");
        }
        if (piece(position) == null) {
            return null;
        }
        Piece aux = piece(position);
        aux.setPosition(null); // desconecta a peça do tabuleiro
        pieces[position.getRow()][position.getColumn()] = null; // limpa a matriz
        return aux;
    }

    private boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean hasPiece(Position position) {
        if (!positionExists(position)) {
            throw new IllegalArgumentException("Erro: pos nao existe.");
        }
        return piece(position) != null;
    }
}