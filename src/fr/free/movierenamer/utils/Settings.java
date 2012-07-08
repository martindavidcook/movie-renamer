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
package fr.free.movierenamer.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 * Class Settings , Movie Renamer settings
 *
 * @author Nicolas Magré
 */
public class Settings {

  public static final String APPNAME = "Movie Renamer";
  private final String version = Utils.getRbTok("apps.version");
  private static final String userPath = System.getProperty("user.home");
  private final String apkMdb = "BQRjATHjATV3Zwx2AwWxLGOvLwEwZ2WwZQWyBGyvMQx=";
  private final String apkTdb = "DmIOExH5DwV1AwZkZRZ3Zt==";
  private final static String movieRenamerFolder = Utils.isWindows() ? "Movie_Renamer" : ".Movie_Renamer";
  public static final String mrFolder = userPath + File.separator + movieRenamerFolder;
  //Cache
  public Cache cache;
  public final String cacheDir = userPath + File.separator + movieRenamerFolder + File.separator + "cache" + File.separator;
  public final String imageCacheDir = cacheDir + "images" + File.separator;
  public final String thumbCacheDir = imageCacheDir + "thumbnails" + File.separator;
  public final String fanartCacheDir = imageCacheDir + "fanarts" + File.separator;
  public final String actorCacheDir = imageCacheDir + "actors" + File.separator;
  public final String xmlCacheDir = cacheDir + "XML" + File.separator;
  public final String tvshowZipCacheDir = cacheDir + "Zip" + File.separator;
  //Files
  public final String configFile = userPath + File.separator + movieRenamerFolder + File.separator + "conf" + File.separator + "movie_renamer.conf";
  public final String renamedFile = cacheDir + "renamed.xml";
  private final String logFile = userPath + File.separator + movieRenamerFolder + File.separator + "Logs" + File.separator + "movie_renamer.log";
  //Logger
  public static final Logger LOGGER = Logger.getLogger("Movie Renamer Logger");
  //IMDB
  public final String imdbSearchUrl = "http://www.imdb.com/find?s=tt&q=";
  public final String imdbMovieUrl = "http://www.imdb.com/title/";
  public final String imdbSearchUrl_fr = "http://www.imdb.fr/find?s=tt&q=";
  public final String imdbMovieUrl_fr = "http://www.imdb.fr/title/";
  //The Movie DB
  public final String tmdbAPISearchUrl = "http://api.themoviedb.org/2.1/Movie.search/en/xml/";
  public final String tmdbAPMovieImdbLookUp = "http://api.themoviedb.org/2.1/Movie.imdbLookup/en/xml/";
  public final String tmdbAPIMovieInf = "http://api.themoviedb.org/2.1/Movie.getInfo/en/xml/";
  //Tvdb
  public final String tvdbAPIUrlTvShow = "http://thetvdb.com/api/";
  public static final String tvdbAPIUrlTvShowImage = "http://thetvdb.com/banners/";
  //Allocine
  public final String allocineAPISearch = "http://api.allocine.fr/rest/v3/search?partner=yW5kcm9pZC12M3M&filter=FILTER&striptags=synopsis,synopsisshort&q=";
  public final String allocineAPIInfo = "http://api.allocine.fr/rest/v3/MEDIA?partner=yW5kcm9pZC12M3M&profile=large&filter=MEDIA&striptags=synopsis,synopsisshort&code=";
  //Xbmc Passion
  public final String xbmcPassionImdblookup = "http://passion-xbmc.org/scraper/ajax.php?Ajax=Home&";
  // List
  public int[] nbResultList = {-1, 5, 10, 15, 20, 30};
  public String[] thumbExtList = {".jpg", ".tbn", "-thumb.jpg"};
  public String[] fanartExtList = {".jpg", "-fanart.jpg"};
  //LAF
  public final static UIManager.LookAndFeelInfo lookAndFeels[] = UIManager.getInstalledLookAndFeels();
  public boolean lafChanged = false;
  public boolean interfaceChanged = false;
  //Apk
  public String xurlMdb = Utils.rot13(apkMdb);
  public String xurlTdb = Utils.rot13(apkTdb);
  // Saved settings
  public String locale = "";
  public static String[] nameFilters = {
    "notv", "readnfo", "repack", "proper$", "nfo$", "extended.cut", "limitededition", "limited", "k-sual",
    "extended", "uncut", "n° [0-9][0-9][0-9]", "yestv", "stv", "remastered", "limited", "x264", "bluray",
    "bd5", "bd9", "hddvd", "hdz", "unrated", "dvdrip", "cinefile",
    "hdmi", "dvd5", "ac3", "culthd", "dvd9", "remux", "edition.platinum", "frenchhqc", "frenchedit",
    "h264", "bdrip", "brrip", "hdteam", "hddvdrip", "subhd", "xvid", "divx", "null$", "divx511",
    "vorbis", "=str=", "www", "ffm", "mp3", "divx5", "dvb", "mpa2", "blubyte", "brmp", "avs", "filmhd",
    "hd4u", "1080p", "1080i", "720p", "720i", "720", "truefrench", "dts", "french", "vostfr", "1cd", "2cd", "vff", " vo$", " vf ", "hd",
    " cam$ ", "telesync", " ts ", " tc ", "ntsc", " pal$ ", "dvd-r", "dvdscr", "scr$", "r1", "r2", "r3", "r4",
    "r5", "wp", "subforced", "dvd", "vcd", "avchd", " md"
  };
  public ArrayList<String> mediaNameFilters;
  public String xmlVersion = "";
  public boolean xmlError = false;
  public String[] extensions = {"mkv", "avi", "wmv", "mp4", "m4v", "mov", "ts", "m2ts", "ogm", "mpg", "mpeg", "flv", "iso", "rm", "mov", "asf"};
  public int thumbSize = 0;
  public int fanartSize = 0;
  public int nbResult = 2;
  public String movieFilenameFormat = "<t> (<y>)";
  public int thumbExt = 0;
  public int fanartExt = 1;
  public int renameCase = 1;
  public String movieDir = "";
  public int movieDirRenamedTitle = 0;
  public String commande = "";
  public int nfoType = 0;
  public String separator = ", ";
  public int limit = 3;
  public String laf = "";
  public int movieScrapper = 0;
  public int tvshowScrapper = 0;
  // Boolean
  public boolean useExtensionFilter = true;
  public boolean showMovieFilePath = false;
  public boolean scanSubfolder = false;
  public boolean hideRenamedMedia = false;
  public boolean displayApproximateResult = false;
  public boolean displayThumbResult = true;
  public boolean createMovieDirectory = false;
  public boolean movieScrapperFR = false;
  public boolean tvshowScrapperFR = false;
  public boolean selectFrstMedia = false;
  public boolean selectFrstRes = true;
  public boolean movieInfoPanel = true;
  public boolean actorImage = true;
  public boolean thumb = true;
  public boolean fanart = true;
  public boolean checkUpdate = false;
  public boolean showNotaMovieWarn = true;
  public boolean autoSearchMedia = true;
  public boolean rmSpcChar = true;
  public boolean rmDupSpace = true;
  public boolean tvdbFr = true;
  public boolean clearXMLCache = false;
  public boolean sortBySimiYear = true;

