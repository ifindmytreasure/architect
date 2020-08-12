package com.tristeza.springframework.utils;

import com.tristeza.springframework.enums.ProtocolType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author chaodong.xi
 * @date 2020/8/11 10:14 下午
 */
public class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);
    private static final String CLASS_SUFFIX = ".class";
    private static final String CLASS_SEPARATOR = ".";

    public static Set<Class<?>> extractPackageClass(String packageName) {
        ClassLoader classLoader = getClassLoader();
        URL url = classLoader.getResource(packageName.replace('.', '/'));
        if (Objects.isNull(url)) {
            LOGGER.warn("unable to retrieve anything from package:{}", packageName);
            return null;
        }
        Set<Class<?>> classSet = new HashSet<>();
        ;
        if (url.getProtocol().equalsIgnoreCase(ProtocolType.FILE.getCode())) {
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet, packageDirectory, packageName);
        }
        return classSet;
    }

    private static void extractClassFile(Set<Class<?>> classSet, File fileSource, String packageName) {
        if (!fileSource.isDirectory()) {
            return;
        }
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                String absolutePath = file.getAbsolutePath();
                if (absolutePath.endsWith(CLASS_SUFFIX)) {
                    add2ClassSet(absolutePath);
                }
                return false;
            }

            private void add2ClassSet(String absolutePath) {
                absolutePath = absolutePath.replace(File.separator, ".");

                String className = absolutePath.substring(absolutePath.indexOf(packageName));
                className = className.substring(0, className.lastIndexOf(CLASS_SEPARATOR));

                Class<?> clazz = loadClass(className);
                classSet.add(clazz);
            }
        });
        if (Objects.nonNull(files)) {
            Arrays.stream(files).forEach(file -> extractClassFile(classSet, file, packageName));
        }
    }

    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            LOGGER.error("load class error", e);
            throw new RuntimeException("load class error", e);
        }
    }

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
