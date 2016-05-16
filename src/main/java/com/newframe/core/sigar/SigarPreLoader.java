package com.newframe.core.sigar;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class SigarPreLoader {

    private static final String SIGAR_PATH = "org.hyperic.sigar.path";
    private static final Logger logger = LoggerFactory.getLogger(SigarFactory.class);

    public void setLibraryPath() {
        try {
            SigarLoader loader = new SigarLoader(Sigar.class);
            String libName = loader.getLibraryName();
            String sysTemp = System.getProperty("java.io.tmpdir");
            File tempFile = new File(sysTemp + File.separator + libName);

            InputStream inputStream = getClass().getResourceAsStream("/sigar/" + libName);
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            byte[] array = new byte[8192];
            for (int i = inputStream.read(array); i != -1; i = inputStream.read(array)) {
                outputStream.write(array, 0, i);
            }
            outputStream.close();
            tempFile.deleteOnExit();
            System.setProperty(SIGAR_PATH, sysTemp);
        } catch (Exception e) {
            logger.error("Can't set sigar library path!", e);
        }
    }
}

