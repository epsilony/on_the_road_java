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
package net.epsilony.otr.concurrency;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class ReentrantLockBenchmark {

    public static class Data {
        public static int maxId = 1;
        int id;

        public Data() {
            id = maxId++;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class DataWithLock {
        public static int maxId = 1;
        int id;
        ReentrantLock lock = new ReentrantLock();

        public DataWithLock() {
            id = maxId++;
        }

        public ReentrantLock getLock() {
            return lock;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static void main(String[] args) {
        int warmCycles = 100000;
        int benchNum = 1000000;
        ArrayList<Data> datas = new ArrayList<>(benchNum);
        ArrayList<DataWithLock> datasWithLock = new ArrayList<>(benchNum);
        for (int i = 0; i < benchNum; i++) {
            datas.add(new Data());
            datasWithLock.add(new DataWithLock());
        }
        for (int i = 0; i < warmCycles; i++) {
            Data data = datas.get(i);
            data.setId(data.getId() - 10);
        }
        long dataStart = System.nanoTime();
        for (int i = 0; i < benchNum; i++) {
            Data data = datas.get(i);
            data.setId(data.getId() - 10);
        }
        long dataEnd = System.nanoTime();
        long gap = dataEnd - dataStart;
        System.out.println("dataEnd-dataStart = " + TimeUnit.NANOSECONDS.toMillis(gap) + "ms");

        for (int i = 0; i < warmCycles; i++) {
            DataWithLock data = datasWithLock.get(i);
            ReentrantLock lock = data.getLock();
            try {
                lock.lock();
                data.setId(data.getId() - 10);
            } finally {
                lock.unlock();
            }
        }
        long dataStartWL = System.nanoTime();
        for (int i = 0; i < benchNum; i++) {
            DataWithLock data = datasWithLock.get(i);
            ReentrantLock lock = data.getLock();
            try {
                lock.lock();
                data.setId(data.getId() - 10);
            } finally {
                lock.unlock();
            }
        }
        long dataEndWL = System.nanoTime();
        long gapWL = dataEndWL - dataStartWL;
        System.out.println("dataEndWL-dataStartWL = " + TimeUnit.NANOSECONDS.toMillis(gapWL) + "ms");
        double ratio = gapWL * 1.0 / gap;
        System.out.println("ratio = " + ratio);
    }
}
