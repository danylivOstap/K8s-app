package com.od.book_persistence.service;

import com.od.book_persistence.model.Notification;

public interface NotificationService {
  void publishNotification(Notification notification);
}
