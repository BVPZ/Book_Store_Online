package com.bookshop.dao;

import com.bookshop.model.Book;
import com.bookshop.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDao {
    public void addBook(Book book) {
        String sqlBook = "INSERT INTO books (title, author_id, price, stock, description, image) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlCategory = "INSERT INTO book_categories (book_id, category_id) VALUES (?, (SELECT category_id FROM categories WHERE category_name = ? LIMIT 1))";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement psBook = con.prepareStatement(sqlBook, PreparedStatement.RETURN_GENERATED_KEYS)) {

            psBook.setString(1, book.getTitle());
            psBook.setInt(2, book.getAuthorId());
            psBook.setDouble(3, book.getPrice());
            psBook.setInt(4, book.getStock());
            psBook.setString(5, book.getDescription());
            psBook.setString(6, book.getImageUrl());

            int row = psBook.executeUpdate();
            if(row > 0) {
                ResultSet rs = psBook.getGeneratedKeys();
                if (rs.next()) {
                    int newBookId = rs.getInt(1);
                    if (book.getGenres() != null && !book.getGenres().isEmpty()) {
                        try (PreparedStatement psCat = con.prepareStatement(sqlCategory)) {
                            psCat.setInt(1, newBookId);
                            psCat.setString(2, book.getGenres().get(0));
                            psCat.executeUpdate();
                        }
                    }
                }
                System.out.println("Thêm sách mới thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public static ArrayList<Book> getNewBooks() throws SQLException,Exception {

        try(Connection con = DBConnection.getConnection();){
            String sql = "SELECT *" +
                    " FROM books" +
                    " ORDER BY created_at DESC" +
                    " LIMIT 4; ";
            PreparedStatement ps = con.prepareStatement(sql);
            ArrayList<Book> new_books = new  ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setPrice(rs.getDouble("price"));
                book.setTitle(rs.getString("title"));
                book.setImageUrl(rs.getString("image"));
                book.setStock(rs.getInt("stock"));
                new_books.add(book);
            }
            if (new_books.isEmpty()) System.out.println("lay sach moi ko thanh cong");
            else System.out.println("lay sach moi thanh cong");
            return new_books;
        }

    }
    public static ArrayList<Book> getBestSellerBooks() throws SQLException,Exception {
        try(Connection con = DBConnection.getConnection();){
            String sql = "SELECT b.*, SUM(od.quantity) AS total_sold " +
                    " FROM books b" +
                    " JOIN order_details od ON b.book_id = od.book_id" +
                    " JOIN orders o ON od.order_id = o.order_id" +
                    " WHERE o.status != 'Cancelled'" +
                    " GROUP BY b.book_id" +
                    " ORDER BY total_sold DESC" +
                    " LIMIT 4;";
            PreparedStatement ps = con.prepareStatement(sql);
            ArrayList<Book> best_seller_books = new  ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setPrice(rs.getDouble("price"));
                book.setTitle(rs.getString("title"));
                book.setImageUrl(rs.getString("image"));
                book.setStock(rs.getInt("stock"));
                best_seller_books.add(book);
            }
            if(best_seller_books.isEmpty()) System.out.println("lay sach ban chay ko thanh cong");
            else System.out.println("lay sach ban chay thanh cong");
            return best_seller_books;
        }

    }
    public static ArrayList<Book> getAllBooks() {
        ArrayList<Book> all_books = new ArrayList<>();
        String sql = "SELECT b.*, a.author_name, GROUP_CONCAT(c.category_name SEPARATOR ', ') AS genre_names " +
                "FROM books b " +
                "LEFT JOIN authors a ON b.author_id = a.author_id " +
                "LEFT JOIN book_categories bc ON b.book_id = bc.book_id " +
                "LEFT JOIN categories c ON bc.category_id = c.category_id " +
                "GROUP BY b.book_id " +
                "ORDER BY b.book_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setPrice(rs.getDouble("price"));
                book.setImageUrl(rs.getString("image"));
                book.setStock(rs.getInt("stock"));
                book.setDescription(rs.getString("description"));
//                book.setRating(4.5);
//                book.setReviewCount(120);
                book.setAuthorName(rs.getString("author_name"));
                String genresStr = rs.getString("genre_names");
                ArrayList<String> genreList = new ArrayList<>();
                if (genresStr != null && !genresStr.isEmpty()) {
                    String[] gArray = genresStr.split(", ");
                    for (String g : gArray) {
                        genreList.add(g);
                    }
                }
                book.setGenres(genreList);

                all_books.add(book);
            }
            System.out.println("Lấy toàn bộ sách (kèm Tác giả & Danh mục) thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi lấy toàn bộ sách: " + e.getMessage());
        }
        return all_books;
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
                book.setStock(rs.getInt("stock"));
                search_results.add(book);
            }
            return search_results;
        }
    }
    // lọc sách theo tác giả
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
//                book.setRating(4.5);
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
                book.setStock(rs.getInt("stock"));
                list.add(book);
            }
        }
        return list;
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
    public static void increaseQuantity(Connection conn, int bookId, int quantity) {
        String sql =  "UPDATE books SET stock = stock + ? WHERE book_id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, quantity);
            ps.setInt(2, bookId);
            int rs = ps.executeUpdate();
            if(rs>0) System.out.println("update success bookId"+ bookId+  " quantity to" +quantity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book) {
        String sqlBook = "UPDATE books SET title=?, author_id=?, price=?, stock=?, description=?, image=? WHERE book_id=?";
        String sqlDelCat = "DELETE FROM book_categories WHERE book_id=?";
        String sqlAddCat = "INSERT INTO book_categories (book_id, category_id) VALUES (?, (SELECT category_id FROM categories WHERE category_name = ? LIMIT 1))";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement psBook = con.prepareStatement(sqlBook)) {

            psBook.setString(1, book.getTitle());
            psBook.setInt(2, book.getAuthorId());
            psBook.setDouble(3, book.getPrice());
            psBook.setInt(4, book.getStock());
            psBook.setString(5, book.getDescription());
            psBook.setString(6, book.getImageUrl());
            psBook.setInt(7, book.getBookId());

            int rows = psBook.executeUpdate();
            if (rows > 0) {
                if (book.getGenres() != null && !book.getGenres().isEmpty()) {
                    try (PreparedStatement psDel = con.prepareStatement(sqlDelCat);
                         PreparedStatement psAdd = con.prepareStatement(sqlAddCat)) {

                        psDel.setInt(1, book.getBookId());
                        psDel.executeUpdate();

                        psAdd.setInt(1, book.getBookId());
                        psAdd.setString(2, book.getGenres().get(0));
                        psAdd.executeUpdate();
                    }
                }
                System.out.println("Đã UPDATE thành công sách ID: " + book.getBookId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteBook(int bookId) {
        String sqlCat = "DELETE FROM book_categories WHERE book_id=?";
        String sqlBook = "DELETE FROM books WHERE book_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement psCat = con.prepareStatement(sqlCat);
             PreparedStatement psBook = con.prepareStatement(sqlBook)) {

            psCat.setInt(1, bookId);
            psCat.executeUpdate();

            psBook.setInt(1, bookId);
            int rows = psBook.executeUpdate();
            if (rows > 0) {
                System.out.println("Đã xóa thành công sách ID: " + bookId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


