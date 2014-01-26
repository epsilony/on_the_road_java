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

@Configuration
public class ConfigExample implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    AHolder aholder() {
        return new AHolder();
    }

    @Bean
    @Scope("prototype")
    // must be prototype or the A should be an interface and use proxy
    APrinter aprinter() {
        APrinter aPrinter = new APrinter();
        aPrinter.setA(applicationContext.getBean(AHolder.class).getA());
        return aPrinter;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ConfigExample.class);
        applicationContext.getBean(APrinter.class).printA();
        applicationContext.getBean(AHolder.class).setA("filled A");
        applicationContext.getBean(APrinter.class).printA();
    }
}
