package MainPackage;

import java.awt.*;
import java.io.Serializable;

public interface Drawable extends Serializable{
    public void render(Graphics2D G);
}
