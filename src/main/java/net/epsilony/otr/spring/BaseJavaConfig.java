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

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

/**
 * @author Man YUAN <epsilon@epsilony.net> the @Code{@Bean} annotated
 *         method will be auto proxied so that the bean overridden mechanism
 *         works
 */
@Configuration
public class BaseJavaConfig {

    @Bean
    public String beanA() {
        return "origin A";
    }

    @Bean
    public String beanB() {
        return "in B ref:" + beanA();
    }

    @Configuration
    public static class OverrideConf {
        @Bean(name = "beanA")
        public String overrider() {
            return "overrided A in static nested class";
        }
    }

    @Configuration
    public static class OverrideConf2 {
        @Bean(name = "beanA")
        public String overrider2() {
            return "overrider B in another static nested class";
        }
    }

    // <---- wrong cannot apply @Configuration on a non-static class
    // for non-static nested class dose not have a null construtor!
    // @Configuration
    // public class NonStaticOverride {
    // @Bean
    // public String beanC() {
    // return "beanC";
    // }
    // }

    public static void main(String[] args) {

        Logger spring_logger = (Logger) LoggerFactory.getLogger("org.springframework");
        spring_logger.setLevel(Level.ERROR);
        spring_logger.setAdditive(true);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(BaseJavaConfig.class);
        context.refresh();
        String beanB = (String) context.getBean("beanB");
        System.out.println("base configure:");
        System.out.println(beanB);
        context.close();

        try {// <---- wrong only can call refresh() once!
            context.register(OverrideConf.class);
            context.refresh();
        } catch (RuntimeException ex) {
            System.out.println("expected exception: " + ex);
        } finally {
            context.close();
        }

        context = new AnnotationConfigApplicationContext(BaseJavaConfig.class, OverrideConf.class);
        System.out.println("registered override");
        beanB = (String) context.getBean("beanB");
        System.out.println(beanB);
        context.close();

        try {// <---- wrong, cannot mix non-null constructor and
             // register/refresh
            context = new AnnotationConfigApplicationContext(BaseJavaConfig.class);
            context.register(OverrideConf2.class);
            context.refresh();
        } catch (RuntimeException ex) {
            System.out.println("expected exception: " + ex);
        } finally {
            context.close();
        }
        context = new AnnotationConfigApplicationContext(BaseJavaConfig.class, OverrideConf2.class);
        System.out.println("registered override2");
        beanB = (String) context.getBean("beanB");
        System.out.println(beanB);
        context.close();
    }
}
