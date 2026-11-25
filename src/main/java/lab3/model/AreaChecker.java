package lab3.model;

/**
 * Область варианта, границы входят:
 *   II  — прямоугольник: -R <= x <= 0, 0 <= y <= R
 *   III — четверть круга радиуса R/2
 *   IV  — треугольник (0,0), (R,0), (0,-R/2): выше прямой y = x/2 - R/2
 *   I   — пусто
 */
public final class AreaChecker {

    private AreaChecker() {
    }

    public static boolean isHit(Point point) {
        double x = point.x();
        double y = point.y();
        double r = point.r();
        if (x <= 0 && y >= 0) return x >= -r && y <= r;
        if (x <= 0 && y <= 0) return x * x + y * y <= (r / 2) * (r / 2);
        if (x >= 0 && y <= 0) return y >= x / 2 - r / 2;
        return false;
    }
}
