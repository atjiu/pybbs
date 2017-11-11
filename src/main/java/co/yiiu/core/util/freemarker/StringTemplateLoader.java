package co.yiiu.core.util.freemarker;

import com.google.common.collect.Maps;
import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

/**
 * Created by teddyzhu.
 * Copyright (c) 2017, All Rights Reserved.
 */
public class StringTemplateLoader implements TemplateLoader {
    private static final String DEFAULT_TEMPLATE_KEY = "_default_template_key";
    private Map templates = Maps.newConcurrentMap();

    public StringTemplateLoader(String defaultTemplate) {
        if (defaultTemplate != null && !defaultTemplate.equals("")) {
            templates.put(DEFAULT_TEMPLATE_KEY, defaultTemplate);
        }
    }

    @Override
    public void closeTemplateSource(Object templateSource)
            throws IOException {

    }

    @Override
    public Object findTemplateSource(String name) throws IOException {
        if (name == null || "".equals(name)) {
            name = DEFAULT_TEMPLATE_KEY;
        }
        return templates.get(name);
    }

    @Override
    public long getLastModified(Object templateSource) {
        return 0;
    }

    @Override
    public Reader getReader(Object templateSource, String encoding)
            throws IOException {
        return new StringReader((String) templateSource);
    }
}
