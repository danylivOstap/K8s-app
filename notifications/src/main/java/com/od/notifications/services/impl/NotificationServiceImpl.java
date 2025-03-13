package com.od.notifications.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.od.notifications.domain.Notification;
import com.od.notifications.repositories.NotificationRepository;
import com.od.notifications.services.NotificationsService;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationsService {
    private final NotificationRepository notificationRepository;

    @Override
    public Notification save(final Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Page<Notification> listNotifications(final Pageable pageable) {
      return notificationRepository.findAll(pageable);
    }

}
