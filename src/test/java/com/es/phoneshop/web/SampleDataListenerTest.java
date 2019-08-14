package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SampleDataListenerTest {
    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    ServletContext servletContext;
    @Mock
    private Product product;
    @Mock
    private ProductDao productDao;

    @InjectMocks
    private SampleDataListener sampleDataListener;

    @Before
    public void setup() {
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
    }

    @Test
    public void testGetSampleProductsFalse() {
        when(servletContextEvent.getServletContext().getInitParameter("insertSampleData")).thenReturn("false");
        sampleDataListener.contextInitialized(servletContextEvent);

        verify(productDao, never()).save(product);
    }
}
