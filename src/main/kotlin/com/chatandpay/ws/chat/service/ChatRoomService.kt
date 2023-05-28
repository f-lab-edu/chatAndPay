package com.chatandpay.ws.chat.service

import org.springframework.stereotype.Service
import com.chatandpay.ws.chat.entity.ChatRoom
import com.chatandpay.ws.chat.repository.ChatRoomRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository
) {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    class ChatRoomCreationException(message: String, cause: Throwable) : RuntimeException(message, cause)

    fun findAllRoom(): List<ChatRoom> {
        val re = chatRoomRepository.findAll();
        println(re);
        return re;

    }
    fun findById(id: UUID): ChatRoom? {
        val optionalChatRoom: Optional<ChatRoom> = chatRoomRepository.findById(id)
        println(optionalChatRoom);
        return optionalChatRoom.orElse(null)
    }

    fun createRoom(name: String): ChatRoom {
        try {
            val chatRoom = ChatRoom(name)
            return chatRoomRepository.save(chatRoom)
        } catch (e: Exception) { e.printStackTrace()
            throw ChatRoomCreationException("Failed to create chat room", e)
        }
    }
}