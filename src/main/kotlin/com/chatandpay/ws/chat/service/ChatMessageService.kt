package com.chatandpay.ws.chat.service

import com.chatandpay.ws.chat.dtos.ChatMessageDto
import com.chatandpay.ws.chat.entity.ChatGroupMessage
import com.chatandpay.ws.chat.entity.ChatMessage
import com.chatandpay.ws.chat.entity.ChatRoom
import com.chatandpay.ws.chat.repository.ChatGroupMessageRepository
import com.chatandpay.ws.chat.repository.ChatMessageRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatMessageService (
    private val chatMessageRepository: ChatMessageRepository
    private val chatGroupMessageRepository:ChatGroupMessageRepository
    ){

    fun saveMessage(chatMessageDto: ChatMessageDto): Unit {
        println(chatMessageDto);
        if(chatMessageDto.type == ChatMessageDto.Type.COMMENT){
            val chatMessage = ChatMessage(

                message = chatMessageDto.message,
                senderId = chatMessageDto.senderId,
                receiverId = chatMessageDto.recieverId,
                createdAt = chatMessageDto.createdAt
            )
            chatMessageRepository.save(chatMessage);
        }
    }

    fun getChatMessagesBySenderId(chatMessageDto: ChatMessageDto):List<ChatMessageDto> {

        // 채팅방의 모든 채팅 기록 조회
        val allChatMessages = chatMessageRepository.findAllBySenderIdOrderByCreatedAtAsc(UUID.randomUUID());
        // 나중에 현재 유저의 아이디 값으로 변경할 것

        println(allChatMessages);
        if (allChatMessages.isNotEmpty()) {
            return allChatMessages.map {chatMessage -> ChatMessageDto(
                type = ChatMessageDto.Type.ENTER,
                message = chatMessage.message,
                senderName = "test_user",// 🔵 이름은 나중에 유저테이블에서 가져올것
                recieverName = chatMessageDto.recieverName
                )
            } // 🔴 전개연산자를 사용할 수 없는가?
        }

        // 채팅방 기록이 없다면 최초 입장으로 파악 -> 입장했습니다 메시지 반환
        return listOf(ChatMessageDto(
            type = ChatMessageDto.Type.ENTER,
            message = "${chatMessageDto.senderName}님이 입장했습니다.",
            senderName = chatMessageDto.senderName,// 🔵 이름은 나중에 유저테이블에서 가져올것
            recieverName = chatMessageDto.recieverName
            )
        );

    }

    // 그룹챗 저장
    fun createGroupMessage(name: String) {
//        try {
//            val chatRoom = ChatGroupMessage();
//            return chatGroupMessageRepository.save()
//        } catch (e: Exception) { e.printStackTrace()
//            throw ChatRoomService.ChatRoomCreationException("Failed to create chat room", e)
//        }
    }

}