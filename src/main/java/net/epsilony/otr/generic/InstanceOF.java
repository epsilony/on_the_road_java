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
package net.epsilony.otr.generic;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class InstanceOF {
    interface B {
    };

    public static class A<T> {
        T a;

        public A(T a) {
            this.a = a;
        }

        @Override
        public String toString() {
            return "A [a=" + a + "] is instance of B:" + (a instanceof B);
        }
    }

    public static void main(String[] args) {
        A<B> a1 = new A(new B() {
        });
        System.out.println("a1 = " + a1);
        Class<B> a1class = (Class<B>) a1.getClass();
        System.out.println("alclass = " + a1class);
    }
}
