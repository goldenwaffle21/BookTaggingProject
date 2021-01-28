package examples.pubhub.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import examples.pubhub.dao.*;
import examples.pubhub.model.*;

/**
 * Servlet implementation class BookTagsControllerServlet
 */
@WebServlet("/TagsController")
public class BookTagsControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookTagsControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		session.setAttribute("name","session scoped attribute");
		
		List<Book> list = new ArrayList<Book>();   //starts empty, and resets with each search/modification.
		String dialogue = "You haven't changed anything yet";
		
		BookDAO dao = new BookDAOImpl();
		BookTagsDAO dao2 = new BookTagsDAOImpl();
			
			
		//First, the Summon page.
			
			if (request.getParameter("searchby").equals("ISBN")) {
				String isbn = request.getParameter("input");
				Book book = dao.getBookByISBN(isbn);
				String alltags = "";
				list.add(book);
				List<BookTag> tags = dao2.getAllBookTags(isbn);
				if (tags.size()!=0) {
					alltags += tags.get(0).getTag_name();
					for (int j=1; j<tags.size();j++) {
						alltags += ", "+tags.get(j).getTag_name();
					}
				}
				book.setTags(alltags);
				session.setAttribute("books1",list);
				request.getRequestDispatcher("summon.jsp").forward(request,response);
			}
			
			else if (request.getParameter("searchby").equals("Title")) {
				String title = request.getParameter("input");
				list = dao.getBooksByTitle(title);
				String alltags = "";
				for (int i=0;i<list.size();i++) {
					Book book = list.get(i);
					List<BookTag> tags = dao2.getAllBookTags(book.getIsbn13());
					if (tags.size()!=0) {
						alltags += tags.get(0).getTag_name();
						for (int j=1; j<tags.size();j++) {
							alltags += ", "+tags.get(j).getTag_name();
						}
					}
					book.setTags(alltags);
				}
				session.setAttribute("books1",list);
				request.getRequestDispatcher("summon.jsp").forward(request,response);
			}
			
			else if (request.getParameter("searchby").equals("Author")) {
				list = dao.getBooksByAuthor(request.getParameter("input"));
				String alltags = "";
				for (int i=0;i<list.size();i++) {
					Book book = list.get(i);
					List<BookTag> tags = dao2.getAllBookTags(book.getIsbn13());
					if (tags.size()!=0) {
						alltags += tags.get(0).getTag_name();
						for (int j=1; j<tags.size();j++) {
							alltags += ", "+tags.get(j).getTag_name();
						}
					}
					book.setTags(alltags);
				}
				session.setAttribute("books1",list);
				request.getRequestDispatcher("summon.jsp").forward(request,response);
			}
			
			else if (request.getParameter("searchby").equals("Price (up to)")) {
				String price = request.getParameter("input");
				//Need to make sure it can be converted into a double.
				price.replaceAll("$","");
				price.replaceAll("dollars","");
				price.trim();
				list = dao.getBooksLessThanPrice(Double.valueOf(price));
				String alltags = "";
				for (int i=0;i<list.size();i++) {
					Book book = list.get(i);
					List<BookTag> tags = dao2.getAllBookTags(book.getIsbn13());
					if (tags.size()!=0) {
						alltags += tags.get(0).getTag_name();
						for (int j=1; j<tags.size();j++) {
							alltags += ", "+tags.get(j).getTag_name();
						}
					}
					book.setTags(alltags);
				}
				session.setAttribute("books1",list);
				request.getRequestDispatcher("summon.jsp").forward(request,response);
			}
			
			else if (request.getParameter("searchby").equals("Tag")) {
				String tag = request.getParameter("input");
				tag.trim();
				tag.toLowerCase();
				list = dao2.getAllBooksByBookTag(tag);
				String alltags = "";
				for (int i=0;i<list.size();i++) {
					Book book = list.get(i);
					List<BookTag> tags = dao2.getAllBookTags(book.getIsbn13());
					//There will always be at least 1 tag in the list, but there might not be more.
					alltags += tags.get(0).getTag_name();
					if (tags.size()>1) {
						for (int j=1; j<tags.size();j++) {
							alltags += ", "+tags.get(j).getTag_name();
						}
					}
					book.setTags(alltags);
				}
				session.setAttribute("books1",list);
				request.getRequestDispatcher("summon.jsp").forward(request,response);
			}
			
			
			
			
		//Now, for the Modify page.
			
			else if (request.getParameter("searchby").equals("add")) {
				String input = request.getParameter("input");
				String isbn = request.getParameter("book").trim();
				String changes = "";
				
				//First, check to see if more than one tag was submitted; if so, separate and clean them.
				if (input.contains(",")) {
					int i = 0;
					while (i<input.lastIndexOf(',')) {
						int j = input.indexOf(',',i);
						String tag = input.substring(i,j).trim().toLowerCase();
						dao2.addBookTag(isbn,tag);
						changes += tag+", ";
						i = j+1;   //skip the comma
					}
					String tag = input.substring(i).trim().toLowerCase();
					dao2.addBookTag(isbn,tag);
					changes += tag;
				}
				else {
					String tag = input.trim().toLowerCase();
					changes += tag;
					dao2.addBookTag(isbn,tag);
				}
				
				//Now we call up all the tags, alphabetize them, and add them to the book object.
				List<BookTag> tags = dao2.getAllBookTags(isbn);
				List<String> tagnames = new ArrayList<String>();
				for (BookTag t:tags) {
					tagnames.add(t.getTag_name());
				}
				java.util.Collections.sort(tagnames);
				String alltags = tagnames.get(0);
				if (tagnames.size()>1) {
					for (int i=1;i<tagnames.size();i++) {
						alltags += ", "+tagnames.get(i);
					}
				}
				Book book = dao.getBookByISBN(isbn);
				book.setTags(alltags);
				list.add(book);
				session.setAttribute("books1",list);
				if (changes!="") {dialogue = "You added the tag(s): "+changes;}    //If changes were made
				session.setAttribute("dialogue",dialogue);
				request.getRequestDispatcher("modify.jsp").forward(request,response);
			}
			
			
			else if (request.getParameter("searchby").equals("remove")) {
				String input = request.getParameter("input").trim().toLowerCase();
				String isbn = request.getParameter("book").trim();
				String changes = "";
				
				//First, check to see if the user wants to delete all the tags
				if (input.equals("all")) {
					List<BookTag> tags = dao2.getAllBookTags(isbn);
					changes += tags.get(0).getTag_name();
					for (int i=1;i<tags.size();i++) {
						changes += ", "+tags.get(i).getTag_name();
					}
					dao2.removeAllBookTags(isbn);
				}
				else if (input.contains(",")) {
					int i = 0;
					while (i<input.lastIndexOf(',')) {
						int j = input.indexOf(',',i);
						String tag = input.substring(i,j).trim();
						dao2.removeBookTag(isbn,tag);
						changes += tag+", ";
						i = j+1;   //skip the comma
					}
					String tag = input.substring(i).trim();
					dao2.removeBookTag(isbn,tag);
					changes += tag;
				}
				else {
					String tag = input;
					changes += tag;
					dao2.removeBookTag(isbn,tag);
				}
				
				//No need to alphabetize; they're all already in order.
				List<BookTag> tags = dao2.getAllBookTags(isbn);
				String alltags = tags.get(0).getTag_name();
				for (BookTag t:tags) {
					alltags += ", "+t.getTag_name();
				}
				Book book = dao.getBookByISBN(isbn);
				book.setTags(alltags);
				list.add(book);
				session.setAttribute("books1",list);
				if (changes!="") {dialogue = "You removed the tag(s): "+changes;}
				session.setAttribute("dialogue",dialogue);
				request.getRequestDispatcher("modify.jsp").forward(request,response);
			}
	}

}
