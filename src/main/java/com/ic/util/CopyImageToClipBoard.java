package com.ic.util;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class CopyImageToClipBoard implements ClipboardOwner {
  public CopyImageToClipBoard(Image image) {
    try {
      TransferableImage trans = new TransferableImage(image);
      Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
      c.setContents(trans, this);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void lostOwnership(Clipboard clip, Transferable trans) {
    System.out.println("Lost Clipboard Ownership");
  }

  private class TransferableImage implements Transferable {

    Image i;

    public TransferableImage(Image i) {
      this.i = i;
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
      if (flavor.equals(DataFlavor.imageFlavor) && i != null) {
        return i;
      } else {
        throw new UnsupportedFlavorException(flavor);
      }
    }

    public DataFlavor[] getTransferDataFlavors() {
      DataFlavor[] flavors = new DataFlavor[1];
      flavors[0] = DataFlavor.imageFlavor;
      return flavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
      DataFlavor[] flavors = getTransferDataFlavors();
      for (DataFlavor flavor1 : flavors) {
        if (flavor.equals(flavor1)) {
          return true;
        }
      }

      return false;
    }
  }
}