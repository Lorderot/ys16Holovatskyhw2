package web;

import Domain.Customer;
import Domain.Order;
import Domain.Pizza;
import Exceptions.NoSuchOrderException;
import Exceptions.NoSuchPizzaException;
import Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "order/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<Order> getOrder(@PathVariable("orderId") int id) {
        try {
            return new ResponseEntity<>(orderService.getOrderById(id),
                    HttpStatus.OK);
        } catch (NoSuchOrderException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "order/{orderId}/item", method = RequestMethod.GET)
    public List<Pizza> getItemsInOrder(@PathVariable("orderId") int id) {
        try {
            return orderService.getOrderById(id).getOrderList();
        } catch (NoSuchOrderException e) {
            return null;
        }
    }

    @RequestMapping(value = "order/{orderId}/item/{pizzaId}",
            method = RequestMethod.PUT)
    public ResponseEntity<Integer> addItemToOrder(@PathVariable("orderId") int orderId,
                                         @PathVariable("pizzaId") int pizzaId) {
        try {
            orderService.addPizzaToOrder(orderId, pizzaId);
        } catch (NoSuchOrderException e) {
            return new ResponseEntity<>(pizzaId, HttpStatus.NOT_FOUND);
        } catch (NoSuchPizzaException e) {
            return new ResponseEntity<>(orderId, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "order/{orderId}/item/{pizzaId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Integer> deleteItemFromOrder(@PathVariable("orderId") int orderId,
                                              @PathVariable("pizzaId") int pizzaId) {
        try {
            orderService.removePizzaFromOrder(orderId, pizzaId);
        } catch (NoSuchOrderException e) {
            return new ResponseEntity<>(orderId, HttpStatus.NOT_FOUND);
        } catch (NoSuchPizzaException e) {
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
        try {
            Order order = orderService.getOrderById(orderId);
            order.setCustomer(customer);
            orderService.updateOrder(order);
        } catch (NoSuchOrderException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "order",
            method = RequestMethod.POST,
            headers = "Content-Type=application/json")
    public ResponseEntity<List<Integer>> addOrder(
            @RequestBody RequestOrderWrapper wrapper) {
        Order order;
        try {
            order = orderService.placeNewOrder(wrapper.getCustomer(), wrapper.getPizzasID());
        } catch (NoSuchPizzaException e) {
            return new ResponseEntity<>(e.getPizzasId(), HttpStatus.NOT_FOUND);
        }
        List<Integer> list = new ArrayList<>();
        list.add(order.getId());
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

}
