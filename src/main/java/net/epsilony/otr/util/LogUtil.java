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
package net.epsilony.otr.util;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class LogUtil {
    public static void setSpringLogLevel(Level level) {
        Logger logger;// the Logger type is logback specified! not more general
        // slf4j
        logger = (Logger) LoggerFactory.getLogger("org.springframework");
        logger.setLevel(level);
        logger.debug("debug a log");
        logger.info("info a log");
    }
}
