package examples.pubhub.model;

public class BookTag {
	private String isbn;
	private String tag_name;
	
	public BookTag(String isbn, String tag_name) {
		this.isbn = isbn;
		this.setTag_name(tag_name);
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}
	
}
