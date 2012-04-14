package com.hystericalporpoises.dns;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

public class TestServlets {

  private static final Logger LOGGER = Logger.getLogger(TestServlets.class);

  private TestServlets() {}

  public static final int PORT1 = 33333;

  public static final int PORT2 = 33334;

  public static void createServlets() {
    try {
      TestServlet.create(PORT1);
      TestServlet.create(PORT2);
    }
    catch (Exception e) {
      LOGGER.fatal("The test servlets didn't start; unit tests will fail", e);
    }
  }

  private static class TestServlet extends HttpServlet {

    private Server server;

    public static TestServlet create(int port) throws Exception {
      return new TestServlet(port);
    }

    private TestServlet(int port) throws Exception {
      server = new Server();
      Connector con = new SelectChannelConnector();
      con.setPort(port);
      server.addConnector(con);
      Context context = new Context(server, "/");
      context.addServlet(new ServletHolder(this), "/*");
      server.start();
      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          try {
            server.stop();
          }
          catch (Exception e) {
            LOGGER.error("Could not shut down servlet", e);
          }
        }
      });
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException,
      IOException {
      long now = System.currentTimeMillis();
      response.setDateHeader("Expires", now);
      response.setDateHeader("Date", now);
      response.setHeader("Pragma", "no-cache");
      response.setStatus(200);
      response.getWriter().print("ok");
    }
  }

}
