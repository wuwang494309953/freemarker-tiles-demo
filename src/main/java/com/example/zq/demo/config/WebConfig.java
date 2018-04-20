package com.example.zq.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.core.StandardWrapperFacade;
import org.apache.tiles.request.freemarker.servlet.SharedVariableLoaderFreemarkerServlet;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * @author tangcheng
 * 2018/04/04
 */
@Configuration
@Slf4j
public class WebConfig {

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("home");
//    }

    /*@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }*/

    /**
     * TilesConfigure会加载Tile定义并与Apache Tiles协作
     * 负责定位和加载Tile定义并协调生成Tiles
     *
     * @return
     */
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[]{"WEB-INF/layout/tiles.xml"});//指定Tiles定义的位置
        tilesConfigurer.setCheckRefresh(true);//启用刷新功能
        return tilesConfigurer;
    }


    /**
     * 将逻辑视图名解析为Tile定义
     * TileViewResolver会将逻辑视图名称解析为引用Tile定义的视图
     * 它是通过查找与逻辑视图名称相匹配的Tile定义来实现的
     *
     * @return
     */
    @Bean
    public TilesViewResolver tilesViewResolver() {
        TilesViewResolver viewResolver = new TilesViewResolver();
        viewResolver.setViewClass(TilesView.class);
        viewResolver.setOrder(2);
        return viewResolver;
    }

    @Bean
    public ServletRegistrationBean freemarkerServlet() {
        SharedVariableLoaderFreemarkerServlet servlet = new SharedVariableLoaderFreemarkerServlet();
        StandardWrapper wrapper = new StandardWrapper();
        wrapper.addInitParameter("org.apache.tiles.request.freemarker.CUSTOM_SHARED_VARIABLE_FACTORIES",
                "tiles,org.apache.tiles.freemarker.TilesSharedVariableFactory");
        try {
            servlet.init(new StandardWrapperFacade(wrapper));
        } catch (ServletException e) {
            log.error("freemarker初始化失败", e.getMessage());
        }
        return new ServletRegistrationBean(servlet, "*.ftl");
    }

}
