package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BookingSystemTest {

    private BookingSystem bookingSystem;
    private TimeProvider timeProvider;
    private RoomRepository roomRepository;
    private NotificationService notificationService;
    private Room room;

    @BeforeEach
    void setUp() {
        timeProvider = mock(TimeProvider.class);
        roomRepository = mock(RoomRepository.class);
        notificationService = mock(NotificationService.class);
        bookingSystem = new BookingSystem(timeProvider, roomRepository, notificationService);
        room = mock(Room.class);
    }

    @Test
    void bookRoom_shouldThrowException_whenStartTimeIsNull() {
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

    @Test
    void bookRoom_shouldThrowException_whenStartTimeIsInThePast() {
        when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.now());
        assertThatThrownBy(() ->
                bookingSystem.bookRoom("roomId", LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Kan inte boka tid i dåtid");
    }

    @Test
    void bookRoom_shouldThrowException_whenEndTimeIsBeforeStartTime() {
        when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.now());
        assertThatThrownBy(() ->
                bookingSystem.bookRoom("roomId", LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Sluttid måste vara efter starttid");
    }

    @Test
    void bookRoom_shouldThrowException_whenRoomDoesNotExist() {

        when(roomRepository.findById("Room123")).thenReturn(Optional.empty());
        when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.now());
        assertThrows(IllegalArgumentException.class, () ->
                bookingSystem.bookRoom("Room123", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3)));
    }

    @Test
    void bookRoom_shouldReturnFalse_whenRoomIsNotAvailable() {

        when(roomRepository.findById("roomId")).thenReturn(Optional.of(room));
        when(room.isAvailable(any(), any())).thenReturn(false);
        when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.now());

        boolean result = bookingSystem.bookRoom("roomId", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        assertFalse(result);
    }

}
