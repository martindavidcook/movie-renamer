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
package fr.free.movierenamer.worker.provider;

import fr.free.movierenamer.parser.MrParser;
import fr.free.movierenamer.parser.TvRageSearch;
import fr.free.movierenamer.utils.SearchResult;
import fr.free.movierenamer.utils.Settings;
import fr.free.movierenamer.worker.TvShowSearchWorker;
import java.beans.PropertyChangeSupport;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Class TvRageSearchWorker
 *
 * @author Nicolas Magré
 */
public class TvRageSearchWorker extends TvShowSearchWorker {

  public TvRageSearchWorker(PropertyChangeSupport errorSupport, String searchTitle) {
    super(errorSupport, searchTitle);
  }

  @Override
  protected String getUri() throws Exception {
    return Settings.tvRageAPIsearch + URLEncoder.encode(searchTitle, "UTF-8");
  }

  @Override
  protected MrParser<ArrayList<SearchResult>> getParser() throws Exception {
    return new TvRageSearch();
  }
}
