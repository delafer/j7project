package org.delafer.xmlbench.gui;


import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public class FlowLayout extends Layout
{
  public static final int LEFT = 0;
  public static final int CENTER = 1;
  public static final int RIGHT = 2;
  public static final int LEADING = 3;
  public static final int TRAILING = 4;
  private int _alignment;
  private int _hgap;
  private int _vgap;

  public FlowLayout()
  {
    this(1, 5, 5);
  }

  public FlowLayout(int align) {
    this(align, 5, 5);
  }

  public FlowLayout(int align, int hgap, int vgap) {
    setHgap(hgap);
    setVgap(vgap);
    setAlignment(align);
  }

  protected void layout(Composite target, boolean flushCache)
  {
    Rectangle r = target.getClientArea();

    Control[] children = target.getChildren();
    int maxwidth = r.width - getHgap() * 2;
    int nmembers = children.length;
    int x = 0; int y = getVgap();
    int rowh = 0; int start = 0;

    boolean ltr = true;

    for (int i = 0; i < nmembers; i++) {
      Control m = children[i];
      if (m.isVisible()) {
        Point d = m.computeSize(-1, -1);
        m.setSize(d.x, d.y);

        if ((x == 0) || (x + d.x <= maxwidth)) {
          if (x > 0) {
            x += getHgap();
          }
          x += d.x;
          rowh = Math.max(rowh, d.y);
        }
        else {
          moveComponents(target, r, getHgap(), y, maxwidth - x, rowh, start, i, ltr);
          x = d.x;
          y += getVgap() + rowh;
          rowh = d.y;
          start = i;
        }
      }
    }
    moveComponents(target, r, getHgap(), y, maxwidth - x, rowh, start, nmembers, ltr);
  }

  protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache)
  {
    Point size = new Point(0, 0);
    Control[] children = composite.getChildren();

    boolean firstVisibleComponent = true;

    for (int i = 0; i < children.length; i++) {
      Control m = children[i];
      if (m.isVisible()) {
        Point d = m.computeSize(-1, -1);
        size.y = Math.max(size.y, d.y);
        if (firstVisibleComponent) {
          firstVisibleComponent = false;
        }
        else {
          size.x += getHgap();
        }
        size.x += d.x;
      }
    }
    size.x += getHgap() * 2;
    size.y += getVgap() * 2;
    return size;
  }

  public int getAlignment()
  {
    return _alignment;
  }

  public void setAlignment(int align)
  {
    _alignment = align;
  }

  public int getHgap()
  {
    return _hgap;
  }

  public void setHgap(int hgap)
  {
    _hgap = hgap;
  }

  public int getVgap()
  {
    return _vgap;
  }

  public void setVgap(int vgap)
  {
    _vgap = vgap;
  }

  private void moveComponents(Composite target, Rectangle r, int x, int y, int width, int height, int rowStart, int rowEnd, boolean ltr)
  {
    switch (getAlignment()) {
    case 0:
      x += (ltr ? 0 : width);
      break;
    case 1:
      x += width / 2;
      break;
    case 2:
      x += (ltr ? width : 0);
      break;
    case 3:
      break;
    case 4:
      x += width;
    }

    Control[] children = target.getChildren();

    for (int i = rowStart; i < rowEnd; i++) {
      Control m = children[i];
      Point size = m.getSize();
      if (m.isVisible()) {
        if (ltr)
          m.setLocation(x, y + (height - size.y) / 2);
        else {
          m.setLocation(r.width - x - size.x, y + (height - size.y) / 2);
        }
        x += size.x + getHgap();
      }
    }
  }

  public String toString()
  {
    return getClass().getName() + "[" + paramString() + "]";
  }

  protected String paramString() {
    String str = "";
    switch (getAlignment()) { case 0:
      str = ",align=left"; break;
    case 1:
      str = ",align=center"; break;
    case 2:
      str = ",align=right"; break;
    case 3:
      str = ",align=leading"; break;
    case 4:
      str = ",align=trailing";
    }
    return "hgap=" + getHgap() + ",vgap=" + getVgap() + str;
  }
}