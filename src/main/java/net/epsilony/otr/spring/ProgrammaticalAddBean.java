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

import javax.annotation.Resource;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class ProgrammaticalAddBean {
    String runtimeValue;

    /**
     * @param extraValue
     */
    public ProgrammaticalAddBean(String runtimeValue) {
        super();
        this.runtimeValue = runtimeValue;
    }

    public static class SampleBeanClass {
        String a, b, c;

        public void setA(String a) {
            this.a = a;
        }

        public void setB(String b) {
            this.b = b;
        }

        public void setC(String c) {
            this.c = c;
        }

        @Override
        public String toString() {
            return "SampleBeanClass [a=" + a + ", b=" + b + ", c = " + c + "]";
        }
    }

    @Configuration
    public static class OtherBeansDependentsOnProgramBean {
        @Resource(name = "progBean")
        SampleBeanClass sampleBeanClass;

        @Bean
        public String otherBean() {
            return "otherBean on " + sampleBeanClass;
        }
    }

    @Configuration
    public static class DisturbingConfig {
        @Bean
        public String progBean() {
            return "progBean before overrided";
        }
    }

    public GenericBeanDefinition newDefinition() {
        GenericBeanDefinition definition = new GenericBeanDefinition();
        definition.setBeanClass(SampleBeanClass.class);
        MutablePropertyValues values = new MutablePropertyValues();
        values.addPropertyValue("a", new RuntimeBeanReference("beanA"));
        values.addPropertyValue("b", new RuntimeBeanReference("beanB"));
        values.addPropertyValue("c", "value c by program " + runtimeValue);
        definition.setPropertyValues(values);
        return definition;
    }

    public ApplicationContext getContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(BaseJavaConfig.class);
        context.register(OtherBeansDependentsOnProgramBean.class);
        context.register(DisturbingConfig.class);
        context.registerBeanDefinition("progBean", newDefinition());
        context.refresh();
        return context;
    }

    public static void main(String[] args) {
        ProgrammaticalAddBean pab = new ProgrammaticalAddBean("just some runtime value");
        ApplicationContext context = pab.getContext();

        System.out.println("prog bean = " + context.getBean("progBean"));
        System.out.println("other bean = " + context.getBean("otherBean"));
    }

}