  /**
   * Constructor
   */
  public Settings() {
    mediaNameFilters = new ArrayList<String>();
    mediaNameFilters.addAll(Arrays.asList(nameFilters));
    Utils.createFilePath(configFile, false);
    Utils.createFilePath(fanartCacheDir, true);
    Utils.createFilePath(thumbCacheDir, true);
    Utils.createFilePath(actorCacheDir, true);
    Utils.createFilePath(xmlCacheDir, true);
    Utils.createFilePath(tvshowZipCacheDir, true);
    Utils.createFilePath(logFile, false);
    try {
      FileHandler fh = new FileHandler(logFile);
      LOGGER.addHandler(fh);
    } catch (SecurityException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
    cache = new Cache(this);
  }

  /**
   * Save setting
   *
   * @return True if setting was saved, False otherwise
   */
  public boolean saveSetting() {
    LOGGER.log(Level.INFO, "Save configuration");
    try {
      String endl = Utils.ENDLINE;
      BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8"));
      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + endl);
      out.write("<Movie_Renamer Version=\"" + version + "\">" + endl);
      out.write("  <setting>" + endl);

      // Variables
      out.write("    <locale>" + locale + "</locale>" + endl);
      out.write("    <nameFilters>" + Utils.escapeXML(Utils.arrayToString(nameFilters, "/_", 0)) + "</nameFilters>" + endl);
      out.write("    <extensions>" + Utils.arrayToString(extensions, "/_", 0) + "</extensions>" + endl);
      out.write("    <thumbSize>" + thumbSize + "</thumbSize>" + endl);
      out.write("    <fanartSize>" + fanartSize + "</fanartSize>" + endl);
      out.write("    <nbResult>" + nbResult + "</nbResult>" + endl);
      out.write("    <movieFilenameFormat>" + Utils.escapeXML(movieFilenameFormat) + "</movieFilenameFormat>" + endl);
      out.write("    <thumbExt>" + thumbExt + "</thumbExt>" + endl);
      out.write("    <fanartExt>" + fanartExt + "</fanartExt>" + endl);
      out.write("    <renameCase>" + renameCase + "</renameCase>" + endl);
      out.write("    <movieDir>" + movieDir + "</movieDir>" + endl);
      out.write("    <movieDirRenamedTitle>" + movieDirRenamedTitle + "</movieDirRenamedTitle>" + endl);
      out.write("    <commande>" + Utils.escapeXML(commande) + "</commande>" + endl);
      out.write("    <nfoType>" + nfoType + "</nfoType>" + endl);
      out.write("    <separator>" + separator + "</separator>" + endl);
      out.write("    <limit>" + limit + "</limit>" + endl);
      out.write("    <laf>" + laf + "</laf>" + endl);
      out.write("    <movieScrapper>" + movieScrapper + "</movieScrapper>" + endl);
      out.write("    <tvshowScrapper>" + tvshowScrapper + "</tvshowScrapper>" + endl);

      // booleans
      out.write("    <useExtensionFilter>" + (useExtensionFilter ? 0 : 1) + "</useExtensionFilter>" + endl);
      out.write("    <showMovieFilePath>" + (showMovieFilePath ? 0 : 1) + "</showMovieFilePath>" + endl);
      out.write("    <scanSubfolder>" + (scanSubfolder ? 0 : 1) + "</scanSubfolder>" + endl);
      out.write("    <hideRenamedMedia>" + (hideRenamedMedia ? 0 : 1) + "</hideRenamedMedia>" + endl);
      out.write("    <displayApproximateResult>" + (displayApproximateResult ? 0 : 1) + "</displayApproximateResult>" + endl);
      out.write("    <displayThumbResult>" + (displayThumbResult ? 0 : 1) + "</displayThumbResult>" + endl);
      out.write("    <createMovieDirectory>" + (createMovieDirectory ? 0 : 1) + "</createMovieDirectory>" + endl);
      out.write("    <movieScrapperFR>" + (movieScrapperFR ? 0 : 1) + "</movieScrapperFR>" + endl);
      out.write("    <tvshowScrapperFR>" + (tvshowScrapperFR ? 0 : 1) + "</tvshowScrapperFR>" + endl);
      out.write("    <selectFrstMedia>" + (selectFrstMedia ? 0 : 1) + "</selectFrstMedia>" + endl);
      out.write("    <selectFrstRes>" + (selectFrstRes ? 0 : 1) + "</selectFrstRes>" + endl);
      out.write("    <movieInfoPanel>" + (movieInfoPanel ? 0 : 1) + "</movieInfoPanel>" + endl);
      out.write("    <actorImage>" + (actorImage ? 0 : 1) + "</actorImage>" + endl);
      out.write("    <thumb>" + (thumb ? 0 : 1) + "</thumb>" + endl);
      out.write("    <fanart>" + (fanart ? 0 : 1) + "</fanart>" + endl);
      out.write("    <checkUpdate>" + (checkUpdate ? 0 : 1) + "</checkUpdate>" + endl);
      out.write("    <showNotaMovieWarn>" + (showNotaMovieWarn ? 0 : 1) + "</showNotaMovieWarn>" + endl);
      out.write("    <autoSearchMedia>" + (autoSearchMedia ? 0 : 1) + "</autoSearchMedia>" + endl);
      out.write("    <rmSpcChar>" + (rmSpcChar ? 0 : 1) + "</rmSpcChar>" + endl);
      out.write("    <rmDupSpace>" + (rmDupSpace ? 0 : 1) + "</rmDupSpace>" + endl);
      out.write("    <tvdbFr>" + (tvdbFr ? 0 : 1) + "</tvdbFr>" + endl);
      out.write("    <clearXMLCache>" + (clearXMLCache ? 0 : 1) + "</clearXMLCache>" + endl);
      out.write("    <sortBySimiYear>" + (sortBySimiYear ? 0 : 1) + "</sortBySimiYear>" + endl);

      out.write("  </setting>" + endl);
      out.write("</Movie_Renamer>" + endl);
      out.close();
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
      return false;
    }
    return true;
  }

  /**
   * Get Movie Renamer version
   *
   * @return Movie Renamer Version
   */
  public String getVersion() {
    return version;
  }
}
