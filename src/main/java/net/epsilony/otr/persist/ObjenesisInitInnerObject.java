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
package net.epsilony.otr.persist;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.objenesis.instantiator.ObjectInstantiator;

/**
 * @author Man YUAN <epsilonyuan@gmail.com>
 *
 */
public class ObjenesisInitInnerObject {
    public static String staticfield;
    public String        good = "good";

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public class T1 {

        public final String bad;

        private T1() {
            bad = "bad";
        }

        public String getGood() {
            return good;
        }
    }

    enum E {
        Good, Bad;
    }

    public static void main(String[] args) {
        Objenesis objenesis = new ObjenesisStd();
        ObjectInstantiator<T1> initer = objenesis.getInstantiatorOf(T1.class);
        T1 newInstance = initer.newInstance();
        System.out.println("newInstance = " + newInstance);
        System.out.println("newInstance.bad = " + newInstance.bad);

        Field field = FieldUtils.getField(T1.class, "bad");
        field.setAccessible(true);
        try {
            field.set(newInstance, "good");
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("newInstance.bad = " + newInstance.bad);

        // System.out.println(newInstance.getGood());

        ObjectInstantiator<E> einiter = objenesis.getInstantiatorOf(E.class);
        E newInstance2 = einiter.newInstance();
        System.out.println("E = " + newInstance2);

        Field[] allFields = FieldUtils.getAllFields(ObjenesisInitInnerObject.class);
        System.out.println(Arrays.asList(allFields));
    }
}
