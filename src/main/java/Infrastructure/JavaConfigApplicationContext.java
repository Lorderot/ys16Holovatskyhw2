package Infrastructure;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class JavaConfigApplicationContext implements ApplicationContext {
    private Config config;
    private Map<String, Object> beans = new HashMap<>();

    public JavaConfigApplicationContext(Config config) {
        this.config = config;
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        Object bean;
        bean = beans.get(beanName);
        if (bean == null) {
            beans.put(beanName, bean);
            BeanBuilder beanBuilder = new BeanBuilder(beanName);
            beanBuilder.createBean();
            beanBuilder.callInitMethod();
            beanBuilder.createProxy();
            bean = beanBuilder.build();
        }
        return bean;
    }

    private class BeanBuilder {
        private Object bean;
        private Class<?> type;

        public BeanBuilder(String beanName) {
            type = config.getImplementation(beanName);
        }

        public void createProxy() {
            bean = new ProxyForBenchmark(bean).createProxy();
        }

        public void callInitMethod() throws Exception {
            try {
                Method init = type.getMethod("init");
                init.invoke(bean);
            } catch (NoSuchMethodException e) {
            }
        }

        public Object build() {
            return bean;
        }


        private void createBean() throws Exception {
            Constructor constructor = type.getConstructors()[0];
            if (constructor.getParameterCount() == 0) {
                bean = type.newInstance();
            } else {
                Object[] params = getParams(constructor);
                bean = constructor.newInstance(params);
            }
        }

        private Object[] getParams(Constructor constructor) throws Exception {
            Object[] params = new Object[constructor.getParameterCount()];
            Class[] paramTypes = constructor.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                params[i] = getBeanByType(paramTypes[i]);
            }
            return params;
        }

        private Object getBeanByType(Class<?> paramType) throws Exception {
            String beanName = getBeanNameByType(paramType);
            return getBean(beanName);
        }

        private String getBeanNameByType(Class<?> beanType) {
            String typeName = beanType.getSimpleName();
            String beanName = convertFirstCharacterToLowerCase(typeName);
            return beanName;
        }

        private String convertFirstCharacterToLowerCase(String typeName) {
            return typeName.substring(0, 1).toLowerCase() + typeName.substring(1);
        }
    }
}
