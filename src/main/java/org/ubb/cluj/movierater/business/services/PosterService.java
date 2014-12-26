package org.ubb.cluj.movierater.business.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(PosterService.class);
    private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
    private static final long MAX_FILE_SIZE = 2048000;
    private static final String DEFAULT_POSTER = "default_poster.png";

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
        if (poster.isEmpty()) {
            return "Poster file may not be empty!";
        }

        if (poster.getSize() > MAX_FILE_SIZE) {
            return "Poster file size may not be larger than 2MB!";
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
            if (!savingDirectory.mkdirs()) {
                LOGGER.error("For some reason, the " + savingDirectory.getAbsolutePath() +
                        " directory could not be created! A default image will be used.");
                // if any error occurs while making the directory for files, set a default file to be used
                return DEFAULT_POSTER;
            }
        }

        File savingFile = new File(savingDirectory, fileName);

        try {
            poster.transferTo(savingFile);
        } catch (IOException e) {
            LOGGER.error("An error occurred while saving the poster file! A default image will be used.", e);
            // if any error occurs while saving the file, set a default file to be used
            return DEFAULT_POSTER;
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
