package com.bookshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookshop.model.Book;
import com.bookshop.util.DBConnection;

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
    public static ArrayList<Book> getSuggestedBooks(int bookId, int authorId, int categoryId) {
        ArrayList<Book> list = new ArrayList<>();
        String sqlRandom = "SELECT * FROM books WHERE stock > 0 ORDER BY RAND() LIMIT 6";
        String sqlContent
                = "SELECT DISTINCT b.*, "
                + "  (CASE WHEN bc.category_id = ? THEN 2 ELSE 0 END + "
                + "   CASE WHEN b.author_id = ? THEN 1 ELSE 0 END) AS score "
                + "FROM books b "
                + "LEFT JOIN book_categories bc ON b.book_id = bc.book_id "
                + "WHERE b.book_id != ? AND b.stock > 0 "
                + "  AND (bc.category_id = ? OR b.author_id = ?) "
                + "ORDER BY score DESC, RAND() LIMIT 6";
        try (Connection con = DBConnection.getConnection()) {
            if (bookId == 0 && categoryId == 0 && authorId == 0) {
                try (PreparedStatement ps = con.prepareStatement(sqlRandom); ResultSet rs = ps.executeQuery()) {
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
            } else {
                try (PreparedStatement ps = con.prepareStatement(sqlContent)) {
                    ps.setInt(1, categoryId);
                    ps.setInt(2, authorId);
                    ps.setInt(3, bookId);
                    ps.setInt(4, categoryId);
                    ps.setInt(5, authorId);
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
            }
            System.out.println("Lấy sách gợi ý fallback thành công: " + list.size() + " cuốn");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Book> getCourchasedBooks(List<Integer> viewedBookIds) {
        ArrayList<Book> list = new ArrayList<>();
        if (viewedBookIds == null || viewedBookIds.isEmpty()) {
            return list;
        }

        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < viewedBookIds.size(); i++) {
            placeholders.append("?");
            if (i < viewedBookIds.size() - 1) {
                placeholders.append(",");
            }
        }

        String sql = "SELECT b.book_id, b.title, b.price, b.image, b.stock, COUNT(*) AS co_count "
                + "FROM order_details od1 "
                + "JOIN order_details od2 ON od1.order_id = od2.order_id "
                + "JOIN books b ON b.book_id = od2.book_id "
                + "WHERE od1.book_id IN (" + placeholders + ") "
                + "AND od2.book_id NOT IN (" + placeholders + ") "
                + "AND b.stock > 0 "
                + "GROUP BY b.book_id "
                + "ORDER BY co_count DESC "
                + "LIMIT 10";

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < viewedBookIds.size(); i++) {
                ps.setInt(i + 1, viewedBookIds.get(i));
            }
            for (int i = 0; i < viewedBookIds.size(); i++) {
                ps.setInt(viewedBookIds.size() + i + 1, viewedBookIds.get(i));
            }

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
            System.out.println("Lấy sách co-purchased thành công: " + list.size() + " cuốn");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thực thi SQL tùy ý để lấy danh sách sách
    public static ArrayList<Book> getBooksFromSql(String sql) throws SQLException {
        ArrayList<Book> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            java.sql.ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            while (rs.next()) {
                Book book = new Book();
                for (int i = 1; i <= colCount; i++) {
                    String col = meta.getColumnLabel(i).toLowerCase();
                    switch (col) {
                        case "book_id": book.setBookId(rs.getInt(i)); break;
                        case "title": book.setTitle(rs.getString(i)); break;
                        case "price": book.setPrice(rs.getDouble(i)); break;
                        case "image": book.setImageUrl(rs.getString(i)); break;
                        case "stock": book.setStock(rs.getInt(i)); break;
                        case "author_id": book.setAuthorId(rs.getInt(i)); break;
                        case "description": book.setDescription(rs.getString(i)); break;
                        case "rating": book.setRating(rs.getDouble(i)); break;
                        case "reviewcount": book.setReviewCount(rs.getInt(i)); break;
                        case "author_name": book.setAuthorName(rs.getString(i)); break;
                        default:
                            // Ignore unsupported fields
                            break;
                    }
                }
                list.add(book);
            }
        }
        return list;
    }
        public static ArrayList<Book> getRelatedBooks(int bookId) {
        ArrayList<Book> list = new ArrayList<>();
        String sql = "SELECT b.* FROM books b " +
                     "JOIN book_categories bc ON b.book_id = bc.book_id " +
                     "WHERE bc.category_id IN (SELECT category_id FROM book_categories WHERE book_id = ?) " +
                     "AND b.book_id != ? " +
                     "ORDER BY RAND() LIMIT 4";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, bookId);
            ps.setInt(2, bookId);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int getCategoryIdByBookId(int bookId) {
        String sql = "SELECT category_id FROM book_categories WHERE book_id = ? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("category_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}



