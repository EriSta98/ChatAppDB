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
        String sql = "INSERT INTO messages (user_id, text, created_at) VALUES (?, ?, ?)";

        try(Connection conn = db.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){

            ps.setInt(1, message.getUserId());
            ps.setString(2, message.getText());
            ps.setTimestamp(3, Timestamp.valueOf(message.getTimestamp()));

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()){
                message.setId(keys.getInt(1));
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to save message", e);
        }

    }

    @Override
    public List<Message> getMessagesByUserId(int userId) {
        String sql = "SELECT text, created_at FROM messages WHERE user_id = ? ORDER BY created_at ASC";
        List<Message> list = new ArrayList<>();

        try(Connection conn = db.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    String Text = rs.getString("text");
                    LocalDateTime time = rs.getTimestamp("created_at").toLocalDateTime();
                    list.add(new Message(userId, Text, time));
                }
            }

        } catch (Exception e){
            throw new RuntimeException("Failed to load messages", e);
        }
        return list;
    }

    @Override
    public Message save(Message message){
        saveMessage(message);
        return message;
    }

    @Override
    public List<Message> getAll(){
        String sql = "SELECT user_id, text, created_at FROM messages ORDER BY created_at ASC";
        List<Message> list = new ArrayList<>();
        
        
        try(Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String text = rs.getString("text");
                LocalDateTime timestamp = rs.getTimestamp("created_at").toLocalDateTime();
                list.add(new Message(userId, text, timestamp));
            }
            
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return list;
    }
}
