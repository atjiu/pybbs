package com.jfinalbbs.common;


import com.jfinal.plugin.activerecord.Model;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class BaseModel<T extends Model> extends Model<T> {

    public String formatDate(Date date) {
        PrettyTime prettyTime = new PrettyTime(Locale.CHINA);
        return prettyTime.format(date);
    }

}
