package se.sprinto.hakan.chatapp.dao;

import se.sprinto.hakan.chatapp.model.Message;
import se.sprinto.hakan.chatapp.model.User;
import se.sprinto.hakan.chatapp.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageDatabaseDAO implements MessageDAO{

    private final DatabaseUtil db = DatabaseUtil.getInstance();

    @Override
    public void saveMessage(Message message) {

    }

    @Override
    public List<Message> getMessagesByUserId(int userId) {
        return List.of();
    }

    @Override
    public Message save(Message message){
        String sql = "INSERT INTO messages (user_id, content, created_at) VALUES (?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setInt(1, (int) message.getUser().getId());
            ps.setString(2, message.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(message.getCreatedAt()));

            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("Inserting message failed, no rows affected.");
            try(ResultSet keys = ps.getGeneratedKeys()){
                if (keys.next()){
                    message.setId(keys.getInt(1));
                }
            }
            return message;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> getAll(){
        String sql = "SELECT m.id, m.content, m.created_at, u.id as user_id, u.username" +
                "FROM messages m JOIN users u ON m.user_id = u.id ORDER BY m.created_at ASC";
        List<Message> list = new ArrayList<>();
        try(Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Message m = new Message();
                m.setId(rs.getInt("id"));
                m.setContent(rs.getString("content"));
                Timestamp ts = rs.getTimestamp("created_at");
                m.setCreatedAt(ts != null ? ts.toLocalDateTime() : LocalDateTime.now());

                User u = new User();
                u.setId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                m.setUser(u);

                list.add(m);
            }
            return list;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
