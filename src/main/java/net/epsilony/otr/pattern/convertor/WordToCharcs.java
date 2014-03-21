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
package net.epsilony.otr.pattern.convertor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class WordToCharcs implements Function<Word, Collection<Charc>> {

    @Override
    public Collection<Charc> apply(Word input) {
        ArrayList<Charc> result = new ArrayList<>(input.getWord().length());
        String w = input.getWord();
        for (int i = 0; i < w.length(); i++) {
            String c = w.substring(i, i + 1);
            Charc charc = new Charc();
            charc.setCharc(c);
            result.add(charc);
        }
        return result;
    }
}
