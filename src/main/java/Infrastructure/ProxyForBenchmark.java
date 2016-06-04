package Infrastructure;

import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyForBenchmark {
    private Object bean;

    public ProxyForBenchmark(Object bean) {
        this.bean = bean;
    }

    public Object createProxy() {
        Method[] methods = bean.getClass().getMethods();
        boolean hasAnyMethodBenchmarkAnnotation = false;
        for (Method method : methods) {
            if (method.isAnnotationPresent(Benchmark.class)) {
                hasAnyMethodBenchmarkAnnotation = true;
                break;
            }
        }
        if (!hasAnyMethodBenchmarkAnnotation) {
            return bean;
        }
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                ClassUtils.getAllInterfaces(bean), (proxy, method, args) -> {
                    Benchmark annotation = bean.getClass()
                            .getMethod(method.getName(),
                            method.getParameterTypes())
                            .getAnnotation(Benchmark.class);
                    Object response;
                    if (annotation != null && annotation.active()) {
                        long start = System.nanoTime();
                        try {
                            response = method.invoke(bean, args);
                        } catch (InvocationTargetException e) {
                            throw e.getTargetException();
                        }
                        long finish = System.nanoTime();
                        System.out.println("Method: " + method.getName());
                        System.out.println("Time: " + (finish - start));
                    } else {
                        try {
                            response = method.invoke(bean, args);
                        }catch (InvocationTargetException e) {
                            throw e.getTargetException();
                        }
                    }
                    return response;
                });
    }
}
