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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class ProtoTypeAutowiredFail {

    @Configuration
    public static class ConfigA {
        static int id = 0;

        @Bean
        @Scope("prototype")
        public int aPrototype() {
            return id++;
        }
    }

    @Configuration
    public static class ConfigB implements ApplicationContextAware {
        private ApplicationContext applicationContext;

        // fail: on runtime
        @Bean
        @Autowired
        @Qualifier("aPrototype")
        @Scope("prototype")
        public int bPrototype(int input) {
            return input + 10;
        }

        @Bean
        @Scope("prototype")
        public int c() {
            return applicationContext.getBean("aPrototype", int.class) + 100;
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigA.class,
                ConfigB.class);
        for (int i = 0; i < 3; i++) {
            // fail
            // Integer bean = applicationContext.getBean("bPrototype",
            // int.class);
            // System.out.println("bean = " + bean);
            Integer bean2 = applicationContext.getBean("c", int.class);
            System.out.println("bean2 = " + bean2);
        }
    }
}
