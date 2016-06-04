package Exceptions;

import java.util.*;

public class NoSuchOrderException extends Exception {
    private List<Integer> ordersId = new ArrayList<>();

    public NoSuchOrderException(Integer ... orderId) {
        super("No such orders were found: " + Arrays.toString(orderId));
        Collections.addAll(ordersId, orderId);
    }

    public NoSuchOrderException(List<Integer> orderId) {
        super("No such orders were found: " + orderId);
        ordersId = orderId;
    }

    public List<Integer> getOrdersId() {
        return ordersId;
    }
}
