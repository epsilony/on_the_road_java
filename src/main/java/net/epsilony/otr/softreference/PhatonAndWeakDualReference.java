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
package net.epsilony.otr.softreference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class PhatonAndWeakDualReference {

    public static class Mock {

    }

    public static void main(String[] args) {
        WeakReference<Mock> weakRef;
        ReferenceQueue<Mock> rq = new ReferenceQueue<>();
        PhantomReference<Mock> phantomReference;

        Mock mock = new Mock();
        weakRef = new WeakReference<PhatonAndWeakDualReference.Mock>(mock);
        phantomReference = new PhantomReference<PhatonAndWeakDualReference.Mock>(mock, rq);
        mock = null;
        System.gc();
        System.out.println(Arrays.asList(weakRef.get()));
        Reference<? extends Mock> poll = rq.poll();
        System.out.println("poll==phantomReference = " + (poll == phantomReference));

    }
}
