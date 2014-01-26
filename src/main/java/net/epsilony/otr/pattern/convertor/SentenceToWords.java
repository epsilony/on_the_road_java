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
import java.util.List;

/**
 * @author Man YUAN <epsilon@epsilony.net>
 * 
 */
public class SentenceToWords implements Convertor<Sentence, Collection<Word>> {

    @Override
    public Collection<Word> convert(Sentence sentence) {
        String[] split = sentence.getSentence().split(" ");
        List<Word> result = new ArrayList<>(split.length);
        for (String s : split) {
            Word w = new Word();
            w.setWord(s);
            result.add(w);
        }
        return result;
    }

}
