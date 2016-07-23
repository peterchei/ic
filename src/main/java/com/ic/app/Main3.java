package com.ic.app;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main3 {

	static final String BOUNDARY = "----------V2ymHFg03ehbqgZCaKO6jy";

	public static void main(String[] args) throws IOException {
		URL url;
		HttpURLConnection urlConn;
		DataOutputStream printout;
		DataInputStream input;

		// -------------------------------------------------------------------

		File image = new File("c://temp//hand.png");

		FileInputStream imgStream = new FileInputStream(image);

		byte[] buffer = new byte[(int) image.length()];

		imgStream.read(buffer);

		// -------------------------------------------------------------------

		// Proxy proxy = new Proxy(Proxy.ActionType.HTTP, new
		// InetSocketAddress("10.18.1.1", 4444));

		url = new URL("https://graph.facebook.com/me/feed");
		// url = new
		// URL("https://graph.facebook.com/me/photos?access_token=AAACkMOZA41QEBACsafBxqVfXX54JqGLQSaE6YQ062NuTe3XUZBTdTEvy3R2H9Yr4PZA9r38JvLni7r1hYLuZCnBZAAPPH3krMMSKtIraiswZCiIZBu0nyYT");
		System.out.println("Before Open Connection");

		urlConn = (HttpURLConnection) url.openConnection();

		urlConn.setRequestProperty("Content-ActionType", "multipart/form-data; boundary=" + getBoundaryString());

		urlConn.setDoOutput(true);
		urlConn.setUseCaches(false);
		urlConn.setRequestMethod("POST");

		// String content = "access_token=" + URLEncoder.encode
		// ("AAACkMOZA41QEBAHQHUyYcMsLAewOYIe1j5dlOVOlMZBm6h9rvCQEFhmcBHg7ETHrdlrgv4sau573xMVuxIt8DzRxKFuqRqqBskDvOZA9iIkZCdPyI4Bu");

		String boundary = getBoundaryString();

		String boundaryMessage = getBoundaryMessage(boundary, "upload_field", "P1025[01]_03-07-11.JPG", "image/png");

		String endBoundary = "\r\n--" + boundary + "--\r\n";

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		bos.write(boundaryMessage.getBytes());

		bos.write(buffer);

		bos.write(endBoundary.getBytes());

		printout = new DataOutputStream(urlConn.getOutputStream());

		// printout.writeBytes(content);

		printout.write(bos.toByteArray());

		printout.flush();
		printout.close();
		// Get response data.

		// input = new DataInputStream (urlConn.getInputStream ());

		if (urlConn.getResponseCode() == 400 || urlConn.getResponseCode() == 500) {
			input = new DataInputStream(urlConn.getErrorStream());
		} else {
			input = new DataInputStream(urlConn.getInputStream());
		}

		String str;
		while (null != ((str = input.readLine()))) {
			System.out.println(str);
		}
		input.close();

	}

	public static String getBoundaryString() {
		return BOUNDARY;
	}

	public static String getBoundaryMessage(String boundary, String fileField, String fileName, String fileType) {
		StringBuffer res = new StringBuffer("--").append(boundary).append("\r\n");
		res.append("Content-Disposition: form-data; name=\"").append(fileField).append("\"; filename=\"")
				.append(fileName).append("\"\r\n").append("Content-ActionType: ").append(fileType).append("\r\n\r\n");

		return res.toString();
	}
}