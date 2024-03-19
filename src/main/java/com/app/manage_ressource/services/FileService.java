package com.app.manage_ressource.services;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import com.app.manage_ressource.dto.response.FileDto;
import com.app.manage_ressource.entities.Reponse;
import com.app.manage_ressource.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileService implements IFileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Value("${file.upload-dir}")
    private String fileStorageProperties;

    private Path initStoragePath(String subFolder) {
        Path rootPath = Paths.get(fileStorageProperties + subFolder + File.separatorChar).toAbsolutePath().normalize();
        try {
            Files.createDirectories(rootPath);
            return rootPath;
        } catch (IOException e) {
            logger.error("Echec de création du dossier de stockage des fichiers. ");

            throw new RuntimeException("Echec de création du dossier de stockage des fichiers.");
        }
    }

    @Override
    public Reponse uploadFile(MultipartFile file) {
        Reponse reponse = new Reponse();
        try {
            if (file != null &&(!file.getOriginalFilename().toLowerCase().endsWith(".pdf")
                    && !file.getOriginalFilename().toLowerCase().endsWith(".PDF") &&
                    !file.getOriginalFilename().toLowerCase().endsWith(".png")
                    && !file.getOriginalFilename().toLowerCase().endsWith(".PNG") &&
                    !file.getOriginalFilename().toLowerCase().endsWith(".jpg")
                    && !file.getOriginalFilename().toLowerCase().endsWith(".JPG")&&
                    !file.getOriginalFilename().toLowerCase().endsWith(".jpeg")
                    && !file.getOriginalFilename().toLowerCase().endsWith(".JPEG")))
            {
                reponse.setCode(201);
                logger.error("Ce format de fichier n'est pas autorisé. Réessayez avec un .pdf,png,jpg,jpeg ou .PDF,PNG,JPG,JPEG, SVP.= "+new Date());
                reponse.setMessage(" Ce format de fichier n'est pas autorisé. Réessayez avec un .pdf,png,jpg,jpeg ou .PDF,PNG,JPG,JPEG, SVP.");
            } else {
                Path destinationStockage = this.initStoragePath(Utility.ATTACHED_PIECE_FOLDER);//on cree un subFolder dans le folder de storage
                // Initialisation, enregistrement de meta-donnees et stockage du fichier
                String filename = StringUtils.cleanPath(file.getOriginalFilename());// Normalisation du nom du fichier
                destinationStockage = destinationStockage.resolve(filename);//formalise le path concatene du filename
                //transfert du fichier de la source locale vers la destination systeme
                //on le remplace s'il existe un fichier de meme nom
                Files.copy(file.getInputStream(), destinationStockage, StandardCopyOption.REPLACE_EXISTING);
                FileDto dtoFile= new FileDto(destinationStockage.toString(),filename);
                reponse.setData(dtoFile);
                reponse.setCode(200);
                logger.info("Le fichier  a été bien chargé = "+new Date());
                reponse.setMessage(" Le fichier  a été bien chargé");
            }


            return reponse;
        } catch (Exception e) {

            reponse.setCode(500);
            logger.error("Erreur survenue lors de l'enregistrement du fichier .= "+new Date());
            reponse.setMessage(" Erreur survenue lors de l'enregistrement du fichier.");
            return reponse;
        }

    }


    @Override
    public boolean deletedFile(String fileName) {

        Path destinationStockage = this.initStoragePath(Utility.ATTACHED_PIECE_FOLDER);//on cree un subFolder dans le folder de storage
        // Initialisation, enregistrement de meta-donnees et stockage du fichier
        destinationStockage = destinationStockage.resolve(fileName);//formalise le path concatene du filename

        // deleteIfExists File
        try {
            // File to be deleted
            File file = new File(destinationStockage.toString());
            if (file.exists()) {
                return file.delete();
            } else {
                return false;
            }

        } catch (Exception e) {

            return false;
        } finally {
            return false;
        }

    }

    @Override
    public String getPathFile(String filename) {
        Path destinationStockage = this.initStoragePath(Utility.ATTACHED_PIECE_FOLDER);//on cree un subFolder dans le folder de storage
        // Initialisation, enregistrement de meta-donnees et stockage du fichier
        destinationStockage = destinationStockage.resolve(filename);//formalise le path concatene du filename

        // deleteIfExists File
        try {
            // File to be deleted
            File file = new File(destinationStockage.toString());
            if (file.exists()) {
                return file.getAbsolutePath().toString();
            } else {
                return null;
            }

        } catch (Exception e) {

            return null;
        }

    }
    @Override
    public Resource loadFileAsResource(String filename)
    {
        try
        {
            Path destinationStockage = this.initStoragePath(Utility.ATTACHED_PIECE_FOLDER);//on cree un subFolder dans le folder de storage
            // Initialisation, enregistrement de meta-donnees et stockage du fichier
            destinationStockage = destinationStockage.resolve(filename);//formalise le path concatene du filename
            Resource resource = new UrlResource(destinationStockage.toUri());
            System.out.println(resource.exists());
            if(resource.exists())
            {
                return  resource;
            } else
            {
                return  null;
            }
        }
        catch (Exception ex)
        {
            return  null;
        }
    }
}

