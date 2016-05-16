//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.newframe.core.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.log4j.Logger;

public class PackagesToScanUtil {
    private static final Logger logger = Logger.getLogger(PackagesToScanUtil.class);
    private static final String SUB_PACKAGE_SCREEN__SUFFIX = ".*";
    private static final String SUB_PACKAGE_SCREEN__SUFFIX_RE = ".\\*";

    public PackagesToScanUtil() {
    }

    public static Set<Class<?>> getClasses(String pack) {
        boolean recursive = false;
        String[] packArr = new String[0];
        if(pack.lastIndexOf(".*") != -1) {
            packArr = pack.split(".\\*");
            if(packArr.length > 1) {
                pack = packArr[0];

                for(int classes = 0; classes < packArr.length; ++classes) {
                    packArr[classes] = packArr[classes].replace(".*".substring(1), "");
                }
            } else {
                pack = pack.replace(".*", "");
            }

            recursive = true;
        }

        LinkedHashSet var11 = new LinkedHashSet();
        String packageName = pack;
        String packageDirName = pack.replace('.', '/');

        try {
            Enumeration dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

            while(dirs.hasMoreElements()) {
                URL e = (URL)dirs.nextElement();
                String protocol = e.getProtocol();
                if("file".equals(protocol)) {
                    logger.debug("-------------- file类型的扫描 ----------------");
                    String filePath = URLDecoder.decode(e.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName, packArr, filePath, recursive, var11);
                } else if("jar".equals(protocol)) {
                    findAndAddClassesInPackageByJarFile(packageName, packArr, e, packageDirName, recursive, var11);
                }
            }
        } catch (IOException var10) {
            var10.printStackTrace();
        }

        return var11;
    }

    private static void findAndAddClassesInPackageByFile(String packageName, String[] packArr, String packagePath, final boolean recursive, Set<Class<?>> classes) {
        File dir = new File(packagePath);
        if(dir.exists() && dir.isDirectory()) {
            File[] dirfiles = dir.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return recursive && file.isDirectory() || file.getName().endsWith(".class");
                }
            });
            File[] var10 = dirfiles;
            int var9 = dirfiles.length;

            for(int var8 = 0; var8 < var9; ++var8) {
                File file = var10[var8];
                if(file.isDirectory()) {
                    findAndAddClassesInPackageByFile(packageName + "." + file.getName(), packArr, file.getAbsolutePath(), recursive, classes);
                } else {
                    String className = file.getName().substring(0, file.getName().length() - 6);

                    try {
                        String e = packageName + '.' + className;
                        if(e.startsWith(".")) {
                            e = e.replaceFirst(".", "");
                        }

                        boolean flag = true;
                        if(packArr.length > 1) {
                            for(int i = 1; i < packArr.length; ++i) {
                                if(e.indexOf(packArr[i]) <= -1) {
                                    flag = false;
                                } else {
                                    flag = flag;
                                }
                            }
                        }

                        if(flag) {
                            classes.add(Thread.currentThread().getContextClassLoader().loadClass(e));
                        }
                    } catch (ClassNotFoundException var15) {
                        logger.error("添加用户自定义视图类错误 找不到此类的.class文件");
                        var15.printStackTrace();
                    }
                }
            }

        }
    }

    private static void findAndAddClassesInPackageByJarFile(String packageName, String[] packArr, URL url, String packageDirName, boolean recursive, Set<Class<?>> classes) {
        logger.debug("------------------------ jar类型的扫描 ----------------------");

        try {
            JarFile jar = ((JarURLConnection)url.openConnection()).getJarFile();
            Enumeration e = jar.entries();

            while(true) {
                JarEntry entry;
                String name;
                do {
                    int idx;
                    do {
                        do {
                            do {
                                if(!e.hasMoreElements()) {
                                    return;
                                }

                                entry = (JarEntry)e.nextElement();
                                name = entry.getName();
                                if(name.charAt(0) == 47) {
                                    name = name.substring(1);
                                }
                            } while(!name.startsWith(packageDirName));

                            idx = name.lastIndexOf(47);
                            if(idx != -1) {
                                packageName = name.substring(0, idx).replace('/', '.');
                            }
                        } while(idx == -1 && !recursive);
                    } while(!name.endsWith(".class"));
                } while(entry.isDirectory());

                String className = name.substring(packageName.length() + 1, name.length() - 6);

                try {
                    boolean e1 = true;
                    if(packArr.length > 1) {
                        for(int i = 1; i < packArr.length; ++i) {
                            if(packageName.indexOf(packArr[i]) <= -1) {
                                e1 = false;
                            } else {
                                e1 = e1;
                            }
                        }
                    }

                    if(e1) {
                        classes.add(Class.forName(packageName + '.' + className));
                    }
                } catch (ClassNotFoundException var14) {
                    logger.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    var14.printStackTrace();
                }
            }
        } catch (IOException var15) {
            logger.error("在扫描用户定义视图时从jar包获取文件出错");
            var15.printStackTrace();
        }
    }
}
