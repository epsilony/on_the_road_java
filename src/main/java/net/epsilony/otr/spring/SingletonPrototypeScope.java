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

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class SingletonPrototypeScope {

    public static class Data {
        private static int maxId = 0;
        String name;
        String content = "unmodified";
        int id;

        /**
         * @param name
         */
        public Data(String name) {
            id = maxId++;
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "Data [name=" + name + ", content=" + content + ", id=" + id + "]";
        }
    }

    @Configuration
    public static class SomeConf {
        @Bean
        public Data singletonBean() {
            return new Data("singleton");
        }

        @Bean
        @Scope("prototype")
        public Data prototypeBean() {
            return new Data("prototype");
        }
    }

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SomeConf.class);
        Data singleton = context.getBean("singletonBean", Data.class);
        Data prototype = context.getBean("prototypeBean", Data.class);
        singleton.setContent("modified singleton");
        prototype.setContent("modified prototype");
        Data singleton2 = context.getBean("singletonBean", Data.class);
        Data prototype2 = context.getBean("prototypeBean", Data.class);
        System.out.println("singleton = " + singleton);
        System.out.println("prototype = " + prototype);
        System.out.println("singleton2 = " + singleton2);
        System.out.println("prototype2 = " + prototype2);
        System.out.println("(singleton == singleton2) = " + (singleton == singleton2));

        context.close();
    }
}
