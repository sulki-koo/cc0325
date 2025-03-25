package cookcloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cookcloud.entity.Message;
import cookcloud.repository.MessageRepository;
import cookcloud.service.MessageService;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;

	// 메시지 목록 조회
	public List<Message> getMessages(String memId) {
		return messageRepository.findByMemberMemId(memId);
	}

	// 메시지 읽음 처리
	public void markMessageAsRead(Long messageId) {
		Message message = messageRepository.findById(messageId)
				.orElseThrow(() -> new RuntimeException("Message not found"));
		message.setMessageIsRead("Y");
		messageRepository.save(message);
	}

	// 메시지 삭제
	public void deleteMessage(Long messageId) {
	}

}
