/* Skeleton code copyright (C) 2008, 2022 Paul N. Hilfinger and the
 * Regents of the University of California.  Do not distribute this or any
 * derivative work without permission. */

package ataxx;




import java.util.ArrayList;
import java.util.Random;

import static ataxx.PieceColor.*;
import static java.lang.Math.min;
import static java.lang.Math.max;

/** A Player that computes its own moves.
 *  @author Smit Malde
 */
class AI extends Player {


    /** Maximum minimax search depth before going to static evaluation. */
    private static final int MAX_DEPTH = 4;
    /** A position magnitude indicating a win (for red if positive, blue
     *  if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 20;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new AI for GAME that will play MYCOLOR. SEED is used to initialize
     *  a random-number generator for use in move computations.  Identical
     *  seeds produce identical behaviour. */
    AI(Game game, PieceColor myColor, long seed) {
        super(game, myColor);
        _random = new Random(seed);
    }

    @Override
    boolean isAuto() {
        return true;
    }

    @Override
    String getMove() {
        if (!getBoard().canMove(myColor())) {
            game().reportMove(Move.pass(), myColor());
            return "-";
        }
        Main.startTiming();
        Move move = findMove();
        Main.endTiming();
        game().reportMove(move, myColor());
        return move.toString();
    }

    /** Return a move for me from the current position, assuming there
     *  is a move. */
    private Move findMove() {
        Board b = new Board(getBoard());
        _lastFoundMove = null;
        if (myColor() == RED) {
            minMax(b, MAX_DEPTH, true, 1, -INFTY, INFTY);
        } else {
            minMax(b, MAX_DEPTH, true, -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /** The move found by the last call to the findMove method
     *  above. */
    private Move _lastFoundMove;

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _foundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _foundMove. If the game is over
     *  on BOARD, does not set _foundMove. */
    private int minMax(Board board, int depth, boolean saveMove, int sense,
                       int alpha, int beta) {
        /* We use WINNING_VALUE + depth as the winning value so as to favor
         * wins that happen sooner rather than later (depth is larger the
         * fewer moves have been made. */
        if (depth == 0 || board.getWinner() != null) {
            return staticScore(board, WINNING_VALUE + depth);
        }
        Move best;
        best = null;
        int bestScore = (sense == 1) ? -INFTY : INFTY;
        int eval;
        ArrayList<Move> possibleMoves = possibleMoves(board);
        ArrayList<Integer> random = new ArrayList<>();
        if (sense == 1) {
            for (int i = 0; i < possibleMoves.size(); i++) {
                int rand = _random.nextInt(0, possibleMoves.size());
                if (!random.contains(rand)) {
                    random.add(rand);
                } else {
                    while (!random.contains(rand)) {
                        rand = _random.nextInt(0, possibleMoves.size());
                    }
                    random.add(rand);
                }
                Board b = new Board(board);
                b.makeMove(possibleMoves.get(rand));
                eval = minMax(b, depth - 1, saveMove, sense * -1, alpha, beta);
                bestScore = max(bestScore, eval);
                if (bestScore == eval) {
                    best = possibleMoves.get(rand);
                }
                alpha = max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
        } else {
            for (int i = 0; i < possibleMoves.size(); i++) {
                int rand = _random.nextInt(0, possibleMoves.size());
                if (!random.contains(rand)) {
                    random.add(rand);
                } else {
                    while (!random.contains(rand)) {
                        rand = _random.nextInt(0, possibleMoves.size());
                    }
                    random.add(rand);
                }
                Board b = new Board(board);
                b.makeMove(possibleMoves.get(rand));
                eval = minMax(b, depth - 1, saveMove, sense * -1, alpha, beta);
                bestScore = min(bestScore, eval);
                if (bestScore == eval) {
                    best = possibleMoves.get(rand);
                }
                beta = min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
        }
        if (saveMove) {
            _lastFoundMove = best;
        }
        return bestScore;
    }

    static ArrayList<Move> possibleMoves(Board board) {
        PieceColor[] board1D = board.get1DBoard();
        ArrayList<Move> movesFromPoint = new ArrayList<>();
        for (int i = 2; i < 9; i++) {
            for (int j = 2; j < 9; j++) {
                int index = (11 * i) + j;
                if (board1D[index] == board.whoseMove()) {
                    for (int c = -2; c < 3; c++) {
                        for (int r = -2; r < 3; r++) {
                            if (board1D[Board.neighbor(index, c, r)] == EMPTY) {
                                movesFromPoint.add(convertMove(index,
                                        Board.neighbor(index, c, r)));
                            }
                        }
                    }
                }
            }
        }
        return movesFromPoint;
    }

    int pieces_with_border(int border, PieceColor piececolor, Board board) {
        PieceColor[] board1D = board.get1DBoard();
        int Pieces_with_border = 0;
        for (int i = 2; i < 9; i++) {
            for (int j = 2; j < 9; j++) {
                int index = (11 * i) + j;
                if (board1D[index] == piececolor) {
                    for (int c = -1 * (border); c < border + 1; c++) {
                        for (int r = -1 * (border); r < border + 1; r++) {
                            if (board1D[Board.neighbor(index, c, r)] == piececolor) {
                                Pieces_with_border ++;
                            }
                        }
                    }
                }
            }
        }
        return Pieces_with_border;
    }

    private static Move convertMove(int from, int to) {
        char startColumn = (char) ((int) 'a' - 2 + (from % 11));
        char startRow = (char) ((int) '1' - 2 + (from / 11));
        char toColumn = (char) ((int) 'a' - 2 + (to % 11));
        char toRow = (char) ((int) '1' - 2 + (to / 11));
        return Move.move(startColumn, startRow, toColumn, toRow);
    }

    /** Return a heuristic value for BOARD.  This value is +- WINNINGVALUE in
     *  won positions, and 0 for ties. */
    private int staticScore(Board board, int winningValue) {
        PieceColor winner = board.getWinner();
        if (winner != null) {
            return switch (winner) {
                case RED -> winningValue;
                case BLUE -> -winningValue;
                default -> 0;
            };
        }
        return board.redPieces() + ((pieces_with_border(1, RED, board) + pieces_with_border(2, RED, board)))
                - (board.bluePieces() + ((pieces_with_border(1, BLUE, board) + pieces_with_border(2, BLUE, board))));
    }

    /** Pseudo-random number generator for move computation. */
    private Random _random = new Random();
}