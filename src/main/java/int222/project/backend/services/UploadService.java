package int222.project.backend.services;

import int222.project.backend.exceptions.ExceptionResponse;
import int222.project.backend.exceptions.ImageHandlerException;
import int222.project.backend.models.Admin;
import int222.project.backend.models.Customer;
import int222.project.backend.models.Receptionist;
import int222.project.backend.models.Room;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;

@Component
public class UploadService {
    final private ImageFilter imageFilter = new ImageFilter();

    public void saveImage(MultipartFile file, String id,Class<? extends Object> tClass){
        try {
            String folder = "";
            if(tClass.equals(Customer.class)){
                folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/user-storage/customer/";
            }
            else if(tClass.equals(Receptionist.class)){
                folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/user-storage/receptionist/";
            }
            else if(tClass.equals(Admin.class)){
                folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/user-storage/admin/";
            }
            else{
                folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/room-storage/";
            }
            byte[] bytes = file.getBytes();
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//            System.out.println(folder);
            System.out.println(extension);
            FileOutputStream outputStream = new FileOutputStream(folder + id + extension);
            outputStream.write(bytes);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("Could not save image.");
        }
    }

    public byte[] get(String id,Class<? extends Object> tClass) {
        byte[] data = null;
        File file = null;
        try{
            if(tClass.equals(Customer.class)){
                 file = getCustomerFile(id);
            }
            else if(tClass.equals(Receptionist.class)){
                file = getReceptionistFile(id);
            }
            else if(tClass.equals(Admin.class)){
                file = getAdminFile(id);
            }
            else if(tClass.equals(Room.class)){
                file = getRoomFile(id);
            }
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

    private File getAdminFile(String id) throws ImageHandlerException,IOException{
        String folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/user-storage/admin/";
        File file = getFile(id,folder);
        if(file != null){
            return file;
        }
        else{
            return getAdminFile("default");
        }
    }

    private File getReceptionistFile(String id) throws ImageHandlerException,IOException{
        String folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/user-storage/receptionist/";
        File file = getFile(id,folder);
        if(file != null){
            return file;
        }
        else{
            return getReceptionistFile("default");
        }
    }

    private File getCustomerFile(String id) throws ImageHandlerException,IOException {
        String folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/user-storage/customer/";
        File file = getFile(id,folder);
        if(file != null){
            return file;
        }
        else{
            return getCustomerFile("default");
        }
    }

    private File getRoomFile(String id) throws ImageHandlerException,IOException {
        String folder = new File(".").getCanonicalPath() + "/src/main/resources/storage/room-storage/";
        File file = getFile(id,folder);
        if(file != null){
            return file;
        }
        else{
            throw new ImageHandlerException("No such a file name "+ id, ExceptionResponse.ERROR_CODE.IMAGE_DOES_NOT_EXISTS);
        }
    }

    private File getFile(String id, String folder) throws FileNotFoundException {
        File file =  null;
        File[] listOfFile = ResourceUtils.getFile(folder).listFiles();
        if(listOfFile != null) {
            for (File temp : listOfFile) {
                String extension = temp.getName().substring(temp.getName().lastIndexOf("."));
//                    System.out.println("extension : " +extension);
//                    System.out.println("product code + extension : " + roomId +extension);
//                    System.out.println("temp.getName() : "  + temp.getName());
                if (temp.getName().equals(id + extension)) {
                    file = temp;
                }
            }
        }
        return file;
    }

    public void deleteImage(String id,Class<? extends Object> tClass){
        File file = null;
        try{
            if(tClass.equals(Customer.class)){
                file = getCustomerFile(id);
            }
            else if(tClass.equals(Receptionist.class)){
                file = getReceptionistFile(id);
            }
            else if(tClass.equals(Admin.class)){
                file = getAdminFile(id);
            }
            else if(tClass.equals(Room.class)){
                file = getRoomFile(id);
            }
            else{
                throw new IOException();
            }
            if(!file.getName().equals("default.png")) file.delete();
            else throw new IOException();
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
}
