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
package net.epsilony.otr.spring.aop;

import net.epsilony.otr.util.LogUtil;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import ch.qos.logback.classic.Level;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
@Configuration
@EnableAspectJAutoProxy
public class AopBeanConf {
    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.DEFAULT)
    public PojoType pojo() {
        return new PojoType();
    }

    @Bean
    public AopAspect aopAspect() {
        return new AopAspect();
    }

    @Bean
    public SecondAspect secondAspect() {
        return new SecondAspect();
    }

    public static void main(String[] args) {
        LogUtil.setSpringLogLevel(Level.ERROR);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AopBeanConf.class);
        context.refresh();

        PojoType pojo = (PojoType) context.getBean("pojo");
        String result = pojo.pojoMethod("just an input arg");
        System.out.println("pojoMethod result " + result);

        System.out.println("-------------------------second pojo------------------------------");

        PojoType pojo2 = (PojoType) context.getBean("pojo");
        String result2 = pojo2.pojoMethod("input 2");
        System.out.println("pojo2Method result " + result2);

        context.close();
    }
}
