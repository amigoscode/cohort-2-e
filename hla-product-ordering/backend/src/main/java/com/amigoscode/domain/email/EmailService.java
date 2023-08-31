package com.amigoscode.domain.email;

import com.amigoscode.domain.order.Order;
import com.amigoscode.domain.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
public class EmailService {
    private final EmailRepository emailRepository;
    private final EmailSender emailSender;
    private final OrderService orderService;


    public Email findById(Integer id){
        Email email =  emailRepository.findById(id).
                orElseThrow(EmailNotFoundException::new);
        List<Order> orders = orderService.findByEmailId(id);
        email.setOrders(orders.stream().map(Order::getId).toList());
        return email;
    }

    public PageEmail findAll(Pageable pageable) {
        final PageEmail pageEmail = emailRepository.findAll(pageable);
        for (Email email : pageEmail.getEmails()) {
            List<Order> orders = orderService.findByEmailId(email.getId());
            email.setOrders(orders.stream().map(Order::getId).toList());
        }

        return pageEmail;
    }

    public PageEmail findUnsent(Pageable pageable) {
        final PageEmail pageEmail = emailRepository.findUnsent(pageable);
        for (Email email : pageEmail.getEmails()) {
            List<Order> orders = orderService.findByEmailId(email.getId());
            email.setOrders(orders.stream().map(Order::getId).toList());
        }

        return pageEmail;
    }

    public Email save(Email email) {
        Email savedEmail = emailRepository.save(email);
//        savedEmail.setOrders(email.getOrders());
//        markOrdersAsSentByEmail(savedEmail);
        return savedEmail;
    }

    public void send(Email email) {
//        final String currentContent = email.getContent();
//        final List<Integer> ordersWithoutEmailId = email.getOrders()
//                .stream()
//                .filter(id -> orderService.findById(id).getEmailId() == null).toList();
//        email.setOrders(ordersWithoutEmailId);
//        // information needed to order products
//        String orderRelatedContent = email.getOrders().toString();
//        email.setContent(currentContent + " " + orderRelatedContent);

        emailSender.send(email);
        email.setSentAt(ZonedDateTime.now());
        emailRepository.save(email);

    }

//    private void markOrdersAsSentByEmail (Email email) {
//        for (Integer id: email.getOrders()) {
//            Order order = orderService.findById(id);
//            order.setEmailId(email.getId());
//            orderService.update(order);
//        }
//    }

}
