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

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import fr.free.movierenamer.info.CastingInfo;
import fr.free.movierenamer.info.ImageInfo;
import fr.free.movierenamer.info.ImageInfo.ImageCategoryProperty;
import fr.free.movierenamer.info.MovieInfo;
import fr.free.movierenamer.scrapper.MovieScrapperTest;
import fr.free.movierenamer.searchinfo.Movie;

/**
 * Class IMDbScrapperTest
 * 
 * @author Simon QUÉMÉNEUR
 */
public class IMDbScrapperTest extends MovieScrapperTest {
  private IMDbScrapper imdb = null;

  @Override
  public void init() {
    imdb = new IMDbScrapper();
  }

  @Override
  public void search() throws Exception {
    imdb.setLocale(Locale.FRENCH);
    List<Movie> results = imdb.search("il était une fois dans l'ouest");

    Movie movie = results.get(0);

    assertEquals("Il était une fois dans l'ouest", movie.getName());
    assertEquals("http://ia.media-imdb.com/images/M/MV5BOTMxMTUyMDI2Ml5BMl5BanBnXkFtZTcwMjkxNDAwMQ@@._V1._SY70_SX100_.jpg", movie.getURL().toExternalForm());
    assertEquals(1968, movie.getYear());
    assertEquals(64116, movie.getImdbId());
    assertEquals(64116, movie.getMediaId());
  }
  
  @Test
  public void searchRedirect() throws Exception {
    imdb.setLocale(Locale.FRENCH);
    List<Movie> results = imdb.search("the shop around the corner");

    Movie movie = results.get(0);

    assertEquals("Rendez-vous", movie.getName());
    assertEquals("http://ia.media-imdb.com/images/M/MV5BMTI4ODEwMDgyMV5BMl5BanBnXkFtZTcwMjIzMTUxMQ@@._V1._SY70_SX100_.jpg", movie.getURL().toExternalForm());
    assertEquals(1940, movie.getYear());
    assertEquals(33045, movie.getImdbId());
    assertEquals(33045, movie.getMediaId());
    
  }

  @Override
  public void getMovieInfo() throws Exception {
    imdb.setLocale(Locale.ITALIAN);
    MovieInfo movie = imdb.getInfo(new Movie(64116, null, null, -1, -1));

    assertEquals("C'era una volta il West", movie.getTitle());
    assertEquals(Integer.valueOf(175), Integer.valueOf(movie.getRuntime()));
  }

  @Override
  public void getCasting() throws Exception {
    List<CastingInfo> cast = imdb.getCasting(new Movie(64116, null, null, -1, -1));
    for(CastingInfo info : cast) {
      if(info.isDirector()) {
        assertEquals("Sergio Leone", info.getName());
        return;
      }
    }
    
    Assert.fail();
  };

  @Override
  public void getImages() throws Exception {
    List<ImageInfo> images = imdb.getImages(new Movie(64116, null, null, -1, -1));
    
    assertEquals(ImageCategoryProperty.unknown, images.get(0).getCategory());
    assertEquals("http://ia.media-imdb.com/images/M/MV5BMTM2NTQ2MzkwNV5BMl5BanBnXkFtZTcwMjU1ODIwNw@@._V1._SY214_SX314_.jpg", images.get(1).getHref().toExternalForm());
  }
}
