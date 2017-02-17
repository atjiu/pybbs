package cn.tomoya.util.identicon.generator;

import java.awt.*;

/**
 * Author: Bryant Hang
 * Date: 15/1/10
 * Time: 下午2:43
 */
public interface IBaseGenartor {
    /**
     * Converts a hash string to a bool 2D 6 * 5 array
     *
     * @param hash
     * @return
     */
    public boolean[][] getBooleanValueArray(String hash);


    /**
     * Get the picture background color
     *
     * @return
     */
    public Color getBackgroundColor();


    /**
     * Get the picture foreground color
     *
     * @return
     */
    public Color getForegroundColor();
}
