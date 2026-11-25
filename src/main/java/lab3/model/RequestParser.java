package lab3.model;

import jakarta.servlet.http.HttpServletRequest;

import java.util.regex.Pattern;

public final class RequestParser {
    private static final double[] X_VALUES = {-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2};
    private static final double[] R_VALUES = {1, 2, 3, 4, 5};
    private static final double Y_MIN = -5;
    private static final double Y_MAX = 5;
    // Клик по графику даёт любой X, поэтому для него проверяется только интервал
    private static final double CLICK_MIN = -5;
    private static final double CLICK_MAX = 5;
    private static final Pattern NUMBER = Pattern.compile("[-+]?\\d{1,2}(\\.\\d{1,10})?");

    private RequestParser() {
    }

    public static Point parse(HttpServletRequest request) {
        boolean fromClick = "click".equals(request.getParameter("source"));

        double x = readNumber(request, "x");
        double y = readNumber(request, "y");
        double r = readNumber(request, "r");

        if (fromClick) {
            if (!(x > CLICK_MIN && x < CLICK_MAX)) {
                throw new IllegalArgumentException("X точки с графика должен быть в интервале (" + text(CLICK_MIN) + ", " + text(CLICK_MAX) + ")");
            }
        } else if (!contains(X_VALUES, x)) {
            throw new IllegalArgumentException("X вне допустимых значений");
        }
        if (!(y > Y_MIN && y < Y_MAX)) {
            throw new IllegalArgumentException("Y должен быть в интервале (" + text(Y_MIN) + ", " + text(Y_MAX) + ")");
        }
        if (!contains(R_VALUES, r)) {
            throw new IllegalArgumentException("R вне допустимых значений");
        }
        return new Point(x, y, r);
    }

    private static double readNumber(HttpServletRequest request, String name) {
        // getParameter сам разбирает строку запроса и декодирует %XX — parseQuery из лабы 2 больше не нужен
        String raw = request.getParameter(name);
        if (raw == null) {
            throw new IllegalArgumentException("Не передан параметр " + name.toUpperCase());
        }
        String s = raw.trim().replace(',', '.');
        if (!NUMBER.matcher(s).matches()) {
            throw new IllegalArgumentException("Параметр " + name.toUpperCase() + " должен быть числом");
        }
        return Double.parseDouble(s);
    }

    // -5.0 -> "-5" для сообщений
    private static String text(double value) {
        return value == Math.rint(value) ? String.valueOf((long) value) : String.valueOf(value);
    }

    private static boolean contains(double[] values, double value) {
        for (double v : values) {
            if (v == value) return true;
        }
        return false;
    }
}
