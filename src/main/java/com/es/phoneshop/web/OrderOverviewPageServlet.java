package com.es.phoneshop.web;

import com.es.phoneshop.model.order.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init() {
        orderDao = OrderDaoImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        int idIndex = uri.lastIndexOf("/");
        String id = uri.substring(idIndex + 1);
        Order order = orderDao.getBySecureId(id);
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("order", order);
        request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request, response);
    }
}
