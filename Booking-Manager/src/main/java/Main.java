import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8081);

    }

    private static ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        // TODO
        return contextHandler;
    }
}
