package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;

public interface BookTagsDAO {
	public List<BookTag> getAllBookTags(String isbn);
	public boolean addBookTag(String isbn, String tag_name);
	public boolean removeBookTag(String isbn, String tag_name);
	public boolean removeAllBookTags(String isbn);
	public List<Book> getAllBooksByBookTag(String tag_name);
	
	//Why are these booleans instead of voids? Maybe it's important later.

}
