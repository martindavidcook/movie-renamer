/*
 * movie-renamer-core
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
package fr.free.movierenamer.scrapper.impl;

import java.util.List;
import java.util.Locale;

import fr.free.movierenamer.info.ImageInfo;
import fr.free.movierenamer.scrapper.ImageScrapper;
import fr.free.movierenamer.searchinfo.Media;
import fr.free.movierenamer.settings.Settings;

/**
 * Class FanartTV
 * @author Simon QUÉMÉNEUR
 */
public class FanartTV extends ImageScrapper {

  private static final String host = "???";
  private static final String name = "FanartTV";

  private final String apikey;

  public FanartTV() {
    super(Locale.ENGLISH);
    String key = Settings.getApplicationProperty("fanarttv.apikey");
    if (key == null || key.trim().length() == 0) {
      throw new NullPointerException("apikey must not be null");
    }
    this.apikey = key;
  }
    
  @Override
  public String getName() {
    return host;
  }

  @Override
  protected String getHost() {
    return name;
  }

  @Override
  protected List<ImageInfo> fetchImagesInfo(Media media, Locale locale) throws Exception {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Not supported yet.");
    // return null;
  }

}
