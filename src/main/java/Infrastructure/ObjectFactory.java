package Infrastructure;

public class ObjectFactory implements ServiceLocator {
    private Config config;

    public ObjectFactory(Config config) {
        this.config = config;
    }

    @Override
    public Object lookUp(String beanName) throws IllegalAccessException, InstantiationException{
        Class<?> clazz = config.getImplementation(beanName);
        if (clazz != null) {
            return clazz.newInstance();
        }
        return null;
    }
}
