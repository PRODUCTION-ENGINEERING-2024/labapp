package ro.unibuc.hello.data;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonFormat;

import ro.unibuc.hello.data.UserEntity;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

public class PostEntity {

    @Id
    public String id;
    public String title;
    public String location;
    public Timestamp dateTime;
    public Integer totalNumberOfPlayers;
    @DBRef
    public List<UserEntity> playersJoined;

    public PostEntity() {

    }

    public PostEntity(String title, String location, LocalDateTime dateTime, Integer totalNumberOfPlayers) {
        this.title = title;
        this.location = location;
        this.dateTime = Timestamp.valueOf(dateTime);
        this.totalNumberOfPlayers = totalNumberOfPlayers;
        this.playersJoined = new ArrayList<UserEntity>();
    }

    public void addUser(UserEntity user) {
        this.playersJoined.add(user);
    }
}