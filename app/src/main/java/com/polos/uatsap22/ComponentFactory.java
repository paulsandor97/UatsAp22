package com.polos.uatsap22;

import com.polos.uatsap22.service.Admin.AdminService;
import com.polos.uatsap22.service.Admin.AdminServiceImpl;
import com.polos.uatsap22.service.contacts.ContactService;
import com.polos.uatsap22.service.contacts.ContactServiceImpl;
import com.polos.uatsap22.service.login.AuthenticationLogInService;
import com.polos.uatsap22.service.login.AuthenticationLogInServiceImpl;
import com.polos.uatsap22.service.messages.MessageService;
import com.polos.uatsap22.service.messages.MessageServiceImpl;

public class ComponentFactory {

    private final AuthenticationLogInService authenticationLogInService;
    private final ContactService contactService;
    private final MessageService messageService;
    private final AdminService adminService;

    private static ComponentFactory instance;

    public AuthenticationLogInService getAuthenticationLogInService() {
        return authenticationLogInService;
    }

    public ContactService getContactService() {
        return contactService;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public AdminService getAdminService(){
        return adminService;
    }

    public static synchronized ComponentFactory getInstance(){
        if (instance == null){
            instance = new ComponentFactory();


        }
        return instance;
    }

    private ComponentFactory(){
        authenticationLogInService = new AuthenticationLogInServiceImpl();
        contactService = new ContactServiceImpl();
        messageService = new MessageServiceImpl();
        adminService = new AdminServiceImpl();
    }

}
