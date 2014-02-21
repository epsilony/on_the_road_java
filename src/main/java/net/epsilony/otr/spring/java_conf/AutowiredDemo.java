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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class AutowiredDemo {
    @Configuration
    public static class ConfigA {
        @Autowired
        private int a;

        @Autowired
        private int b;

        @Bean
        public String ab() {
            return a + ", " + b;
        }
    }

    public static class ConfigB {
        @Bean
        public int a() {
            return 3;
        }

        @Bean
        public int b() {
            return 4;
        }
    }

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ConfigA.class, ConfigB.class)) {
            String ab = applicationContext.getBean("ab", String.class);
            System.out.println("ab = " + ab);
        }
    }
}
