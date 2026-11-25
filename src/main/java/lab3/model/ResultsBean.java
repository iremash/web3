package lab3.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaBean со списком результатов. Живёт в HTTP-сессии под именем "results",
 * поэтому у каждого пользователя (браузера) своя история.
 * Требования JavaBean: public-конструктор без аргументов, геттеры, Serializable.
 */
public class ResultsBean implements Serializable {

    public static final String ATTRIBUTE = "results";
    private static final long serialVersionUID = 1L;

    // ArrayList, а не List: поле должно быть Serializable, а интерфейс List этого не гарантирует
    private final ArrayList<CheckResult> results = new ArrayList<>();

    public ResultsBean() {
    }

    // synchronized: один пользователь может слать несколько запросов одновременно
    // (две вкладки), и они обработаются в разных потоках над одним бином.
    public synchronized void add(CheckResult result) {
        results.add(0, result);   // новые сверху
    }

    /** Копия списка: JSP перебирает её спокойно, даже если параллельно добавят новую строку. */
    public synchronized List<CheckResult> getResults() {
        return List.copyOf(results);
    }

    /** Последний результат или null. Нужен JSP, чтобы выбрать R по умолчанию. */
    public synchronized CheckResult getLast() {
        return results.isEmpty() ? null : results.get(0);
    }
}
