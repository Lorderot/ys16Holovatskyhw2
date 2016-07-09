package Domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
public class Pizza {
    public enum PizzaType {
        MEAT, CHEESE, VEGETABLE, EXOTIC, TOMATO, PINEAPPLE, SEA;
    }
    public enum PizzaSize {
        S, L, XL, XXL;
    }
    private Integer id;
    private String name;
    private Double price;
    private PizzaSize size;
    private PizzaType type;
    private boolean available;
    private String description;

    public Pizza() {
    }

    public Pizza(String name, Double price, PizzaSize size, PizzaType type,
                 String description, boolean available) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.type = type;
        this.available = available;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PizzaType getType() {
        return type;
    }

    public void setType(PizzaType type) {
        this.type = type;
    }

    public PizzaSize getSize() {
        return size;
    }

    public void setSize(PizzaSize size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Pizza{name=" + name + ", type=" + type
                + ", size=" + size + ", price=" + price + "}";
    }
}
