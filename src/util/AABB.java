package util;

import java.awt.*;

public class AABB {
    float x;
    float y;
    float width;
    float height;

    public AABB(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public AABB(Rectangle rect) {
        this.x = rect.x;
        this.y = rect.y;
        this.width = rect.width;
        this.height = rect.height;
    }

    public AABB(float width, float height) {
        this(0, 0, width, height);
    }

    public boolean intersects(AABB r) {
        float tw = this.width;
        float th = this.height;
        float rw = r.width;
        float rh = r.height;
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        float tx = this.x;
        float ty = this.y;
        float rx = r.x;
        float ry = r.y;
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        //      overflow || intersect
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }

    public void grow(float h, float v) {
        float x0 = this.x;
        float y0 = this.y;
        float x1 = this.width;
        float y1 = this.height;
        x1 += x0;
        y1 += y0;

        x0 -= h;
        y0 -= v;
        x1 += h;
        y1 += v;

        if (x1 < x0) {
            // Non-existent in X direction
            // Final width must remain negative so subtract x0 before
            // it is clipped so that we avoid the risk that the clipping
            // of x0 will reverse the ordering of x0 and x1.
            x1 -= x0;
            if (x1 < Float.MIN_VALUE) x1 = Float.MIN_VALUE;
            if (x0 < Float.MIN_VALUE) x0 = Float.MIN_VALUE;
            else if (x0 > Float.MAX_VALUE) x0 = Float.MAX_VALUE;
        } else { // (x1 >= x0)
            // Clip x0 before we subtract it from x1 in case the clipping
            // affects the representable area of the rectangle.
            if (x0 < Float.MIN_VALUE) x0 = Float.MIN_VALUE;
            else if (x0 > Float.MAX_VALUE) x0 = Float.MAX_VALUE;
            x1 -= x0;
            // The only way x1 can be negative now is if we clipped
            // x0 against MIN and x1 is less than MIN - in which case
            // we want to leave the width negative since the result
            // did not intersect the representable area.
            if (x1 < Float.MIN_VALUE) x1 = Float.MIN_VALUE;
            else if (x1 > Float.MAX_VALUE) x1 = Float.MAX_VALUE;
        }

        if (y1 < y0) {
            // Non-existent in Y direction
            y1 -= y0;
            if (y1 < Float.MIN_VALUE) y1 = Float.MIN_VALUE;
            if (y0 < Float.MIN_VALUE) y0 = Float.MIN_VALUE;
            else if (y0 > Float.MAX_VALUE) y0 = Float.MAX_VALUE;
        } else { // (y1 >= y0)
            if (y0 < Float.MIN_VALUE) y0 = Float.MIN_VALUE;
            else if (y0 > Float.MAX_VALUE) y0 = Float.MAX_VALUE;
            y1 -= y0;
            if (y1 < Float.MIN_VALUE) y1 = Float.MIN_VALUE;
            else if (y1 > Float.MAX_VALUE) y1 = Float.MAX_VALUE;
        }

        reshape(x0, y0, x1, y1);
    }

    public void reshape(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

}
