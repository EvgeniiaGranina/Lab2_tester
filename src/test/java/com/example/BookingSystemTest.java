package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookingSystemTest {

    private BookingSystem bookingSystem;
    private TimeProvider timeProvider;
    private RoomRepository roomRepository;
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        bookingSystem = new BookingSystem(timeProvider, roomRepository, notificationService);
    }

    @Test
    void bookRoomShouldThrowExceptionWhenStartTimeIsNull() {
        assertThatThrownBy(() ->
                bookingSystem.bookRoom("roomId", null, LocalDateTime.now().plusHours(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bokning kräver giltiga start- och sluttider samt rum-id");
    }
    @Test
    void bookRoom_shouldThrowException_whenEndTimeIsNull() {
        assertThatThrownBy(() ->
                bookingSystem.bookRoom("roomId", LocalDateTime.now().plusHours(1), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bokning kräver giltiga start- och sluttider samt rum-id");
    }

    @Test
    void bookRoom_shouldThrowException_whenRoomIdIsNull() {
        assertThatThrownBy(() ->
                bookingSystem.bookRoom(null, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bokning kräver giltiga start- och sluttider samt rum-id");
    }
}
