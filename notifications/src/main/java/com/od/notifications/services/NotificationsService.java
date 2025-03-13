package com.od.notifications.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.od.notifications.domain.Notification;

public interface NotificationsService {
    Notification save(Notification book);

    Page<Notification> listNotifications(Pageable pageable);
}
