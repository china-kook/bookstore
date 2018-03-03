package com.ikook.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ikook.bookstore.domain.Book;
import com.ikook.bookstore.domain.ShoppingCart;
import com.ikook.bookstore.service.BookService;
import com.ikook.bookstore.web.BookStoreWebUtils;
import com.ikook.bookstore.web.CriteriaBook;
import com.ikook.bookstore.web.Page;

public class BookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);

	}

	private BookService bookService = new BookService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String methodName = request.getParameter("method");

		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 4. 在 updateItemQuantity 方法中, 获取 quanity, id, 再获取购物车对象, 调用 service 的方法做修改
		String idString = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		
		
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
		
		int id = -1; 
		int quantity = -1;
		
		try {
			id = Integer.parseInt(idString);
			quantity = Integer.parseInt(quantityStr);
		} catch (NumberFormatException e) {
		}
		
		if(id > 0 && quantity > 0)
			bookService.updateItemQuantity(shoppingCart, id, quantity);
		
		
		// 5. 传回 JSON 数据: bookNumber:xx, totalMoney
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("bookNumber", shoppingCart.getBookNumber());
		result.put("totalMoney", shoppingCart.getTotalMoney());
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);

	}

	protected void clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
		bookService.clearShoppingCart(shoppingCart);

		request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
	}

	protected void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idString = request.getParameter("id");
		int id = -1;

		try {
			id = Integer.parseInt(idString);
		} catch (NumberFormatException e) {
		}

		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		bookService.removeItemFromShoppingCart(cart, id);
		
		if (cart.isEmpty()) {
			request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);

	}

	protected void forwardPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp").forward(request, response);
	}

	protected void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. 获取商品的 id
		String idString = request.getParameter("id");
		int id = -1;
		boolean flag = false;

		try {
			id = Integer.parseInt(idString);
		} catch (NumberFormatException e) {
		}

		if (id > 0) {
			// 2. 获取购物车对象
			ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
			// 3. 调用 BookService 的 addToCart() 方法将商品放到购物车当中
			flag = bookService.addToCart(id, shoppingCart);
		}
		if (flag) {
			// 4. 直接调用 getBooks() 方法
			getBooks(request, response);
			return;
		}

		response.sendRedirect(request.getContextPath() + "/error-1.jsp");

	}

	protected void getBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idStr = request.getParameter("id");
		int id = -1;

		Book book = null;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}

		if (id > 0) {
			book = bookService.getBook(id);
		}

		if (book == null) {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
			return;
		}

		request.setAttribute("book", book);

		request.getRequestDispatcher("WEB-INF/pages/book.jsp").forward(request, response);

	}

	protected void getBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");

		int pageNo = 1;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;

		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}

		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (NumberFormatException e) {
		}

		try {
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {
		}

		CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = bookService.getPage(criteriaBook);

		request.setAttribute("bookpage", page);

		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);

	}
}
