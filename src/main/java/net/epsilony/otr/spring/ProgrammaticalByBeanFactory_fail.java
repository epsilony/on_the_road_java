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

import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class ProgrammaticalByBeanFactory_fail {

    public static class AFactory {
        String content;

        public String produce() {
            return "the context is " + content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @Configuration
    public static class ConfigurationClass {
        @Bean
        public String content() {
            return "untouched!";
        }

        @Bean
        public AFactory afactory() {
            AFactory factory = new AFactory();
            factory.setContent(content());
            return factory;
        }

        @Bean
        public String myBean() {
            return afactory().produce();
        }
    }

    public GenericBeanDefinition contentOverriden() {
        GenericBeanDefinition definition = new GenericBeanDefinition();
        definition.setBeanClass(String.class);
        ConstructorArgumentValues values = new ConstructorArgumentValues();
        values.addGenericArgumentValue("touched");
        definition.setConstructorArgumentValues(values);
        return definition;
    }

    public GenericBeanDefinition myBeanDef() {
        GenericBeanDefinition definition = new GenericBeanDefinition();
        definition.setBeanClass(String.class);
        definition.setLazyInit(true);
        definition.setFactoryBeanName("afactory");
        definition.setFactoryMethodName("produce");
        return definition;
    }

    public ApplicationContext produceContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ConfigurationClass.class);
        context.registerBeanDefinition("content", contentOverriden());
        // context.registerBeanDefinition("myBean", myBeanDef()); // <------
        // doesn't work!
        context.refresh();
        return context;
    }

    public static void main(String[] args) {
        ApplicationContext context = new ProgrammaticalByBeanFactory_fail().produceContext();
        String myBean = context.getBean("myBean", String.class);
        System.out.println("myBean = " + myBean);
    }
}
