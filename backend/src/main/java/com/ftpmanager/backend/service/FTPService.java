package com.ftpmanager.backend.service;

import com.ftpmanager.backend.model.ChangeDirectoryResponse;
import com.ftpmanager.backend.model.ConnectionStatusResponse;
import com.ftpmanager.backend.model.FileListResponse;
import com.ftpmanager.backend.model.FileInfo;
import lombok.Getter;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FTPService {

	private FTPClient ftpClient;
	private boolean isConnected = false;
	private String currentHost;
	private String currentDirectory;

	@Getter
	private String lastError;

	public boolean connect(String host, int port, String username, String password) {
		try {
			if (ftpClient == null) {
				ftpClient = new FTPClient();
			}
			ftpClient.connect(host, port);
			boolean loginSuccess = ftpClient.login(username, password);

			if (!loginSuccess) {
				lastError = "Login failed: Invalid username or password";
				ftpClient.disconnect();
				return false;
			}

			isConnected = true;
			currentHost = host;
			currentDirectory = ftpClient.printWorkingDirectory();
			lastError = null;
			return true;

		} catch (Exception e) {
			lastError = "Connection failed: " + e.getMessage();
			isConnected = false;
			return false;
		}
	}

	public boolean disconnect() {
		try {
			if (isConnected) {
				ftpClient.disconnect();
				currentHost = null;
				currentDirectory = null;
				isConnected = false;
				lastError = null;
			}
			return true;
		} catch (Exception e) {
			lastError = "Disconnection failed: " + e.getMessage();
			isConnected = false;
			return false; // if we have an error with the connection, should we clean up as well and return true?
		}
	}

	public ConnectionStatusResponse status() {
		ConnectionStatusResponse response = new ConnectionStatusResponse();
		response.setConnected(isConnected);
		response.setHost(currentHost);
		response.setCurrentDirectory(currentDirectory);
		return response;
	}

	public FileListResponse listFiles() {
		if (!isConnected) {
			lastError = "Not connected to FTP server";
			return null;
		}

		try {
			FTPFile[] ftpFiles = ftpClient.listFiles();
			List<FileInfo> fileInfoList = new ArrayList<>();
			for (FTPFile ftpFile : ftpFiles) {
				FileInfo info = new FileInfo();
				info.setName(ftpFile.getName());
				info.setSize(ftpFile.getSize());
				info.setDirectory(ftpFile.isDirectory());
				info.setLastModified(ftpFile.getTimestamp() != null ? ftpFile.getTimestamp().getTime().toString() : "Unknown");
				fileInfoList.add(info);
			}
			FileListResponse response = new FileListResponse();
			response.setFiles(fileInfoList);
			response.setCurrentPath(currentDirectory);
			return response;
		} catch (Exception e) {
			lastError = "Failed to list files: " + e.getMessage();
			return null;
		}
	}

	public ChangeDirectoryResponse changeDirectory(String path) {
		if (!isConnected) {
			lastError = "Not connected to FTP server";
			ChangeDirectoryResponse response = new ChangeDirectoryResponse();
			response.setSuccess(false);
			return response;
		}

		try {
			boolean success = ftpClient.changeWorkingDirectory(path);
			ChangeDirectoryResponse response = new ChangeDirectoryResponse();
			response.setSuccess(success);
			response.setCurrentPath(this.currentDirectory);
			response.setNewPath(ftpClient.printWorkingDirectory());

			if (success) {
				this.currentDirectory = ftpClient.printWorkingDirectory();
			}

			return response;

		} catch (Exception e) {
			lastError = "Failed to change directory: " + e.getMessage();
			ChangeDirectoryResponse response = new ChangeDirectoryResponse();
			response.setSuccess(false);
			return response;
		}
	}
}

