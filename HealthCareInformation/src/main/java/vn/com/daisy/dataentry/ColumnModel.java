package vn.com.daisy.dataentry;

public class ColumnModel {
	private String header;
	private String property;

	public ColumnModel(String header, String property) {
        this.header = header;
        this.property = property;
    }

	public String getHeader() {
		return header;
	}

	public String getProperty() {
		return property;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
}
