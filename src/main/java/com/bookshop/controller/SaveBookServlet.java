package com.bookshop.controller;

import com.bookshop.dao.AuthorDao;
import com.bookshop.dao.BookDao;
import com.bookshop.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

@WebServlet("/save-book")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10)
public class SaveBookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        try {
            String action = request.getParameter("action");
            String bookIdStr = request.getParameter("bookId");

            System.out.println("---- DEBUG SAVE BOOK ----");
            System.out.println("Action nhận được: " + action);
            System.out.println("Book ID nhận được: " + bookIdStr);

            String title = request.getParameter("title");
            String authorName = request.getParameter("authorName");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            String genre = request.getParameter("genre");
            String description = request.getParameter("description");

            int authorId = 1; // Mặc định
            if (authorName != null && !authorName.trim().isEmpty()) {
                String name = authorName.trim();
                int foundId = AuthorDao.getAuthorIdByName(name);

                if (foundId != -1) {
                    authorId = foundId;
                } else {
                    AuthorDao.addAuthor(name);
                    authorId = AuthorDao.getAuthorIdByName(name);
                }
            }

            Part filePart = request.getPart("imageFile");
            String imageUrl = null;

            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String applicationPath = request.getServletContext().getRealPath("");
                String uploadFilePath = applicationPath + File.separator + "uploads";
                File uploadDir = new File(uploadFilePath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                filePart.write(uploadFilePath + File.separator + fileName);
                imageUrl = "uploads/" + fileName;
            }

            Book book = new Book();
            book.setTitle(title);
            book.setAuthorId(authorId);
            book.setPrice(price);
            book.setStock(stock);
            book.setDescription(description);

            ArrayList<String> genresList = new ArrayList<>();
            genresList.add(genre);
            book.setGenres(genresList);

            BookDao bookDao = new BookDao();

            if ("edit".equals(action)) {
                System.out.println("=> Đang thực hiện SỬA sách (UPDATE)");
                int bookId = Integer.parseInt(bookIdStr);
                book.setBookId(bookId);
                if (imageUrl != null) {
                    book.setImageUrl(imageUrl);
                } else {
                    Book oldBook = BookDao.getBookById(bookId);
                    book.setImageUrl(oldBook.getImageUrl());
                }

                bookDao.updateBook(book);
                session.setAttribute("status", "Cập nhật sách thành công!");

            } else {
                System.out.println("=> Đang thực hiện THÊM sách mới (INSERT)");
                if (imageUrl == null) {
                    imageUrl = "https://via.placeholder.com/150";
                }
                book.setImageUrl(imageUrl);

                bookDao.addBook(book);
                session.setAttribute("status", "Đã thêm sách mới!");
            }

            session.setAttribute("showTab", "books");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("status", "Lỗi: " + e.getMessage());
            session.setAttribute("showTab", "books");
        }

        response.sendRedirect("admin?showTab=books");
    }
}