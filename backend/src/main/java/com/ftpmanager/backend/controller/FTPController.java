package com.ftpmanager.backend.controller;

import com.ftpmanager.backend.model.*;
import com.ftpmanager.backend.service.FTPService;
import org.springframework.web.bind.annotation.*;

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
		return ftpService.status();
	}

	@GetMapping("/files")
	public FileListResponse listFiles() {
		return ftpService.listFiles();
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
