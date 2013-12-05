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
package net.epsilony.otr.benchmark;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class SmallArrayAsMedia {
    public static abstract class AbstractBase {
        static int maxID = 1;

        int id;
        int size;

        public AbstractBase(int size) {
            this.size = size;
            id = maxID++;
        }
    }

    public static class ArrayProducer extends AbstractBase {

        public ArrayProducer(int size) {
            super(size);
        }

        public double[] produce(double input) {
            double[] result = new double[size];
            for (int i = 0; i < size; i++) {
                result[i] = id + input;
            }
            return result;
        }

    }

    public static class ArrayReuser extends AbstractBase {

        public ArrayReuser(int size) {
            super(size);
        }

        public double[] reuse(double input, double[] result) {
            if (null == result) {
                result = new double[size];
            }
            Arrays.fill(result, 0);
            for (int i = 0; i < size; i++) {
                result[i] = id + input;
            }
            return result;
        }
    }

    public static class RunCase {
        int size;
        int testTimes;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTestTimes() {
            return testTimes;
        }

        public void setTestTimes(int testTimes) {
            this.testTimes = testTimes;
        }

        public double[] sumUpProducer() {
            ArrayProducer ap = new ArrayProducer(size);
            double[] result = new double[size];
            for (int i = 0; i < testTimes; i++) {
                for (int j = 0; j < size; j++) {
                    double[] arr = ap.produce(i * j * 100);
                    result[j] += arr[j];
                }
            }
            return result;
        }

        public double[] sumUpReuser() {
            ArrayReuser ar = new ArrayReuser(size);
            double[] result = new double[size];
            double[] buffer = new double[size];
            for (int i = 0; i < testTimes; i++) {
                for (int j = 0; j < size; j++) {
                    double[] arr = ar.reuse(i * j * 100, buffer);
                    result[j] += arr[j];
                }
            }
            return result;
        }
    }

    public static void main(String[] args) {
        RunCase runCase = new RunCase();
        double[] pRes = null;
        double[] rRes = null;
        for (int size : new int[] { 1, 2, 3, 5, 10, 20, 40, 80, 100 }) {
            for (int testTime : new int[] { 100000, 100, 1000, 10000, 100000, 1000000 }) {
                runCase.setTestTimes(testTime);
                runCase.setSize(size);
                System.out.println("testTime = " + testTime + ", size =" + size);
                long pTime = -System.nanoTime();
                pRes = runCase.sumUpProducer();
                pTime += System.nanoTime();

                long rTime = -System.nanoTime();
                rRes = runCase.sumUpReuser();
                rTime += System.nanoTime();

                System.out.println("pTime " + pTime + ", pRes=" + Arrays.toString(pRes));
                System.out.println("rTime " + rTime + ", rRes=" + Arrays.toString(rRes));
                long diff = TimeUnit.NANOSECONDS.toMillis(pTime - rTime);
                System.out.println("diff = " + diff + " millis");
            }
        }
    }
}
