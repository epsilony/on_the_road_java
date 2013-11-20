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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
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
public class GivenLengthListAcrossConfClasses {
    public static final Logger logger = LoggerFactory.getLogger(GivenLengthListAcrossConfClasses.class);

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
        public int beanListSize() {
            return 10;
        }

        @Bean
        @Scope("prototype")
        public String aDependOnBeanAList() {
            List<String> as = beanAList();
            String result = "depends :" + as;
            if (as.isEmpty()) {
                result += "VERY DANGEROUS!";
            }
            logger.debug("dependOnBeanAList:{}", result);
            return result;
        }

        @Bean
        @Scope("prototype")
        public List<String> beanAList() {
            ArrayList<String> result = new ArrayList<>(beanListSize());
            ApplicationContext context = contextHolder().getContext();
            for (int i = 0; i < beanListSize(); i++) {
                result.add(context.getBean("beanA", String.class));
            }
            return result;
        }

        @Bean
        ApplicationAwareImp contextHolder() {
            return new ApplicationAwareImp();
        }
    }

    public static class ApplicationAwareImp implements ApplicationContextAware {

        ApplicationContext context;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.context = applicationContext;
        }

        public ApplicationContext getContext() {
            return context;
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfA.class, ConfB.class);
        String dependOnBeanAList = context.getBean("aDependOnBeanAList", String.class);
        System.out.println("aDependOnBeanAList = " + dependOnBeanAList);
        List<String> beanAList = (List<String>) context.getBean("beanAList");
        System.out.println("beanAList = " + beanAList);
    }
}
