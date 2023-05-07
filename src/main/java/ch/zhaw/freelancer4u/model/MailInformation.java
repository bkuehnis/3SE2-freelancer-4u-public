package ch.zhaw.freelancer4u.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a response object for the api https://docs.disify.com/
 * 
 * For informations about the properties see: https://docs.disify.com/?java#json-response-parameters
 **/
@Getter
@NoArgsConstructor
public class MailInformation{
    private boolean format;
    private boolean alias;
    private String domain;
    private boolean disposable;
    private boolean dns;
}