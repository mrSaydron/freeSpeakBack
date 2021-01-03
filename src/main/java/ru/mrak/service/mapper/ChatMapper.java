package ru.mrak.service.mapper;


import ru.mrak.domain.*;
import ru.mrak.service.dto.ChatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chat} and its DTO {@link ChatDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ChatMapper extends EntityMapper<ChatDTO, Chat> {


    @Mapping(target = "removeUser", ignore = true)

    default Chat fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chat chat = new Chat();
        chat.setId(id);
        return chat;
    }
}
