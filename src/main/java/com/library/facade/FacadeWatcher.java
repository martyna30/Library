package com.library.facade;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FacadeWatcher {

    private static Logger LOGGER = LoggerFactory.getLogger(FacadeWatcher.class);


    @Before("execution(* com.library.facade.RegistrationFacade.processRegistration(..))")
    public void logEvent(){
        LOGGER.info("Logging the event");
    }

    @Before("execution(* com.library.facade.RentalFacade.processCheckoutBook(..))")
    public void showEvent(){
        LOGGER.info("Logging the event");
    }

}
