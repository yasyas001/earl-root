package com.earl.Tools;

import org.springframework.web.servlet.view.InternalResourceView;

import java.io.File;
import java.util.Locale;

public class HtmlResourceView extends InternalResourceView {

    @Override
    public boolean checkResource(Locale locale) throws Exception {
        File file = new File(this.getServletContext().getRealPath("/") + getUrl());
        return file.exists();
    }
}
