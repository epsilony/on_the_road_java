/*
 * Copyright (C) 2013 Man YUAN <epsilon@epsilony.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.epsilony.otr.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author Man YUAN <epsilonyuan@gmail.com>
 *
 */
public class MethodDeclaringClass {
    public interface IA {
        default String methodA() {
            return null;
        }

        default String methodB() {
            return null;
        }

        default void methodC() {

        }

        default void methodE(A a) {

        }
    }

    public static class A implements IA {

        public String methodD() {
            return null;
        }

        @Override
        public String methodA() {
            return "inA";
        }

        @Override
        public String methodB() {
            return "inA";
        }
    }

    public static class B extends A implements IA {
        @Override
        public String methodA() {
            return "inB";
        }

        void methodE(B obj) {

        }
    }

    public static class MethodInterceptorImp implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("method: " + method);
            return proxy.invokeSuper(obj, args);
        }

    }

    public static void print(Class<?> cls) {
        for (Method m : cls.getMethods()) {
            System.out.println(m);
        }
    }

    public static void main(String[] args) {

        System.out.println("B class methods");
        print(B.class);
        System.out.println("--------------");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(B.class);
        enhancer.setCallback(new MethodInterceptorImp());
        Object b = enhancer.create();
        System.out.println("b enhanced class methods");
        print(b.getClass());
        System.out.println("-----------------");

        enhancer.setInterfaces(new Class[] { IA.class });
        enhancer = new Enhancer();
        enhancer.setSuperclass(B.class);
        enhancer.setCallback(new MethodInterceptorImp());
        Object bWithInterface = enhancer.create();
        System.out.println("b enhanced with interface class methods");
        print(bWithInterface.getClass());

    }
}
