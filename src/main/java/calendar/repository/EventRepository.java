package calendar.repository;

import calendar.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(
            value = "SELECT * FROM events WHERE events.start_time >= ?1 AND events.end_time <= ?2 ORDER BY events.start_time",
            nativeQuery = true)
    List<Event> findAllBetween(Timestamp startTime, Timestamp endTime);

    @Query(
            value = "SELECT exists(" +
                    "SELECT * FROM events WHERE " +
                    "(events.start_time >= ?1 AND events.start_time <= ?2) OR " +
                    "(events.end_time >= ?1 AND events.end_time <= ?2))",
            nativeQuery = true)
    boolean findAnyBetween(Timestamp startTime, Timestamp endTime);

}
