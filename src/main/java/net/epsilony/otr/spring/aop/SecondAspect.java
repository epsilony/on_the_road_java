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

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
@Aspect
public class SecondAspect {

    @Before("net.epsilony.otr.spring.aop.AopAspect.basePointCut()")
    @Order(20)
    public void before20() {
        System.out.println("second before, order 20!");
    }

    @Before("net.epsilony.otr.spring.aop.AopAspect.basePointCut()")
    @Order(10)
    public void before() {
        System.out.println("second before, order 10!");
    }
}
