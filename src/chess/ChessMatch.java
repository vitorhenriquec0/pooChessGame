package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch { // config da partida

    private int turn;
    private Color currentPlayer;
    private Board board;
    private ChessPiece vulnerableEnPassantPawn;

    private boolean isXeque;
    private boolean isXequeMate;

    public boolean getCheck() {
        return isXeque;
    }

    // construtor
    public ChessMatch() {

        board = new Board(8, 8); //tabuleiro
        turn = 1; //primeira jogada
        currentPlayer = Color.WHITE; //brancas começam

        initialSetup(); //tabuleiro inicial
    }

    private void initialSetup() {

        // peças brancas
        placeNewPiece('e', 1,
            new King(board, Color.WHITE, this));

        placeNewPiece('a', 1,
            new Rook(board, Color.WHITE));

        placeNewPiece('h', 1,
            new Rook(board, Color.WHITE));

        // peças pretas
        placeNewPiece('e', 8,
            new King(board, Color.BLACK, this));

        placeNewPiece('a', 8,
            new Rook(board, Color.BLACK));

        placeNewPiece('h', 8,
            new Rook(board, Color.BLACK));
    }

    // colocar peça
    private void placeNewPiece(
        char column,
        int row,
        ChessPiece piece
    ) {

        board.placePiece(
            piece,
            new ChessPosition(column, row).toPosition()
        );
    }

    // executar movimento de captura e move
    private Piece makeMove(
        Position source,
        Position target
    ) {

        ChessPiece p =
            (ChessPiece) board.removePiece(source);

        p.increaseMoveCount();

        Piece capturedPiece =
            board.removePiece(target);

        board.placePiece(p, target);

        // en passant
        if (p instanceof Pawn) {

            if (source.getColumn() != target.getColumn() && capturedPiece == null) {

                int direction = (p.getColor() == Color.WHITE) ? 1 : -1;

                Position pawnPosition = new Position(
                    target.getRow() + direction,
                    target.getColumn()
                );

                capturedPiece = board.removePiece(pawnPosition);
            }

            if (Math.abs(target.getRow() - source.getRow()) == 2) {
                vulnerableEnPassantPawn = p;
            } else {
                vulnerableEnPassantPawn = null;
            }

        } else {
            vulnerableEnPassantPawn = null;
        }

        // roque pequeno
        if (
            p instanceof King &&
            target.getColumn() == source.getColumn() + 2
        ) {

            Position rookSource =
                new Position(source.getRow(), source.getColumn() + 3);

            Position rookTarget =
                new Position(source.getRow(), source.getColumn() + 1);

            ChessPiece rook =
                (ChessPiece) board.removePiece(rookSource);

            if (rook.getMoveCount() == 0) {
                board.placePiece(rook, rookTarget);
                rook.increaseMoveCount();
            } else {
                board.placePiece(rook, rookSource);
            }
        }

        // roque grande
        if (
            p instanceof King &&
            target.getColumn() == source.getColumn() - 2
        ) {

            Position rookSource =
                new Position(source.getRow(), source.getColumn() - 4);

            Position rookTarget =
                new Position(source.getRow(), source.getColumn() - 1);

            ChessPiece rook =
                (ChessPiece) board.removePiece(rookSource);

            if (rook.getMoveCount() == 0) {
                board.placePiece(rook, rookTarget);
                rook.increaseMoveCount();
            } else {
                board.placePiece(rook, rookSource);
            }
        }

        return capturedPiece;
    }

    // desfazer movimento
    private void undoMove(
        Position source,
        Position target,
        Piece capturedPiece
    ) {

        ChessPiece p =
            (ChessPiece) board.removePiece(target);

        p.decreaseMoveCount();

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
        }

        board.placePiece(p, source);

        // desfazer roque pequeno
        if (
            p instanceof King &&
            target.getColumn() == source.getColumn() + 2
        ) {

            Position sourceT =
                new Position(source.getRow(), source.getColumn() + 3);

            Position targetT =
                new Position(source.getRow(), source.getColumn() + 1);

            ChessPiece rook =
                (ChessPiece) board.removePiece(targetT);

            board.placePiece(rook, sourceT);

            rook.decreaseMoveCount();
        }

        // desfazer roque grande
        if (
            p instanceof King &&
            target.getColumn() == source.getColumn() - 2
        ) {

            Position sourceT =
                new Position(source.getRow(), source.getColumn() - 4);

            Position targetT =
                new Position(source.getRow(), source.getColumn() - 1);

            ChessPiece rook =
                (ChessPiece) board.removePiece(targetT);

            board.placePiece(rook, sourceT);

            rook.decreaseMoveCount();
        }
    }

    private ChessPiece king(Color color) {

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {

                Piece p = board.piece(i, j);

                if (p instanceof King) {

                    ChessPiece cp = (ChessPiece) p;

                    if (cp.getColor() == color) {
                        return cp;
                    }
                }
            }
        }

        return null;
    }

    private boolean testCheck(Color color) {

        Position kingPosition =
            king(color).getChessPosition().toPosition();

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {

                Piece p = board.piece(i, j);

                if (p != null) {

                    ChessPiece cp = (ChessPiece) p;

                    if (cp.getColor() != color) {

                        boolean[][] mat = cp.possibleMoves();

                        if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean testCheckMate(Color color) {

        if (!testCheck(color)) {
            return false;
        }

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {

                Piece p = board.piece(i, j);

                if (p != null) {

                    ChessPiece cp = (ChessPiece) p;

                    if (cp.getColor() == color) {

                        boolean[][] mat = cp.possibleMoves();

                        for (int row = 0; row < board.getRows(); row++) {
                            for (int col = 0; col < board.getColumns(); col++) {

                                if (mat[row][col]) {

                                    Position source = new Position(i, j);
                                    Position target = new Position(row, col);

                                    Piece capturedPiece =
                                        makeMove(source, target);

                                    boolean testCheck = testCheck(color);

                                    undoMove(source, target, capturedPiece);

                                    if (!testCheck) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public Piece performChessMove(
        ChessPosition sourcePosition,
        ChessPosition targetPosition
    ) {

        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        Piece capturedPiece = makeMove(source, target);

        // promoção
        ChessPiece movedPiece =
            (ChessPiece) board.piece(target);

        if (movedPiece instanceof Pawn) {

            if (
                (movedPiece.getColor() == Color.WHITE && target.getRow() == 0)
                ||
                (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)
            ) {

                System.out.println("Promoção!");

                board.removePiece(target);

                ChessPiece queen =
                    new Queen(board, movedPiece.getColor());

                board.placePiece(queen, target);
            }
        }

        if (testCheck(currentPlayer)) {

            undoMove(source, target, capturedPiece);

            System.out.println("Você não pode se colocar em xeque");

            return null;
        }

        isXeque = testCheck(opponent(currentPlayer));

        if (testCheckMate(opponent(currentPlayer))) {
            isXequeMate = true;
        } else {
            nextTurn();
        }

        return capturedPiece;
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void nextTurn() {
        turn++;
        currentPlayer = opponent(currentPlayer);
    }

    private boolean testCheckPosition(Color color, Position pos) {

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {

                Piece p = board.piece(i, j);

                if (p != null) {

                    ChessPiece cp = (ChessPiece) p;

                    if (cp.getColor() != color) {

                        boolean[][] mat = cp.possibleMoves();

                        if (mat[pos.getRow()][pos.getColumn()]) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}