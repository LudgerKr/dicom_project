package com.test.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
//import org.dcm4che3.data.Tag;
//import org.dcm4che3.data.Attributes;


@RestController
public class FileUploadController {

    //Save the uploaded file to this folder
    private static final String UPLOADED_FOLDER = "./";

    @GetMapping("/")
    public String index() {
        return "upload";
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

}
