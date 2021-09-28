package org.xr.happy.service.mail;

import javax.mail.MessagingException;

/**
 *
 */
public interface MailService {

    public void postTemplateEmail() throws MessagingException;
}
