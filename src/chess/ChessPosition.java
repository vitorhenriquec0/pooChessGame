package chess;

import boardgame.Position;

// Leitura e escrita da notação, aqui está a logica da conversao
public class ChessPosition {
    private char column;
    private int row;

    public ChessPosition(char column, int row) {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new IllegalArgumentException("Erro: valores validos apenas de a1 a h8");
        }
        this.column = column;
        this.row = row;
    }

    // converte a pos do xadrez para pos da matriz
    protected Position toPosition() {
        return new Position(8 - row, column - 'a');
    }

    protected static ChessPosition fromPosition(Position position) {
        return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
    }

    public static ChessPosition fromString(String s) {
        if (s == null || s.length() != 2) {
            throw new IllegalArgumentException("Erro: Posicao valida deve ter 2 caracteres, como 'e2'.");
        }

        char column = Character.toLowerCase(s.charAt(0));
        int row = Character.getNumericValue(s.charAt(1));

        return new ChessPosition(column, row);
    }

    @Override
    public String toString() {
        return "" + column + row;
    }
}
