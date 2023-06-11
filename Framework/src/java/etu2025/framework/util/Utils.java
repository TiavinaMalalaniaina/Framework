/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu2025.framework.util;

import etu2025.framework.Mapping;
import etu2025.framework.annotation.url;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author tiavi
 */
public class Utils {
    
    public static List<Class> getClassFrom(String packages) throws Exception {
        String path = packages.replaceAll("[.]", "/");
        URL packagesUrl = Thread.currentThread().getContextClassLoader().getResource(path);
        File packDir = new File(packagesUrl.toURI());
        File[] inside = packDir.listFiles();
        List<Class> lists = new ArrayList<>();
        for (File f : inside) {
            String c = packages + "." + f.getName().substring(0, f.getName().lastIndexOf("."));
            lists.add(Class.forName(c));
        }
        return lists;
    }
    
    public static HashMap<String, Mapping> getMappingUrls(Class<?> c) {
        HashMap<String, Mapping> mappingUrls = new HashMap<>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            url[] a = method.getAnnotationsByType(url.class);
            if (a.length > 0) {
                mappingUrls.put(a[0].value(), new Mapping(c.getSimpleName(), method.getName()));
            }
        }
        return mappingUrls;
    }
    
    
}
