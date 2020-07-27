package myDicomToJpeg;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.io.DicomInputStream;

public class DicomToJpeg {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		File myDicomFile = new File("C:\\Users\\pzg19\\Desktop\\hiring_test\\hiring_test\\hiring-test\\data\\IM-0001.dcm");
		DicomInputStream dis = new DicomInputStream(myDicomFile);
		Attributes attr = dis.readDataset(-1, -1);
		String patientName = attr.getString(Tag.PatientName, "");
		System.out.println(patientName);
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
				      return;
			   }
			   File myJpegFile = new File("../jpegImage.jpg");   
			   OutputStream output = new BufferedOutputStream(new FileOutputStream(myJpegFile));
			   ImageIO.write(myJpegImage, "jpeg", output);
			   output.close();
		}catch(IOException e) {
			   System.out.println("\nError: couldn't read dicom image!"+ e.getMessage());
			   return;
		}
	}

}
