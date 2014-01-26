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
package net.epsilony.otr.pattern.convertor;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class MultiLevelIterator<T> implements Iterator<T> {
    ArrayList<Iterator<?>> iterators;
    int numLevels;

    public MultiLevelIterator(Iterable<?> multiLevelIterable, int numLevels) {
        this.numLevels = numLevels;
        iterators = new ArrayList<>(numLevels);
        iterators.add(multiLevelIterable.iterator());
        ensureIterators(0);
    }

    private void ensureIterators(int fromLevel) {
        int currentLevel = fromLevel;
        while (currentLevel >= 0 && currentLevel < numLevels) {
            Iterator<?> iterator = iterators.get(currentLevel);
            if (iterator.hasNext()) {
                currentLevel++;
                if (currentLevel >= numLevels) {
                    break;
                }
                iterator = ((Iterable<?>) iterator.next()).iterator();
                if (currentLevel >= iterators.size()) {
                    iterators.add(iterator);
                } else {
                    iterators.set(currentLevel, iterator);
                }
            } else {
                currentLevel--;
            }
        }
        if (currentLevel < 0) {
            iterators = null;
        }
    }

    @Override
    public boolean hasNext() {
        return iterators != null;
    }

    @Override
    public T next() {
        @SuppressWarnings("unchecked")
        T result = (T) iterators.get(numLevels - 1).next();
        ensureIterators(numLevels - 1);
        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
