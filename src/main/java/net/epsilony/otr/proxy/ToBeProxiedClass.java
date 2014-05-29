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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Man YUAN <epsilonyuan@gmail.com>
 *
 */
public class ToBeProxiedClass implements ToBeProxied {

    @Override
    public void a() {
        System.out.println("method a");
    }

    @Override
    public void b() {
        System.out.println("method b");
    }

    public static void main(String[] args) {
        MyInvocationHandler handler = new MyInvocationHandler(new ToBeProxiedClass());
        ToBeProxied foo = (ToBeProxied) Proxy.newProxyInstance(ToBeProxiedClass.class.getClassLoader(),
                new Class[] { ToBeProxied.class }, handler);
        foo.callAB();

    }

    public static class MyInvocationHandler implements InvocationHandler {

        ToBeProxied toBeProxied;

        public MyInvocationHandler(ToBeProxied toBeProxied) {
            this.toBeProxied = toBeProxied;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("proxied");
            return method.invoke(toBeProxied, args);
        }

    }

}
