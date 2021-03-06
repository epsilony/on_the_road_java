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

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class MixMetaAndAutoBoxing {
    @Configuration
    public static class Config {
        @Bean
        public int metaValue() {
            return 100;
        }

        @Bean
        public Integer boxingValue() {
            return 1000;
        }
    }

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                Config.class)) {
            Integer metaBoxed = applicationContext.getBean("metaValue", Integer.class);
            int openBox = applicationContext.getBean("boxingValue", int.class);
            System.out.println("metaBoxed = " + metaBoxed);
            System.out.println("openBox = " + openBox);
        }
    }
}
