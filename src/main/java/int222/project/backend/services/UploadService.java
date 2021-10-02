package int222.project.backend.services;

import int222.project.backend.exceptions.ExceptionResponse;
import int222.project.backend.exceptions.ImageHandlerException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

@Component
public class UploadService {
    final private ImageFilter imageFilter = new ImageFilter();

    public void saveImage(MultipartFile file, int roomId){
        try {
            String folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/room-storage/";
            byte[] bytes = file.getBytes();
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            System.out.println(extension);
            FileOutputStream outputStream = new FileOutputStream(folder + roomId + extension);
            outputStream.write(bytes);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("Could not save image.");
        }
    }

    public byte[] get(int roomId) {
        byte[] data = null;
        try{
            File file = getFile(roomId);
            BufferedImage image = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image,imageFilter.getExtension(file),bos);
            data = bos.toByteArray();
        }
        catch (ImageHandlerException e){
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("Could not get Image file.");
        }
        return data;
    }

    public Resource getImage(int roomId){
//        Image image = null;
        Resource resource = null;
        Path path = null;
        try {
            String folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/room-storage/";
            File[] listOfFile = ResourceUtils.getFile(folder).listFiles();
            if(listOfFile != null){
                for(File temp : listOfFile){
                    String extension = temp.getName().substring(temp.getName().lastIndexOf("."));
//                    System.out.println("extension : " +extension);
//                    System.out.println("product code + extension : " + productCode+extension);
//                    System.out.println("temp.getName() : "  + temp.getName());
                    if(temp.getName().equals(roomId+extension)){
                        path = temp.toPath();
                    }
                }
            }
            System.out.println(path.getFileName());
//            File file = ResourceUtils.getFile(folder + productCode + ".jpg");
//            image = ImageIO.read(file);
            resource = new UrlResource(path.toUri());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("Could not get Image file.");
        }
        return resource;
    }
    public void deleteImage(int roomId){
        try{
            File file = getFile(roomId);
            file.delete();
        }
        catch (ImageHandlerException e){
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("Could not delete Image file.");
        }
    }
    private File getFile(int roomId) throws ImageHandlerException,IOException {
        File file = null;
        String folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/room-storage/";
        File[] listOfFile = ResourceUtils.getFile(folder).listFiles();
        if(listOfFile != null) {
            for (File temp : listOfFile) {
                String extension = temp.getName().substring(temp.getName().lastIndexOf("."));
//                    System.out.println("extension : " +extension);
//                    System.out.println("product code + extension : " + roomId +extension);
//                    System.out.println("temp.getName() : "  + temp.getName());
                if (temp.getName().equals(roomId + extension)) {
                    file = temp;
                }
            }
        }
//        System.out.println(file.getName());
        if(file == null) throw new ImageHandlerException("No such a file name "+ roomId, ExceptionResponse.ERROR_CODE.IMAGE_DOES_NOT_EXISTS);
        return file;
    }
}
