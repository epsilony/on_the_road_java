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
package net.epsilony.otr.spring.java_conf;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class GenericTypeAutowiredUnable {

    @Configuration
    public static class ConfigA {
        @Bean
        public List<TypeTwo> a() {
            return Arrays.asList(new TypeTwo());
        }
    }

    @Configuration
    public static class ConfigB implements ApplicationContextAware {
        @Autowired
        // alternative comment below
        List<TypeTwo> a;

        private ApplicationContext applicationContext;

        @Bean
        public String b() {
            // alternative uncomment two lines below:
            // List<? extends TypeOne> a = (List<? extends TypeOne>)
            // applicationContext.getBean("a");
            return a.toString();
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;

        }
    }

    public static class TypeOne {
    };

    public static class TypeTwo extends TypeOne {
    };

    public static void main(String[] args) {
        // fail still at 4.0.1.release
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ConfigB.class);
        applicationContext.register(ConfigA.class);
        applicationContext.refresh();
        System.out.println(applicationContext.getBean("b"));
    }
}
