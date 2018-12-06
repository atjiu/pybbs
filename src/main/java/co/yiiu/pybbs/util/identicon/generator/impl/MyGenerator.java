package co.yiiu.pybbs.util.identicon.generator.impl;

import co.yiiu.pybbs.util.identicon.generator.IBaseGenartor;

import java.awt.*;
import java.util.Random;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
public class MyGenerator implements IBaseGenartor {

  private Random random = new Random();

  @Override
  public boolean[][] getBooleanValueArray(String hash) {

    boolean[][] array = new boolean[5][5];

    //初始化字符串
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        array[i][j] = false;
      }
    }

    for (int i = 0; i < 15; i++) {
      if (i % 3 == 0) {
        boolean b = random.nextBoolean();
        array[i / 3][0] = b;
        array[i / 3][4] = b;
      } else if (i % 3 == 1) {
        boolean b = random.nextBoolean();
        array[i / 3][1] = b;
        array[i / 3][3] = b;
      } else {
        boolean b = random.nextBoolean();
        array[i / 3][2] = b;
      }
    }

    return array;
  }

  @Override
  public Color getBackgroundColor() {
    return new Color(236, 236, 236);
  }

  @Override
  public Color getForegroundColor() {
    int r = random.nextInt(256);
    int g = random.nextInt(256);
    int b = random.nextInt(256);
    return new Color(r, g, b);
  }
}
