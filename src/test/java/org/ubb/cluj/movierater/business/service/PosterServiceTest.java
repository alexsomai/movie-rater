package org.ubb.cluj.movierater.business.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

import static java.io.File.separator;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author Alexandru Somai
 *         date 12/29/2015
 */
@RunWith(MockitoJUnitRunner.class)
public class PosterServiceTest {

    @InjectMocks
    private PosterService posterService = new PosterService();

    @Mock
    private ServletContext servletContext;

    @Test
    public void shouldSavePoster() {
        // arrange
        MultipartFile multipartFile = new MockMultipartFile("name", "originalFileName", "content", new byte[2]);
        when(servletContext.getRealPath("/")).thenReturn(System.getProperty("user.dir"));

        // act
        String result = posterService.savePoster(multipartFile);

        // assert
        assertThat(result, containsString("originalFileName"));
    }

    @Test
    public void savePosterShouldThrowIoException() throws IOException {
        // arrange
        MultipartFile multipartFile = spy(new MockMultipartFile("name", "originalFileName", "content", new byte[2]));
        doThrow(new IOException()).when(multipartFile).transferTo(any(File.class));

        // act
        String result = posterService.savePoster(multipartFile);

        // assert
        assertThat(result, is("default_poster.png"));
    }


    @Test
    public void shouldValidatePoster() {
        // arrange
        MultipartFile multipartFile = new MockMultipartFile("name", "originalFileName.jpg", "content", new byte[2]);

        // act
        String result = posterService.validatePoster(multipartFile);

        // assert
        assertThat(result, is(nullValue()));
    }

    @Test
    public void shouldValidatePosterEmptyFile() {
        // arrange
        MultipartFile multipartFile = new MockMultipartFile("name", "originalFileName.jpg", "content", new byte[0]);

        // act
        String result = posterService.validatePoster(multipartFile);

        // assert
        assertThat(result, is("Poster file may not be empty!"));
    }

    @Test
    public void shouldValidatePosterMaxFileSize() {
        // arrange
        MultipartFile multipartFile = new MockMultipartFile("name", "originalFileName.jpg", "content", new byte[2048001]);

        // act
        String result = posterService.validatePoster(multipartFile);

        // assert
        assertThat(result, is("Poster file size may not be larger than 2MB!"));
    }

    @Test
    public void shouldValidatePosterWrongFormat() {
        // arrange
        MultipartFile multipartFile = new MockMultipartFile("name", "originalFileName", "content", new byte[2]);

        // act
        String result = posterService.validatePoster(multipartFile);

        // assert
        assertThat(result, is("Only .jpg, .png, .gif and .bmp file types are allowed!"));
    }

    @Test
    public void shouldGetRelativeSavingFolder() {
        // act
        String result = PosterService.getRelativeSavingFolder();

        // assert
        String expectedPath = separator + "resources" + separator + "images" + separator + "movie-poster" + separator;
        assertThat(result, is(expectedPath));
    }
}