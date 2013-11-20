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
import java.util.List;

import net.epsilony.otr.util.LogUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ch.qos.logback.classic.Level;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class GivenLengthListAcrossConfClasses_inchoate {
    public static final Logger logger = LoggerFactory.getLogger(GivenLengthListAcrossConfClasses_inchoate.class);

    @Configuration
    public static class ConfA {
        public static int id = 0;

        @Bean
        @Scope("prototype")
        public String beanA() {
            id++;
            return "beanA_" + id;
        }
    }

    @Configuration
    public static class ConfB {
        @Bean
        public int size() {
            return 10;
        }

        @Bean
        public String aDependOnBeanAList() {
            List<String> as = beanAList();
            String result = "depends :" + as;
            if (as.isEmpty()) {
                result += "VERY DANGEROUS!";
            }
            logger.debug("dependOnBeanAList:{}", result);
            return result;
        }

        /*
         * this implementation has drawbacks: 1. the beanAList must be singleton
         * 2. other potential risk of mistake of aDependOnBeanAList
         */
        @Bean
        // @Scope("prototype")
        public List<String> beanAList() {
            return new ArrayList<>(size());
        }

        @Bean
        public static BeanFactoryPostProcessor beanAFactoryPostProcessor() {
            return new BeanAFactoryPostProcessor();
        }
    }

    public static class BeanAFactoryPostProcessor implements BeanFactoryPostProcessor {

        @SuppressWarnings("unchecked")
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            logger.debug("postProcessorStartToWork");
            Integer size = beanFactory.getBean("size", Integer.class);
            List<String> beanAList = (List<String>) beanFactory.getBean("beanAList");
            for (int i = 0; i < size; i++) {
                beanAList.add(beanFactory.getBean("beanA", String.class));
            }

        }
    }

    public static void main(String[] args) {
        LogUtil.setSpringLogLevel(Level.WARN);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfA.class, ConfB.class);
        String dependOnBeanAList = context.getBean("aDependOnBeanAList", String.class);
        System.out.println("aDependOnBeanAList = " + dependOnBeanAList);
        List<String> beanAList = (List<String>) context.getBean("beanAList");
        System.out.println("beanAList = " + beanAList);

    }

}
