package com.bigrhyme.jetty;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages={"com.bigrhyme.jetty"})
@PropertySource("classpath:app.properties")
@Controller
public class App 
{
    private static final Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        new App().startJetty(8080);
    }

    private void startJetty(int port) throws Exception {
        log.debug("Starting server at port " + port);
        Server server = new Server(port);
        server.setHandler(getServletContextHandler());
        server.start();
        log.info("Server started at port " + port);
        server.join();
    }

    private static ServletContextHandler getServletContextHandler() throws IOException {
    	AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(App.class);
  
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(ctx)), "/");
        contextHandler.addEventListener(new ContextLoaderListener(ctx));
        return contextHandler;
    }

    @RequestMapping(value = "/home", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String home(HttpServletRequest request,
			HttpServletResponse response) {
    	return "hello " + request.getRemoteAddr();
    }
}
