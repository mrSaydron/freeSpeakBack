package ru.mrak.web.rest;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mrak.service.FileService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileResource {

    private final Logger log = LoggerFactory.getLogger(FileResource.class);

    private final FileService fileService;

    @PostMapping("/picture")
    public String saveFile(@RequestParam("file") MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.debug("Rest request for save file");
        return fileService.savePictureFile(file);
    }

    @GetMapping("/url")
    public String getFileUrl(@RequestParam("file-name") String fileName) {
        log.debug("REST request url for file name: {}", fileName);
        return fileService.getUrl(fileName);
    }

}
