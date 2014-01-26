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

import java.util.LinkedList;
import java.util.List;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class GenericSpecificedMethods {

    static class A {
    };

    static class B extends A {
    };

    // this method argment specification of single arg must be more strick then
    // varargs ones,
    // or the varargs ones will never be called with single arg
    public <T extends List<A>> void method(T input) {
        System.out.println("single arg method");
    }

    public <T extends List<? extends B>> void method(T... input) {
        System.out.println("var args method");
    }

    public static void main(String[] args) {
        List sample = new LinkedList<>();

        GenericSpecificedMethods gsm = new GenericSpecificedMethods();
        gsm.method(sample);
        gsm.method((List<A>) sample);
        gsm.method((List<B>) sample);
    }
}
