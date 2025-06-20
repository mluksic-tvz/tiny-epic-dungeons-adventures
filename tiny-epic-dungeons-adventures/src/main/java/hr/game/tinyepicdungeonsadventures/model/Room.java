package hr.game.tinyepicdungeonsadventures.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class Room {
    private final String id;
    private final List<Monster> monsters;
    private final List<Item> items;
}
