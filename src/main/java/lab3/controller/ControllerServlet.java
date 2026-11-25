package lab3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Единая точка входа (Front Controller). Сам ничего не считает и не рисует,
 * только решает, кому отдать запрос:
 *   есть x, y или r  -> AreaCheckServlet
 *   нет ни одного    -> страница с формой index.jsp
 * doPost не переопределён: по варианту только GET, на POST сервер сам ответит 405.
 */
public class ControllerServlet extends HttpServlet {

    private static final String FORM_PAGE = "/WEB-INF/jsp/index.jsp";
    private static final String AREA_CHECK = "AreaCheckServlet";   // имя из web.xml, не URL

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (hasPointData(request)) {
            // Именованный диспетчер: AreaCheckServlet не имеет URL, попасть в него можно только отсюда.
            getServletContext().getNamedDispatcher(AREA_CHECK).forward(request, response);
        } else {
            request.getRequestDispatcher(FORM_PAGE).forward(request, response);
        }
    }

    // Хотя бы один параметр: тогда неполный запрос получит понятную ошибку от AreaCheckServlet,
    // а не молча покажет пустую форму.
    private static boolean hasPointData(HttpServletRequest request) {
        return request.getParameter("x") != null
                || request.getParameter("y") != null
                || request.getParameter("r") != null;
    }
}
