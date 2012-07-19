/*
 * Movie Renamer
 * Copyright (C) 2012 Nicolas Magré
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
package fr.free.movierenamer.parser;

import java.io.File;
import org.xml.sax.ContentHandler;

/**
 * Interface for XML parser
 * 
 * @param <T>
 *          Object returned by parser
 * @author Nicolas Magré
 * @author QUÉMÉNEUR Simon
 */
public interface IParser<T> extends ContentHandler {
  public T getObject();

  public void setOriginalFile(File originalFile);
}