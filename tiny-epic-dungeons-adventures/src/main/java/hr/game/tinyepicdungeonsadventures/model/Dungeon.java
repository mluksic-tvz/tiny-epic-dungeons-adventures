package hr.game.tinyepicdungeonsadventures.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class Dungeon {
    private final List<Room> rooms;
}

