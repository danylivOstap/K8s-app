package com.od.author_persistence.services;

import com.od.author_persistence.model.Notification;

public interface NotificationService {
    void publishNotification(Notification notification);
}
