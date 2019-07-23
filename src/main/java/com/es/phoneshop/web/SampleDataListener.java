package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SampleDataListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String insertSampleData = servletContextEvent.getServletContext().getInitParameter("insertSampleData");
        if("true".equals(insertSampleData)) {
            ArrayListProductDao.getInstance();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
