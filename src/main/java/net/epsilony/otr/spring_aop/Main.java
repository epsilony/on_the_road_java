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

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Man YUAN <epsilonyuan@gmail.com>
 *
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        RunnableTrigger lambdaTrigger = ac.getBean("lambdaTrigger", RunnableTrigger.class);
        RunnableTrigger anonymousWrapperTrigger = ac.getBean("anonymousWrapperTrigger", RunnableTrigger.class);
        RunnableTrigger beanTrigger = ac.getBean("beanTrigger", RunnableTrigger.class);

        System.out.println("----start lambda trigger");
        lambdaTrigger.trigger();
        System.out.println("----end lambda trigger\n");

        System.out.println("----start anonymous wrapper trigger");
        anonymousWrapperTrigger.trigger();
        System.out.println("----end anonymous wrapper trigger\n");

        System.out.println("----start bean trigger");
        beanTrigger.trigger();
        System.out.println("----end bean trigger\n");

        System.out.println("----diriect call");
        TargetClass targetClass = ac.getBean(TargetClass.class);
        targetClass.targetMethod();
        System.out.println("----end direct call");

        System.out.println("----anonymous");
        targetClass.asRunnable().run();

        System.out.println("----direct reference");
        Runnable run = targetClass::targetMethod;
        run.run();

        System.out.println("----indirect reference");
        Runnable run2 = targetClass::indirectTarget;
        run2.run();

        System.out.println("----inner class");
        targetClass.new AsRunnable().run();

    }
}
