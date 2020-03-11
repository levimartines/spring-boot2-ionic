package com.levimartines.cursomc.service;

import com.levimartines.cursomc.exceptions.FileException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    public BufferedImage getJpgFromFile(MultipartFile file) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!"png".equals(ext) && !"jpg".equals(ext)) {
            throw new FileException("Somente imagens PNG e JPG são permitidas");
        }
        try {
            BufferedImage img = ImageIO.read(file.getInputStream());
            if ("png".equals(ext)) {
                img = pngToJpg(img);
            }
            return img;
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
    }

    public BufferedImage pngToJpg(BufferedImage img) {
        BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(),
            BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
        return jpgImage;
    }

    public InputStream getInputStream(BufferedImage img, String extension) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(img, extension, outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
    }
}
