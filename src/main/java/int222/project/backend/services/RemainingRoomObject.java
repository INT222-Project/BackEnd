package int222.project.backend.services;

public class RemainingRoomObject {
    private int roomTypeId;
    private String bedType;
    private int count;

    public RemainingRoomObject(int roomTypeId, String bedType, int count) {
        this.roomTypeId = roomTypeId;
        this.bedType = bedType;
        this.count = count;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
