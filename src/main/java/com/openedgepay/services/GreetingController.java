package com.openedgepay.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * added new rest point for deep linking issue. (this is temporary fix and needs
 * to be roll back after actual one.) .
 * @author M1034465
 *
 */
@RestController
public class GreetingController {
    /**
     * just say success of the request.
     * @return string.
     */
    @RequestMapping("/greeting")
    public final String greeting() {
        return "preliminary message succeeded";
    }
}
