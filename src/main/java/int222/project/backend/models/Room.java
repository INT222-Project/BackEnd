package int222.project.backend.models;

import javax.persistence.*;

@Entity
@Table(name="room")
public class Room {
    @Id
    private int roomId;
    private String roomNo;
    @ManyToOne
    @JoinColumn(name="roomtypeid")
    private RoomType roomTypeId;
    private double roomCharge;
    private String bedType;

    public Room() {
    }

    public Room(int roomId, String roomNo, RoomType roomTypeId, double roomCharge, String bedType) {
        this.roomId = roomId;
        this.roomNo = roomNo;
        this.roomTypeId = roomTypeId;
        this.roomCharge = roomCharge;
        this.bedType = bedType;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public RoomType getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(RoomType roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public double getRoomCharge() {
        return roomCharge;
    }

    public void setRoomCharge(double roomCharge) {
        this.roomCharge = roomCharge;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }
}
