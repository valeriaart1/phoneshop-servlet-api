package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ProductDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {

    private ProductDaoImpl productDao = ProductDaoImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        request.setAttribute("products", productDao.sortByParameter(productDao.findProducts(query), sort, order));
        /*if(sort != null && order != null) {
            String sortOrder = sort + " " + order;
            request.setAttribute("products", productDao.sortByParameter(productDao.findProducts(query), sortOrder));
        }
        else {
            request.setAttribute("products", productDao.findProducts(query));
        }*/
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
