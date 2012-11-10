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
package fr.free.movierenamer.mediainfo;

import fr.free.movierenamer.mediainfo.MediaInfo.StreamKind;
import fr.free.movierenamer.settings.Settings;
import fr.free.movierenamer.utils.LocaleUtils;
import fr.free.movierenamer.utils.NumberUtils;
import fr.free.movierenamer.utils.StringUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Class MediaTag
 * 
 * @author Nicolas Magré
 */
public class MediaTag {

  private MediaInfo mediaInfo;
  private final File mediaFile;
  private final boolean libMediaInfo = Settings.libMediaInfo();

  public enum TagType {

    Audio,
    Text;
  }

  public enum TagList {

    AudioChannel(TagType.Audio),
    AudioCodec(TagType.Audio),
    AudioLanguage(TagType.Audio),
    AudioTitleString(TagType.Audio),
    TextTitle(TagType.Text),
    TextLanguage(TagType.Text);
    private TagType type;

    TagList(TagType type) {
      this.type = type;
    }

    public TagType getTagType() {
      return type;
    }
  }

  public MediaTag(File mediaFile) {
    this.mediaFile = mediaFile;
    mediaInfo = null;
  }

  public String getContainerFormat() {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }
    String extensions = getMediaInfo(StreamKind.General, 0, "Codec/Extensions", "Format");
    if (extensions == null || extensions.length() == 0) {
      return StringUtils.EMPTY;
    }
    return new Scanner(extensions).next().toLowerCase();
  }

  public String getFileSize() {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }
    String fileSize = getMediaInfo(StreamKind.General, 0, "FileSize/String4", "FileSize/String");
    return fileSize == null ? StringUtils.EMPTY : fileSize;
  }

  public String getDuration() {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }
    String duration = getMediaInfo(StreamKind.General, 0, "Duration/String");
    return duration == null ? StringUtils.EMPTY : duration;
  }

  public String getVideoCodec() {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }
    String codec = getMediaInfo(StreamKind.Video, 0, "Encoded_Library/Name", "CodecID/Hint", "Format");
    if (codec == null || codec.length() == 0) {
      return StringUtils.EMPTY;
    }
    return new Scanner(codec).next();
  }

  public String getVideoFrameRate() {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }
    String frameRate = getMediaInfo(StreamKind.Video, 0, "FrameRate", "FrameRate/String");
    return frameRate == null ? StringUtils.EMPTY : frameRate;
  }

  public String getVideoFormat() {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }
    String height = getMediaInfo(StreamKind.Video, 0, "Height");
    String scanType = getMediaInfo(StreamKind.Video, 0, "ScanType");

    if (height == null || scanType == null) {
      return StringUtils.EMPTY;
    }
    return height + Character.toLowerCase(scanType.charAt(0));
  }

  public String getVideoResolution() {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }
    String width = getMediaInfo(StreamKind.Video, 0, "Width");
    String height = getMediaInfo(StreamKind.Video, 0, "Height");

    if (width == null || height == null) {
      return StringUtils.EMPTY;
    }
    return width + 'x' + height;
  }

  public String getVideoDefinitionCategory() {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }
    String width = getMediaInfo(StreamKind.Video, 0, "Width");
    if (width == null || !NumberUtils.isDigit(width)) {
      return StringUtils.EMPTY;
    }
    return Integer.parseInt(width) < 900 ? "SD" : "HD";
  }

  private int getAudioStreamCount() {
    if (!libMediaInfo) {
      return -1;
    }
    String count = getMediaInfo(StreamKind.Audio, 0, "StreamCount");
    if (!NumberUtils.isDigit(count)) {
      return 0;
    }

    return Integer.parseInt(count);
  }

  public String getAudioCodec(int stream) {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }

    String codec = getMediaInfo(StreamKind.Audio, stream, "CodecID/Hint", "Format");
    return codec == null ? StringUtils.EMPTY : codec.replaceAll("\\p{Punct}", StringUtils.EMPTY);
  }

  public String getAudioLanguage(int stream) {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }
    String language = getMediaInfo(StreamKind.Audio, stream, "Language/String");
    if (language == null) {
      return StringUtils.EMPTY;
    }

    return language.toLowerCase();
  }

  public String getAudioChannels(int stream) {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }
    String channels = getMediaInfo(StreamKind.Audio, stream, "Channel(s)");
    if (channels == null) {
      return StringUtils.EMPTY;
    }
    return channels + "ch";
  }

  public String getAudioTitle(int stream) {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }
    String title = getMediaInfo(StreamKind.Audio, stream, "Title");
    if (title == null) {
      return StringUtils.EMPTY;
    }
    return title;
  }

  private int getTextStreamCount() {
    if (!libMediaInfo) {
      return -1;
    }
    String count = getMediaInfo(StreamKind.Text, 0, "StreamCount");
    if (!NumberUtils.isDigit(count)) {
      return 0;
    }

    return Integer.parseInt(count);
  }

  public String getTextTitle(int stream) {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }
    String title = getMediaInfo(StreamKind.Text, stream, "Title");
    if (title == null) {
      return "Untitled";
    }
    return title;
  }

  public String getTextLanguage(int stream) {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }
    String lang = getMediaInfo(StreamKind.Text, stream, "Language/String");
    if (lang == null) {
      return StringUtils.EMPTY;
    }
    return lang;
  }

  private synchronized MediaInfo getMediaInfo() {
    if (mediaInfo == null) {
      MediaInfo newMediaInfo = new MediaInfo();
      if (!newMediaInfo.open(mediaFile)) {
        throw new RuntimeException("Cannot open media file: " + mediaFile);
      }

      mediaInfo = newMediaInfo;
    }

    return mediaInfo;
  }

  private String getMediaInfo(StreamKind streamKind, int streamNumber, String... keys) {
    for (String key : keys) {
      String value = getMediaInfo().get(streamKind, streamNumber, key);

      if (value.length() > 0) {
        return value;
      }
    }

    return null;
  }

  public List<MediaAudio> getMediaAudios() {
    List<MediaAudio> audios = new ArrayList<MediaAudio>();
    if (!libMediaInfo) {
      return audios;
    }

    for (int i = 0; i < getAudioStreamCount(); i++) {
      String title = getAudioTitle(i).trim();
      Locale lang = LocaleUtils.getLocale(getAudioLanguage(i).trim());
      if (title.equals(StringUtils.EMPTY) && lang.equals(Locale.ROOT)) {
        continue;
      }
      MediaAudio audio = new MediaAudio(i + 1);
      audio.setTitle(title);
      audio.setCodec(getAudioCodec(i));
      audio.setLanguage(lang);
      audio.setChannel(getAudioChannels(i));
      audios.add(audio);
    }

    return audios;
  }
  
  public List<MediaSubTitle> getMediaSubTitles() {
    List<MediaSubTitle> subTitles = new ArrayList<MediaSubTitle>();
    if (!libMediaInfo) {
      return subTitles;
    }

    for (int i = 0; i < getTextStreamCount(); i++) {
      String title = getTextTitle(i).trim();
      Locale lang = LocaleUtils.getLocale(getTextLanguage(i).trim());
      if (title.equals(StringUtils.EMPTY) && lang.equals(Locale.ROOT)) {
        continue;
      }
      MediaSubTitle subTitle = new MediaSubTitle(i + 1);
      subTitle.setTitle(title);
      subTitle.setLanguage(lang);
      subTitles.add(subTitle);
    }

    return subTitles;
  }

  public String getTagString(TagList tag, String separator, int limit) {
    if (!libMediaInfo) {
      return StringUtils.EMPTY;
    }

    int count = 0;
    switch (tag.getTagType()) {
      case Audio:
        count = getAudioStreamCount();
        break;
      case Text:
        count = getTextStreamCount();
        break;
      default:
        break;
    }

    StringBuilder res = new StringBuilder();
    for (int i = 0; i < count; i++) {
      String info = StringUtils.EMPTY;
      switch (tag) {
        case AudioChannel:
          info = getAudioChannels(i);
          break;
        case AudioCodec:
          info = getAudioCodec(i);
          break;
        case AudioLanguage:
          info = getAudioLanguage(i);
          break;
        case AudioTitleString:
          info = getAudioTitle(i);
          break;
        case TextTitle:
          info = getTextTitle(i);
          break;
        case TextLanguage:
          info = getTextLanguage(i);
          break;
        default:
          break;
      }

      if (!info.equals(StringUtils.EMPTY)) {
        res.append(info);
        if (i + 1 < count && limit <= 0 || i < limit - 1) {
          res.append(separator);
        }
      }
      if (limit > 0 && i == limit - 1) {
        break;
      }
    }
    return res.toString();
  }

  @Override
  public String toString() {
    String res = "Media Info : \n";
    res += "  Duration : " + getDuration() + " \n";
    res += "  File size : " + getFileSize() + " \n";
    res += "  Video codec : " + getVideoCodec() + " \n";
    res += "  Video definition category : " + getVideoDefinitionCategory() + " \n";
    res += "  Video format : " + getVideoFormat() + " \n";
    res += "  Video framerate : " + getVideoFrameRate() + " \n";
    res += "  Video resolution : " + getVideoResolution() + " \n";
    res += "  Container format : " + getContainerFormat() + " \n";
    List<MediaAudio> audios = getMediaAudios();
    for(MediaAudio audio : audios){
      res += audio.toString() + " \n";
    }
    List<MediaSubTitle> subTitles = getMediaSubTitles();
    for(MediaSubTitle subTitle : subTitles){
      res += subTitle.toString() + " \n";
    }
    return res;
  }
}
