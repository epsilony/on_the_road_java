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

import java.util.Iterator;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class CascadeConvertorIterator<IN, OUT> implements StreamIterator<IN, OUT> {

    @SuppressWarnings("rawtypes")
    private StreamIterator headIterator, firstIterator;

    public void push(Convertor<?, ?> convertor) {
        ConvertorMapType annotation = convertor.getClass().getAnnotation(ConvertorMapType.class);
        if (null == annotation) {
            throw new IllegalArgumentException();
        }
        push(convertor, annotation.value());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void push(Convertor<?, ?> convertor, MapType mapType) {
        StreamIterator newIterator;
        switch (mapType) {
        case ONE_TO_MANY:
            OneManyConvertorIterator oneManyIterator = new OneManyConvertorIterator<>();
            oneManyIterator.setConvertor(convertor);
            newIterator = oneManyIterator;
            break;
        case ONE_TO_ONE:
            OneOneConvertedIterator oneOneIterator = new OneOneConvertedIterator<>();
            oneOneIterator.setConvertor(convertor);
            newIterator = oneOneIterator;
            break;
        default:
            throw new IllegalArgumentException();
        }
        newIterator.setUpstream(headIterator);
        headIterator = newIterator;
        if (null == firstIterator) {
            firstIterator = headIterator;
        }
    }

    @Override
    public boolean hasNext() {
        return headIterator.hasNext();
    }

    @SuppressWarnings("unchecked")
    @Override
    public OUT next() {
        return (OUT) headIterator.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setUpstream(Iterator<? extends IN> upstream) {
        firstIterator.setUpstream(upstream);
    }
}
