package web.Rest;

import Domain.Customer;
import Domain.Order;
import Domain.Pizza;
import Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/*")
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "order/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<Order> getOrder(@PathVariable("orderId") int id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderService.getOrderById(id),
                HttpStatus.OK);
    }

    @RequestMapping(value = "order/{orderId}/item", method = RequestMethod.GET)
    public List<Pizza> getItemsInOrder(@PathVariable("orderId") int id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return null;
        }
        return orderService.getOrderById(id).getOrderList();
    }

    @RequestMapping(value = "order/{orderId}/item/{pizzaId}",
            method = RequestMethod.PUT)
    public ResponseEntity<Integer> addItemToOrder(@PathVariable("orderId") int orderId,
                                         @PathVariable("pizzaId") int pizzaId) {
        Order order = orderService.addPizzaToOrder(orderId, pizzaId);
        if (order == null) {
            return new ResponseEntity<>(orderId, HttpStatus.NOT_FOUND);
        }
        boolean isAdded = false;
        for (Pizza pizza : order.getOrderList()) {
            if (pizza.getId() == pizzaId) {
                isAdded = true;
            }
        }
        if (!isAdded) {
            return new ResponseEntity<>(pizzaId, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "order/{orderId}/item/{pizzaId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Integer> deleteItemFromOrder(@PathVariable("orderId") int orderId,
                                              @PathVariable("pizzaId") int pizzaId) {
        Order order = orderService.removePizzaFromOrder(orderId, pizzaId);
        if (order == null) {
            return new ResponseEntity<>(orderId, HttpStatus.NOT_FOUND);
        }
        boolean isRemoved = true;
        for (Pizza pizza : order.getOrderList()) {
            if (pizza.getId() == pizzaId) {
                isRemoved = false;
            }
        }
        if (!isRemoved) {
            return new ResponseEntity<>(pizzaId, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "order/{orderId}/customer",
            method = RequestMethod.PUT,
            headers = "Content-Type=application/json")
    public ResponseEntity updateCustomerInOrder(
            @PathVariable("orderId") int orderId,
            @RequestBody Customer customer) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        order.setCustomer(customer);
        orderService.updateOrder(order);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "order",
            method = RequestMethod.POST,
            headers = "Content-Type=application/json")
    public ResponseEntity<?> addOrder(
            @RequestBody RequestOrderWrapper wrapper) {
        Integer[] pizzasId = wrapper.getPizzasID();
        Order order = orderService.placeNewOrder(
                wrapper.getCustomer(), pizzasId);
        List<Pizza> pizzas = order.getOrderList();
        if (pizzas.size() != pizzasId.length) {
            List<Integer> notFoundPizzas = new ArrayList<>();
            List<Integer> foundPizzas = new ArrayList<>();
            pizzas.forEach(pizza -> foundPizzas.add(pizza.getId()));
            Collections.sort(foundPizzas);
            for (Integer id : pizzasId) {
                int position = Collections.binarySearch(foundPizzas, id);
                if (position < 0) {
                    notFoundPizzas.add(id);
                }
            }
            return new ResponseEntity<>(notFoundPizzas, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order.getId(),
                HttpStatus.CREATED);
    }

}
