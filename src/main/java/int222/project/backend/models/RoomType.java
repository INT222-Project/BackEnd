package int222.project.backend.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roomtype")
public class RoomType {
    @Id
    private int roomTypeId;
    private String name;
    private String description;
    private int maxRest;
    private String roomSize;

    public RoomType() {
    }

    public RoomType(int roomTypeId, String name, String description, int maxRest, String roomSize) {
        this.roomTypeId = roomTypeId;
        this.name = name;
        this.description = description;
        this.maxRest = maxRest;
        this.roomSize = roomSize;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxRest() {
        return maxRest;
    }

    public void setMaxRest(int maxRest) {
        this.maxRest = maxRest;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }
}
