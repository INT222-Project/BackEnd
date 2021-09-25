package int222.project.backend.services;

import java.io.File;

public class ImageFilter {
        final String GIF = "gif";
        final String PNG = "png";
        final String JPG = "jpg";
        final String BMP = "bmp";
        final String JPEG = "jpeg";

        public boolean accept(File file) {
            if(file != null) {
                if(file.isDirectory())
                    return false;
                String extension = getExtension(file);
                if(extension != null && isSupported(extension))
                    return true;
            }
            return false;
        }

        public String getExtension(File file) {
            if(file != null) {
                String filename = file.getName();
                int dot = filename.lastIndexOf('.');
                if(dot > 0 && dot < filename.length()-1)
                    return filename.substring(dot+1).toLowerCase();
            }
            return null;
        }

        private boolean isSupported(String ext) {
            return ext.equalsIgnoreCase(GIF) || ext.equalsIgnoreCase(PNG) ||
                    ext.equalsIgnoreCase(JPG) || ext.equalsIgnoreCase(BMP) ||
                    ext.equalsIgnoreCase(JPEG);
        }
}
