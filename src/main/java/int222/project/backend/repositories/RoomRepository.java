package int222.project.backend.repositories;

import int222.project.backend.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    @Override
    @Query("select r from Room r order by r.roomNo")
    List<Room> findAll();
}
