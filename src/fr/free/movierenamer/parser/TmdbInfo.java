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

import fr.free.movierenamer.media.MediaImage;
import fr.free.movierenamer.media.MediaImages;
import fr.free.movierenamer.media.MediaPerson;
import fr.free.movierenamer.media.movie.MovieInfo;
import fr.free.movierenamer.utils.ActionNotValidException;
import fr.free.movierenamer.utils.Settings;
import fr.free.movierenamer.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Class TheMovieDbInfo
 *
 * @author Nicolas Magré
 */
public class TmdbInfo extends MrParser<MovieInfo> {

  private StringBuffer buffer;
  private boolean movie;
  private boolean images;
  private MediaImage currentMovieImage;
  private String currentId;
  private List<MediaImage> thumbs;
  private List<MediaImage> fanarts;
  private MovieInfo movieinfo;

  public TmdbInfo() {
    super();
  }

  @Override
  public void startDocument() throws SAXException {
    super.startDocument();
    movie = images = false;
    movieinfo = new MovieInfo();
    currentMovieImage = null;
    currentId = "";
    thumbs = new ArrayList<MediaImage>();
    fanarts = new ArrayList<MediaImage>();
  }

  @Override
  public void endDocument() throws SAXException {
    super.endDocument();
    MediaImages images = new MediaImages();
    images.setThumbs(thumbs);
    images.setThumbs(fanarts);
    movieinfo.addImages(images);
  }

  @Override
  public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
    buffer = new StringBuffer();
    if (name.equalsIgnoreCase("movie")) {
      movie = true;
    }
    if (name.equalsIgnoreCase("country")) {
      movieinfo.addCountry(attributes.getValue("name"));
    }

    if (name.equalsIgnoreCase("images")) {
      images = true;
    }

    if (images) {
      if (name.equalsIgnoreCase("image")) {
        if (!currentId.equals(attributes.getValue("id"))) {
          if (currentMovieImage != null) {
            if (currentMovieImage.getType() == MediaImage.MediaImageType.THUMB) {
              thumbs.add(currentMovieImage);
            } else {
              fanarts.add(currentMovieImage);
            }
          }
          currentId = attributes.getValue("id");
          currentMovieImage = new MediaImage(0, attributes.getValue("type").equals("poster") ? MediaImage.MediaImageType.THUMB : MediaImage.MediaImageType.FANART);
        }
        if (attributes.getValue("size").equals("original")) {
          currentMovieImage.setUrl(attributes.getValue("url").replace(".png", ".jpg"), MediaImage.MediaImageSize.ORIGINAL);// API bug png ar jpg on server
        }
        if (attributes.getValue("size").equals("thumb")) {
          currentMovieImage.setUrl(attributes.getValue("url").replace(".png", ".jpg"), MediaImage.MediaImageSize.THUMB);
        }
        if (attributes.getValue("size").equals("mid") || attributes.getValue("type").equals("poster")) {
          currentMovieImage.setUrl(attributes.getValue("url").replace(".png", ".jpg"), MediaImage.MediaImageSize.MEDIUM);
        }
      }
    }

    if (name.equalsIgnoreCase("person")) {
      String personnJob = attributes.getValue("job");

      if (personnJob.equals("Director") || personnJob.equals("Actor") || personnJob.equals("Writer")) {
        try {
          MediaPerson person;
          person = movieinfo.getActorByName(attributes.getValue("name"));
          int job = MediaPerson.ACTOR;
          if (personnJob.equals("Director")) {
            job = MediaPerson.DIRECTOR;
          }
          if (personnJob.equals("Writer")) {
            job = MediaPerson.WRITER;
          }
          if (person == null) {
            person = new MediaPerson(attributes.getValue("name"), attributes.getValue("thumb"), job);
            if (job == MediaPerson.ACTOR) {
              person.addRole(attributes.getValue("character"));
            }
            movieinfo.addPerson(person);
          } else if (person.getJob() == MediaPerson.ACTOR) {
            movieinfo.addRole(person.getName(), attributes.getValue("character"));
          }
        } catch (ActionNotValidException ex) {
          Settings.LOGGER.log(Level.SEVERE, null, ex);
        }
      }
    }
    if (name.equalsIgnoreCase("category")) {
      if (attributes.getValue("type").equals("genre")) {
        movieinfo.addGenre(attributes.getValue("name"));
      }
    }
    if (name.equalsIgnoreCase("studio")) {
      movieinfo.addStudio(attributes.getValue("name"));
    }
  }

  @Override
  public void endElement(String uri, String localName, String name) throws SAXException {
    if (name.equalsIgnoreCase("movie")) {
      movie = false;
    }
    if (name.equalsIgnoreCase("images")) {
      images = false;
    }
    if (movie) {
      if (name.equalsIgnoreCase("name")) {
        movieinfo.setTitle(buffer.toString());
      }
      if (name.equalsIgnoreCase("original_name")) {
        movieinfo.setOriginalTitle(buffer.toString());
      }
      if (name.equalsIgnoreCase("trailer")) {
        movieinfo.setTrailer(buffer.toString());
      }
      if (name.equalsIgnoreCase("overview")) {
        movieinfo.setSynopsis(buffer.toString());
      }
      if (name.equalsIgnoreCase("tagline")) {
        movieinfo.setTagline(buffer.toString());
      }
      if (name.equalsIgnoreCase("rating")) {
        if (Utils.isDigit(buffer.toString())) {
          movieinfo.setRating(buffer.toString());
        }
      }
      if (name.equalsIgnoreCase("runtime")) {
        if (Utils.isDigit(buffer.toString())) {
          movieinfo.setRuntime(buffer.toString());
        }
      }
      if (name.equalsIgnoreCase("votes")) {
        movieinfo.setVotes(buffer.toString());
      }
      if (name.equalsIgnoreCase("certification")) {
        movieinfo.setMpaa(buffer.toString());
      }
      if (name.equalsIgnoreCase("released")) {
        String year = buffer.toString();
        if (year.contains("-")) {
          year = year.substring(0, buffer.toString().indexOf("-"));
        }
        movieinfo.setYear(year);
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
  public MovieInfo getObject() {
    return movieinfo;
  }
}
