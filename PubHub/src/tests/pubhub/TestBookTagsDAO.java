package tests.pubhub;

import java.util.List;

import examples.pubhub.dao.BookTagsDAO;
import examples.pubhub.dao.BookTagsDAOImpl;
import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;

public class TestBookTagsDAO {
	public static void main(String[] args) {
		BookTagsDAO dao = new BookTagsDAOImpl();
		List<BookTag> list = dao.getAllBookTags("1111111111111");
		for (int i = 0; i < list.size(); i++) {
			BookTag t = list.get(i);
			System.out.println(t.getTag_name());
		}
		
		
		BookTagsDAO dao2 = new BookTagsDAOImpl();
		List<Book> list2 = dao2.getAllBooksByBookTag("fiction");
		for (int i = 0; i < list2.size(); i++) {
			Book b = list2.get(i);
			System.out.println(b.getTitle());
		}
		
	}

}
