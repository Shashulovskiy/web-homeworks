package ru.itmo.wp.web.page;

import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.function.UnaryOperator;

public class TicTacToePage {
    private void action(Map<String, Object> view, HttpServletRequest request) {
        if (request.getSession().getAttribute("state") == null) {
            newGame(view, request);
        }
        view.put("state", request.getSession().getAttribute("state"));
    }

    private void newGame(Map<String, Object> view, HttpServletRequest request) {
        State gameState = new State();
        view.put("state", gameState);
        request.getSession().setAttribute("state", gameState);
        throw new RedirectException(request.getRequestURI());
    }

    private void onMove(Map<String, Object> view, HttpServletRequest request) {
        State gameState = (State) request.getSession().getAttribute("state");
        if (gameState.phase == State.Phase.RUNNING) {
            int x = -1, y = -1;
            for (String parameterKey : request.getParameterMap().keySet()) {
                if (parameterKey.startsWith("cell")) {
                    y = parameterKey.charAt(parameterKey.length() - 1) - '0';
                    x = parameterKey.charAt(parameterKey.length() - 2) - '0';
                }
            }
            gameState.makeMove(x, y);
            request.getSession().setAttribute("state", gameState);
        }
        view.put("state", gameState);
        throw new RedirectException(request.getRequestURI());
    }

    public static class State {
        private final int size = 3;
        private int movesCount = 0;

        private Phase phase;
        private boolean crossesMove;
        private final Cell[][] cells;

        public State() {
            this.phase = Phase.RUNNING;
            this.crossesMove = true;
            this.cells = new Cell[size][size];
        }

        private Cell getLastMove() {
            return crossesMove ? Cell.X : Cell.O;
        }

        public void makeMove(int x, int y) {
            movesCount++;
            if (cells[x][y] == null) {
                cells[x][y] = getLastMove();
                gameStateCheck();
                crossesMove = !crossesMove;
            }
        }

        private void gameStateCheck() {
            Cell lastMove = getLastMove();
            UnaryOperator<Integer> identity = UnaryOperator.identity();
            UnaryOperator<Integer> inc = x -> x + 1;
            UnaryOperator<Integer> dec = x -> x - 1;

            boolean won = false;

            // Check rows
            for (int i = 0; i < size; ++i) {
                won |= checkLine(i, 0, identity, inc);
            }

            // Check columns
            for (int i = 0; i < size; ++i) {
                won |= checkLine(0, i, inc, identity);
            }

            // Check main diagonal
            won |= checkLine(0, 0, inc, inc);

            // Check secondary diagonal
            won |= checkLine(size - 1, 0, dec, inc);

            if (won) {
                phase = crossesMove ? Phase.WON_X : Phase.WON_O;
            } else if (movesCount == size * size) {
                phase = Phase.DRAW;
            }
        }

        private boolean checkLine(int x, int y, UnaryOperator<Integer> deltaX, UnaryOperator<Integer> deltaY) {
            for (int i = 0; i < size; ++i) {
                if (cells[x][y] != getLastMove()) {
                    return false;
                }
                x = deltaX.apply(x);
                y = deltaY.apply(y);
            }
            return true;
        }

        public Phase getPhase() {
            return phase;
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }

        public int getSize() {
            return size;
        }

        public Cell[][] getCells() {
            return cells;
        }

        private enum Phase {
            RUNNING, WON_X, WON_O, DRAW
        }

        private enum Cell {
            X, O
        }
    }
}
