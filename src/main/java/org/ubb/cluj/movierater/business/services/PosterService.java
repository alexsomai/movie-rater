package org.ubb.cluj.movierater.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by somai on 20.12.2014.
 */
@Service
public class PosterService {

    private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
    private static final long MAX_FILE_SIZE = 5096000;

    @Autowired
    ServletContext servletContext;

    private Pattern pattern;

    public PosterService() {
        pattern = Pattern.compile(IMAGE_PATTERN);
    }

    public static String getRelativeSavingFolder() {
        return File.separator + "resources" + File.separator + "images" +
                File.separator + "movie-poster" + File.separator;
    }

    public String validatePoster(MultipartFile poster) {

        // TODO
//        BufferedImage image = ImageIO.read(poster.getInputStream());
//        Integer width = image.getWidth();
//        Integer height = image.getHeight();
        // if width and height doesn't match, will return an error
        if (poster.isEmpty()) {
            return "Poster file may not be empty!";
        }

        if (poster.getSize() > MAX_FILE_SIZE) {
            return "Poster file size may not be larger than 5MB!";
        }

        String fileName = poster.getOriginalFilename();
        if (!validateExtension(fileName)) {
            return "Only .jpg, .png, .gif and .bmp file types are allowed!";
        }
        return null;
    }

    public String savePoster(MultipartFile poster) {
        String fileName = System.nanoTime() + "_" + poster.getOriginalFilename();
        String webAppRoot = servletContext.getRealPath("/");

        File savingDirectory = new File(webAppRoot, getRelativeSavingFolder());
        if (!savingDirectory.exists()) {
            savingDirectory.mkdirs();
        }

        File savingFile = new File(savingDirectory, fileName);

        try {
            poster.transferTo(savingFile);
        } catch (IOException e) {
            // if any error occurs while saving the file, set a default file to be used
            e.printStackTrace();
            return "default_poster";
        }
        return fileName;
    }

    /**
     * Validate image with regular expression
     *
     * @param image image for validation
     * @return true valid image, false invalid image
     */
    private boolean validateExtension(final String image) {
        Matcher matcher = pattern.matcher(image);
        return matcher.matches();
    }

}
