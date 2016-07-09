package web;

import Domain.Order;
import Domain.Pizza;
import Service.OrderService;
import Service.PizzaService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class PizzaServlet extends HttpServlet {
    private ConfigurableApplicationContext repositoryContext;
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        repositoryContext = new ClassPathXmlApplicationContext("repositoryContext.xml");
        applicationContext = new ClassPathXmlApplicationContext(
                        new String[] {"resources/appContext.xml"}, repositoryContext);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
/* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Hello from Pizza Service</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PizzaServlet at " + request.getRequestURI() + "</h1>");
            out.println("<br>");
            out.println("<h1> Params: " + request.getParameter("pizzas") + " </h1>");
            out.println("</body>");
            out.println("</html>");
            out.println("<br>");
            PizzaService pizzaService = applicationContext.getBean(PizzaService.class);
            List<Pizza> pizzas = pizzaService.getAllPizzas();

            for (Pizza pizza : pizzas) {
                out.println(pizza);
                out.println("<br>");
            }

            OrderService orderService = applicationContext.getBean(OrderService.class);

            String pizzasArr = request.getParameter("pizzas");
            if (pizzasArr != null) {
                Integer[] pizzasID = parseStringToArray(request.getParameter("pizzas"));
                Order order = orderService.placeNewOrder(null, pizzasID);
                orderService.placeNewOrder(null, pizzasID);
                out.println("Pizzas in order: " + order.getOrderList());
                out.println("<br>");
                out.println("Total price: "  + order.getTotalPrice());
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    public void destroy() {
        repositoryContext.close();
        applicationContext.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private Integer[] parseStringToArray(String arr) {
        String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
        Integer[] results = new Integer[items.length];
        for (int i = 0; i < items.length; i++) {
            try {
                results[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException e) {}
        }
        return results;
    }
}