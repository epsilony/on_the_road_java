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
package net.epsilony.otr.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class HashMapRemove {
    Map<Integer, Integer> map = new HashMap<>();

    HashMapRemove() {
        for (int i = 0; i < 100; i++) {
            map.put(i, i * 100);
        }
    }

    public void removeOver10() {
        Set<Integer> keySet = map.keySet();
        for (Integer key : keySet) {
            if (key > 10) {
                keySet.remove(key);
            }
        }
    }

    public static void main(String[] args) {
        new HashMapRemove().removeOver10();
    }
}
