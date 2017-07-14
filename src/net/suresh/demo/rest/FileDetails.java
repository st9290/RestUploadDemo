/**
 * 
 */
package net.suresh.demo.rest;

/**
 * @author Suresh
 *
 */
public class FileDetails {

	private static final String SERVER_PATH = "http://localhost:8080/RestUploadDemo/rest/files/getFile/";
	
	private String name;
	private String uri;

	public FileDetails(String name) {
		this.name = name;
		setUri(name);
	}
	
	public FileDetails(String name, String uri) {
		this.name = name;
		setUri(uri);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	@SuppressWarnings("deprecation")
	public void setUri(String uri) {
		this.uri =SERVER_PATH + uri;
	}

}
