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
package net.epsilony.otr.jdk8.lambda;

import java.lang.invoke.LambdaMetafactory;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author epsilon
 *
 */
public class MethodRef {
    int base;

    public int mult2(int input) {
        return base + input * 2;
    }

    public int mult3(int input) {
        return base + input * 3;
    }

    /**
     * @param base
     */
    public MethodRef(int base) {
        super();
        this.base = base;
    }

    public static void main(String[] args) {
        Function<Integer, Integer> function;
        function = new MethodRef(2)::mult2;
        System.out.println(function.apply(3));
        function = new MethodRef(3)::mult3;
        System.out.println(function.apply(3));
        System.out.println("function = " + function);
        Consumer<Integer> consumer = function.andThen((a) -> {
            System.out.println("a = " + a);
            return null;
        })::apply;
        consumer.accept(10);
        LambdaMetafactory t;
    }

}
