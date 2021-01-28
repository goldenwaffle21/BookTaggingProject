package examples.pubhub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;
import examples.pubhub.utilities.DAOUtilities;

public class BookTagsDAOImpl implements BookTagsDAO{
	
	Connection conn = null;
	PreparedStatement ps = null;
	
	public List<BookTag> getAllBookTags(String isbn){
		
		List<BookTag> tags = new ArrayList<>();
		
		try {
			conn = DAOUtilities.getConnection();
			String sql = "Select * from book_tags where isbn_13=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,isbn);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BookTag tag = new BookTag(isbn, rs.getString("tag_name"));
				tags.add(tag);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {closeResources();}
		
		return tags;
	}
	
	public boolean addBookTag(String isbn, String tag_name) {
		try {
			conn = DAOUtilities.getConnection();
			String sql = "insert into book_tags values(?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1,isbn);
			ps.setString(2,tag_name);
			if (ps.executeUpdate() != 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {closeResources();}

	}
	
	public boolean removeBookTag(String isbn, String tag_name) {
		try {
			conn = DAOUtilities.getConnection();
			String sql = "delete from book_tags where isbn_13=? and tag_name=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,isbn);
			ps.setString(2,tag_name);
			if (ps.executeUpdate() != 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {closeResources();}
	}
	
	public boolean removeAllBookTags(String isbn) {
		try {
			conn = DAOUtilities.getConnection();
			String sql = "delete from book_tags where isbn_13=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,isbn);
			if (ps.executeUpdate() != 0) {return true;}
			else {return false;}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {closeResources();}
	}
	
	public List<Book> getAllBooksByBookTag(String tag){
		
		List<Book> books = new ArrayList<>();
		
		try {
			conn = DAOUtilities.getConnection();
			String sql = "Select * from books where isbn_13 in( select isbn_13 from book_tags where tag_name = ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1,tag);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setIsbn13(rs.getString("isbn_13"));
				book.setAuthor(rs.getString("author"));
				book.setTitle(rs.getString("title"));
				book.setPublishDate(rs.getDate("publish_date").toLocalDate());
				book.setPrice(rs.getDouble("price"));
				book.setContent(rs.getBytes("content"));
				books.add(book);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {closeResources();}
		
		return books;
	}
	
	//And one last method that goes through and makes sure everything is closed. Well, everything but the ResultSets,
	//which won't be created if there's an exception anyway, so their close() method can safely be in the try block.
	private void closeResources() {
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}
}
