package com.bookshop.dao;

import com.bookshop.model.Book;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDao {
    public void  addBook(Book book) throws SQLException
    {

    }
    public static Book getBookById(int bookId)
    {
        Book book = new Book();
        try(Connection con = DBConnection.getConnection();){
        String sql = "select * from books where book_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,bookId);
        ResultSet rs = ps.executeQuery();
        rs.next();

        book.setBookId(bookId);
        book.setTitle(rs.getString("title"));
        book.setAuthorId(rs.getInt("author_id"));
//        book.setRating(rs.getDouble("rating"));
//        book.setReviewCount(rs.getInt("reviewCount"));
        book.setPrice(rs.getDouble("price"));
        book.setImageUrl(rs.getString("image"));
        book.setDescription(rs.getString("description"));
        book.setStock(rs.getInt("stock"));
        book.setGenres();
        System.out.println("lay thong tin sach dua tren id thanh cong");}
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return book;
    }
    public static ArrayList<Book> getNewBooks() throws SQLException,Exception
    {
        try(Connection con = DBConnection.getConnection();){
            String sql = "select * from books where books.book_id >= 10 limit 4 ";
            PreparedStatement ps = con.prepareStatement(sql);
            ArrayList<Book> new_books = new  ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setPrice(rs.getDouble("price"));
                book.setTitle(rs.getString("title"));
                book.setImageUrl(rs.getString("image"));
                new_books.add(book);
            }
            if (new_books.isEmpty()) System.out.println("lay sach moi ko thanh cong");
            else System.out.println("lay sach moi thanh cong");
            return new_books;
        }

    }
    public static ArrayList<Book> getBestSellerBooks() throws SQLException,Exception
    {
        try(Connection con = DBConnection.getConnection();){
            String sql = "select * from books where books.book_id >= 1 limit 4 ";
            PreparedStatement ps = con.prepareStatement(sql);
            ArrayList<Book> best_seller_books = new  ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setPrice(rs.getDouble("price"));
                book.setTitle(rs.getString("title"));
                book.setImageUrl(rs.getString("image"));
                best_seller_books.add(book);
            }
            if(best_seller_books.isEmpty()) System.out.println("lay sach ban chay ko thanh cong");
            else System.out.println("lay sach ban chay thanh cong");
            return best_seller_books;
        }

    }
    public static  ArrayList<Book> getAllBooks()
    {
        try(Connection con = DBConnection.getConnection();){
            String sql = "select * from books limit 20 ";
            PreparedStatement ps = con.prepareStatement(sql);
            ArrayList<Book> all_books = new  ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setPrice(rs.getDouble("price"));
                book.setTitle(rs.getString("title"));
                book.setImageUrl(rs.getString("image"));
                all_books.add(book);
            }
            if (all_books.isEmpty()) System.out.println("lay toan bo sach ko thanh cong");
            else System.out.println("Lay toan bo sach thanh cong");
            return  all_books;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<Book> searchBooks(String keyword) throws SQLException,Exception {
        try(Connection con = DBConnection.getConnection();){
            String sql = "SELECT * FROM books WHERE title LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");

            ArrayList<Book> search_results = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setPrice(rs.getDouble("price"));
                book.setTitle(rs.getString("title"));
                book.setImageUrl(rs.getString("image"));
                search_results.add(book);
            }
            return search_results;
        }
    }

    public static ArrayList<Book> getBooksByAuthorId(int authorId)  {
        ArrayList<Book> author_books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE author_id = ?";
        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);){
            ps.setInt(1, authorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setPrice(rs.getDouble("price"));
                book.setTitle(rs.getString("title"));
                book.setImageUrl(rs.getString("image"));
                book.setDescription(rs.getString("description"));
                book.setAuthorId(rs.getInt("author_id"));
                // Mock rating and review count because they don't exist in the database
                book.setRating(4.5);
                book.setReviewCount((int) (Math.random() * 1000 + 100));

                author_books.add(book);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return author_books;
    }

    //locSachTheoTheLoai
    public static ArrayList<Book> getBooksByCategoryId(int categoryId) throws SQLException {
        ArrayList<Book> list = new ArrayList<>(); // 1. Tạo danh sách trống
        String sql = "SELECT b.* FROM books b " +
                "JOIN book_categories bc ON b.book_id = bc.book_id " +
                "WHERE bc.category_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setPrice(rs.getDouble("price"));
                book.setImageUrl(rs.getString("image"));
                list.add(book); // 2. Thêm sách vào danh sách
            }
        }
        return list; // 3. QUAN TRỌNG: Trả danh sách này về để hiển thị lên web!
    }

    public static ArrayList<Book> getBooksByIds(String[] ids) {
        ArrayList<Book> list = new ArrayList<>();
        if (ids == null || ids.length == 0) return list;
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < ids.length; i++) {
            placeholders.append("?");
            if (i < ids.length - 1) placeholders.append(",");
        }
        String sql = "SELECT * FROM books WHERE book_id IN (" + placeholders + ") " +
                "ORDER BY FIELD(book_id, " + placeholders + ")";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < ids.length; i++) {
                ps.setInt(i + 1, Integer.parseInt(ids[i]));
            }
            for (int i = 0; i < ids.length; i++) {
                ps.setInt(ids.length + i + 1, Integer.parseInt(ids[i]));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setBookId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setPrice(rs.getDouble("price"));
                    book.setImageUrl(rs.getString("image"));
                    list.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}


