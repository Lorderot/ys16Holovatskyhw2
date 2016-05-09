package Domain;

public class Pizza {
    public enum PizzaType {
        MEAT, CHEESE, VEGETABLE, EXOTIC, TOMATO, PINEAPPLE, SEA;
    }
    private Integer id;
    private String name;
    private Double price;
    private PizzaType type;

    public Pizza() {
    }

    public Pizza(Pizza pizza) {
        this(pizza.id, pizza.name, pizza.price, pizza.type);
    }

    public Pizza(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Pizza(int id, String Name, Double price, PizzaType type) {
        this(id, Name);
        this.price = price;
        this.type = type;
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

    @Override
    public String toString() {
        return "Pizza{" + "id=" + id + ", name=" + name + ", type=" + type + '}';
    }
}
