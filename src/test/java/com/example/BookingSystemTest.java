package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BookingSystemTest {

    private BookingSystem bookingSystem;
    private TimeProvider timeProvider;
    private RoomRepository roomRepository;
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        timeProvider = mock(TimeProvider.class);
        bookingSystem = new BookingSystem(timeProvider, roomRepository, notificationService);
        roomRepository = mock(RoomRepository.class);
        notificationService = mock(NotificationService.class);
    }

    @Test
    void bookRoomShouldThrowExceptionWhenStartTimeIsNull() {
        assertThatThrownBy(() ->
                bookingSystem.bookRoom("roomId", null, LocalDateTime.now().plusHours(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bokning kräver giltiga start- och sluttider samt rum-id");
    }
    @Test
    void bookRoomShouldThrowExceptionWhenEndTimeIsNull() {
        assertThatThrownBy(() ->
                bookingSystem.bookRoom("roomId", LocalDateTime.now().plusHours(1), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bokning kräver giltiga start- och sluttider samt rum-id");
    }

    @Test
    void bookRoomShouldThrowExceptionWhenRoomIdIsNull() {
        assertThatThrownBy(() ->
                bookingSystem.bookRoom(null, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bokning kräver giltiga start- och sluttider samt rum-id");
    }

    @Test
    void bookRoomShouldThrowExceptionWhenStartTimeIsInThePast() {
        when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.now());
        assertThatThrownBy(() ->
                bookingSystem.bookRoom("roomId", LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Kan inte boka tid i dåtid");
    }

    @Test
    void bookRoomShouldThrowExceptionWhenEndTimeIsBeforeStartTime() {
        when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.now());
        assertThatThrownBy(() ->
                bookingSystem.bookRoom("roomId", LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Sluttid måste vara efter starttid");
    }


}
