package proxy;

import annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
конструкторы, public, protected, private.
 */
class ProxyLogger {

    private ProxyLogger() {
    }

    static CalcClass createMyClass() {
        InvocationHandler handler = new CustomInvocationHandler(new CalcClassImpl());
        return (CalcClass) Proxy.newProxyInstance(ProxyLogger.class.getClassLoader(),
                new Class<?>[]{CalcClass.class}, handler);
    }

    static class CustomInvocationHandler implements InvocationHandler {
        public CustomInvocationHandler(CalcClass myClass) {
            this.myClass = myClass;
            this.loggedMethods = getLoggedMethods();
        }

        private List<String> getLoggedMethods() {
            List<String> methods = new ArrayList<>();
            for (Method method : myClass.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(Log.class)) {
                    methods.add(method.getName() + "_" + Arrays.toString(method.getParameterTypes()));
                }
            }
            return methods;
        }
        private final CalcClass myClass;
        private final List<String> loggedMethods;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (loggedMethods.contains(method.getName() + "_" + Arrays.toString(method.getParameterTypes()))) {
                System.out.println("Execute method: " + method.getName() + ", param: " + Arrays.toString(args));
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "CustomInvocationHandler{myClass = " + myClass + "}";
        }
    }
}
