package jp.co.skill.spider.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * bytesプロパティーだけJson形式に変換する？
 * @author yuuichi
 *
 */
@JsonIgnoreProperties({"bytes"})
public class FileMeta {

	private String fileName;
    private String fileSize;
    private String fileType;
    private String filePath;
    private boolean uploadResult = true;

    private byte[] bytes;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isUploadResult() {
		return uploadResult;
	}

	public void setUploadResult(boolean uploadResult) {
		this.uploadResult = uploadResult;
	}
}
