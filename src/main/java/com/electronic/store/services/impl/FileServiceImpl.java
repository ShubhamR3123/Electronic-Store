package com.electronic.store.services.impl;

import com.electronic.store.exceptions.BadApiRequest;
import com.electronic.store.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    /**
     * @author Shubham Dhokchaule
     * @apiNote This Method is used For Uplod Image
     * @param file
     * @param path
     * @return
     */
    @Override
    public String uplodImage(MultipartFile file, String path) throws IOException {
        log.info("Initiated Dao call for uplod user image ");
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullPathWithFileName = path + fileNameWithExtension;
        log.info("Full image path:{}",fullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".pdf")) {

            //file save
            log.info("File extension is:{}",extension);
            File folder = new File(path);
            if (!folder.exists()) {

                //create folder
               folder.mkdirs();
            }
            //uplod
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            log.info("Completed Dao call for uplod user image ");
            return fileNameWithExtension;
        } else {
            throw new BadApiRequest("File wit this" + extension + "Not Found ...!!" + extension);

        }

    }

    /**
     * @param file
     * @param path
     * @return
     * @throws IOException
     */
    @Override
    public String uplodFile(MultipartFile file, String path) throws IOException {
        log.info("Initiated Dao call for uplod user image ");
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullPathWithFileName = path + fileNameWithExtension;
        log.info("Full image path:{}",fullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".pdf")) {

            //file save
            log.info("File extension is:{}",extension);
            File folder = new File(path);
            if (!folder.exists()) {

                //create folder
                folder.mkdirs();
            }
            //uplod
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            log.info("Completed Dao call for uplod user image ");
            return fileNameWithExtension;
        } else {
            throw new BadApiRequest("File wit this" + extension + "Not Found ...!!" + extension);

        }

    }



    /**
     * @author Shubham Dhokchaule
     * @apiNote This Method is used for serve Image
     * @param path
     * @param name
     * @return
     */
    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath=path+File.separator + name;
        InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }
}
