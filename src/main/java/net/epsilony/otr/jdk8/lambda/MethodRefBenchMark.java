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

import java.util.function.IntUnaryOperator;

/**
 * @author Man YUAN <epsilonyuan@gmail.com>
 *
 */
public class MethodRefBenchMark {
    public static class Mock {
        int plus2(int input) {
            return input + 2;
        }
    }

    public int classRelative(Mock mock, int times) {
        int result = 0;
        for (int i = 0; i < times; i++) {
            result = mock.plus2(result);
        }
        return result;
    }

    public int lambdaClass(IntUnaryOperator plus2, int times) {
        int result = 0;
        for (int i = 0; i < times; i++) {
            result = plus2.applyAsInt(result);
        }
        return result;
    }

    public int instanceLambdaClass(IntUnaryOperator plus2, int input) {
        return plus2.applyAsInt(input);
    }

    public int lambdaCreateManyTimes(Mock mock, int times) {
        int result = 0;
        for (int i = 0; i < times; i++) {
            result = instanceLambdaClass(mock::plus2, result);
        }
        return result;
    }

    public static void main(String[] args) {
        MethodRefBenchMark benchMark = new MethodRefBenchMark();
        Mock mock = new Mock();
        int warmUp = 100000;
        benchMark.classRelative(mock, warmUp);
        benchMark.lambdaClass(mock::plus2, warmUp);
        benchMark.lambdaCreateManyTimes(mock, warmUp);
        int times = 1000;
        for (int i = 0; i < 10; i++) {
            long time1 = System.nanoTime();
            long result = benchMark.classRelative(mock, times);
            time1 -= System.nanoTime();
            long time2 = System.nanoTime();
            result += benchMark.lambdaClass(mock::plus2, warmUp);
            time2 -= System.nanoTime();
            long time3 = System.nanoTime();
            result += benchMark.lambdaCreateManyTimes(mock, warmUp);
            time3 -= System.nanoTime();
            System.out.println("times = " + times);
            System.out.println("time1 = " + (time1));
            System.out.println("time2 = " + (time2));
            System.out.println("time3 = " + (time3));
            System.out.println("nonsen = " + result);
            System.out.println("");
            times *= 2;
        }

    }
}
