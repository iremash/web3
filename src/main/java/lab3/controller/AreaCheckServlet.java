package lab3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab3.model.AreaChecker;
import lab3.model.CheckResult;
import lab3.model.Point;
import lab3.model.RequestParser;
import lab3.model.ResultsBean;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Проверяет точку, кладёт результат в бин из сессии и отдаёт страницу результата.
 * В web.xml у сервлета нет url-pattern: из браузера он недоступен, только через ControllerServlet.
 * Журнал пишется через java.util.logging — WildFly сам выводит его в standalone/log/server.log
 * (поэтому AppLog из лабы 2 больше не нужен).
 */
public class AreaCheckServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(AreaCheckServlet.class.getName());
    private static final String RESULT_PAGE = "/WEB-INF/jsp/result.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long start = System.nanoTime();
        LOG.info("запрос: " + request.getQueryString());

        try {
            Point p = RequestParser.parse(request);
            boolean hit = AreaChecker.isHit(p);
            long execMicros = (System.nanoTime() - start) / 1000;
            CheckResult result = new CheckResult(p, hit, System.currentTimeMillis(), execMicros);

            ResultsBean bean = (ResultsBean) request.getSession().getAttribute(ResultsBean.ATTRIBUTE);
            bean.add(result);
            request.setAttribute("result", result);
            LOG.info("ответ: hit=" + hit + ", " + execMicros + " мкс");
        } catch (IllegalArgumentException e) {
            LOG.warning("отклонён: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("error", e.getMessage());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "сбой", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute("error", "Внутренняя ошибка сервера");
        }

        request.getRequestDispatcher(RESULT_PAGE).forward(request, response);
    }
}
