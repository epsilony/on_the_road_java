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
package net.epsilony.otr.spring_aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Man YUAN <epsilonyuan@gmail.com>
 *
 */
@Configuration
@EnableAspectJAutoProxy
public class Config {

    @Bean
    public int someValue() {
        return 101;
    }

    @Bean
    public RunnableTrigger lambdaTrigger() {
        RunnableTrigger runnableTrigger = new RunnableTrigger("lambda");
        runnableTrigger.setRunnable(targetClass()::indirectTarget);
        return runnableTrigger;
    }

    @Bean
    public RunnableTrigger anonymousWrapperTrigger() {
        RunnableTrigger runnableTrigger = new RunnableTrigger("annoymous wrapper");
        runnableTrigger.setRunnable(targetClass().asRunnable());
        return runnableTrigger;
    }

    @Bean
    public RunnableTrigger beanTrigger() {
        RunnableTrigger runnableTrigger = new RunnableTrigger("bean");
        runnableTrigger.setRunnable(targetRunnable());
        return runnableTrigger;
    }

    @Bean
    public Runnable targetRunnable() {
        return targetClass().asRunnable();
    }

    @Bean
    public TargetClass targetClass() {
        return new TargetClass();
    }

    @Bean
    public AspectClass aspectClass() {
        return new AspectClass();
    }
}
