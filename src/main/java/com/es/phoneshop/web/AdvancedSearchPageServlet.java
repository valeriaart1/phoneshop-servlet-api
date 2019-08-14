package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.model.validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class AdvancedSearchPageServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", productService.findProducts(null));
        request.getRequestDispatcher("/WEB-INF/pages/searchingPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = request.getParameter("description");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String minStock = request.getParameter("minStock");
        String maxStock = request.getParameter("maxStock");

        Map<String, String> errors = Validator.validateSearchingFields(minPrice, maxPrice, minStock, maxStock);
        if (!errors.values().isEmpty()) {
            Set keys = errors.keySet();
            for (Object key : keys) {
                request.setAttribute((String) key, errors.get(key));
            }
            request.setAttribute("products", productService.findProducts(null));
            request.getRequestDispatcher("/WEB-INF/pages/searchingPage.jsp")
                    .forward(request, response);
        } else {
            request.setAttribute("products",
                    productService.findProductsAdvancedSearch(description, minPrice, maxPrice, minStock, maxStock));
            request.getRequestDispatcher("/WEB-INF/pages/searchingPage.jsp").forward(request, response);
        }
    }
}
