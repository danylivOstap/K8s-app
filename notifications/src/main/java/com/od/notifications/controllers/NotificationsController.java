package com.od.notifications.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.od.notifications.domain.Notification;
import com.od.notifications.services.NotificationsService;

@RestController
@RequiredArgsConstructor
public class NotificationsController {
    private final NotificationsService notificationsService;

    @GetMapping(path="/notifications")
    public Page<Notification> listNotifications(final Pageable pageable) {
        return notificationsService.listNotifications(pageable);
    }
}
