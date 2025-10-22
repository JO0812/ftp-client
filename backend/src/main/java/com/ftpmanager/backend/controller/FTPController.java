package com.ftpmanager.backend.controller;

import com.ftpmanager.backend.model.FTPConnectionRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ftp")
public class FTPController {

	@PostMapping("/connect")
	public String connect(@RequestBody FTPConnectionRequest request) {
		return "Connecting to " + request.getHost() +
				" with user " + request.getUsername();
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
	public String changeDirectory() {
		return "Changing directory in the ftp server";
	}

	@PostMapping("/upload")
	public String upload() {
		return "File uploaded to the ftp server";
	}

	@GetMapping("/download")
	public String download() {
		return "File downloaded from the ftp server";
	}

	@DeleteMapping("/delete")
	public String delete() {
		return "File deleted from the ftp server";
	}

}
