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

import java.lang.ref.WeakReference;
import java.util.Arrays;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class DualWeakReference {

    public static class Mock {

    }

    public static void main(String[] args) {
        WeakReference<Mock> ref1, ref2;
        Mock mock1 = new Mock();
        Mock mock2 = new Mock();
        ref1 = new WeakReference<>(mock1);
        ref2 = new WeakReference<>(mock2);
        mock1 = null;
        mock2 = null;
        System.gc();
        System.out.println(Arrays.asList(ref1.get(), ref2.get()));
    }
}
