package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemRepository {

    Collection<Item> getItems();

    Item updateItem(Item item);

    Item getItem(Long id);

    void saveItem(Item item);

}
