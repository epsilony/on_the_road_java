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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class BeanPostProcessorDemo {

    public static class MyBeanPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("before initialization!");
            System.out.println("bean = " + bean);
            System.out.println("beanName = " + beanName);
            if (beanName == "beanB") {
                String beanB = (String) bean;
                bean = beanB + " befored";
            }
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            System.out.println("after initialization");
            System.out.println("bean = " + bean);
            System.out.println("beanName = " + beanName);
            if (beanName == "beanB") {
                String beanB = (String) bean;
                bean = beanB + " aftered";
            }
            return bean;
        }

    }

    @Configuration
    @Import(BaseJavaConfig.class)
    public static class ConfigureClass {
        @Bean
        MyBeanPostProcessor myBeanPostProcessor() {
            return new MyBeanPostProcessor();
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigureClass.class);
        System.out.println("context created");
        Object beanA = context.getBean("beanA");
        System.out.println("beanA getted");
        Object beanB = context.getBean("beanB");
        System.out.println("beanB getted");
        System.out.println("beanA = " + beanA);
        System.out.println("beanB = " + beanB);
    }
}
