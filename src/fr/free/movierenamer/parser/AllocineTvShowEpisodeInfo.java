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

import fr.free.movierenamer.media.tvshow.TvShowEpisode;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Class AllocineTvShowEpisodeInfo
 * 
 * @author Nicolas Magré
 */
public class AllocineTvShowEpisodeInfo extends MrParser<TvShowEpisode> {

  private StringBuffer buffer;
  private final TvShowEpisode tvshowInfo;
  private boolean episode, statistics;

  public AllocineTvShowEpisodeInfo() {
    super();
    tvshowInfo = new TvShowEpisode();
  }

  @Override
  public void startDocument() throws SAXException {
    super.startDocument();
    episode = false;
  }

  @Override
  public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
    buffer = new StringBuffer();
    if (name.equalsIgnoreCase("episode")) {
      episode = true;
    }
    if(episode) {
      if (name.equalsIgnoreCase("statistics")) {
        statistics = true;
      }
    }
  }

  @Override
  public void endElement(String uri, String localName, String name) throws SAXException {
    if (name.equalsIgnoreCase("episode")) {
      episode = false;
    }
    if (episode) {
      if (name.equalsIgnoreCase("statistics")) {
        statistics = false;
      }
      if(statistics) {
        if (name.equalsIgnoreCase("userRating")) {
          tvshowInfo.setRating(buffer.toString());
        }
      }
      if (name.equalsIgnoreCase("episodeNumberSeason")) {
        tvshowInfo.setNum(Integer.parseInt(buffer.toString()));
      }
      /*if (name.equalsIgnoreCase("episodeNumberSeries")) {
      }*/
      if (name.equalsIgnoreCase("originalTitle")) {
        tvshowInfo.setOriginalTitle(buffer.toString());
      }
      if (name.equalsIgnoreCase("title")) {
        tvshowInfo.setTitle(buffer.toString());
      }
      if (name.equalsIgnoreCase("synopsis")) {
        tvshowInfo.setSynopsis(buffer.toString());
      }
      if (name.equalsIgnoreCase("userRating")) {
        tvshowInfo.setRating(buffer.toString());
      }
      if (name.equalsIgnoreCase("userRatingCount")) {
        tvshowInfo.setVotes(buffer.toString());
      }
    }
    buffer = null;
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    String lecture = new String(ch, start, length);
    if (buffer != null) {
      buffer.append(lecture);
    }
  }

  @Override
  public TvShowEpisode getObject() {
    return tvshowInfo;
  }
}
