package com.ftpmanager.backend.model;

import lombok.Data;

@Data
public class ChangeDirectoryResponse {
	private boolean success;
	private String currentPath;
	private String newPath;
}
