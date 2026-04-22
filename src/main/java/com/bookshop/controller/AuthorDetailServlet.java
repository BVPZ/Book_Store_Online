package com.bookshop.controller;

import com.bookshop.dao.AuthorDao;
import com.bookshop.dao.BookDao;
import com.bookshop.model.Author;
import com.bookshop.model.Book;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/author")
public class AuthorDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int authorId = Integer.parseInt(request.getParameter("id"));
            System.out.println("authorId: " + authorId);

            Author author = AuthorDao.getAuthorbyId(authorId);
            ArrayList<Book> books = BookDao.getBooksByAuthorId(authorId);

//            int totalReviewCount = 0;
//            double totalRatingSum = 0;
//            double averageRating = 0.0;
//            int countWithRating = 0;

//            for (Book b : books) {
//                if (b.getReviewCount() > 0) {
//                    totalReviewCount += b.getReviewCount();
//                    totalRatingSum += b.getRating() * b.getReviewCount();
//                    countWithRating++;
//                }
//            }

//            if (totalReviewCount > 0) {
//                averageRating = Math.round((totalRatingSum / totalReviewCount) * 10.0) / 10.0;
//            }

            if (author != null && author.getAuthorName() != null) {
                request.setAttribute("author", author);
                request.setAttribute("author_books", books);
//                request.setAttribute("average_rating", averageRating);
//                request.setAttribute("total_review_count", totalReviewCount);
                request.getRequestDispatcher("/view/author_detail.jsp").forward(request, response);
            } else {
                response.getWriter().println("<h1>Khong tim thay tac gia nay!</h1>");
            }
        } catch (NumberFormatException e){
            e.printStackTrace();
            response.getWriter().println("<h1>Loi xu ly du lieu</h1>");
        }
        return;
    }
}
