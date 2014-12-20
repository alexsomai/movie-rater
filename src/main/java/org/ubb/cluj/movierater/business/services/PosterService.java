package org.ubb.cluj.movierater.business.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by somai on 20.12.2014.
 */
@Service
public class PosterService {

    private static final String MOVIE_POSTER_PATH = "/var/app/movie-rater/poster";
    private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
    private static final long MAX_FILE_SIZE = 5096000;

    private Pattern pattern;

    public PosterService() {
        pattern = Pattern.compile(IMAGE_PATTERN);
    }

    public String validatePoster(MultipartFile poster) {
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
        try {
            poster.transferTo(new File(MOVIE_POSTER_PATH, fileName));
        } catch (IOException e) {
            // if any error occurs while saving the file, set a default file to be used
            e.printStackTrace();
            return "default_file";
        }
        return fileName;
//        try {
//            byte[] bytes = poster.getBytes();
//            BufferedOutputStream buffStream =
//                    new BufferedOutputStream(new FileOutputStream(new File(MOVIE_POSTER_PATH, fileName)));
//            buffStream.write(bytes);
//            buffStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
