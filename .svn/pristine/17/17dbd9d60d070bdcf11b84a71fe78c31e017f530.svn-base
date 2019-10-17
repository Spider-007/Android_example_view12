package com.htmitech.proxy.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by htrf-pc on 2016/11/28.
 */
public class ClassLoaderUtil extends URLClassLoader {

    public ClassLoaderUtil(URL[] urls) {
        super (urls);
    }

    public ClassLoaderUtil(URL[] urls, ClassLoader parent) {
        super (urls, parent);
    }

    public Class load(String name)
            throws ClassNotFoundException {
        return load(name, false );
    }

    public Class load(String name, boolean resolve)
            throws ClassNotFoundException {
        if ( null != super .findLoadedClass(name))
            return reload(name, resolve);

        Class clazz = super .findClass(name);

        if (resolve)
            super .resolveClass(clazz);

        return clazz;
    }

    public Class reload(String name, boolean resolve)
            throws ClassNotFoundException {
        return new ClassLoaderUtil( super .getURLs(), super .getParent()).load(
                name, resolve);
    }
}
