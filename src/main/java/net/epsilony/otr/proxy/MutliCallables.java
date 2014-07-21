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
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Man YUAN <epsilonyuan@gmail.com>
 *
 */
public class MutliCallables {

    public static final Logger logger = LoggerFactory.getLogger(MutliCallables.class);

    public static interface InterfaceA {
        public String name();
    }

    public static class Sample implements InterfaceA {
        public String value() {
            return "value";
        }

        @Override
        public String name() {
            return null;
        }
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Sample.class);
        enhancer.setInterfaces(new Class[] { InterfaceA.class });
        enhancer.setCallback(new InterceptorA());
        Object create = enhancer.create();
        InterfaceA asA = (InterfaceA) create;
        asA.name();
        Sample asSample = (Sample) create;
        asSample.value();
    }

    public static class InterceptorA implements MethodInterceptor {
        Set<Method> interfaceMethods = new HashSet<>(Arrays.asList((InterfaceA.class.getMethods())));

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            logger.info("obj interfaces {}", obj.getClass().getInterfaces());
            logger.info("obj super class {}", obj.getClass().getSuperclass());
            logger.info("method declaring class {}", method.getDeclaringClass());
            if (interfaceMethods.contains(method)) {
                logger.info("interface method: {}", method);
            }
            if (Modifier.isAbstract(method.getModifiers())) {
                return null;
            }

            return proxy.invokeSuper(obj, args);
        }
    };
}
