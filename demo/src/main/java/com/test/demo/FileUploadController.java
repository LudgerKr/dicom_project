package com.test.demo;

import org.apache.commons.io.IOUtils;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.Attributes;

@CrossOrigin(origins = "localhost:4200", maxAge = 3600)
@RestController
public class FileUploadController {

    //Save the uploaded file to this folder
    private static final String UPLOADED_FOLDER = "./";
    private String PatientName;
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
            File myDicomFile = new File(path.toString());
            System.out.println(path.toString());
            DicomInputStream dis = new DicomInputStream(myDicomFile);
            Attributes attr = dis.readDataset(-1, -1);
            String patientName = attr.getString(Tag.PatientName, "");
            System.out.println(patientName);
            this.PatientName = patientName;
            BufferedImage myJpegImage = null;
            Iterator<ImageReader> iter = ImageIO.getImageReadersByFormatName("DICOM");
            ImageReader reader = (ImageReader) iter.next();
            DicomImageReadParam param = (DicomImageReadParam) reader.getDefaultReadParam();
            try {
                ImageInputStream iis = ImageIO.createImageInputStream(myDicomFile);
                reader.setInput(iis, false);
                myJpegImage = reader.read(0, param);
                iis.close();
                if (myJpegImage == null) {
                    System.out.println("\nError: couldn't read dicom image!");
                }
                File myJpegFile = new File("./jpegImage.jpg");
                OutputStream output = new BufferedOutputStream(new FileOutputStream(myJpegFile));
                ImageIO.write(myJpegImage, "jpeg", output);
                output.close();
            }catch(IOException e) {
                System.out.println("\nError: couldn't read dicom image!"+ e.getMessage());
            }
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.PatientName;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> image() throws IOException {
        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
                "./jpegImage.jpg"
        )));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);

    }

//    @CrossOrigin(origins = "http://localhost:4200")
//    @GetMapping("/uploadStatus")
//    public String uploadStatus() {
//        return "uploadStatus";
//    }
//
//
//
//    @GetMapping(
//            value = "/image",
//            produces = MediaType.IMAGE_JPEG_VALUE
//    )
//    @CrossOrigin(origins = "http://localhost:4200")
//    public @ResponseBody byte[] getImageWithMediaType() throws IOException {
//        InputStream in = getClass()
//                .getResourceAsStream("./jpegImage.jpg");
//        return IOUtils.toByteArray(in);
//    }

}
