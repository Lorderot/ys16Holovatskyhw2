package Infrastructure;

public interface ApplicationContext {
    Object getBean(String beanName) throws Exception;
}
