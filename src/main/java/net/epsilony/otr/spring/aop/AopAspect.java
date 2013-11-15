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

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
@Aspect
public class AopAspect {
    @Pointcut("execution(public * pojoMethod(..))")
    public void basePointCut() {
    };

    @Before("basePointCut()")
    public void beforeAdvise() {
        System.out.println("in before advice");
    }

    @AfterReturning(pointcut = "basePointCut()", returning = "retVal")
    public void afterReturning(Object retVal) {
        System.out.println("after returning");
        System.out.println("return value: " + retVal);
    }

    @Around("basePointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("in around");
        Object retVal = pjp.proceed();
        System.out.println("in around return value: " + retVal);
        return "around processed: " + retVal;
    }
}
