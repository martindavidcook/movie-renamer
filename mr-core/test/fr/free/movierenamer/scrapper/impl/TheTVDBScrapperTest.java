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

import fr.free.movierenamer.scrapper.impl.tvshow.TheTVDBScrapper;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;

import fr.free.movierenamer.info.CastingInfo;
import fr.free.movierenamer.info.EpisodeInfo;
import fr.free.movierenamer.info.IdInfo;
import fr.free.movierenamer.info.ImageInfo;
import fr.free.movierenamer.info.ImageInfo.ImageCategoryProperty;
import fr.free.movierenamer.info.TvShowInfo;
import fr.free.movierenamer.scrapper.TvShowScrapperTest;
import fr.free.movierenamer.searchinfo.TvShow;
import fr.free.movierenamer.utils.ScrapperUtils;

/**
 * Class TheTVDBScrapperTest
 *
 * @author Simon QUÉMÉNEUR
 */
public final class TheTVDBScrapperTest extends TvShowScrapperTest {
  private TheTVDBScrapper thetvdb = null;

  @Override
  public void init() {
    thetvdb = new TheTVDBScrapper();
  }

  @Override
  public void search() throws Exception {
    List<TvShow> results = thetvdb.search("Mystères du Cosmos");

    Assert.assertEquals(1, results.size());

    TvShow tvShow =  results.get(0);

    Assert.assertEquals("Desperate Housewives", tvShow.getName());
    Assert.assertEquals("http://www.thetvdb.com/banners/graphical/73800-g15.jpg", tvShow.getURL().toExternalForm());
    Assert.assertEquals(2004, tvShow.getYear());
    Assert.assertEquals(73800, tvShow.getMediaId());
  }

  @Override
  public void getTvShowInfo() throws Exception {
    thetvdb.setLanguage(Locale.FRENCH);
    TvShowInfo tvShow = thetvdb.getInfo(new TvShow(new IdInfo(82066, ScrapperUtils.AvailableApiIds.TVDB), null, null, -1));

    Assert.assertEquals("Fringe", tvShow.getName());
    Assert.assertEquals("2008-08-26", tvShow.getFirstAired().toString());
    Assert.assertEquals("[Drama, Science-Fiction]", tvShow.getGenres().toString());
  }

  @Override
  public void getCasting() throws Exception {
    List<CastingInfo> cast = thetvdb.getCasting(new TvShow(new IdInfo(82066, ScrapperUtils.AvailableApiIds.TVDB), null, null, -1));
    for(CastingInfo info : cast) {
      if(info.isActor()) {
        Assert.assertEquals("Anna Torv", info.getName());
        return;
      }
    }

    Assert.fail();
  }

  @Override
  public void getImages() throws Exception {
    List<ImageInfo> images = thetvdb.getImages(new TvShow(new IdInfo(70327, ScrapperUtils.AvailableApiIds.TVDB), null, null, -1));
    Assert.assertEquals(ImageCategoryProperty.fanart, images.get(0).getCategory());
    Assert.assertNotNull(images.get(1).getHref(ImageInfo.ImageSize.big));
  }

  @Override
  public void getEpisodesInfoList() throws Exception {
    thetvdb.setLanguage(Locale.GERMAN);
    List<EpisodeInfo> episodes = thetvdb.getEpisodesInfoList(new TvShow(new IdInfo(81189, ScrapperUtils.AvailableApiIds.TVDB), null, null, -1));

    EpisodeInfo first = episodes.get(0);

    Assert.assertEquals("Breaking Bad", first.getTvShowName());
    Assert.assertEquals("Der Einstieg", first.getName());
    Assert.assertEquals("2008-01-20", first.getAirdate().toString());
  }

}
