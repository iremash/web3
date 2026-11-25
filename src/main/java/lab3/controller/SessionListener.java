package lab3.controller;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lab3.model.ResultsBean;

/**
 * Кладёт пустой ResultsBean в каждую новую сессию.
 * Так бин создаётся ровно один раз и в одном месте, без гонки
 * «два потока одновременно увидели null и создали по бину».
 */
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setAttribute(ResultsBean.ATTRIBUTE, new ResultsBean());
    }
}
