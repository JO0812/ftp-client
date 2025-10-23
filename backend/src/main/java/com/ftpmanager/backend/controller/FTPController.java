package com.ftpmanager.backend.controller;

import com.ftpmanager.backend.model.ApiResponse;
import com.ftpmanager.backend.model.DirectoryRequest;
import com.ftpmanager.backend.model.FTPConnectionRequest;
import com.ftpmanager.backend.model.FileRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ftp")
public class FTPController {

	@PostMapping("/connect")
	public ApiResponse connect(@RequestBody FTPConnectionRequest request) {
		ApiResponse response = new ApiResponse();
		response.setSuccess(true);
		response.setMessage("Connected to " + request.getHost());
		return response;
	}

	@PostMapping("/disconnect")
	public String disconnect() {
		return "Disconnecting from ftp server";
	}

	@GetMapping("/status")
	public String status() {
		return "Conection status";
	}

	@GetMapping("/files")
	public String files() {
		return "Files in the ftp server";
	}

	@PostMapping("/change-directory")
	public String changeDirectory(@RequestBody DirectoryRequest request) {
		return "Changing to directory: " + request.getPath();
	}

	@PostMapping("/upload")
	public String upload() {
		return "File uploaded to the ftp server";
	}

	@GetMapping("/download")
	public String download(@RequestBody FileRequest request) {
		return "Downloading file: " + request.getFilepath();
	}

	@DeleteMapping("/delete")
	public String delete(@RequestBody FileRequest request) {
		return "Deleting file: " + request.getFilepath();
	}

}
