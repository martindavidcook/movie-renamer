/*
 * movie-renamer-core
 * Copyright (C) 2012 Nicolas MagrÃ©
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

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;

import fr.free.movierenamer.info.CastingInfo;
import fr.free.movierenamer.info.ImageInfo;
import fr.free.movierenamer.info.ImageInfo.ImageCategoryProperty;
import fr.free.movierenamer.info.MovieInfo;
import fr.free.movierenamer.scrapper.MovieScrapperTest;
import fr.free.movierenamer.searchinfo.Movie;

/**
 * Class TMDbScrapperTest
 * 
 * @author Simon QUÉMÉNEUR
 */
public class TMDbScrapperTest extends MovieScrapperTest {
  private TMDbScrapper tmdb = null;

  @Override
  public void init() {
    tmdb = new TMDbScrapper();
  }

  @Override
  public void search() throws Exception {
    tmdb.setLocale(Locale.CHINESE);
    List<Movie> results = tmdb.search("pulp fiction");

    Movie movie = results.get(0);

    assertEquals("低俗小说", movie.getName());
    assertEquals("http://cf2.imgobject.com/t/p/original/2ak8InN93TQDKrE9UkIgUvy9rER.jpg", movie.getURL().toExternalForm());
    assertEquals(1994, movie.getYear());
    assertEquals(110912, movie.getImdbId());
    assertEquals(680, movie.getMediaId());
  }

  @Override
  public void getMovieInfo() throws Exception {
    tmdb.setLocale(Locale.GERMAN);
    MovieInfo movie = tmdb.getInfo(new Movie(1858, null, null, -1, -1));

    assertEquals(Integer.valueOf(1858), movie.getId());
    assertEquals(Integer.valueOf(418279), movie.getImdbId());
    assertEquals("Transformers", movie.getTitle());
    assertEquals("2007-07-03", movie.getReleasedDate().toString());
    assertEquals("[Abenteuer, Action, Thriller, Science Fiction]", movie.getGenres().toString());

  }

  @Override
  public void getCasting() throws Exception {
    List<CastingInfo> cast = tmdb.getCasting(new Movie(1858, null, null, -1, -1));
    boolean dir = false, actor = false;
    for(CastingInfo info : cast) {
      if(!dir && info.isDirector()) {
        assertEquals("Michael Bay", info.getName());
        dir = true;
      }
      if(!actor&&info.isActor()) {
        assertEquals("Shia LaBeouf", info.getName());
        actor = true;
      }
    }
    
    if(!dir || !actor) {
      Assert.fail();
    }
  };

  @Override
  public void getImages() throws Exception {
    List<ImageInfo> images = tmdb.getImages(new Movie(1858, null, null, -1, -1));
    
    assertEquals(ImageCategoryProperty.fanart, images.get(0).getCategory());
    assertEquals("http://cf2.imgobject.com/t/p/original/p4OHBbXfxToWF4e36uEhQMSidWu.jpg", images.get(0).getHref().toExternalForm());
  }

}