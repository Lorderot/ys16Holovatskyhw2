package Infrastructure;

public interface ServiceLocator {
    Object lookUp(String beanName) throws IllegalAccessException, InstantiationException;
}
