package council.website.action.beans;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadRequestEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1793767780349350918L;
	
	private MultipartFile file;
	private String title;
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
