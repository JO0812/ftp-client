package com.ftpmanager.backend.controller;

import com.ftpmanager.backend.model.*;
import com.ftpmanager.backend.service.FTPService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ftp")
public class FTPController {

	private final FTPService ftpService;

	public FTPController(FTPService ftpService) {
		this.ftpService = ftpService;
	}

	@PostMapping("/connect")
	public ApiResponse connect(@RequestBody FTPConnectionInfo request) {
		boolean success = ftpService.connect(request.getHost(), request.getPort(), request.getUsername(), request.getPassword());
		ApiResponse response = new ApiResponse();
		if (success) {
			response.setSuccess(true);
			response.setMessage("Connected to " + request.getHost());
			response.setError(null);
		} else {
			response.setSuccess(false);
			response.setMessage("Connection error");
			response.setError(ftpService.getLastError());
		}

		return response;
	}

	@PostMapping("/disconnect")
	public ApiResponse disconnect() {
		boolean success = ftpService.disconnect();
		ApiResponse response = new ApiResponse();
		if (success) {
			response.setSuccess(true);
			response.setMessage("Disconnected from server");
			response.setError(null);
		} else {
			response.setSuccess(false);
			response.setMessage("Disconnection error");
			response.setError(ftpService.getLastError());
		}
		return response;
	}

	@GetMapping("/status")
	public ConnectionStatusResponse status() {
		ConnectionStatusResponse response = new ConnectionStatusResponse();
		response.setConnected(true);
		response.setHost("ftp.example.com");
		response.setCurrentDirectory("/home/usr");
		return response;
	}

	@GetMapping("/files")
	public FileListResponse listFiles() {
		FileListResponse response = new FileListResponse();
		List<FileInfo> fileList = new ArrayList<>();

		// Create first file (a PDF document)
		FileInfo file1 = new FileInfo();
		file1.setName("document.pdf");
		file1.setSize(2048);
		file1.setDirectory(false);
		file1.setLastModified("2024-10-23 14:30:00");
		fileList.add(file1);

		// Create second file (a folder)
		FileInfo file2 = new FileInfo();
		file2.setName("photos");
		file2.setSize(0);
		file2.setDirectory(true);
		file2.setLastModified("2024-10-20 10:15:00");
		fileList.add(file2);

		// Create third file (a text file)
		FileInfo file3 = new FileInfo();
		file3.setName("readme.txt");
		file3.setSize(512);
		file3.setDirectory(false);
		file3.setLastModified("2024-10-22 09:00:00");
		fileList.add(file3);

		response.setFiles(fileList);
		response.setCurrentPath("/home/user/documents");

		return response;
	}

	@PostMapping("/change-directory")
	public ApiResponse changeDirectory(@RequestBody DirectoryRequest request) {
		ApiResponse response = new ApiResponse();
		response.setSuccess(true);
		response.setMessage("Changed dir to " + request.getPath());
		return response;
	}

	@PostMapping("/upload")
	public ApiResponse upload(@RequestBody FileRequest request) {
		ApiResponse response = new ApiResponse();
		response.setSuccess(true);
		response.setMessage("Uploaded file to " + request.getFilepath());
		return response;
	}

	@GetMapping("/download")
	public ApiResponse download(@RequestBody FileRequest request) {
		ApiResponse response = new ApiResponse();
		response.setSuccess(true);
		response.setMessage("Downloaded file " + request.getFilepath());
		return response;
	}

	@DeleteMapping("/delete")
	public ApiResponse delete(@RequestBody FileRequest request) {
		ApiResponse response = new ApiResponse();
		response.setSuccess(true);
		response.setMessage("Deleted file " + request.getFilepath());
		return response;
	}
}
