package Infrastructure;

import Repository.InMemOrderRepository;
import Repository.InMemPizzaRepository;
import Service.SimpleOrderService;

import java.util.HashMap;
import java.util.Map;

public class JavaConfig implements Config {
    private Map<String, Class<?>> beans = new HashMap<>();
    {
        beans.put("orderRepository", InMemOrderRepository.class);
        beans.put("pizzaRepository", InMemPizzaRepository.class);
        beans.put("orderService", SimpleOrderService.class);
    }

    @Override
    public Class<?> getImplementation(String beanName) {
        return beans.get(beanName);
    }
}
