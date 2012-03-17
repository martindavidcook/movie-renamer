/******************************************************************************
 *                                                                             *
 *    Movie Renamer                                                            *
 *    Copyright (C) 2011 Magré Nicolas                                         *
 *                                                                             *
 *    Movie Renamer is free software: you can redistribute it and/or modify    *
 *    it under the terms of the GNU General Public License as published by     *
 *    the Free Software Foundation, either version 3 of the License, or        *
 *    (at your option) any later version.                                      *
 *                                                                             *
 *    This program is distributed in the hope that it will be useful,          *
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of           *
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the            *
 *    GNU General Public License for more details.                             *
 *                                                                             *
 *    You should have received a copy of the GNU General Public License        *
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.    *
 *                                                                             *
 ******************************************************************************/
package fr.free.movierenamer.ui.res;

import fr.free.movierenamer.movie.MovieImage;
import fr.free.movierenamer.ui.MoviePanel;
import fr.free.movierenamer.utils.Cache;
import fr.free.movierenamer.utils.Settings;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.logging.Level;
import javax.imageio.ImageIO;

/**
 *
 * @author duffy
 */
public class DropImage implements DropTargetListener {

  private Settings setting;
  private MoviePanel moviePanel;
  private boolean thumb = false;
  private int cache;

  public DropImage(MoviePanel moviePanel, int cache, Settings setting) {
    this.moviePanel = moviePanel;
    this.setting = setting;
    this.cache = cache;
    if(cache == Cache.thumb) thumb = true;
  }

  @Override
  public void dragEnter(DropTargetDragEvent dtde) {
  }

  @Override
  public void dragOver(DropTargetDragEvent dtde) {
  }

  @Override
  public void dropActionChanged(DropTargetDragEvent dtde) {
  }

  @Override
  public void dragExit(DropTargetEvent dte) {
  }

  @Override
  public void drop(DropTargetDropEvent evt) {
    try {
      int action = evt.getDropAction();
      Transferable data = evt.getTransferable();
      evt.acceptDrop(action);

      if (data.isDataFlavorSupported(DataFlavor.stringFlavor)) {
        String dropedFile = (String) data.getTransferData(DataFlavor.stringFlavor);
        String[] res = dropedFile.split("\n");

        for (int i = 0; i < res.length; i++) {
          if (res[i].startsWith("file://")) {
            String file = URLDecoder.decode(res[i].replace("file://", "").replace("\n", ""), "UTF-8");
            file = file.substring(0, file.length() - 1);
            File f = new File(file);
            if (f.exists()){

              Image img = null;
              try{
                img = ImageIO.read(f);
              }
              catch(IllegalArgumentException e){
                continue;
              }
              
              MovieImage mvImg = new MovieImage("-1", thumb ? "poster":"fanart");
              mvImg.setMidUrl(res[i]);
              mvImg.setOrigUrl(res[i]);
              mvImg.setThumbUrl(res[i]);
              if(thumb) moviePanel.addThumbToList(img, mvImg, true);
              else moviePanel.addFanartToList(img, mvImg, true);
            }
          } else if (res[i].startsWith("http") || res[i].startsWith("www")) {
            URL url = new URL(res[i]);
            Image img = setting.cache.getImage(url, cache);
            if (img == null) {
              setting.cache.add(url.openStream(), url.toString(), cache);
              img = setting.cache.getImage(url, cache);
            }
            if (img != null){
              MovieImage mvImg = new MovieImage("-1", thumb ? "poster":"fanart");
              mvImg.setMidUrl(url.toString());
              mvImg.setOrigUrl(url.toString());
              mvImg.setThumbUrl(url.toString());
              if(thumb) moviePanel.addThumbToList(img, mvImg, true);
              else moviePanel.addFanartToList(img, mvImg, true);
            }
          }
        }
      }
    } catch (UnsupportedFlavorException ex) {
      setting.getLogger().log(Level.SEVERE, ex.toString());
    } catch (IOException ex) {
      setting.getLogger().log(Level.SEVERE, ex.toString());
    }
  }
}
