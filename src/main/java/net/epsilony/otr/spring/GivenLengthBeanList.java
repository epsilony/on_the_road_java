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
package net.epsilony.otr.spring;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class GivenLengthBeanList {

    @Configuration
    public static class ConfigurationClass {
        public static int id = 0;

        @Bean
        @Scope("prototype")
        public String entry() {
            id++;
            return "entry[" + id + "]";
        }

        @Resource(name = "length")
        int length;

        @Bean
        public ArrayList<String> entryList() {
            ArrayList<String> result = new ArrayList<>(length);
            int size = length;
            for (int i = 0; i < size; i++) {
                result.add(entry());
            }
            return result;
        }
    }

    public static GenericBeanDefinition lengthDefinition(int length) {
        GenericBeanDefinition def = new GenericBeanDefinition();
        def.setBeanClass(Integer.class);
        ConstructorArgumentValues values = new ConstructorArgumentValues();
        values.addGenericArgumentValue(length);
        def.setConstructorArgumentValues(values);
        return def;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        int runtimeLength = 10;
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ConfigurationClass.class);
        context.registerBeanDefinition("length", lengthDefinition(runtimeLength));
        context.refresh();
        ArrayList<String> entryList = (ArrayList<String>) context.getBean("entryList");
        System.out.println("entryList =" + entryList);
        context.close();
    }
}
