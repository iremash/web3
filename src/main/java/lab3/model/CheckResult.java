package lab3.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Одна строка таблицы результатов.
 * Класс с геттерами, а не record: EL в JSP (Jakarta EE 10) читает свойства
 * только через getX()/isHit(), метод x() у record он не увидит.
 * Serializable — объект лежит в HTTP-сессии, а сессию сервер может сохранить на диск.
 */
public class CheckResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private final double x;
    private final double y;
    private final double r;
    private final boolean hit;
    private final long time;   // System.currentTimeMillis(): мс от 1970-01-01 UTC
    private final long exec;   // время работы в микросекундах

    public CheckResult(Point point, boolean hit, long time, long exec) {
        this.x = point.x();
        this.y = point.y();
        this.r = point.r();
        this.hit = hit;
        this.time = time;
        this.exec = exec;
    }

    // Для data-атрибутов и вывода: "1" вместо "1.0", без экспоненты
    public String getX() {
        return format(x);
    }

    public String getY() {
        return format(y);
    }

    public String getR() {
        return format(r);
    }

    public boolean isHit() {
        return hit;
    }

    public long getTime() {
        return time;
    }

    public long getExec() {
        return exec;
    }

    private static String format(double value) {
        return BigDecimal.valueOf(value).stripTrailingZeros().toPlainString();
    }
}
