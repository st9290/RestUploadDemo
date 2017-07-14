package net.suresh.demo.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("files/")
public class RestfulUploadService {

	private static final String FOLDER_PATH = "C:\\my_files\\";

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String uploadFile(@FormDataParam("file") InputStream fis,
			@FormDataParam("file") FormDataContentDisposition fdcd) {

		String fileName = fdcd.getFileName();
		String filePath = FOLDER_PATH + fileName;
		try {
			Files.copy(fis, Paths.get(filePath));
		} catch (FileAlreadyExistsException f) {
			return "File Already exist at the destination folder !!";
		} catch (IOException iox) {
			iox.printStackTrace();
		}
		return "File Uploaded Successfully !!";
	}

	@GET
	@Path("/list")
	@Produces(MediaType.TEXT_PLAIN)
	public String listFiles() {
		List<FileDetails> fileList = new ArrayList();
		File folder = new File(FOLDER_PATH);
		File[] listOfFiles = folder.listFiles();

		for (File f : listOfFiles) {
			if (f.isFile()) {
				System.out.println("File " + f.getName());
				fileList.add(new FileDetails(f.getName()));
			}
		}
		return new Gson().toJson(fileList);
	}

	@GET
	@Path("/getFile/{fileName}")
	public Response getFile(@PathParam("fileName") String fileName) {
		File f = new File(FOLDER_PATH + fileName);
		ResponseBuilder res = Response.ok(fileName, MediaType.WILDCARD);
		res.header("Content-Disposition", "attachment;filename="+fileName);
		return res.build();
	}
	
}
