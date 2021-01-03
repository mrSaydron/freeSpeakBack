package ru.mrak.service.mapper;


import ru.mrak.domain.*;
import ru.mrak.service.dto.MessageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Message} and its DTO {@link MessageDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ChatMapper.class})
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "chat.id", target = "chatId")
    MessageDTO toDto(Message message);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "chatId", target = "chat")
    Message toEntity(MessageDTO messageDTO);

    default Message fromId(Long id) {
        if (id == null) {
            return null;
        }
        Message message = new Message();
        message.setId(id);
        return message;
    }
}
