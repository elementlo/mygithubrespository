package net.wash8.classbean;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;

/**
 * Created by ncb-user on 2014/12/20.
 */
public class RequestHeader implements Header {
    @Override
    public String getName() {
        return "Client-Secret";
    }

    @Override
    public String getValue() {
        return "db266d6766a7c79f1defd856caf1d877";
    }

    @Override
    public HeaderElement[] getElements() throws ParseException {
        return new HeaderElement[0];
    }
}
