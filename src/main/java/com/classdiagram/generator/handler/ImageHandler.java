package com.classdiagram.generator.handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class ImageHandler {

	private static final String YUML_URL = "http://yuml.me/diagram/scruffy/class/";

	public static void generateClassDiagram(String inputRules, String destinationFile) throws Exception {
		ImageHandler imageHandler = new ImageHandler();
		String encodedURL = imageHandler.getEncodedRulesContent(inputRules);
		imageHandler.saveImage(encodedURL, destinationFile);
		imageHandler.showImage(destinationFile);
	}

	private String getEncodedRulesContent(String inputRules) {
		String encodedContent = null;
		try {
			encodedContent = URLEncoder.encode(inputRules, "UTF-8");
			System.out.println(encodedContent);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return YUML_URL + encodedContent;

	}

	private void saveImage(String encodedURL, String destinationFile) throws IOException {
		URL url = new URL(encodedURL);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

	private void showImage(String imageName) {

		try {
			BufferedImage img = ImageIO.read(new File(imageName));
			ImageIcon icon = new ImageIcon(img);
			JLabel label = new JLabel(icon);
			JScrollPane scroller = new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			JFrame frame = new JFrame("YUML generated Class Diagram");
			frame.getContentPane().add(scroller);
			frame.setSize(1000, 3000);
			frame.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
